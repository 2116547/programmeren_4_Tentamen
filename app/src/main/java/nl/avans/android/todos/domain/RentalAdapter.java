package nl.avans.android.todos.domain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nl.avans.android.todos.R;

/**
 * Created by maartje on 29-6-2017.
 */

public class RentalAdapter extends ArrayAdapter<Film> {
    public static final String TAG = RentalAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Film> items;


    private static class ViewHolder {
        TextView title, available;


    }

    public RentalAdapter(Context context, ArrayList<Film> items){
        super(context, R.layout.detail_film_list, items);
        this.mContext = context;
        this.items = items;
        Log.d(TAG, "Consturctor called");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView Called");
        Film item = items.get(position);

        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.detail_film_list, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(R.id.filmdetailTitle);
            viewHolder.available = (TextView) convertView.findViewById(R.id.filmdetailAvailable);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(item.getTitle());


        Integer rental_id = item.getRental_id();
        if (rental_id != 0){
            viewHolder.available.setText("Uitgeleend");
        }else{
            viewHolder.available.setText("Huren");
        }


        return convertView;
    }
}
