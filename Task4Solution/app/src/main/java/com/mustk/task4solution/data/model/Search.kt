package com.mustk.task4solution.data.model

import com.google.gson.annotations.SerializedName

data class Search (
    @SerializedName("Search") val search : List<Movie>,
    val totalResults : String?,
) : BaseResponse()