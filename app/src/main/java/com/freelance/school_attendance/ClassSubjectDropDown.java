package com.freelance.school_attendance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class ClassSubjectDropDown extends AppCompatActivity {

    private SmartMaterialSpinner subjectspinner;
    TextView sc_name;
    private SmartMaterialSpinner classspinner,teacherspinner;
    private List<String> provinceList;
    FetchDetailsSheet info;
    String selected_class, selected_teacher, selected_subject;
    ArrayList<String> teacherlist = new ArrayList<String>();
    ArrayList<String> subjectlist = new ArrayList<String>();
    ArrayList<String> classlist = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down_menus);

        sc_name=findViewById(R.id.ed_scname);
        CircularProgressIndicator indicator = findViewById(R.id.indicator);


       //  info= new FetchDetailsSheet(this,indicator);
        //info.getItems();
        getIntentExtrasData();
        initSpinner();

      //  getItems();

    }

    private void getIntentExtrasData() {

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("School name");
            teacherlist= (ArrayList<String>) getIntent().getSerializableExtra("Teacherlist");
            subjectlist= (ArrayList<String>) getIntent().getSerializableExtra("Subjectlist");
            classlist= (ArrayList<String>) getIntent().getSerializableExtra("Classlist");

            sc_name.setText(j);
        }
    }


    private void initSpinner() {
        subjectspinner = findViewById(R.id.subjectspinner);
        classspinner = findViewById(R.id.classspinner);
        teacherspinner= findViewById(R.id.teacherspinner);


        provinceList = new ArrayList<>();





        subjectspinner.setItem(subjectlist);
        classspinner.setItem(classlist);
        teacherspinner.setItem(teacherlist);


        //indicator.setVisibility(View.GONE);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.VISIBLE);


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

        confirm_dropdown_selected();

    }

    private void confirm_dropdown_selected() {

        subjectspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                     selected_subject=info.subjectlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ClassSubjectDropDown.this, "Please Select From the Drop Down", Toast.LENGTH_SHORT).show();
            }
        });

        classspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selected_class=info.classlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ClassSubjectDropDown.this, "Please Select From the Drop Down", Toast.LENGTH_SHORT).show();
            }
        });


        Log.d("SELECTEEED",teacherspinner.getSelectedItem()+"");


        teacherspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
               selected_teacher=info.teacherlist.get(position);
                Toast.makeText(ClassSubjectDropDown.this, info.teacherlist.get(position), Toast.LENGTH_SHORT).show();
               Log.d("SELECTEDD", selected_teacher);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ClassSubjectDropDown.this, "Please Select From the Drop Down", Toast.LENGTH_SHORT).show();
            }
        });


        selected_teacher=teacherspinner.getSelectedItem()+"";
        selected_class=classspinner.getSelectedItem()+"";
        selected_subject=subjectspinner.getSelectedItem()+"";


        Intent i =new Intent(this,MainActivity.class);
        i.putExtra("Teacher", selected_teacher);
        i.putExtra("Class", selected_class);
        i.putExtra("Subject", selected_subject);
        startActivity(i);
    }
}