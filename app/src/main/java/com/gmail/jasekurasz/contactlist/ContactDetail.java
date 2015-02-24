package com.gmail.jasekurasz.contactlist;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class ContactDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            TextView name = (TextView) findViewById(R.id.detailName);
            TextView company = (TextView) findViewById(R.id.detailCompany);
            TextView phone = (TextView) findViewById(R.id.detailNumber);
            TextView birthday = (TextView) findViewById(R.id.detailBirthday);
            String detailURL = intent.getCharSequenceExtra("detailURL").toString();
            name.setText(intent.getCharSequenceExtra("name"));
            company.setText(intent.getCharSequenceExtra("company"));
            phone.setText(intent.getCharSequenceExtra("workNum"));
            birthday.setText(intent.getCharSequenceExtra("birthdate"));
            GetDetails task = new GetDetails(this.getApplicationContext(), detailURL);
            task.execute();
        }
    }

    //Get contact details in AsyncTask to avoid hanging up the UI Thread
    private class GetDetails extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private String detailURL;

        public GetDetails(Context context, String detail) {
            mContext = context;
            detailURL = detail;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //Parse the JSON data and set the text in the new view
        @Override
        protected Void doInBackground(Void... arg0) {
            final ImageView pic = (ImageView) findViewById(R.id.detailPic);
            final ImageView star = (ImageView) findViewById(R.id.star);
            star.setImageResource(R.drawable.star);
            final TextView email = (TextView) findViewById(R.id.detailEmail);
            final TextView loc1 = (TextView)  findViewById(R.id.detailAddress1);
            final TextView loc2 = (TextView)  findViewById(R.id.detailAddress2);
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, detailURL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ImageLoader mImageLoader;
                    try {
                        String picURL = response.getString("largeImageURL");
                        String mail = response.getString("email");
                        JSONObject location = response.getJSONObject("address");
                        String street = location.getString("street");
                        String cityState = location.getString("city") + ", " + location.getString("state");
                        String favorite = response.getString("favorite");
                        if (favorite.equals("false")) {
                            star.setVisibility(View.INVISIBLE);
                        }
                        email.setText(mail);
                        loc1.setText(street);
                        loc2.setText(cityState);
                        mImageLoader = RequestSingleton.getInstance().getImageLoader();
                        mImageLoader.get(picURL, ImageLoader.getImageListener(pic, 0, 0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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


        }
    }
}
