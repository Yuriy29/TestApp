package app.test.android.dopa.yuriy.testapp;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yuriy on 18.03.16.
 */
public class MovieService extends IntentService {

    public static final String ACTION_MyUpdate = "com.movies.android.UPDATE";
    public static final String EXTRA_KEY_UPDATE = "EXTRA_UPDATE";

    private final String LOG_TAG = MovieService.class.getSimpleName();

    public MovieService() {
        super("Movies");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJsonStr = null;

        try {
            final String MOVIES_BASE_URL =  "http://api.themoviedb.org/3/discover/movie?";

            Uri uriBuild = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendQueryParameter("api_key", ApplicationConfig.API_KEY)
                    .appendQueryParameter("page", "1")
                    .build();
            URL url = new URL(uriBuild.toString());

            Log.v(LOG_TAG, "Built URI" + uriBuild.toString());
            // Create the request to TMoviesDB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }

            moviesJsonStr = buffer.toString();
            Log.v(LOG_TAG, "Movies JSON String: " + moviesJsonStr);
            Collection<MoviesData> data = getMoviesDataFromJson(moviesJsonStr);
            ArrayList<MoviesData> list = new ArrayList<>(data);


            //send update
            Intent intentUpdate = new Intent();
            intentUpdate.setAction(ACTION_MyUpdate);
            intentUpdate.addCategory(Intent.CATEGORY_DEFAULT);
            intentUpdate.putParcelableArrayListExtra(EXTRA_KEY_UPDATE, list);
            sendBroadcast(intentUpdate);

        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }


    private Collection<MoviesData> getMoviesDataFromJson(String moviesJsonStr)
            throws JSONException {

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray("results");

        List result = new ArrayList<>();

        for(int i = 0; i < moviesArray.length(); i++) {
            JSONObject movie = moviesArray.getJSONObject(i);
            result.add(MoviesData.getObjectFromJson(movie));
        }

        return result;

    }
}

