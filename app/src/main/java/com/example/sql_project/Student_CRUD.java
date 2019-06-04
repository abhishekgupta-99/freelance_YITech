package com.example.sql_project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Util;


public class Student_CRUD extends Activity {
    HamButton.Builder build=new HamButton.Builder();
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__crud);
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
                tv.setText(index+"");
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
    }
