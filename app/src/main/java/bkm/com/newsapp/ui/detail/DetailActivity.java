package bkm.com.newsapp.ui.detail;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import bkm.com.newsapp.R;
import bkm.com.newsapp.databinding.DetailActivityBinding;
import bkm.com.newsapp.utilities.Constants;
import bkm.com.newsapp.utilities.NewsWebviewClient;

public class DetailActivity extends AppCompatActivity {

    DetailActivityBinding detailActivityBinding;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        String url = getIntent().getStringExtra(Constants.NEWS_URL);
        detailActivityBinding = DataBindingUtil.setContentView(this, R.layout.detail_activity);

        setSupportActionBar(detailActivityBinding.myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        detailActivityBinding.webView.loadUrl(url);
        detailActivityBinding.webView.setWebViewClient(new NewsWebviewClient());
    }
}
