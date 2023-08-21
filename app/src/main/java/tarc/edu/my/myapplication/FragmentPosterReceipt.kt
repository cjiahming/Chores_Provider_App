package tarc.edu.my.myapplication

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class FragmentPosterReceipt : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_posterreceipt, container, false)

        val paymentID = view.findViewById<TextView>(R.id.generatePaymentID)
        val paymentDate = view.findViewById<TextView>(R.id.generatePaymentDate)
        val paymentMethod = view.findViewById<TextView>(R.id.generatePaymentMethod)
        val paymentAmount = view.findViewById<TextView>(R.id.generatePaymentAmount)
        val paidBy = view.findViewById<TextView>(R.id.generatePaidBy)
        val choreName = view.findViewById<TextView>(R.id.generateCName)
        val downloadReceiptButton = view.findViewById<Button>(R.id.downloadReceiptBtn)

        //Receive data from bundle
        val args = this.arguments
        val paymentDateF4 = args?.getString("paymentDate")
        val usernameF4 = args?.getString("username")
        val chorePriceF4 = args?.getString("chorePrice")
        val paymentMethodF4 = args?.getString("paymentMethod")
        val cName = args?.getString("choreName")
        val paymentIDF4 = args?.getString("paymentID")

        paymentID.text = paymentIDF4
        paymentDate.text = paymentDateF4.toString()
        paymentMethod.text = paymentMethodF4.toString()
        paymentAmount.text = "RM "+chorePriceF4.toString()
        paidBy.text = usernameF4.toString()
        choreName.text = cName

        downloadReceiptButton.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentPosterReceipt_to_homeFragment)
        }

        return view
    }

}