package tarc.edu.my.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

class FragmentPaymentMethod : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_paymentmethod, container, false)
        val maybankButton = view.findViewById<ImageButton>(R.id.maybankBtn)
        val publicBankButton = view.findViewById<ImageButton>(R.id.pbeBtn)
        val cimbButton = view.findViewById<ImageButton>(R.id.cimbBtn)
        val hongLeongButton = view.findViewById<ImageButton>(R.id.hongLeongBtn)
        val bankIslamButton = view.findViewById<ImageButton>(R.id.bankIslamBtn)

        val payCardButton = view.findViewById<ImageButton>(R.id.payCardBtn)

        val tngButton = view.findViewById<ImageButton>(R.id.tngBtn)
        val boostButton = view.findViewById<ImageButton>(R.id.boostBtn)
        val grabButton = view.findViewById<ImageButton>(R.id.grabBtn)

        //Create bundle to pass data
        val bundle1 = Bundle()
        bundle1.putString("username", "xiangling")

        val args = this.arguments
        val chorePriceF1 = args?.getString("chorePrice")
        val choreNameF1 = args?.getString("choreName")
        bundle1.putString("chorePrice", chorePriceF1)
        bundle1.putString("choreName", choreNameF1)

        maybankButton.setOnClickListener {
            bundle1.putString("paymentMethod", "Maybank")
            findNavController().navigate(R.id.action_fragmentPaymentMethod_to_fragmentFPX, bundle1)
        }

        publicBankButton.setOnClickListener {
            bundle1.putString("paymentMethod", "Public Bank")
            findNavController().navigate(R.id.action_fragmentPaymentMethod_to_fragmentFPX, bundle1)
        }

        cimbButton.setOnClickListener {
            bundle1.putString("paymentMethod", "CIMB Bank")
            findNavController().navigate(R.id.action_fragmentPaymentMethod_to_fragmentFPX, bundle1)
        }

        hongLeongButton.setOnClickListener {
            bundle1.putString("paymentMethod", "Hong Leong Bank")
            findNavController().navigate(R.id.action_fragmentPaymentMethod_to_fragmentFPX, bundle1)
        }

        bankIslamButton.setOnClickListener {
            bundle1.putString("paymentMethod", "Bank Islam")
            findNavController().navigate(R.id.action_fragmentPaymentMethod_to_fragmentFPX, bundle1)
        }

        payCardButton.setOnClickListener {
            bundle1.putString("paymentMethod", "Card Payment")
            findNavController().navigate(R.id.action_fragmentPaymentMethod_to_fragmentCard, bundle1)
        }

        tngButton.setOnClickListener {
            bundle1.putString("paymentMethod", "Touch n GO")
            findNavController().navigate(R.id.action_fragmentPaymentMethod_to_fragmentEWallet, bundle1)
        }

        boostButton.setOnClickListener {
            bundle1.putString("paymentMethod", "Boost Pay")
            findNavController().navigate(R.id.action_fragmentPaymentMethod_to_fragmentEWallet, bundle1)
        }

        grabButton.setOnClickListener {
            bundle1.putString("paymentMethod", "Grab Pay")
            findNavController().navigate(R.id.action_fragmentPaymentMethod_to_fragmentEWallet, bundle1)
        }

        return view
    }
}