package bkm.com.newsapp.data.network

import android.net.Uri
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class NetworkUtils {

    companion object {
        var TAG: String = NetworkUtils.javaClass.simpleName
        var DYNAMIC_TOP_NEWS_URL: String = "https://newsapi.org/v2/top-headlines?"
        var NEWS_BASE_URL: String = DYNAMIC_TOP_NEWS_URL

        var country: String = "us"
        var apiKey: String = "93507bb1709a487f885bd517460ca1ab"
        var COUNTRY_PARAM: String = "country"
        var API_KEY_PARAM: String = "apiKey"

        val getUrl: URL get() = buildUrl()

        fun buildUrl(): URL {
            var newsQueryUri: Uri = Uri.parse(NEWS_BASE_URL).buildUpon()
                    .appendQueryParameter(COUNTRY_PARAM, country)
                    .appendQueryParameter(API_KEY_PARAM, apiKey)
                    .build()

            var newsQueryUrl: URL = URL(newsQueryUri.toString())
            Log.v(TAG, "URL: " + newsQueryUrl)
            return newsQueryUrl
        }

        fun getResponseFromHttpUrl(url: URL): String? {
            var urlConnection = url.openConnection() as HttpURLConnection
            try {

                var inputStream = urlConnection.inputStream

                var scanner = Scanner(inputStream)
                scanner.useDelimiter("\\A")

                var hasInput = scanner.hasNext()
                var response: String? = null
                if (hasInput) {
                    response = scanner.next()
                }
                scanner.close()
                return response
            } finally {
                urlConnection.disconnect()
            }
        }


    }
}
