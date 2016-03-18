package app.test.android.dopa.yuriy.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by yuriy on 18.03.16.
 */
public class MoviesGridAdapter extends BaseAdapter {

    private final static String IMAGE_FORMAT = "w185";
    private ArrayList<MoviesData> movies = new ArrayList<>();
    private Context context;
    final String ImageBaseUrl = "http://image.tmdb.org/t/p/" + IMAGE_FORMAT;


    public MoviesGridAdapter(Context c) {
        context = c;

    }

    private static class MoviesHolder{

        public ImageView image;
        public TextView rating;
    }

    public void addAll(Collection<MoviesData> data){
        movies.addAll(data);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public MoviesData getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String  moviesImage = ImageBaseUrl + getItem(position).getPosterPath();
        String rating = Double.toString(getItem(position).getVoteAverage());
        MoviesHolder viweHolder = null;

        if (convertView == null){
            viweHolder = new MoviesHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_movies, parent, false);
            viweHolder.image = (ImageView)convertView.findViewById(R.id.list_item_icon);
            viweHolder.rating = (TextView)convertView.findViewById(R.id.rating_film_textview);
            convertView.setTag(viweHolder);
        } else {
            viweHolder = (MoviesHolder)convertView.getTag();
        }

        Picasso.with(context)
                .load(moviesImage)
                .into(viweHolder.image);
        viweHolder.image.setAdjustViewBounds(true);

        viweHolder.rating.setText(rating);

        return convertView;
    }

}
