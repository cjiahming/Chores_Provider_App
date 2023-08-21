package tarc.edu.my.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import tarc.edu.my.myapplication.databinding.FragmentAddChoresBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import tarc.edu.my.myapplication.ChoreData.chores
import java.lang.Double


class AddChoresFragment : Fragment() {

    private var _binding:FragmentAddChoresBinding? =null
    private val binding get() = _binding!!

    private lateinit var choresDatabase: DatabaseReference
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var storage : FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance()
        storage= FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddChoresBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.backButtonChores.setOnClickListener {
            findNavController().navigate(R.id.action_addChoresFragment_to_choresFragment)
        }

        binding.postButton.setOnClickListener {
            //fIND ALL BINDING
            val startTime = binding.editTextStartTime.text.toString()
            val endTime = binding.editTextEndTime.text.toString()
            val postOwner = binding.addOwnerEmail.text.toString()
            val choreID = binding.addChoresID.text.toString()

            val desc = binding.choreDescText.text.toString()
            val title = binding.choreTitleText.text.toString()
            val price = binding.priceTextNumber.text.toString()
            val acceptedUser = binding.addAcceptedEmail.text.toString()
            val isComplete = false;
            val review = null;

            val bundle = Bundle()
            bundle.putString("choreName", title)
            bundle.putString("chorePrice", price)
            val fragment1 = FragmentPaymentMethod()

            val validate = validateData(choreID,startTime,endTime,postOwner,desc,title,price,acceptedUser,isComplete,review)

            if(validate) {
                uploadCategory()
                fragment1.arguments = bundle
                findNavController().navigate(R.id.action_addChoresFragment_to_fragmentPaymentMethod, bundle)
            }

        }
    }

    fun validateEmail(email: String): Boolean {
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
        return email.matches(emailRegex)
    }

    fun validate24HourTime(input: String): Boolean {
        // Check if the input string has exactly four characters
        if (input.length != 4) {
            return false
        }

        // Check if the input string consists of digits only
        if (!input.all { it.isDigit() }) {
            return false
        }

        // Extract hours and minutes from the input string
        val hours = input.substring(0, 2).toInt()
        val minutes = input.substring(2, 4).toInt()

        // Check if hours and minutes are within the valid range
        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            return false
        }

        return true
    }

    private fun validateData(choreID:String ,startTime:String ,
                             endTime:String,postOwner:String,desc:String,title:String,
                             price:String,acceptedUser:String,isComplete:Boolean,review:String?):Boolean
    {
        //valid empty
        if(choreID.isEmpty() || acceptedUser.isEmpty() || startTime.isEmpty()||endTime.isEmpty()||postOwner.isEmpty()
            ||desc.isEmpty() || title.isEmpty() || price.isEmpty()){
            Toast.makeText(this.context, "Field cannot be empty", Toast.LENGTH_SHORT).show()

            return false;
        }

        var time = true;
        var priceBool = true;

        try{
            //
            val num = Double.parseDouble(endTime)
            val num2 = Double.parseDouble(startTime)
        }catch(e: NumberFormatException) {
            time = false
        }

        try{
            val price2 = Double.parseDouble(price)
        }catch(e: NumberFormatException) {
            priceBool= false
        }

        if(!time){
            //false
            Toast.makeText(this.context, "Time must be numeric", Toast.LENGTH_SHORT).show()
            return false;
        }

        if(!priceBool){
            Toast.makeText(this.context, "Price must be numeric", Toast.LENGTH_SHORT).show()
            return false;
        }

        if(startTime.length!=4 || endTime.length!=4){
            Toast.makeText(this.context, "Time length is 4 characters", Toast.LENGTH_SHORT).show()
            return false;
        }

        if(!validate24HourTime(startTime)){
            Toast.makeText(this.context, "Start Time is not 24 Hour", Toast.LENGTH_SHORT).show()
            return false;
        }

        if(!validate24HourTime(endTime)){
            Toast.makeText(this.context, "End Time is not 24 Hour", Toast.LENGTH_SHORT).show()
            return false;
        }

        if(validateEmail(acceptedUser)&&validateEmail(postOwner)){
            Toast.makeText(this.context, "Email invalid format", Toast.LENGTH_SHORT).show()
            return false;
        }

        return true;

    }

    private fun uploadChoreData(listData : List<String>){

        var choresID = binding.addChoresID.text.toString()
        val title =  binding.choreTitleText.text.toString()
        val desc =  binding.choreDescText.text.toString()
        val price =  binding.priceTextNumber.text.toString()
        val acceptedEMAILPerson = binding.addAcceptedEmail.text.toString();
        val ownerEmail = binding.addOwnerEmail.text.toString();
        val start =binding.editTextStartTime.text.toString()
        val end  = binding.editTextEndTime.text.toString()




        choresDatabase = FirebaseDatabase.getInstance().getReference("chores")
        //val newChoreRef = choresDatabase.push()
        //val choresID = "C1"
        val chore = chores(
            choresID,
            title,
            desc,
            price,
            acceptedEMAILPerson,
            ownerEmail,
            start,
            end,
            false,
            null
        )
        database.reference.child("Chores")
            .child(choresID.toString()).setValue(chore).addOnSuccessListener {

                //Toast.makeText(this.context,"Successfully Add Chores", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this.context,"Got Some Problem...", Toast.LENGTH_SHORT).show()
            }

    }



    private fun uploadCategory() {
        val selectedCategories = mutableListOf<String>()

        uploadChoreData(selectedCategories)
    }
}