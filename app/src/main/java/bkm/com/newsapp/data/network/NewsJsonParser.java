package bkm.com.newsapp.data.network;

import androidx.annotation.Nullable;

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
                return false;
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
        JSONObject source = article.getJSONObject("source");
        String id = source.isNull("id") ? "" : source.getString("id");

        String author = article.isNull("author") ? "" : article.getString("author");
        String title = article.getString("title");
        String description = article.getString("description").toString();
        String url = article.getString("url");
        String urlToImage = article.getString("urlToImage");
        String publishedAt = article.getString("publishedAt");
        // "2019-02-12T00:47:00Z"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = format.parse(publishedAt);

        String content = article.getString("content");

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
