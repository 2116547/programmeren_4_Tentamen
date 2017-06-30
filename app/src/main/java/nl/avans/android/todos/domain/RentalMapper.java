package nl.avans.android.todos.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Joep on 28-06-17.
 */

public class RentalMapper {

    public static final String RENTAL_DATA = "rental_date";

    public static ArrayList<Rental> mapRentalList(JSONObject response){

        ArrayList<Rental> result = new ArrayList<>();

        try{
            JSONArray jsonArray = response.getJSONArray("result");

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonProduct = jsonArray.getJSONObject(i);

                // Convert stringdate to Date
                String title = jsonProduct.getString("title");
                String description = jsonProduct.getString("description");
                int releaseYear = jsonProduct.getInt("release_year");
                int length = jsonProduct.getInt("length");
                String rating = jsonProduct.getString("rating");
                String customerFirstName = jsonProduct.getString("first_name");
                int customerId = jsonProduct.getInt("customer_id");
                int filmId = jsonProduct.getInt("film_id");
                int inventoryid = jsonProduct.getInt("inventory_id");



                Rental rental = new Rental();
                rental.setFilmTitle(title);
                rental.setFilmID(filmId);
                rental.setFilmDescription(description);
                rental.setReleaseYear(releaseYear);
                rental.setFilmRating(rating);
                rental.setFilmLength(length);
                rental.setCustomerFistname(customerFirstName);
                rental.setCustomerID(customerId);
                rental.setInventoryID(inventoryid);
                result.add(rental);
            }
        } catch( JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}