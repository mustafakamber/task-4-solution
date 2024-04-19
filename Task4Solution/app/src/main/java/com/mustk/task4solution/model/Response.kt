package com.mustk.task4solution.model

import com.google.gson.annotations.SerializedName

open class Response(
    @SerializedName("Response")
    val response : Boolean = false,
    @SerializedName("Error")
    val error : String? = null,
)