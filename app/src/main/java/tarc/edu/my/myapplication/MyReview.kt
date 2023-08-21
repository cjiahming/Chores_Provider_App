package tarc.edu.my.myapplication

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.fragment.app.activityViewModels
import tarc.edu.my.myapplication.databinding.FragmentReviewBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import tarc.edu.my.myapplication.data.ReviewViewModel
import tarc.edu.my.myapplication.databinding.FragmentAddReviewBinding
import tarc.edu.my.myapplication.databinding.FragmentMyReviewBinding


class MyReview : Fragment(), ReviewAdapter.RecordClickListener {


    private var _binding  : FragmentMyReviewBinding?= null
    private val binding get() = _binding!!

    private lateinit var myReviewViewModel: ReviewViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyReviewBinding.inflate(inflater, container, false)
        myReviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recycle view
        val adapter = ReviewAdapter(this)
        val recyclerView = binding.recycleview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_myReview_to_reviewFragment)
        }
        // ReviewViewModel
        myReviewViewModel.readAllReview.observe(viewLifecycleOwner, Observer { review ->
            adapter.setData(review)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onRecordClickListener(index: Int) {
        val bundle1 =Bundle()
        val fragment2 =ViewReview()
        bundle1.putString("index",index.toString())
        fragment2.arguments =bundle1
        myReviewViewModel.selectedIndex = index
        findNavController().navigate(R.id.action_myReview_to_viewReview, bundle1)
    }

}