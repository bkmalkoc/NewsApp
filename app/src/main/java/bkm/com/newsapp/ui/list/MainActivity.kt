package bkm.com.newsapp.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import bkm.com.newsapp.R
import bkm.com.newsapp.data.database.entities.NewsEntry
import bkm.com.newsapp.databinding.ActivityMainBinding
import bkm.com.newsapp.ui.detail.DetailActivity
import bkm.com.newsapp.utilities.Constants
import bkm.com.newsapp.utilities.InjectorUtils

class MainActivity : AppCompatActivity(), NewsAdapter.NewsAdapterListener {

    lateinit var mViewModel: MainActivityViewModel
    lateinit var mMainBinding: ActivityMainBinding
    lateinit var mNewsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var factory = InjectorUtils.provideMainActivityViewModelFactory(this)
        mViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpUI()
        mViewModel.news.observe(this, Observer { newsEntries -> if (newsEntries != null) populateData(newsEntries) })
    }

    fun setUpUI() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mMainBinding.recyclerviewNews.layoutManager = layoutManager
        mNewsAdapter = NewsAdapter(null, this)
        showLoading()
    }

    override fun onItemClicked(newsEntry: NewsEntry?) {
        Toast.makeText(this, newsEntry?.author, Toast.LENGTH_LONG).show()
        showDetailActivity(newsEntry?.url)
    }

    private fun showLoading() {
        mMainBinding.recyclerviewNews.visibility = View.INVISIBLE
        mMainBinding.loadingIndicator.visibility = View.VISIBLE
    }

    private fun showNewsDataView() {
        mMainBinding.loadingIndicator.visibility = View.INVISIBLE
        mMainBinding.recyclerviewNews.visibility = View.VISIBLE
    }

    private fun populateData(newsEntries: List<NewsEntry>) {
        if (newsEntries.isNullOrEmpty()) {
            return
        }
        mNewsAdapter.swapNews(newsEntries)
        showNewsDataView()
    }

    private fun showDetailActivity(newsUrl: String?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.NEWS_URL, newsUrl)
        startActivity(intent)
    }
}