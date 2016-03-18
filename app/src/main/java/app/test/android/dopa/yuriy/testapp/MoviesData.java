package app.test.android.dopa.yuriy.testapp;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yuriy on 18.03.16.
 */
public class MoviesData implements Parcelable {

    public static final String EXTRA_MOVIE = "app.test.android.dopa.yuriy.testapp.EXTRA_MOVIE";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_VOTE_AVERAGE = "vote_average";
    public static final String KEY_VOTE_COUNT = "vote_count";
    public static final String KEY_RELEASE_DATE = "release_date";
    public static final String KEY_POPULARITY = "popularity";

    private final long id;
    private final String title;
    private final String overview;
    private final String posterPath;
    private final double voteAverage;
    private final long voteCount;
    private final String releaseDate;
    private final int popularity;

    public MoviesData(long id,
                      String title, String overview, String poster_path,
                      double vote_average, long vote_count, String release_date, int popularity) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = poster_path;
        this.voteAverage = vote_average;
        this.voteCount = vote_count;
        this.releaseDate = release_date;
        this.popularity = popularity;
    }

    public MoviesData(Bundle extraData) {
        id = extraData.getLong(KEY_ID);
        title = extraData.getString(KEY_TITLE);
        overview = extraData.getString(KEY_OVERVIEW);
        posterPath = extraData.getString(KEY_POSTER_PATH);
        voteAverage = extraData.getDouble(KEY_VOTE_AVERAGE);
        voteCount = extraData.getLong(KEY_RELEASE_DATE);
        releaseDate = extraData.getString(KEY_RELEASE_DATE);
        popularity = extraData.getInt(KEY_POPULARITY);
    }

    protected MoviesData(Parcel in) {
        id = in.readLong();
        title = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        voteAverage = in.readDouble();
        voteCount = in.readLong();
        releaseDate = in.readString();
        popularity = in.readInt();
    }

    public static final Creator<MoviesData> CREATOR = new Creator<MoviesData>() {
        @Override
        public MoviesData createFromParcel(Parcel in) {
            return new MoviesData(in);
        }

        @Override
        public MoviesData[] newArray(int size) {
            return new MoviesData[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getPopularity() {
        return popularity;
    }

    public Bundle putDataToBondle() {

        Bundle extreDate = new Bundle();

        extreDate.putLong(KEY_ID, id);
        extreDate.putString(KEY_TITLE, title);
        extreDate.putString(KEY_OVERVIEW, overview);
        extreDate.putString(KEY_POSTER_PATH, posterPath);
        extreDate.putDouble(KEY_VOTE_AVERAGE, voteAverage);
        extreDate.putLong(KEY_VOTE_COUNT, voteCount);
        extreDate.putString(KEY_RELEASE_DATE, releaseDate);

        return extreDate;
    }

    public static MoviesData getObjectFromJson(JSONObject obj) throws JSONException {
        return new MoviesData(
                obj.getLong(KEY_ID),
                obj.getString(KEY_TITLE),
                obj.getString(KEY_OVERVIEW),
                obj.getString(KEY_POSTER_PATH),
                obj.getDouble(KEY_VOTE_AVERAGE),
                obj.getLong(KEY_VOTE_COUNT),
                obj.getString(KEY_RELEASE_DATE),
                obj.getInt(KEY_POPULARITY)
        );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeDouble(voteAverage);
        dest.writeLong(voteCount);
        dest.writeString(releaseDate);
        dest.writeInt(popularity);
    }
}

