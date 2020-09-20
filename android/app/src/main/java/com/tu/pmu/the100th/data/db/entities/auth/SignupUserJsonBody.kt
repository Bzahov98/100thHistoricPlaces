package com.tu.pmu.the100th.data.db.entities.auth


import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class SignupUserJsonBody(
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val nationality: String,
    val password: String
) {
    companion object {
        fun toJson(jsonObj: SignupUserJsonBody): String {
            val gson = Gson()
            val gsonPretty = GsonBuilder().setPrettyPrinting().create()
            val jsonObject: String = gsonPretty.toJson(jsonObj)

            return jsonObject
        }

        fun fromJson(jsonBody: String): SignupUserJsonBody? {
            val gson = Gson()
            val result = gson.fromJson<SignupUserJsonBody>(jsonBody, SignupUserJsonBody::class.java)
            return result
        }

        fun testIt() {
            val fromJsonObj = SignupUserJsonBody.fromJson(
                "{\n" +
                        "  \"dateOfBirth\": \"12/12/1999\",\n" +
                        "  \"email\": \"sadss@asd.com\",\n" +
                        "  \"firstName\": \"bojos\",\n" +
                        "  \"lastName\": \"ssss\",\n" +
                        "  \"nationality\": \"BG\",\n" +
                        "  \"password\": \"12345678\"\n" +
                        "}"
            )
            val tag = "test SignUpJsonBody"
            Log.e(tag, "loginUser: ${fromJsonObj.toString()} ${fromJsonObj?.email}")
            Log.e(
                tag,
                "loginUser: ${SignupUserJsonBody.toJson(fromJsonObj!!)} ${fromJsonObj?.email}"
            )
        }
    }


//    val json = """{"title": "Kotlin Tutorial #1", "author": "bezkoder", "categories" : ["Kotlin","Basic"]}"""
//    val gson = Gson()
//
//    val tutorial_1: Tutorial = gson.fromJson(json, Tutorial::class.java)
//    println("> From JSON String:\n" + tutorial_1)
//
//    val tutorial_2: Tutorial = gson.fromJson(FileReader("tutorial.json"), Tutorial::class.java)
//    /* tutorial.json
//    {
//        "title": "Kotlin Tutorial #2",
//        "author": "bezkoder",
//        "categories": [
//            "Kotlin",
//            "Basic"
//        ],
//        "dummy": "dummy text"
//    }
//    */
//    println("> From JSON File:\n" + tutorial_2)
//    fun toJson(): JsonObject {
//
//    }
}