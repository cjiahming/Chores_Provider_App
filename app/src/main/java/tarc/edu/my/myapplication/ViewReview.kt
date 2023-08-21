package tarc.edu.my.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tarc.edu.my.myapplication.data.ReviewViewModel
import tarc.edu.my.myapplication.databinding.FragmentViewReviewBinding


class ViewReview : Fragment() {

    private var _binding: FragmentViewReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var myReviewViewModel: ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewReviewBinding.inflate(inflater, container, false)
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_viewReview_to_myReview)
        }
        myReviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

        val args = this.arguments
        val position = args?.getString("index").toString().toInt()

        myReviewViewModel.getReviewInfo(position + 1).observe(viewLifecycleOwner) { review ->
            if (review != null) {
                binding.textViewTitle.text = review.title
                binding.textViewDesc.text = review.desc
                binding.textViewAcceptance.text = review.acceptance
                binding.textViewRating.text = review.rate
                binding.reviewRating.rating =review.rate.toString().toFloat()
                binding.textViewComment.text = review.comment
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}