package tarc.edu.my.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import tarc.edu.my.myapplication.databinding.ActivityMainBinding
import tarc.edu.my.myapplication.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import tarc.edu.my.myapplication.UserData.User

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textAld.setOnClickListener{
            startActivity(Intent(this,Login::class.java))
        }

        binding.button3.setOnClickListener {
            validateData()
        }
    }

    private fun validateData(){

        val username = binding.editUsername.text.toString()
        val ic = binding.editIc.text.toString()
        val age = binding.editAge.text.toString()
        val phone = binding.editTextPhone.text.toString()
        val email = binding.editTextTextEmailAddress.text.toString()
        val address = binding.editTextTextPostalAddress.text.toString()
        val bank=binding.spinnerBank.selectedItem.toString()
        val acc = binding.editAccount.text.toString()
        val pw = binding.editPass.text.toString()
        val conPw = binding.editConfirm.text.toString()
        //val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$".toRegex()

        if(username.isEmpty()){
            binding.editUsername.error="Username Field Required"
        }else if(ic.isEmpty()){
            binding.editIc.error="IC Number Field Required"
        } else if(ic.length > 12){
            binding.editIc.error="Invalid IC Number"
        }else if(ic.length < 12) {
            binding.editIc.error = "Invalid IC Number"
        }else if(age.isEmpty()){
            binding.editAge.error="Age Field Required"
        }else if(age <= 0.toString()){
            binding.editAge.error="Invalid Age"
        }else if(phone.isEmpty()){
            binding.editTextPhone.error="Phone Number Field Required"
        }else if(email.isEmpty()){
            binding.editTextTextEmailAddress.error="Email Address Field Required"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editTextTextEmailAddress.error="Invalid Email Address"
        }else if (address.isEmpty()){
            binding.editTextTextPostalAddress.error="Address Field Required"
        }else if(acc.isEmpty()){
            binding.editAccount.error="Account Field Required"
        }else if(pw.isEmpty()){
            binding.editPass.error="Password Field Required"
        }else if(pw.length < 8){
            binding.editPass.error="Password Must At least 8 Characters"
        }else if(pw != conPw){
            binding.editConfirm.error="Confirmed Password Not Match With Password"
        } else{
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp()
    {
        val username = binding.editUsername.text.toString()
        val ic = binding.editIc.text.toString()
        val age = binding.editAge.text.toString()
        val phone = binding.editTextPhone.text.toString()
        val email = binding.editTextTextEmailAddress.text.toString()
        val address = binding.editTextTextPostalAddress.text.toString()
        val bank=binding.spinnerBank.selectedItem.toString()
        val acc = binding.editAccount.text.toString()
        val pw = binding.editPass.text.toString()
        val conPw = binding.editConfirm.text.toString()

        database = FirebaseDatabase.getInstance().getReference("Users")
        val User = User(username, ic, age, phone, email, address,bank, acc, pw, conPw)
        database.child(username).setValue(User).addOnSuccessListener {
            binding.editUsername.text.clear()
            binding.editIc.text.clear()
            binding.editAge.text.clear()
            binding.editTextPhone.text.clear()
            binding.editTextTextEmailAddress.text.clear()
            binding.editTextTextPostalAddress.text.clear()
            binding.spinnerBank.setSelection(0)
            binding.editAccount.text.clear()
            binding.editPass.text.clear()
            binding.editConfirm.text.clear()

            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, Login::class.java))
        }.addOnFailureListener {
            Toast.makeText(this, "Registered Failed", Toast.LENGTH_SHORT).show()
        }
    }
}