package tarc.edu.my.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
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

class FragmentCard : Fragment() {

    private lateinit var creditCardButton: RadioButton
    private lateinit var debitCardButton: RadioButton
    private lateinit var cardAmountToPayTV: TextView
    private lateinit var dbRef: DatabaseReference

    //validation
    private lateinit var etCardNum: EditText
    private lateinit var etExp: EditText
    private lateinit var etCVV: EditText
    private lateinit var etCardHolder: EditText

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
        val view = inflater.inflate(R.layout.fragment_card, container, false)
        val cardPayNowButton = view.findViewById<Button>(R.id.cardPayNowBtn)
        val backBtn = view.findViewById<ImageButton>(R.id.backButton)
        creditCardButton = view.findViewById(R.id.creditCardBtn)
        debitCardButton = view.findViewById(R.id.debitCardBtn)
        cardAmountToPayTV = view.findViewById(R.id.cardAmountToPayTextView)
        dbRef = FirebaseDatabase.getInstance().getReference("Payment")

        //validation
        etCardNum = view.findViewById(R.id.cardNumEditText)
        etExp = view.findViewById(R.id.expDateEditText)
        etCVV = view.findViewById(R.id.cvvEditText)
        etCardHolder = view.findViewById(R.id.cardHolderEditText)

        // Retrieve bundle data
        val args = arguments
        if (args != null) {
            usernameF2 = args.getString("username", "")
            chorePriceF2 = args.getString("chorePrice", "")
            paymentMethodF2 = args.getString("paymentMethod", "")
            choreNameF2 = args.getString("choreName", "")
        }

        cardAmountToPayTV.text = chorePriceF2

        cardPayNowButton.setOnClickListener {
            validateCardPayment()
        }

        backBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        return view
    }

    private fun validateCardPayment(){
        val cardNum = etCardNum.text.toString()
        val expDate = etExp.text.toString()
        val cvv = etCVV.text.toString()
        val cardHolder = etCardHolder.text.toString()

        if(cardNum.isEmpty()){
            etCardNum.error = "Please enter your card number"
        }
        else if(expDate.isEmpty()){
            etExp.error = "Please enter your card expiry date"
        }
        else if(cvv.isEmpty()){
            etCVV.error = "Please enter the CVV"
        }
        else if(cardHolder.isEmpty()){
            etCardHolder.error = "Please enter cardholder name"
        }
        else{
            savePaymentData()
        }
    }

    private fun savePaymentData() {
        var paymentMethod = ""

        val paymentID = dbRef.push().key!!
        //To format the date to dd-MM-yyyy
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        val paymentDate = formatter.format(date)
        ///////////////////////////////////////////////

        if(creditCardButton.isChecked){
            paymentMethod = "Credit Card"
        }
        else{
            paymentMethod = "Debit Card"
        }

        val paidBy = usernameF2
        val paidTo = choreNameF2
        val payment = PaymentDataClass(paymentID, paymentDate, paymentMethod, chorePriceF2, paidBy, paidTo)

        val paymentRef = dbRef
        paymentRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val paymentID = "PAYMENT_${dataSnapshot.childrenCount + 1}"
                val payment = PaymentDataClass(
                    paymentID,
                    paymentDate,
                    paymentMethod,
                    chorePriceF2,
                    paidBy,
                    paidTo
                )

                paymentRef.child(paymentID).setValue(payment).addOnSuccessListener {
                    val bundle = Bundle().apply {
                        putString("username", usernameF2)
                        putString("chorePrice", chorePriceF2)
                        putString("paymentMethod", paymentMethod)
                        putString("choreName", choreNameF2)
                        putString("paymentID", paymentID)
                    }
                    findNavController().navigate(R.id.action_fragmentCard_to_fragmentTqPayment, bundle)
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