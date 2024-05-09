package com.logotet.totochecker.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class JsoupClient {
    private suspend fun getDocument() =
        withContext(Dispatchers.IO) {
            Jsoup.connect(BASE_URL).get()
        }

    suspend fun getElements(): Elements? =
        try {
            val document = getDocument()
            document.getElementsByClass(ELEMENT_SIGNATURE)
        } catch (e: Exception) {
            null
        }

    companion object {
        private const val BASE_URL = "https://www.toto.bg/check/6x49"
        private const val ELEMENT_SIGNATURE = "ball-white"
    }
}