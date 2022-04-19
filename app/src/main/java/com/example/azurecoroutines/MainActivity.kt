package com.example.azurecoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.coroutines.*
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // запуск функции во внешнем потоке
        GlobalScope.launch (Dispatchers.IO)
        { getTranslation("Hello there", "ru") }

    }
    suspend fun getTranslation(text: String, lang: String) {
        val API_URL = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&"
        val key = "" // записать ключ в переменную
        val POSTData = "[{'Text':'Мама, можно сегодня в школу не ходить?'}]"
        // TODO: 2) создать класс, в котором будете хранить текст для перевода

        val url = URL(API_URL + "to=$lang")
        val conn = url.openConnection() as HttpURLConnection
        conn.apply {
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Ocp-Apim-Subscription-Key", "891c1008d9a84e9c90fbe6e59fd1d62b")
            setRequestProperty("Ocp-Apim-Subscription-Region", "westeurope")

            doOutput = true // setting POST method
            val outStream: OutputStream = getOutputStream()
            outStream.write(POSTData.toByteArray())
            val sc = Scanner(conn.getInputStream())

            while (sc.hasNextLine()) {
                Log.d("mytag", sc.nextLine())
            }

            disconnect()
        } // finished connection
    }
}