package tarc.edu.my.myapplication

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import tarc.edu.my.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import tarc.edu.my.myapplication.UserData.User


class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        binding.textAld.setOnClickListener{
            startActivity(Intent(this,Register::class.java))
        }

        binding.buttonLogin.setOnClickListener {
            validateData()
        }

        binding.textForgot.setOnClickListener {
            startActivity(Intent(this,ForgotPassword::class.java))
        }
    }



    private fun validateData(){
        val email = binding.editEmail.text.toString()
        val pw = binding.editTextTextPassword.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //provide invalid email format
            binding.editEmail.error = "Invalid email format"
        }else if (TextUtils.isEmpty(pw)){
            //password is blank
            binding.editTextTextPassword.error = "Please enter password"
        }else if(pw.length < 8){
            binding.editTextTextPassword.error = "Password must at least 8 characters long"
        }else{
            //data is validated, begin login
            signIn()
        }
    }

    private fun signIn() {
        //val email = "user@example.com"
        val email = binding.editEmail.text.toString()
        val pw = binding.editTextTextPassword.text.toString()

        val database = FirebaseDatabase.getInstance()
        val ref = database.reference

        val query = ref.child("Users").orderByChild("email").equalTo(email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.children.first().getValue(User::class.java)
                    if (user != null) {
                        if (user.pw == pw) {
                            // Login successful
                            Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
                            navigate(email)
                        } else {
                            // Password is incorrect
                            Toast.makeText(applicationContext, "Incorrect password", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // User not found
                    Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors here
                Toast.makeText(applicationContext, "Error retrieving data", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun navigate(email: String){
        startActivity(Intent(this,Navigation::class.java).apply {
            putExtra("email", email)
        })
    }



    // ...
}