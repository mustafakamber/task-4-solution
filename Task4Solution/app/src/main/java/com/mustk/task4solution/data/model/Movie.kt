package com.mustk.task4solution.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("imdbID") val imdbID : String?,
    @SerializedName("Title") val title : String?,
    @SerializedName("Year") val year : String?,
    @SerializedName("Director") val director : String?,
    @SerializedName("Actors") val actors : String?,
    @SerializedName("Country") val country : String?,
    @SerializedName("imdbRating") val imdbRating : String?,
    @SerializedName("Poster") val posterURL : String?
) : Response()