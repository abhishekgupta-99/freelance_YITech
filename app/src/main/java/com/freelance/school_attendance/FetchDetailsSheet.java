package com.freelance.school_attendance;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.freelance.school_attendance.HelperClass.Student_Item_Card;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class FetchDetailsSheet {

    ArrayList<String> teacherlist = new ArrayList<String>();
    ArrayList<String> subjectlist = new ArrayList<String>();
    ArrayList<String> classlist = new ArrayList<String>();
    String sc_name, sc_SIN, sc_pw;
    Context ctx;
    CircularProgressIndicator indicator;

    public FetchDetailsSheet(Context ctx, CircularProgressIndicator indicator) {
        this.ctx = ctx;
        this.indicator = indicator;
    }

    public void getItems() {


        final RequestQueue queue = Volley.newRequestQueue(ctx);
        //  final String url = "https://script.google.com/macros/s/AKfycbxueYt0iOuJN6iPKJKG35CSKDegfuvQ3ls3yENsaefg2qVqGiS_/exec?action=getItems";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbz938aPu6B_i3RootSMc4JpFKgA09uMhEyUMsK6F9BT0ijJJAAT/exec?action=getItems",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        parseItems(response);


                        Log.d("ANSRESPONSE", response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                }
        ) {
            @Override
            public void deliverError(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Cache.Entry entry = this.getCacheEntry();
                    if (entry != null) {
                        Response<String> response = parseNetworkResponse(new NetworkResponse(HttpURLConnection.HTTP_OK,
                                entry.data, false, 0, entry.allResponseHeaders));
                        Log.d("FAILED CACHE", response.toString());
                        deliverResponse(response.result);
                        return;
                    }
                } else
                    super.deliverError(error);
            }
        };
        queue.add(stringRequest);

    }


    public void parseItems(String jsonResposnce) {
        ArrayList<Student_Item_Card> list = new ArrayList<Student_Item_Card>();


        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            Log.d("jsonobj", jobj.toString());
            JSONArray teacherarray = jobj.getJSONArray("teachers_name");
            JSONArray classarray = jobj.getJSONArray("classes");
            JSONArray subjectarray = jobj.getJSONArray("subjects");
            JSONArray schoolcredentials = jobj.getJSONArray("school_details");

            school_idpw(schoolcredentials);
            create_ArrayList(teacherarray, teacherlist);
            create_ArrayList(classarray, classlist);
            create_ArrayList(subjectarray, subjectlist);

            indicator.setVisibility(View.GONE);

            // initSpinner();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void school_idpw(JSONArray schoolcredentials) throws JSONException {

        if (schoolcredentials != null) {
            for (int i = 0; i < schoolcredentials.length(); i++) {
                // list.add(array.getString(i));
                JSONObject obj = schoolcredentials.getJSONObject(i);
                sc_name = obj.getString("School Name");
                sc_SIN = obj.getString("SIN");
                sc_pw = obj.getString("School Password");

                Log.d("SINNNN", sc_SIN);

            }

        }

    }

    public void create_ArrayList(JSONArray array, ArrayList<String> list) throws JSONException {
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }

        }

    }
}
