package tarc.edu.my.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

class FragmentPaymentHistory : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_paymenthistory, container, false)
        val backBtn = view.findViewById<ImageButton>(R.id.backButton)

        backBtn.setOnClickListener{
            findNavController().popBackStack()
        }
        return view
    }

}