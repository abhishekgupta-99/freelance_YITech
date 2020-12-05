package com.freelance.school_attendance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.freelance.school_attendance.HelperClass.RecyclerViewAdapter;
import com.freelance.school_attendance.HelperClass.Student_Item_Card;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;


public class Student_CRUD extends Activity {
    HamButton.Builder build=new HamButton.Builder();
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    CRUD crud_operation;

    private  DatabaseHelper dbh;
    EditText studentname;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<Student_Item_Card> student_list;
    public static Context context;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__crud);
        context = getApplicationContext();
        dbh=new DatabaseHelper(Student_CRUD.this);
        mRecyclerview();
        updateUI();
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
         tv=findViewById(R.id.text);
//        bmb.setButtonEnum(ButtonEnum.Ham);
//        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_1);
//        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_3_3);
//        for (int i = 0; i < bmb.getButtonPlaceEnum().buttonNumber(); i++) {
//            bmb.addBuilder(new HamButton.Builder()
//                    .normalImageRes(R.drawable.clock));
//        }
        addbuilders(bmb);

        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                // If you have implement listeners for boom-buttons in builders,
                // then you shouldn't add any listener here for duplicate callbacks.
               // Toast.makeText(getApplicationContext(), index, Toast.LENGTH_SHORT).show();
//                tv.setText(index+"");
                crud_operation=new CRUD();
                if(index==0)
                {
                  crud_operation.show_Add_Student_Dialog(Student_CRUD.this);

                }
                else if(index==1)

                {

                }
                else if(index==2)
                {

                }
            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });



    }

        private void addbuilders (BoomMenuButton bmb){

            HamButton.Builder add = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_plus)
                    .containsSubText(false)
                    .shadowEffect(true)
                    .imagePadding(new Rect(10, 10, 10, 10))
                    .shadowRadius(Util.dp2px(20))
                    .normalTextRes(R.string.add);
            //   .subNormalTextRes("Little butter Doesn't fly, either");
            bmb.addBuilder(add);


            HamButton.Builder delete = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_x_button)
                    .imagePadding(new Rect(10, 10, 10, 10))
                    .normalTextRes(R.string.delete)
                    .shadowRadius(Util.dp2px(20))
                    .shadowEffect(true)
                    .containsSubText(false);
            //   .subNormalTextRes("Little butter Doesn't fly, either");
            bmb.addBuilder(delete);

            HamButton.Builder update = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_update)
                    .shadowEffect(true)
                    .imagePadding(new Rect(10, 10, 10, 10))
                    .shadowRadius(Util.dp2px(20))
                    .containsSubText(false)
                    .normalTextRes(R.string.update);
            //   .subNormalTextRes("Little butter Doesn't fly, either");
            bmb.addBuilder(update);


        }




    private void mRecyclerview() {

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    public void updateUI() {
        Toast.makeText(context, "Entered UI", Toast.LENGTH_SHORT).show();
        student_list=dbh.getAllElements();
        mAdapter = new RecyclerViewAdapter(student_list);
        mRecyclerView.setAdapter(mAdapter);

    }
    }
