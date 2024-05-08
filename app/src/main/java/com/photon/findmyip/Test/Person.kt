package com.photon.findmyip.Test

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

data class Person(private val name: String){

    fun calculateTaxes() : java.math.BigDecimal? = TODO("waiting for something")

}

typealias IntPredicate2 = (i: Int) -> Boolean
typealias IntPredicate1 = (i: Int) -> Boolean
fun test(predicate1: IntPredicate1) = predicate1.invoke(1)



fun main() {
     val p1 = Person("Rahul");
    val p2 = Person("Rahul");
    p1.calculateTaxes()

    val predicate2 : IntPredicate2 = {i -> i%2 == 0}
    val pred1 : IntPredicate1 = {j -> j%2 == 0}
//    test { pred1 }

    System.out.println(p1.hashCode())
    System.out.println(p2.hashCode())

    val URL = "https://jsonmock.hackerrank.com/api/articles?"

    @Throws(IOException::class)
    fun getArticlesTitles(author: String): Array<String> {
        val titles: MutableList<String> = ArrayList()
        var page = 1
        var totalPages = 1
        var response: String?
        while (page <= totalPages) {
            val obj = URL("$URL&author=$author&page=$page")
            val con = obj.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            val `in` = BufferedReader(InputStreamReader(con.inputStream))
            while (`in`.readLine().also { response = it } != null) {
                print(response)
                val jsonResponse = Gson().fromJson(
                    response,
                    JsonObject::class.java
                )
                totalPages = jsonResponse["total_pages"].asInt
                val data = jsonResponse.getAsJsonArray("data")
                for (e in data) {
                    val title = e.asJsonObject["title"].asString
                    val story_title = e.asJsonObject["story_title"].asString
                    if (!title.isEmpty() ) {
                        titles.add(title)
                    } else if (!story_title.isEmpty()) {
                        titles.add(story_title)
                    }
                }
            }
            page++
        }
        return titles.toTypedArray()
    }

    /*
 * Helper function that converts an object of type T -> JSON string.
 */
//    private inline fun <T> toJson(model: T): String = Gson().toJson(model)

    /*
    * Helper function that converts a JSON string -> to an object of type T.
    */
//    private inline fun <reified T> fromJson(json: String): T = Gson().fromJson(json, T::class.java)
}

