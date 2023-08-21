package tarc.edu.my.myapplication.PaymentData

data class PaymentDataClass(
    var paymentID: String? = null,
    var paymentDate: String? = null,
    var paymentMethod: String? = null,
    var paymentAmount: String? = null,
    var paidBy: String? = null,
    var paidTo: String? = null
)
