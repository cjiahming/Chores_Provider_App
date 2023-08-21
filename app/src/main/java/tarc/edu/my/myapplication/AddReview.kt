package tarc.edu.my.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import tarc.edu.my.myapplication.databinding.FragmentAddReviewBinding


import androidx.lifecycle.ViewModelProvider
import tarc.edu.my.myapplication.data.Review
import tarc.edu.my.myapplication.data.ReviewViewModel

class AddReview : Fragment() {

    private var _binding  : FragmentAddReviewBinding?= null
    private val binding get() = _binding!!

    private lateinit var  reviewDatabase: DatabaseReference
    private lateinit var reviewPhotos: Uri
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var storage : FirebaseStorage

    private lateinit var myReviewViewModel: ReviewViewModel

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
        _binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        val args = this.arguments
        //val hah=args?.getString("choreDesc").toString()
        binding.reviewChoresTitle.text =args?.getString("choreTitle").toString()
        binding.reviewChoresDesc.text =args?.getString("choreDescription").toString()
        binding.reviewChoresEmail.text =args?.getString("choreAcceptedEmail").toString()



        // Initialize myUserViewModel
        val reviewViewModelFactory = ReviewViewModelFactory(requireActivity().application)
        myReviewViewModel = ViewModelProvider(this, reviewViewModelFactory).get(ReviewViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        binding.ratingBar2.setOnRatingBarChangeListener { ratingBar, rating , fromUser ->
            binding.rateChangetextView.text = ratingBar.rating.toString()
        }


        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_addReview_to_reviewFragment)
        }


        binding.AddPhotoButton.setOnClickListener {
            val intent = Intent()
            intent.action=Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,0)

        }

        binding.submitButton.setOnClickListener {

            if(binding.commentMultiLine.text!!.isEmpty()){
                Toast.makeText(this.context, "Give Some Comments", Toast.LENGTH_SHORT).show()
            }else{
                uploadData()
            }

        }

    }

    private fun uploadData() {
        val titleName = binding.reviewChoresTitle.text.toString()
        val reviewChoreDesc = binding.reviewChoresDesc.text.toString()
        val editTextChoreAccepted = binding.reviewChoresEmail.text.toString()
        val commentMultiLine = binding.commentMultiLine.text.toString()
        val ratingBar = binding.ratingBar2.rating.toString()

        //create userWealth object
        val review = Review(0,titleName,reviewChoreDesc,ratingBar,editTextChoreAccepted,commentMultiLine)
        //add data to database
        myReviewViewModel.addReview(review)
        //myReviewViewModel.
        uploadInfoToCloud()
        //redirect back to profile fragment
        Toast.makeText(requireContext(), "Review Added Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.myReview)

    }

    private fun uploadInfoToCloud(){


        val databaseReference = FirebaseDatabase.getInstance().reference
        val newId = generateRandomId()
                reviewDatabase = FirebaseDatabase.getInstance().getReference("Review")

                val review = Review(
                    newId,
                    binding.reviewChoresTitle.text.toString(),
                    binding.reviewChoresDesc.text.toString(),
                    binding.ratingBar2.rating.toString(),
                    binding.reviewChoresEmail.text.toString(),
                    binding.commentMultiLine.text.toString(),

                    )
                database.reference.child("Review")
                    .child(newId.toString()).setValue(review).addOnSuccessListener {

                        Toast.makeText(this.context,"Thank For Your Reviews",Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this.context,"Got Some Problem...",Toast.LENGTH_SHORT).show()
                    }
            }


    private fun generateRandomId(): Int {
        val range = 100000..999999 // Specify the desired range for the random ID
        return range.random()
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null){
            if(data.data !=null){
                reviewPhotos = data.data!!
                binding.ReviewImageView.setImageURI(reviewPhotos)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}