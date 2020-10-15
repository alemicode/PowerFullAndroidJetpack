package com.example.powerfulljetpack.util


// typeResponse 0 is success
// typeResp0nse 1 is fail
data class LastApiReponseResult(
    val message : String,
    val typeResponse : Int
)