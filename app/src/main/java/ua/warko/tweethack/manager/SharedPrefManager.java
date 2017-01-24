package ua.warko.tweethack.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import ua.warko.tweethack.interfaces.Manager;
import ua.warko.tweethack.util.CachedValue;

/**
 * Created by Dmitriy Dovbnya on 25.09.2014.
 */
public class SharedPrefManager implements Manager {

    private static final String NAME = "sharedPrefs";
    private static final String API_KEY = "api_key";
    private volatile static SharedPrefManager sInstance;
    private SharedPreferences sp;

    private Set<CachedValue> cachedValues;

    private CachedValue<String> apiKey;

    private SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        CachedValue.initialize(sp);
        cachedValues = new HashSet<>();

        cachedValues.add(apiKey = new CachedValue<>(API_KEY, String.class));
    }

    public synchronized static SharedPrefManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPrefManager(context);
        }

        return sInstance;
    }

    public String getApiKey() {
        return apiKey.getValue();
    }

    public void setApiKey(String apiKey) {
        this.apiKey.setValue(apiKey);
    }

    public void clear() {
        for (CachedValue value : cachedValues) {
            value.delete();
        }
    }

}
