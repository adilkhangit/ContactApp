package com.example.assignment.adil.projectassignment.network

import com.google.gson.annotations.SerializedName


data class RandomUserResponse(
    @SerializedName("results") val results: List<User>
)

data class User(
    @SerializedName("name") val name: Name,
    @SerializedName("phone") val phone: String
)

data class Name(
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
)