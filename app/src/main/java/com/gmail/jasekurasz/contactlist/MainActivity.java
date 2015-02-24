package com.gmail.jasekurasz.contactlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private String urlJsonArray = "https://solstice.applauncher.com/external/contacts.json";
    private ArrayList<Person> People = new ArrayList<>();
    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomAdapter(this, People);

        //List Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ContactDetail.class);
                intent.putExtra("detailURL", People.get(position).getDetailsURL());
                intent.putExtra("company", People.get(position).getCompany());
                intent.putExtra("workNum", People.get(position).getworkNum());
                intent.putExtra("name", People.get(position).getName());
                intent.putExtra("birthdate", People.get(position).getBirthday());
                startActivity(intent);
            }
        });

        GetContacts task = new GetContacts(this.getApplicationContext());
        task.execute();
    }

    //Get contacts in AsyncTask to avoid hanging up the UI Thread
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        private Context mContext;

        public GetContacts(Context context) {
            mContext = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //Parse the JSON data and create a new Person
        @Override
        protected Void doInBackground(Void... arg0) {
            JsonArrayRequest req = new JsonArrayRequest(urlJsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject contact = (JSONObject) response.get(i);
                        JSONObject phone = contact.getJSONObject("phone");
                        Person p = new Person(contact.getString("name"), contact.getString("company"),
                                contact.getString("detailsURL"), contact.getString("smallImageURL"),
                                contact.getString("birthdate"));
                        if (phone.has("work")) {
                            p.setworkNum(phone.getString("work"));
                        } else p.setworkNum("");
                        if (phone.has("home")) {
                            p.sethomeNum(phone.getString("home"));
                        } else p.sethomeNum("");
                        if (phone.has("mobile")) {
                            p.setmobileNum(phone.getString("mobile"));
                        } else p.setmobileNum("");
                        People.add(p);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });
        RequestSingleton.getInstance(mContext).addToRequestQueue(req);
        return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            listView.setAdapter(adapter);

        }
    }


}
