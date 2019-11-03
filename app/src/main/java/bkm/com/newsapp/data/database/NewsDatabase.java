package bkm.com.newsapp.data.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import bkm.com.newsapp.data.database.daos.NewsDao;
import bkm.com.newsapp.data.database.entities.NewsEntry;

@Database(entities = {NewsEntry.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();

    private static final String DATABASE_NAME = "news";

    private static final Object LOCK = new Object();
    private static volatile NewsDatabase sInstance;

    public static NewsDatabase getInstance(Context context) {
        if(sInstance == null) {
            synchronized (LOCK) {
                if(sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NewsDatabase.class,
                            NewsDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }
}
