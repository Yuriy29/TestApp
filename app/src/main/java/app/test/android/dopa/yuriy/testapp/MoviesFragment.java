package app.test.android.dopa.yuriy.testapp;

/**
 * Created by yuriy on 18.03.16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import java.util.Collection;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {

    private View rootView;
    private MoviesGridAdapter moviesAdapter;
    private MyBroadcastReceiver myBroadcastReceiver;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        myBroadcastReceiver = new MyBroadcastReceiver();
        //register BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(MovieService.ACTION_MyUpdate);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        moviesAdapter = new MoviesGridAdapter(getContext());

        Intent intent = new Intent(getActivity(), MovieService.class);
        getActivity().startService(intent);

        return rootView;
    }

    private void initGrid(View view) {
        GridView gridView = (GridView)rootView.findViewById(R.id.listview_cinema);

        if (gridView == null) {
            return;
        }

        gridView.setAdapter(moviesAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(myBroadcastReceiver);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Collection<MoviesData> data =intent.getParcelableArrayListExtra("EXTRA_UPDATE");
            moviesAdapter.addAll(data);
            initGrid(rootView);
        }
    }

}
