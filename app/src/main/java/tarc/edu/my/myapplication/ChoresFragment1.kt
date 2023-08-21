package tarc.edu.my.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import tarc.edu.my.myapplication.databinding.FragmentChores1Binding



class ChoresFragment1 : Fragment() {

    private lateinit var btnMyTask:MaterialButton
    private lateinit var btnAddTask:MaterialButton
    private lateinit var btnSearchTask:MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_chores1, container, false)

        btnMyTask = view.findViewById(R.id.myTaskButton)
        btnAddTask=view.findViewById(R.id.addTaskButton)
        btnSearchTask=view.findViewById(R.id.searchTaskButton)

        btnMyTask.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_choresFragment_to_postedTasks)
        }
        btnAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_choresFragment_to_addChoresFragment)
        }

        btnSearchTask.setOnClickListener {
            findNavController().navigate(R.id.action_choresFragment_to_searchChoresFragment)
        }
        return view;
    }




}