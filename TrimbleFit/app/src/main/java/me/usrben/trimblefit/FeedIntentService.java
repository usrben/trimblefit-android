package me.usrben.trimblefit;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class FeedIntentService extends IntentService {
    private static final String ACTION_UPDATE_FEED = "me.usrben.trimblefit.action.UPDATE_FEED";
    private static final String ACTION_LOAD_FEED = "me.usrben.trimblefit.action.LOAD_FEED";
    private static final String ACTION_STORE_FEED = "me.usrben.trimblefit.action.STORE_FEED";

    private static final String EXTRA_FEED_NAME = "me.usrben.trimblefit.extra.FEED_NAME";

    private static final String TAG = "FeedIntentService";
    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdate(Context context, String feedName) {
        Intent intent = new Intent(context, FeedIntentService.class);
        intent.setAction(ACTION_UPDATE_FEED);
        intent.putExtra(EXTRA_FEED_NAME, feedName);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionLoad(Context context, String feedName) {
        Intent intent = new Intent(context, FeedIntentService.class);
        intent.setAction(ACTION_LOAD_FEED);
        intent.putExtra(EXTRA_FEED_NAME, feedName);
        context.startService(intent);
    }

    public static void startActionStore(Context context, String feedName) {
        Intent intent = new Intent(context, FeedIntentService.class);
        intent.setAction(ACTION_STORE_FEED);
        intent.putExtra(EXTRA_FEED_NAME, feedName);
        context.startService(intent);
    }

    public FeedIntentService() {
        super("FeedIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_FEED.equals(action)) {
                final String feedName = intent.getStringExtra(EXTRA_FEED_NAME);
                handleActionUpdate(feedName);
            } else if (ACTION_LOAD_FEED.equals(action)) {
                final String feedName = intent.getStringExtra(EXTRA_FEED_NAME);
                handleActionLoad(feedName);
            } else if (ACTION_STORE_FEED.equals(action)) {
                final String feedName = intent.getStringExtra(EXTRA_FEED_NAME);
                handleActionStore(feedName);
            }
        }
    }

    /**
     * Handle action Update in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdate(String feedName) {
        try {
            CachedRSSFeed feed = new CachedRSSFeed("https://sites.google.com/a/trimble.com/trimblefit/home/wod/posts.xml", "wods");
            if (feed.update()) {
                Log.v(TAG, "Updated feed with title: " + feed.getFeed().getTitle());
            }
            else {
                Log.v(TAG, "Failed to update feed");
            }
            feed.store(this);
            Log.v(TAG, "Successfully stored feed");
        }
        catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Handle action Load in the provided background thread with the provided
     * parameters.
     */
    private void handleActionLoad(String feedName) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Load in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStore(String feedName) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
