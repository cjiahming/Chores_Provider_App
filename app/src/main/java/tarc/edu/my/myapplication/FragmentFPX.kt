package tarc.edu.my.myapplication

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import tarc.edu.my.myapplication.PaymentData.PaymentDataClass
import java.text.SimpleDateFormat
import java.util.Date

class FragmentFPX : Fragment() {

    private lateinit var fpxPayNowButton: Button
    private lateinit var etFPXAmount: EditText
    private lateinit var dbRef: DatabaseReference

    //Bundle
    var usernameF2 = ""
    var chorePriceF2 = ""
    var paymentMethodF2 = ""
    var choreNameF2 = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fpx, container, false)
        val fpxTotalAmount = view.findViewById<TextView>(R.id.totalAmount_FPX)
        val backBtn = view.findViewById<ImageButton>(R.id.backButton)
        fpxPayNowButton = view.findViewById(R.id.fpxPayNowBtn)
        etFPXAmount = view.findViewById(R.id.editTextFPXAmount)
        dbRef = FirebaseDatabase.getInstance().getReference("Payment")

        // Retrieve bundle data
        val args = arguments
        if (args != null) {
            usernameF2 = args.getString("username", "")
            chorePriceF2 = args.getString("chorePrice", "")
            paymentMethodF2 = args.getString("paymentMethod", "")
            choreNameF2 = args.getString("choreName", "")
        }

        // Set the text view
        fpxTotalAmount.text = "RM "+String.format("%.2f", chorePriceF2.toDouble())

        fpxPayNowButton.setOnClickListener {
            validateFPX()
        }

        // Add the back button click listener
        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }

    private fun validateFPX(){
        val fpxAmount = etFPXAmount.text.toString()

        if(fpxAmount.isEmpty()){
            etFPXAmount.error = "Please enter the amount"
        }
        else if(fpxAmount.toDouble() < chorePriceF2.toString().toDouble()){
            etFPXAmount.error = "Input must match the amount to pay"
        }
        else if(fpxAmount.toDouble() > chorePriceF2.toString().toDouble()){
            etFPXAmount.error = "Input must match the amount to pay"
        }
        else{
            savePaymentData()
        }
    }

    private fun savePaymentData() {
        //To format the date to dd-MM-yyyy
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        val paymentDate = formatter.format(date)
        ///////////////////////////////////////////////

        val paymentMethod = paymentMethodF2
        val fpxAmount = etFPXAmount.text.toString()
        val paidBy = usernameF2
        val paidTo = choreNameF2
        val paymentID = dbRef.push().key!!
        val payment = PaymentDataClass(paymentID, paymentDate, paymentMethod, fpxAmount, paidBy, paidTo)

        val paymentRef = dbRef
        paymentRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val paymentID = "PAYMENT_${dataSnapshot.childrenCount + 1}"
                val payment = PaymentDataClass(
                    paymentID,
                    paymentDate,
                    paymentMethod,
                    fpxAmount,
                    paidBy,
                    paidTo
                )

                paymentRef.child(paymentID).setValue(payment).addOnSuccessListener {
                    val bundle = Bundle().apply {
                        putString("username", usernameF2)
                        putString("chorePrice", chorePriceF2)
                        putString("paymentMethod", paymentMethodF2)
                        putString("choreName", choreNameF2)
                        putString("paymentID", paymentID)
                    }
                    findNavController().navigate(R.id.action_fragmentFPX_to_fragmentTqPayment, bundle)
                }.addOnFailureListener { err ->
                    Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        })
    }
}