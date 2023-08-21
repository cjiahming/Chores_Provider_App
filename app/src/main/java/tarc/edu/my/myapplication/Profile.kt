package tarc.edu.my.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import tarc.edu.my.myapplication.databinding.ActivityLoginBinding
import tarc.edu.my.myapplication.databinding.ActivityRegisterBinding
import tarc.edu.my.myapplication.databinding.FragmentProfileBinding
import tarc.edu.my.myapplication.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.grpc.NameResolver.Args
import tarc.edu.my.myapplication.UserData.User


class Profile : Fragment() {



    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityBinding: ActivityLoginBinding
    private lateinit var database: DatabaseReference
    private lateinit var retrievedUser: User
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        activityBinding = ActivityLoginBinding.inflate(inflater)



        retrieveUserData()
        binding.btnSave.setOnClickListener {
            updateUserProfile()
        }
        binding.btnCancel.setOnClickListener {
            clearFields()
        }
        return binding.root
    }

    private fun clearFields() {
        binding.editIc.setText("")
        binding.editAge.setText("")
        binding.editTextPhone.setText("")
        binding.editTextTextEmailAddress.setText("")
        binding.editUsername.setText("")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding.imgbtnBack.setOnClickListener {
            findNavController().navigate(R.id.action_profile2_to_userFragment)
        }
    }

    private fun retrieveUserData() {


        val currentEmail = requireActivity().intent.getStringExtra("email")
        val database = FirebaseDatabase.getInstance().reference.child("Users")
        // Find the user node based on the username
        val query = database.orderByChild("email").equalTo(currentEmail)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        // Retrieve the user data
                        val user = userSnapshot.getValue(User::class.java)
                        if (user != null) {
                            retrievedUser = user
                            binding.editIc.setText(user.ic)
                            binding.editAge.setText(user.age)
                            binding.editTextPhone.setText(user.phone)
                            binding.editTextTextEmailAddress.setText(user.email)
                            binding.editUsername.setText(user.username)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error: $error")
            }
        })
    }

    // Define the function to update the user profile
    private fun updateUserProfile() {
        val database = FirebaseDatabase.getInstance().reference.child("Users")

        if (binding.editUsername.text.isNullOrEmpty() || binding.editIc.text.isNullOrEmpty() || binding.editAge.text.isNullOrEmpty() || binding.editTextPhone.text.isNullOrEmpty() || binding.editTextTextEmailAddress.text.isNullOrEmpty()) {
            Toast.makeText(activity, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val currentEmail = requireActivity().intent.getStringExtra("email")
        // Find the user node based on the email
        val query = database.orderByChild("email").equalTo(currentEmail)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val newUsername = binding.editUsername.text.toString()
                    val newAdd = binding.editIc.text.toString()
                    val newAge = binding.editAge.text.toString()
                    val newPhone = binding.editTextPhone.text.toString()
                    val newEmail = binding.editTextTextEmailAddress.text.toString()

                    // Update the user data
                    for (userSnapshot in dataSnapshot.children) {
                        userSnapshot.ref.child("username").setValue(newUsername)
                        userSnapshot.ref.child("address").setValue(newAdd)
                        userSnapshot.ref.child("age").setValue(newAge)
                        userSnapshot.ref.child("phone").setValue(newPhone)
                        userSnapshot.ref.child("email").setValue(newEmail)
                    }
                    binding.editIc.text = null
                    binding.editAge.text = null
                    binding.editTextPhone.text = null
                    binding.editTextTextEmailAddress.text = null
                    binding.editUsername.text = null

                    requireActivity().intent.putExtra("email", newEmail)
                    Toast.makeText(activity, "Update Successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.userFragment)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error: $error")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}