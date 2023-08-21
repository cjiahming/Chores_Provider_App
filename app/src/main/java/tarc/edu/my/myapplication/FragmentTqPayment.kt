package tarc.edu.my.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.Date

class FragmentTqPayment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tqpayment, container, false)
        val viewReceiptButton = view.findViewById<Button>(R.id.viewReceiptBtn)

        //Date
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        val paymentDate = formatter.format(date)

        //Retrieve data from bundle
        val args = this.arguments
        val usernameF3 = args?.getString("username").toString()
        val chorePriceF3 = args?.getString("chorePrice").toString()
        val paymentMethodF3 = args?.getString("paymentMethod").toString()
        val choreNameF3 = args?.getString("choreName").toString()
        val paymentIDF3 = args?.getString("paymentID").toString()

        viewReceiptButton.setOnClickListener {
            val bundle3 = Bundle().apply {
                putString("username", usernameF3)
                putString("chorePrice", chorePriceF3)
                putString("paymentMethod", paymentMethodF3)
                putString("paymentDate", paymentDate)
                putString("choreName", choreNameF3)
                putString("paymentID", paymentIDF3)
            }
            findNavController().navigate(R.id.action_fragmentTqPayment_to_fragmentPosterReceipt, bundle3)
        }

        return view
    }
}