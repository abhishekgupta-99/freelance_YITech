package com.freelance.school_attendance.HelperClass;
import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.freelance.school_attendance.R;
import com.freelance.school_attendance.Status;

import java.util.ArrayList;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    private ArrayList<Student_Item_Card> mStudentItemCardList;
    private ArrayList<Student_Item_Card> mStudentItemCardListFull;
Context context;
   public ArrayList<Student_Item_Card> absentStudents=new ArrayList<>();
    //Status status=new Status();
    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Context context;
        public TextView student_name;
        public TextView roll_no;
        public TextView lectures_attended;
        //public  TextView status;
        CheckBox checkBox;
        CircularProgressIndicator cp;


        public ViewHolder(View itemView) {
            super(itemView);

            student_name = itemView.findViewById(R.id.student);
            roll_no = itemView.findViewById(R.id.roll_no);
            checkBox= itemView.findViewById(R.id.custom_checkboxx);
            cp=itemView.findViewById(R.id.circular_progress);
            lectures_attended=itemView.findViewById(R.id.lectures_attended);
            //status =itemView.findViewById(R.id.status);
        }
    }

    public RecyclerViewAdapter(ArrayList<Student_Item_Card> exampleList) {
        mStudentItemCardList = exampleList;
        mStudentItemCardListFull = new ArrayList<>(mStudentItemCardList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         context=parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        ViewHolder evh = new ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        int gradientType = CircularProgressIndicator.LINEAR_GRADIENT;
        int endColor = Color.MAGENTA;
        holder.setIsRecyclable(false);
        Student_Item_Card currentItem = mStudentItemCardList.get(position);
        holder.student_name.setText(currentItem.get_student_name());
        holder.roll_no.setText(currentItem.get_roll_no());
        holder.cp.setMaxProgress(100);
        holder.lectures_attended.setText("Lectures :"+currentItem.getPresent_lecs()+"/"+currentItem.getTotallecs());
        //holder.status.setText(status.setstatus(currentItem.getPresent_lecs(),currentItem.getTotallecs()));
        holder.cp.setCurrentProgress(Double.parseDouble(currentItem.getPercent().trim()));
        holder.cp.setTextColor(Color.BLACK);
//        if(Integer.parseInt(currentItem.getPercent())< 75) {
//            holder.cp.setTextColor(Color.RED);
//          //  holder.cp.setGradient(gradientType, Color.RED);
//            Log.d("Entered  here",currentItem.getPercent());
//        }
//        else
//
//        {
//            holder.cp.setTextColor(Color.YELLOW);
//            //holder.cp.setGradient(gradientType, Color.RED);
//            Log.d("Entered  here red",currentItem.getPercent());
//
//        }

//in some cases, it will prevent unwanted situations
        holder.checkBox.setOnCheckedChangeListener(null);

        //if true, your custom_checkbox will be selected, else unselected
        holder.checkBox.setChecked(mStudentItemCardList.get(position).isSelected());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mStudentItemCardList.get(holder.getAdapterPosition()).setSelected(isChecked);
                Integer pos = (Integer) holder.checkBox.getTag();
                if(mStudentItemCardList.get(holder.getAdapterPosition()).isSelected()){
                   // mStudentItemCardList.get(pos).setSelected(true);
                    absentStudents.add(mStudentItemCardList.get(holder.getAdapterPosition()));
                    Toast.makeText(context, absentStudents.toString(), Toast.LENGTH_SHORT).show();
                }
                else if(!mStudentItemCardList.get(holder.getAdapterPosition()).isSelected())
                {
                  //  mStudentItemCardList.get(pos).setSelected(false);
                    absentStudents.remove(mStudentItemCardList.get(holder.getAdapterPosition()));
                }



            }
        });




//        holder.checkBox.setTag(position);
//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Integer pos = (Integer) holder.checkBox.getTag();
//               // Toast.makeText(context, mStudentItemCardList.get(pos).get_student_name() + " clicked!", Toast.LENGTH_SHORT).show();
////                if (mStudentItemCardList.get(pos).isSelected()) {
////                    mStudentItemCardList.get(pos).setSelected(false);
////                } else {
////                    mStudentItemCardList.get(pos).setSelected(true);
////                }
//
//                if(holder.checkBox.isChecked()){
//                    mStudentItemCardList.get(pos).setSelected(true);
//                    absentStudents.add(mStudentItemCardList.get(pos));
//                    Toast.makeText(context, absentStudents.toString(), Toast.LENGTH_SHORT).show();
//                }
//                else if(!holder.checkBox.isChecked())
//                {
//                    mStudentItemCardList.get(pos).setSelected(false);
//                    absentStudents.remove(mStudentItemCardList.get(pos));
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mStudentItemCardList.size();
    }

    public ArrayList<Student_Item_Card> absentstudents()
    {


        return absentStudents;
    }


    public void empty_absentStudents()
    {
        absentStudents.clear();
        Log.d("Abb",absentStudents.toString());
    }


}

