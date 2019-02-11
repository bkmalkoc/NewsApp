package bkm.com.newsapp.data.network;

import android.support.annotation.Nullable;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import bkm.com.newsapp.data.database.entities.NewsEntry;

final class NewsJsonParser {

    private static final String STATUS_CODE = "status";
    private static boolean hasHttpError(JSONObject newsJson) throws JSONException {
        if(newsJson.has(STATUS_CODE)) {
            String errorCode = newsJson.getString(STATUS_CODE);

            switch (errorCode) {
                case "ok":
                return true;
            }
        }
        return false;
    }

    private static NewsEntry[] fromJson(final JSONObject newsJson) throws JSONException, ParseException {

        JSONArray articles = newsJson.getJSONArray("articles");
        NewsEntry[] newsEntries = new NewsEntry[articles.length()];

        for(int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);
            NewsEntry news = fromJsonOfArticle(article);
            newsEntries[i] = news;
        }
        return newsEntries;
    }

    private static NewsEntry fromJsonOfArticle(final JSONObject article) throws JSONException, ParseException {

        String id = article.getJSONObject("source").getJSONObject("id").toString();

        String author = article.getJSONObject("author").toString();
        String title = article.getJSONObject("title").toString();
        String description = article.getJSONObject("description").toString();
        String url = article.getJSONObject("url").toString();
        String urlToImage = article.getJSONObject("urlToImage").toString();
        String publishedAt = article.getJSONObject("publishedAt").toString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = format.parse(publishedAt);

        String content = article.getJSONObject("content").toString();

        return new NewsEntry(id, author, title, description, url, urlToImage, date, content);


    }

    @Nullable
    NewsResponse parse(final String newsJsonStr) throws JSONException, ParseException {
        JSONObject newsJson = new JSONObject(newsJsonStr);

        if(hasHttpError(newsJson)) {
            return null;
        }

        NewsEntry[] newsEntries = fromJson(newsJson);
        return new NewsResponse(newsEntries);
    }
}
