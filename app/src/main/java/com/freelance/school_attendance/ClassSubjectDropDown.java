package com.freelance.school_attendance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.freelance.school_attendance.HelperClass.RecyclerViewAdapter;
import com.freelance.school_attendance.HelperClass.Student_Item_Card;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class ClassSubjectDropDown extends AppCompatActivity {

    private SmartMaterialSpinner subjectspinner;
    private SmartMaterialSpinner classspinner;
    private List<String> provinceList;
    FetchDetailsSheet info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down_menus);

         info= new FetchDetailsSheet();
        info.getItems(this);
        initSpinner();

      //  getItems();

    }


    private void initSpinner() {
        subjectspinner = findViewById(R.id.subjectspinner);
        classspinner = findViewById(R.id.classspinner);


        provinceList = new ArrayList<>();

//        provinceList.add("Kampong Thom");
//        provinceList.add("Kampong Cham");
//        provinceList.add("Kampong Chhnang");
//        provinceList.add("Phnom Penh");
//        provinceList.add("Kandal");
//        provinceList.add("Kampot");






        subjectspinner.setItem(info.subjectlist);
        classspinner.setItem(info.classlist);

        CircularProgressIndicator indicator = findViewById(R.id.indicator);
        indicator.setVisibility(View.GONE);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.VISIBLE);

        subjectspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
               // Toast.makeText(this, provinceList.get(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getItems() {


        final RequestQueue queue = Volley.newRequestQueue(this);
      //  final String url = "https://script.google.com/macros/s/AKfycbxueYt0iOuJN6iPKJKG35CSKDegfuvQ3ls3yENsaefg2qVqGiS_/exec?action=getItems";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbz938aPu6B_i3RootSMc4JpFKgA09uMhEyUMsK6F9BT0ijJJAAT/exec?action=getItems",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        parseItems(response);


                        Log.d("ANSRESPONSE",response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                }
        )
        {
            @Override
            public void deliverError(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Cache.Entry entry = this.getCacheEntry();
                    if(entry != null) {
                        Response<String> response = parseNetworkResponse(new NetworkResponse(HttpURLConnection.HTTP_OK,
                                entry.data, false, 0, entry.allResponseHeaders));
                        Log.d("FAILED CACHE",response.toString());
                        deliverResponse(response.result);
                        return;
                    }
                }
                else
                    super.deliverError(error);
            }
        };
        queue.add(stringRequest);

    }


    private void parseItems(String jsonResposnce) {
        ArrayList<Student_Item_Card> list = new ArrayList<Student_Item_Card>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            Log.d("jsonobj", jobj.toString());
            JSONArray teacherarray = jobj.getJSONArray("teachers_name");
            JSONArray classarray = jobj.getJSONArray("classes");
            JSONArray subjectarray = jobj.getJSONArray("subjects");

//            create_ArrayList(teacherarray,teacherlist);
//            create_ArrayList(classarray,classlist);
//            create_ArrayList(subjectarray,subjectlist);

            initSpinner();

        }catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void create_ArrayList(JSONArray array, ArrayList<String> list) throws JSONException {
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }

        }

    }

    public void markAttendance(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}