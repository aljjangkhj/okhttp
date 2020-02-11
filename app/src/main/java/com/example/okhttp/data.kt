package com.example.okhttp

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

class data {
    @SerializedName("gender")
    private var gender: String = ""
    @SerializedName("name")
    private var name: JSONArray? = null

    @SerializedName("location")
    private var location: JSONArray? = null

    @SerializedName("city")
    private var city: String = ""


    @SerializedName("state")
    private var state: String = ""


    @SerializedName("country")
    private var country: String = ""


    @SerializedName("postcode")
    private var postcode: String = ""


    @SerializedName("coordinates")
    private var coordinates: String = ""

    @SerializedName("timezone")
    private var timezone: String = ""
    @SerializedName("email")
    private var email: String = ""
    @SerializedName("login")
    private var login: JSONArray? = null
    @SerializedName("id")
    private var id: JSONArray? = null
    @SerializedName("picture")
    private var picture: JSONArray? = null
    @SerializedName("nat")
    private var nat: String = ""
    @SerializedName("info")
    private var info: String = ""
}
