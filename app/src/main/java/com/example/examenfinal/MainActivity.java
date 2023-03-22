package com.example.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    ArrayList<String> Datos = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView listView;
    EditText id ,txtname,  txtemail, txtgender, txtstatus;
    Button btnEnviar;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Button buttonGuardasocio=(Button) findViewById(R.id.btnEnviar);
        buttonGuardasocio.setOnClickListener(l->guardar());
        id = findViewById(R.id.id);
        txtname = findViewById(R.id.txtname);
        txtemail = findViewById(R.id.txtemail);
        txtgender = findViewById(R.id.txtgender);
        txtstatus = findViewById(R.id.txtstatus);
        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datos();
                //enviarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
                actualizar(txtname.getText().toString(), txtemail.getText().toString(), txtgender.getText().toString(), txtstatus.getText().toString());

            }
        });*/


    listView = findViewById(R.id.textView);
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, Datos);

        listView.setAdapter(arrayAdapter);
        obtenerDatos();
    }
    /*private void obtenerDatos() {

        String url = "https://gorest.co.in/public/v2/users";

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    id.setText(jsonObject.getString("id"));
                    txtname.setText(jsonObject.getString("name"));
                    txtemail.setText(jsonObject.getString("email"));
                    txtgender.setText(jsonObject.getString("gender"));
                    txtstatus.setText(jsonObject.getString("status"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }*/
    private void obtenerDatos() {
        String url = "https://gorest.co.in/public/v2/users";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //manejo el json
                mmanejarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }



    public void mmanejarJson(JSONArray jsonArray) {


        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                usuario usuario = new usuario();
                usuario.setId(jsonObject.getInt("id"));
                usuario.setName(jsonObject.getString("name"));
                usuario.setEmail(jsonObject.getString("email"));
                usuario.setGender(jsonObject.getString("gender"));
                usuario.setStatus(jsonObject.getString("status"));


                //almacenamos en arraylist los datos dl json
                Datos.add("ID: "+usuario.getId()+" Nombre: "+usuario.getName()+" Correo: "+usuario.getEmail());
                Datos.add("Genero: "+usuario.getGender()+" Status: "+usuario.getStatus());


            } catch (JSONException jsonException) {
                jsonException.getMessage();
            }

            arrayAdapter.notifyDataSetChanged();
        }
    }
}
