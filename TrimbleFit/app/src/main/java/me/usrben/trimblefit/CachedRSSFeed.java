package me.usrben.trimblefit;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
/*
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FetcherException;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;
*/
import nl.matshofman.saxrssreader.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bsutcli on 1/29/2015.
 */
public class CachedRSSFeed {
    private URL mFeedUrl;
    private RssFeed mCachedFeed;
    private String mCacheName;

    private static final String TAG = "CachedRSSFeed";

    public CachedRSSFeed(String feedUrl, String cacheName) throws MalformedURLException {
        mFeedUrl = new URL(feedUrl);
        mCacheName = cacheName;
    }

    public boolean update() {
        try {
            RssFeed feed = RssReader.read(mFeedUrl);

            ArrayList<RssItem> entries = feed.getRssItems();
            ArrayList<RssItem> cachedEntries = mCachedFeed.getRssItems();

            if (cachedEntries.isEmpty() || entries.isEmpty()) {
                mCachedFeed = feed;
            }
            else {
                if (entries.get(0).getPublished().after(cachedEntries.get(0).getPublished())) {
                    mCachedFeed = feed;
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, String.format("Failed to read feed %s - %s", mFeedUrl.toString(), e.toString()));
            return false;
        }

        return true;
    }

    public RssFeed getFeed() {
        return mCachedFeed;
    }

    public void load(Context ctx) throws IOException {
        /*
        BufferedInputStream in = new BufferedInputStream(ctx.openFileInput(mCacheName));
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        */
    }

    public void store(Context ctx) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(mCachedFeed);

        FileOutputStream out = ctx.openFileOutput(mCacheName, Context.MODE_PRIVATE);
        out.write(json.getBytes());
        out.close();
    }
}
