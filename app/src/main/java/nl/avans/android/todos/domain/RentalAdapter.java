package nl.avans.android.todos.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Joep on 28-06-17.
 */

public class RentalAdapter extends ArrayAdapter<Rental> {

    public RentalAdapter(Context context, ArrayList<Rental> rentals){
        super(context, 0, rentals);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Rental rental = getItem(position);

        if (convertView == null) {
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.rentalrow, parent, false);
        }

        //TextView gehuurdeFilm = (TextView)convertView.findViewById(R.id.filmNaamRental);
        //gehuurdeFilm.setText(rental.getFilmTitle());

        //return convertView;
    }
}