package bkm.com.newsapp.data.network;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String DYNAMIC_TOP_NEWS_URL = "https://newsapi.org/v2/top-headlines?";

    private static final String NEWS_BASE_URL = DYNAMIC_TOP_NEWS_URL;

    private static final String country = "us";
    private static final String apiKey = "3a818a4af8524a88b96509a5a3e3ae85";
    private static final String COUNTRY_PARAM = "country";
    private static final String API_KEY_PARAM = "apiKey";


    static URL getUrl() {
        return buildUrl();
    }

    private static URL buildUrl() {
        Uri newsQueryUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(COUNTRY_PARAM, country)
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build();

        try {
            URL newsQueryUrl = new URL(newsQueryUri.toString());
            Log.v(TAG, "URL: " + newsQueryUrl);
            return newsQueryUrl;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

}
