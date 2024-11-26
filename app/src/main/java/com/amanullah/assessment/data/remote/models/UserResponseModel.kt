package com.amanullah.assessment.data.remote.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class UserResponseModel {
    @SerializedName("address")
    var address: Address? = null

    @SerializedName("age")
    var age: Int? = null

    @SerializedName("bank")
    var bank: Bank? = null

    @SerializedName("birthDate")
    var birthDate: String? = null

    @SerializedName("bloodGroup")
    var bloodGroup: String? = null

    @SerializedName("company")
    var company: Company? = null

    @SerializedName("crypto")
    var crypto: Crypto? = null

    @SerializedName("ein")
    var ein: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("eyeColor")
    var eyeColor: String? = null

    @SerializedName("firstName")
    var firstName: String? = null

    @SerializedName("gender")
    var gender: String? = null

    @SerializedName("hair")
    var hair: Hair? = null

    @SerializedName("height")
    var height: Double? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("image")
    var image: String? = null

    @SerializedName("ip")
    var ip: String? = null

    @SerializedName("lastName")
    var lastName: String? = null

    @SerializedName("macAddress")
    var macAddress: String? = null

    @SerializedName("maidenName")
    var maidenName: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("phone")
    var phone: String? = null

    @SerializedName("role")
    var role: String? = null

    @SerializedName("ssn")
    var ssn: String? = null

    @SerializedName("university")
    var university: String? = null

    @SerializedName("userAgent")
    var userAgent: String? = null

    @SerializedName("username")
    var username: String? = null

    @SerializedName("weight")
    var weight: Double? = null

    companion object {
        @Keep
        class Address {
            @SerializedName("address")
            var address: String? = null

            @SerializedName("city")
            var city: String? = null

            @SerializedName("coordinates")
            var coordinates: Coordinates? = null

            @SerializedName("country")
            var country: String? = null

            @SerializedName("postalCode")
            var postalCode: String? = null

            @SerializedName("state")
            var state: String? = null

            @SerializedName("stateCode")
            var stateCode: String? = null

            @Keep
            class Coordinates {
                @SerializedName("lat")
                var lat: Double? = null

                @SerializedName("lng")
                var lng: Double? = null
            }
        }

        @Keep
        class Bank {
            @SerializedName("cardExpire")
            var cardExpire: String? = null

            @SerializedName("cardNumber")
            var cardNumber: String? = null

            @SerializedName("cardType")
            var cardType: String? = null

            @SerializedName("currency")
            var currency: String? = null

            @SerializedName("iban")
            var iban: String? = null
        }

        @Keep
        class Company {
            @SerializedName("address")
            var address: Address? = null

            @SerializedName("department")
            var department: String? = null

            @SerializedName("name")
            var name: String? = null

            @SerializedName("title")
            var title: String? = null

            @Keep
            class Address {
                @SerializedName("address")
                var address: String? = null

                @SerializedName("city")
                var city: String? = null

                @SerializedName("coordinates")
                var coordinates: Coordinates? = null

                @SerializedName("country")
                var country: String? = null

                @SerializedName("postalCode")
                var postalCode: String? = null

                @SerializedName("state")
                var state: String? = null

                @SerializedName("stateCode")
                var stateCode: String? = null

                @Keep
                class Coordinates {
                    @SerializedName("lat")
                    var lat: Double? = null

                    @SerializedName("lng")
                    var lng: Double? = null
                }
            }
        }

        @Keep
        class Crypto {
            @SerializedName("coin")
            var coin: String? = null

            @SerializedName("network")
            var network: String? = null

            @SerializedName("wallet")
            var wallet: String? = null
        }

        @Keep
        class Hair {
            @SerializedName("color")
            var color: String? = null

            @SerializedName("type")
            var type: String? = null
        }
    }
}