package tarc.edu.my.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import tarc.edu.my.myapplication.databinding.FragmentUserBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import tarc.edu.my.myapplication.UserData.User

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding.menuPanel.setOnClickListener {
            findNavController().navigate(R.id.profile2)
        }

        binding.buttonLogout.setOnClickListener{
            val intent = Intent(activity, Login::class.java)
            startActivity(intent)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}