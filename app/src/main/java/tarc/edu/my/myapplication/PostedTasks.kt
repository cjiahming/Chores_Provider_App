package tarc.edu.my.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import tarc.edu.my.myapplication.databinding.FragmentPostedTasksBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Double.parseDouble


class PostedTasks : Fragment() {


    private var _binding: FragmentPostedTasksBinding? =null
    private val binding get() = _binding!!
    var found  = false;

    private lateinit var  choresDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostedTasksBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.backButtonChores.setOnClickListener {
            findNavController().navigate(R.id.action_postedTasks_to_choresFragment)
        }

        binding.searchImageButton.setOnClickListener {
            //get data from the search Text Bar
            val choresID: String = binding.searchText.text.toString();
            if (binding.searchText.text!!.isNotEmpty()) {
                readData(choresID)

            } else {
                Toast.makeText(this.context, "Enter Chore ID", Toast.LENGTH_SHORT).show()
            }

        }

        binding.updateChoreBut.setOnClickListener{
            //check if the field are all occupied and Found

            val startTime = binding.editStartTime2.text.toString()
            val endTime = binding.editEndTime.text.toString()
            val postOwner = binding.editPostOwner.text.toString()
            val choreID = binding.editChoresID.text.toString()

            val desc = binding.editChoresDesc.text.toString()
            val title = binding.editChoresTitle.text.toString()
            val price = binding.editChorePrice.text.toString()
            val acceptedUser = binding.editAcceptedUser.text.toString()
            val isComplete = binding.editChoreIsComplete.text.toString().toBoolean()
            val review = binding.editChoreReview.text.toString()
            val validate = validateData(choreID,startTime,endTime,postOwner,desc,title,price,acceptedUser,isComplete,review)

            if(validate && found==true) {
                //validate pass through
                updateData(
                    choreID,
                    startTime,
                    endTime,
                    postOwner,
                    desc,
                    title,
                    price,
                    acceptedUser,
                    isComplete,
                    review
                )
            }else{
                //Validate fail
            }

        }

        binding.btnFinnish.setOnClickListener{

            val startTime = binding.editStartTime2.text.toString()
            val endTime = binding.editEndTime.text.toString()
            val postOwner = binding.editPostOwner.text.toString()
            val choreID = binding.editChoresID.text.toString()

            val desc = binding.editChoresDesc.text.toString()
            val title = binding.editChoresTitle.text.toString()
            val price = binding.editChorePrice.text.toString()
            val acceptedUser = binding.editAcceptedUser.text.toString()
            val isComplete = binding.editChoreIsComplete.text.toString().toBoolean()
            val review = binding.editChoreReview.text.toString()
            val validate = validateData(choreID,startTime,endTime,postOwner,desc,title,price,acceptedUser,isComplete,review)

            if(found==true && validate){
                //then it will update yes
                completeChore(choreID,startTime,endTime,postOwner,desc,title,price,acceptedUser,isComplete,review)
            }
        }


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
                             price:String,acceptedUser:String,isComplete:Boolean,review:String):Boolean
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
            val num = parseDouble(endTime)
            val num2 = parseDouble(startTime)
        }catch(e: NumberFormatException) {
            time = false
        }

        try{
            val price2 = parseDouble(price)
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

        return true;

    }

    private fun completeChore(choreID:String ,startTime:String ,endTime:String,postOwner:String,desc:String,title:String,price:String,acceptedUser:String,isComplete:Boolean,review:String){
        choresDatabase = FirebaseDatabase.getInstance().getReference("Chores")

        val chore = mapOf<String,Any>(
            "choreID" to choreID,
            "choreTitle" to title,
            "choreDescription" to desc,
            "chorePrice" to price,
            "acceptedEmail" to acceptedUser,
            "ownerEmail" to postOwner,
            "choreStart" to startTime,
            "choreEnd" to endTime,
            "completed" to true,
            "reviewID" to review,
        )

        choresDatabase.child(choreID).updateChildren(chore).addOnSuccessListener {
            //clear all the edit text field
            Toast.makeText(this.context, "Update Completed", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{

            Toast.makeText(this.context, "Failed to Update", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateData(choreID:String ,startTime:String ,endTime:String,postOwner:String,desc:String,title:String,price:String,acceptedUser:String,isComplete:Boolean,review:String){
        choresDatabase = FirebaseDatabase.getInstance().getReference("Chores")

        val chore = mapOf<String,Any>(
            "choreID" to choreID,
            "choreTitle" to title,
            "choreDescription" to desc,
            "chorePrice" to price,
            "acceptedEmail" to acceptedUser,
            "ownerEmail" to postOwner,
            "choreStart" to startTime,
            "choreEnd" to endTime,
            "completed" to isComplete,
            "reviewID" to review,
        )

        choresDatabase.child(choreID).updateChildren(chore).addOnSuccessListener {
            //clear all the edit text field
            Toast.makeText(this.context, "Update Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{

            Toast.makeText(this.context, "Failed to Update", Toast.LENGTH_SHORT).show()
        }

    }

    private fun readData(choresID : String){
        //get into users node
        choresDatabase = FirebaseDatabase.getInstance().getReference("Chores")

        //fetch data snapshot of the particular node
        choresDatabase.child(choresID).get().addOnSuccessListener {

            if(it.exists()){
                //get all the data
                val choreID = it.child("choreID").value
                val cTitle = it.child("choreTitle").value
                val cDesc = it.child("choreDescription").value
                val cPrice = it.child("chorePrice").value
                val cAUser = it.child("acceptedEmail").value
                val owner = it.child("ownerEmail").value
                val choreStart = it.child("choreStart").value
                val choreEnd = it.child("choreEnd").value
                val isComplete = it.child("completed").value //boolean
                val reviewID = it.child("reviewID").value


                binding.editChoresID.text = choreID.toString()
                binding.editChoresDesc.text = cDesc.toString()
                binding.editChoresTitle.text = cTitle.toString()
                binding.editChorePrice.text = cPrice.toString()
                binding.editAcceptedUser.setText(cAUser.toString())
                binding.editPostOwner.text = owner.toString()
                binding.editStartTime2.setText(choreStart.toString());
                binding.editEndTime.setText(choreEnd.toString())
                binding.editChoreIsComplete.text = isComplete.toString()
                binding.editChoreReview.text = reviewID.toString()

                val chorePrice = it.child("chorePrize").value

                Toast.makeText(this.context, "Record Found!", Toast.LENGTH_SHORT).show()
                found=true;

            }else{
                Toast.makeText(this.context, "Chores does not exist", Toast.LENGTH_SHORT).show()
                found=false;
            }
        }.addOnFailureListener{
            Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }






}