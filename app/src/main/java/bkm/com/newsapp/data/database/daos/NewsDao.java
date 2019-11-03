package bkm.com.newsapp.data.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import bkm.com.newsapp.data.database.entities.NewsEntry;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(NewsEntry... news);

    @Query("SELECT * FROM news")
    LiveData<List<NewsEntry>> getTopAndBreakingNews();

    @Query("SELECT count(id) FROM news WHERE publishedAt >= :date")
    int countAllFutureNews(Date date);

    @Query("DELETE FROM news WHERE publishedAt < :date")
    void deleteOldNews(Date date);
}
