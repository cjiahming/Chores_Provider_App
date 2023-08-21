package tarc.edu.my.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import tarc.edu.my.myapplication.databinding.ActivityForgotPasswordBinding
import tarc.edu.my.myapplication.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ForgotPassword : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance().reference

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageArrow.setOnClickListener{
            startActivity(Intent(this,Login::class.java))
        }

        binding.buttonChange.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val phoneNumber = binding.editUsername.text.toString()

        if (phoneNumber.isEmpty()) {
            binding.editUsername.error = "Phone Number is required"
            binding.editUsername.requestFocus()
            return
        }

        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            binding.editUsername.error = "Please enter a valid phone number"
            binding.editUsername.requestFocus()
            return
        }

        database.child("Users").orderByChild("phone").equalTo(phoneNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userId = snapshot.children.first().key
                        val newPassword = generateRandomPassword()

                        // Update the password in the database
                        val updates = HashMap<String, Any>()
                        updates["pw"] = newPassword
                        updates["conPw"] = newPassword
                        database.child("Users").child(userId!!).updateChildren(updates)
                            .addOnSuccessListener {
                                // Send the new password to the user's email
                                sendPasswordResetMessage(phoneNumber, newPassword)
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "Failed to update password: ${e.message}")
                                Toast.makeText(this@ForgotPassword, "Failed to reset password", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this@ForgotPassword, "Phone Number not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Database error: ${error.message}")
                }
            })

    }

    private fun generateRandomPassword(): String {
        // Generate a random 8-character password
        val allowedChars = ('a'..'z') + ('0'..'9')
        return (1..8)
            .map { allowedChars.random() }
            .joinToString("")
    }



    companion object {
        private const val TAG = "ForgotPasswordActivity"
    }


    private fun sendPasswordResetMessage(phoneNumber: String, newPassword: String) {
        val message = "Your new password is: $newPassword"
        val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber, null)).apply {
            putExtra("sms_body", message)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this@ForgotPassword, "No messaging app found", Toast.LENGTH_SHORT).show()
        }
    }
}