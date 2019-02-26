package bkm.com.newsapp.utilities;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsWebviewClient extends WebViewClient {
    public NewsWebviewClient() {
        super();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
