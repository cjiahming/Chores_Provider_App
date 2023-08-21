package tarc.edu.my.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import tarc.edu.my.myapplication.databinding.FragmentSearchChoreRecordBinding


class searchChoreRecordFragment : Fragment() {

    private var _binding: FragmentSearchChoreRecordBinding? =null
    private val binding get() = _binding!!
    private val choreViewModel : ChoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchChoreRecordBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_view_details_search, container, false)

        val tvChoresID = view.findViewById<TextView>(R.id.editChoresID)
        val tvTitle1 = view.findViewById<TextView>(R.id.editChoresTitle)
        val tvDesc = view.findViewById<TextView>(R.id.editChoresDesc)
        val tvPrice = view.findViewById<TextView>(R.id.editChorePrice)
        val tvAccepted = view.findViewById<TextView>(R.id.searchAcceptedUser)
        val tvsearchOwner = view.findViewById<TextView>(R.id.editPostOwner)
        val tvsearchStartTime = view.findViewById<TextView>(R.id.editStartTime)
        val tvEnd = view.findViewById<TextView>(R.id.editChoreEndTime)
        val tvComplete = view.findViewById<TextView>(R.id.editChoreIsComplete)
        val tvReview = view.findViewById<TextView>(R.id.editChoreReview)

        val args = this.arguments

        var choreID = args?.getString("choreID").toString()
        Log.d("TAG","Test")
        var choreTitle = args?.getString("choreTitle").toString()
        var choreDescription = args?.getString("choreDescription").toString()
        var chorePrice = args?.getString("chorePrice").toString()
        var acceptedEmail = args?.getString("acceptedEmail").toString()
        var ownerEmail = args?.getString("ownerEmail").toString()
        var choreStart = args?.getString("choreStart").toString()
        var choreEnd = args?.getString("choreEnd").toString()
        var isCompleted = args?.getString("isCompleted").toBoolean()
        var reviewID = args?.getString("reviewID").toString()

        tvChoresID.text= choreID;
        tvTitle1.text = choreTitle
        tvDesc.text = choreDescription
        tvPrice.text = chorePrice
        tvAccepted.text = acceptedEmail
        tvsearchOwner.text = ownerEmail
        tvsearchStartTime.text = choreStart
        tvEnd.text = choreEnd
        tvComplete.text = isCompleted.toString()
        tvReview.text = reviewID

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


    }




}