package com.example.grabytdata

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object GrabYT {
    private val client = OkHttpClient()

    fun grab(videoID: String, listener: (JSONObject) -> Unit) {

        val testVideoID = "kJQP7kiw5Fk"
        val baseURL = "https://www.youtube.com/watch?v="

        val testURL = "$baseURL$testVideoID"
        val exactURL = "$baseURL$videoID"

        val request = Request.Builder()
            .url(exactURL)
            .build()

        client.newCall(request).enqueue(object : Callback {
            val jsonObject = JSONObject()
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                jsonObject.put("error", e.message)
                listener(jsonObject)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        Log.d(this.javaClass.name, "Unexpcted Error $response")
                        jsonObject.put("error", response)
                        listener(jsonObject)
                        return
                    }

                    val htmlContentInString = response.body!!.string()

                    val videoTitleRegex =
                        Regex("""(?<=<meta name="title" content=")(.*)(?="><meta name="description" content=)""")
                    val videoTitle = videoTitleRegex.find(htmlContentInString)?.value

                    val videoViewCountRegex =
                        Regex("""(?<="viewCount":\{"videoViewCountRenderer":\{"viewCount":\{"simpleText":")(.*)(?= views"\},"shortViewCount")""")
                    val videoViewCount =
                        videoViewCountRegex.find(htmlContentInString)?.value?.replace(",", "")

                    val channelNameRegex =
                        Regex("""(?<=<link itemprop="name" content=")(.*)(?="></span><script )""")
                    val channelName = channelNameRegex.find(htmlContentInString)?.value

                    val subscribersCountRegex =
                        Regex("""(?<= subscribers"\}\},"simpleText":")(.*)(?= subscribers"\},"trackingParams":")""")
                    val subscribersCount = subscribersCountRegex.find(htmlContentInString)?.value

                    jsonObject.put("title", videoTitle)
                    jsonObject.put("views", videoViewCount)
                    jsonObject.put("channel_name", channelName)
                    jsonObject.put("channel_subscribers", subscribersCount)

                    Log.d(this.javaClass.name, jsonObject.toString())

                    listener(jsonObject)
                }
            }
        })
    }
}