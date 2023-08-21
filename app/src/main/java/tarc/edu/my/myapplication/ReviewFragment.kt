package tarc.edu.my.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import tarc.edu.my.myapplication.databinding.FragmentReviewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import tarc.edu.my.myapplication.ChoreAdapter.choreAdapter
import tarc.edu.my.myapplication.ChoreData.chores
import tarc.edu.my.myapplication.data.ReviewViewModel



class ReviewFragment : Fragment() {


    private var _binding:FragmentReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: choreAdapter
    private val choreViewModel : ChoreViewModel by viewModels()
    private lateinit var myTopPostsQuery: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener
    private var choresList1: MutableList<chores> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView: RecyclerView = binding.reviewRecycleView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        binding.myReviewButton2.setOnClickListener {
            findNavController().navigate(R.id.action_reviewFragment_to_myReview)
        }



        // Create an instance of the adapter with an empty mutable list
        var choresAdapter = choreAdapter(mutableListOf())
        recyclerView.adapter = choresAdapter

        // Set up the Firebase query and attach the listener
        val databaseReference = FirebaseDatabase.getInstance().reference
        myTopPostsQuery = databaseReference.child("Chores")

        myTopPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val choresList: MutableList<chores> = mutableListOf()

                for (postSnapshot in dataSnapshot.children) {
                    val chore = postSnapshot.getValue(chores::class.java)
                    chore?.let {
                        if (it.completed) {
                            choresList.add(it)
                        }
                    }
                }

                // Update the adapter with the new data
                choresAdapter.updateData(choresList)
                choresList1 = choresList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        //OnClick (When click the itemView)
        adapter = choresAdapter
        adapter.setOnItemClickListener(object: choreAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                Log.d("TAG", "WORKING" + position)
                //get the position in the list

                if(choresList1.isEmpty()){
                    Log.d("TAG","No Record")
                }

                if (choresList1.isNotEmpty() && position >= 0 && position < choresList1.size) {

                    val localChore = choresList1.get(position)
                    Log.d("TAG","Record Found"+localChore.choreID)

                    //implement bundle
                    val bundle = Bundle()

                    bundle.putString("choreTitle", localChore.choreTitle)
                    bundle.putString("choreDescription", localChore.choreDescription)
                    bundle.putString("choreAcceptedEmail", localChore.acceptedEmail)
                    bundle.putString("choreID", localChore.choreID)

                    val fragment = searchChoreRecordFragment()
                    fragment.arguments = bundle
                    //send to bundle and send to the page
                    findNavController().navigate(R.id.action_reviewFragment_to_addReview, bundle)


                }
            }

        })


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //inding.myReviewButton.




    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Remove the ValueEventListener when the fragment is destroyed or detached
        if (::valueEventListener.isInitialized && ::myTopPostsQuery.isInitialized) {
            myTopPostsQuery.removeEventListener(valueEventListener)
        }
    }




}