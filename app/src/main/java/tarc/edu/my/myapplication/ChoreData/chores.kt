package tarc.edu.my.myapplication.ChoreData

data class chores(
    var choreID :String?=null,
    var choreTitle :String?=null,
    var choreDescription:String?=null,
    var chorePrice :String?=null,
    var acceptedEmail :String?=null,
    var ownerEmail :String?=null,
    var choreStart :String?=null,
    var choreEnd :String?=null,
    var completed:Boolean =false,
    var reviewID :String?
){

    constructor() : this(null, null, null, null, null, null, null, null, false, null)
}