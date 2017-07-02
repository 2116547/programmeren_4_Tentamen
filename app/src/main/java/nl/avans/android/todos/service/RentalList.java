package nl.avans.android.todos.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nl.avans.android.todos.R;
import nl.avans.android.todos.domain.Rental;
import nl.avans.android.todos.domain.RentalAdapter;

public class RentalList extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();
    public final static String RENTALDATA = "RENTALS";


    private ListView listViewRentals;
    private BaseAdapter rentalFilmAdapter;
    private ArrayList<Rental> rentals = new ArrayList<>();
    int customerId;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_list);

        intent = getIntent();

        customerId = (Integer) intent.getSerializableExtra("ID");

        setContentView(R.layout.activity_rental_list);

        listViewRentals = (ListView) findViewById(R.id.rentalList);
        rentalFilmAdapter = new RentalAdapter(this, getLayoutInflater(), rentals);
        listViewRentals.setAdapter(rentalFilmAdapter);

        Log.d(TAG, "Token gevonden - Films ophalen!");
        getRentals();
    }

    //@Override
    public void onRentalsAvailable(ArrayList<Rental> rentalArrayList) {

        Log.i(TAG, "We hebben " + rentals.size() + " items in de lijst");

        rentals.clear();
        for(int i = 0; i < rentalArrayList.size(); i++) {
            rentals.add(rentalArrayList.get(i));
        }
        rentalFilmAdapter.notifyDataSetChanged();
    }



    //@Override
    public void onRentalAvailable(Rental rental) {
        rentals.add(rental);
        rentalFilmAdapter.notifyDataSetChanged();
    }


    //@Override
    public void onRentalsError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void getRentals(){
        RentalRequest request = new RentalRequest(getApplicationContext(), (RentalRequest.RentalListener) this);
        request.handleGetAllRentals(customerId);
    }
}
