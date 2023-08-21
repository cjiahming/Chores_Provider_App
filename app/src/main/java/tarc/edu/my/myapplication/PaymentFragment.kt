package tarc.edu.my.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class PaymentFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_payment, container, false)
        val paymentHistoryButton = view.findViewById<Button>(R.id.paymentHistoryBtn)
        val earningButton = view.findViewById<Button>(R.id.viewEarningsBtn)

        paymentHistoryButton.setOnClickListener{
            findNavController().navigate(R.id.action_paymentFragment_to_fragmentPaymentHistory)
        }

        earningButton.setOnClickListener{
            findNavController().navigate(R.id.action_paymentFragment_to_fragmentViewEarnings)
        }

        return view
    }
}


