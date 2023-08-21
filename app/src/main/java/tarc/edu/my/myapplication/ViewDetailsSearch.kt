package tarc.edu.my.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import tarc.edu.my.myapplication.databinding.FragmentViewDetailsSearchBinding

class ViewDetailsSearch : Fragment() {

    private var _binding: FragmentViewDetailsSearchBinding? =null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewDetailsSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.backButtonChores.setOnClickListener {
            findNavController().navigate(R.id.action_viewDetailsSearch_to_searchChoresFragment)
        }

    }


}