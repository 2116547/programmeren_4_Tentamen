package nl.avans.android.todos.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import nl.avans.android.todos.R;
import nl.avans.android.todos.service.Config;
import nl.avans.android.todos.service.VolleyRequestQueue;

public class RegisterActivity extends AppCompatActivity {

    EditText voornaamEdit, achternaamEdit, emailEdit, wachtwoordEdit;
    Button registreerButton;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        voornaamEdit = (EditText) findViewById(R.id.voornaamEdit);
        achternaamEdit = (EditText) findViewById(R.id.achternaamEdit);
        emailEdit = (EditText) findViewById(R.id.mailEdit);
        wachtwoordEdit = (EditText) findViewById(R.id.wachtwoordEdit);
        registreerButton = (Button) findViewById(R.id.registerButton);
        registreerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String voornaam, achternaam, email, password;

                voornaam = voornaamEdit.getText().toString();
                achternaam = achternaamEdit.getText().toString();
                email = emailEdit.getText().toString();
                password = wachtwoordEdit.getText().toString();

                handleRegister(voornaam, achternaam, email, password);
            }
        });
    }

    private void handleRegister(String first_name, String last_name, String email, String password) {
        //
        // Maak een JSON object met username en password. Dit object sturen we mee
        // als request body (zoals je ook met Postman hebt gedaan)
        //
        String body = "{\"first_name\":\"" + first_name + "\",\"last_name\":\"" + last_name + "\",\"email\":\"" + email + "\",\"password_user\":\"" + password + "\"}";
        Log.i(TAG, "handleRegister - body = " + body);

        try {
            JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, Config.URL_REGISTER, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response - dat betekent dat we een geldig token hebben.
                            // txtLoginErrorMsg.setText("Response: " + response.toString());
                            displayMessage("Succesvol geregistreerd!");

                            // Start the main activity, and close the login activity
                            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(login);
                            // Close the current activity
                            finish();

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleErrorResponse(error);
                        }
                    });

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            //txtLoginErrorMsg.setText(e.getMessage());
            // e.printStackTrace();
        }
        return;
    }

    public void register(String vn, String an, String em, String pw) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("first_name", vn);
        json.put("last_name", an);
        json.put("email", em);
        json.put("password_user", pw);

        Log.d("registreer", json.toString());

        //post(Config.URL_REGISTER, json.toString());
    }

    public void handleErrorResponse(VolleyError error) {
        Log.e(TAG, "handleErrorResponse");

        if(error instanceof com.android.volley.AuthFailureError) {

            String json = null;
            NetworkResponse response = error.networkResponse;
            if (response != null && response.data != null) {
                json = new String(response.data);
                json = trimMessage(json, "error");
                if (json != null) {
                    json = "Error " + response.statusCode + ": " + json;
                    displayMessage(json);
                }
            } else {
                Log.e(TAG, "handleErrorResponse: kon geen networkResponse vinden.");
            }
        } else if(error instanceof com.android.volley.NoConnectionError) {
            Log.e(TAG, "handleErrorResponse: server was niet bereikbaar");
            //txtLoginErrorMsg.setText(getString(R.string.error_server_offline));
        } else {
            Log.e(TAG, "handleErrorResponse: error = " + error);
        }
    }


    public String trimMessage(String json, String key){
        Log.i(TAG, "trimMessage: json = " + json);
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }

    public void displayMessage(String toastString){
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
    }
}
