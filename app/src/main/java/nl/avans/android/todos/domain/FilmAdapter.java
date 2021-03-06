package nl.avans.android.todos.domain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nl.avans.android.todos.R;

public class FilmAdapter extends BaseAdapter {

    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflator;
    private ArrayList<Film>bolFilmArrayList;

    public FilmAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Film> bolFilmArrayList) {
        this.mContext = context;
        this.mInflator = layoutInflater;
        this.bolFilmArrayList = bolFilmArrayList;
    }

    @Override
    public int getCount() {
        int size = bolFilmArrayList.size();
        Log.i(TAG, "getCount() = " + size);
        return size;
    }

    @Override
    public Object getItem(int position) {
        Log.i(TAG, "getItem() at " + position);
        return bolFilmArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView at " + position);

        ViewHolder viewHolder;

        if(convertView == null){

            Log.i(TAG, "convertView is NULL - nieuwe maken");

            // Koppel de convertView aan de layout van onze eigen row
            convertView = mInflator.inflate(R.layout.list_to_do_row, null);

            // Maak een ViewHolder en koppel de schermvelden aan de velden uit onze eigen row.
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.filmTitle);
        //    viewHolder.textViewYear = (TextView) convertView.findViewById(R.id.filmYear);
            viewHolder.textViewRating = (TextView) convertView.findViewById(R.id.filmRating);
            // viewHolder.textViewContents = (TextView) convertView.findViewById(R.id.rowToDoDate);

            // Sla de viewholder op in de convertView
            convertView.setTag(viewHolder);
        } else {
            Log.i(TAG, "convertView BESTOND AL - hergebruik");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Film bolFilm = bolFilmArrayList.get(position);
        viewHolder.textViewTitle.setText(bolFilm.getTitle());
//        viewHolder.textViewYear.setText(bolFilm.getRelease_year());
        viewHolder.textViewRating.setText(bolFilm.getRating());

        return convertView;
    }

    private static class ViewHolder {
        public TextView textViewTitle, textViewYear, textViewRating;
        // public TextView textViewContents;
    }
}

