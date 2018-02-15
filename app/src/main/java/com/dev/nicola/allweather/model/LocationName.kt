package com.dev.nicola.allweather.model

import com.google.gson.annotations.SerializedName

class LocationName {
    val result: List<Result>? = null

    inner class Result {
        @SerializedName("address_components")
        val addressComponents: List<AddressComponent>? = null
        @SerializedName("formatted_address")
        val formattedAddress: String? = null
    }

    inner class AddressComponent {
        @SerializedName("long_name")
        val longName: String? = null
        @SerializedName("short_name")
        val shortName: String? = null
        @SerializedName("types")
        val types: List<String>? = null
    }
}