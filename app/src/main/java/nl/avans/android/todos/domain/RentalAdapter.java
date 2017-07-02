package nl.avans.android.todos.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nl.avans.android.todos.R;


public class RentalAdapter extends ArrayAdapter<Rental> {


    public RentalAdapter(Context context, ArrayList<nl.avans.android.todos.domain.Rental> rentals) {
        super(context, 0, rentals);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        nl.avans.android.todos.domain.Rental rental = getItem(position);



        if( convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_rental_list, parent, false);
        }

        TextView gehuurdeFilm = (TextView)convertView.findViewById(R.id.filmTitle);
        gehuurdeFilm.setText(rental.getFilmTitle());

        return convertView;
    }
}
