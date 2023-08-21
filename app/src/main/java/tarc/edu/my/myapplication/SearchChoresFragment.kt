package tarc.edu.my.myapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import tarc.edu.my.myapplication.ChoreAdapter.choreAdapter
import tarc.edu.my.myapplication.ChoreData.chores
import tarc.edu.my.myapplication.databinding.FragmentSearchChoresBinding

private lateinit var viewModel: ChoreViewModel
private lateinit var choreRecyclerView: RecyclerView



class SearchChoresFragment : Fragment() {
    lateinit var adapter : choreAdapter
    private var _binding: FragmentSearchChoresBinding? =null
    private val binding get() = _binding!!
    private val choreViewModel : ChoreViewModel by viewModels()
    private lateinit var myTopPostsQuery: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener
    private var choresList1: MutableList<chores> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchChoresBinding.inflate(inflater, container, false)

        // Inflate the fragment's layout
        val view = inflater.inflate(R.layout.fragment_search_chores, container, false)

        // Find the RecyclerView by its ID
        val recyclerView: RecyclerView = view.findViewById(R.id.recycleViewSearch)

        // Set up the RecyclerView layout manager
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

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
                        choresList.add(it)
                    }
                }

                // Update the adapter with the new data
                choresAdapter.updateData(choresList)
                choresList1 = choresList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        //OnClick (When click the itemView)
        adapter = choresAdapter
        adapter.setOnItemClickListener(object: choreAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                Log.d("TAG", "WORKING" + position)


                if(choresList1.isEmpty()){
                    Log.d("TAG","No Record")
                }

                if (choresList1.isNotEmpty() && position >= 0 && position < choresList1.size) {

                    val localChore = choresList1.get(position)
                    Log.d("TAG","Record Found"+localChore.choreID)

                    //implement bundle
                    val bundle = Bundle()
                    bundle.putString("choreID", "Testing123")
                    bundle.putString("choreTitle", localChore.choreTitle)
                    bundle.putString("choreDescription", localChore.choreDescription)
                    bundle.putString("chorePrice", localChore.chorePrice)
                    bundle.putString("acceptedEmail", localChore.acceptedEmail.toString())
                    bundle.putString("ownerEmail", localChore.ownerEmail.toString())
                    bundle.putString("choreStart", localChore.choreStart.toString())
                    bundle.putString("choreEnd", localChore.choreEnd.toString())
                    bundle.putBoolean("completed", localChore.completed)
                    bundle.putString("reviewID", localChore.reviewID.toString())


                    val fragment = searchChoreRecordFragment()
                    fragment.arguments = bundle
                    //send to bundle and send to the page
                    findNavController().navigate(R.id.action_searchChoresFragment_to_viewDetailsSearch, bundle)


                }
            }

        })

        val backButton: AppCompatImageButton = view.findViewById(R.id.backButtonChores)


        backButton.setOnClickListener {
            // Navigate to the specific fragment using the action ID
            findNavController().navigate(R.id.action_searchChoresFragment_to_choresFragment)
        }

        return view

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.backButtonChores.setOnClickListener {
            findNavController().navigate(R.id.action_searchChoresFragment_to_choresFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Remove the ValueEventListener when the fragment is destroyed or detached
        if (::valueEventListener.isInitialized && ::myTopPostsQuery.isInitialized) {
            myTopPostsQuery.removeEventListener(valueEventListener)
        }
    }

    private fun handleChoresList() {
        // Handle the chores list here
        for (chore in choresList1) {
            // Access individual chore properties
            val choreTitle = chore.choreTitle
            val choreDescription = chore.choreDescription
            val chorePrice = chore.chorePrice

            // Display the chore details
            Toast.makeText(
                requireContext(),
                "Title: $choreTitle\nDescription: $choreDescription\nPrice: $chorePrice",
                Toast.LENGTH_SHORT
            ).show()
        }
    }





}


