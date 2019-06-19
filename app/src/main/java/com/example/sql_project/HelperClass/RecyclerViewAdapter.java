package com.example.sql_project.HelperClass;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sql_project.R;
import java.util.ArrayList;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    private ArrayList<Student_Item_Card> mStudentItemCardList;
    private ArrayList<Student_Item_Card> mStudentItemCardListFull;
Context context;
    ArrayList<Student_Item_Card> absentStudents=new ArrayList<>();

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Context context;
        public TextView student_name;
        public TextView roll_no;
        CheckBox checkBox;
        CircularProgressIndicator cp;


        public ViewHolder(View itemView) {
            super(itemView);

            student_name = itemView.findViewById(R.id.student);
            roll_no = itemView.findViewById(R.id.roll_no);
            checkBox= itemView.findViewById(R.id.checkbox);
            cp=itemView.findViewById(R.id.circular_progress);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int gradientType = CircularProgressIndicator.LINEAR_GRADIENT;
        int endColor = Color.MAGENTA;
        holder.setIsRecyclable(false);
        Student_Item_Card currentItem = mStudentItemCardList.get(position);
        holder.student_name.setText(currentItem.get_student_name());
        holder.roll_no.setText(currentItem.get_roll_no());
        holder.cp.setMaxProgress(100);
        holder.cp.setCurrentProgress(Double.parseDouble(currentItem.getPercent().trim()));
        if(Double.parseDouble(currentItem.getPercent().trim())< 75) {
            holder.cp.setGradient(gradientType, endColor);
        }

        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) holder.checkBox.getTag();
               // Toast.makeText(context, mStudentItemCardList.get(pos).get_student_name() + " clicked!", Toast.LENGTH_SHORT).show();
//                if (mStudentItemCardList.get(pos).isSelected()) {
//                    mStudentItemCardList.get(pos).setSelected(false);
//                } else {
//                    mStudentItemCardList.get(pos).setSelected(true);
//                }

                if(holder.checkBox.isChecked()){
                    mStudentItemCardList.get(pos).setSelected(true);
                    absentStudents.add(mStudentItemCardList.get(pos));
                    Toast.makeText(context, absentStudents.toString(), Toast.LENGTH_SHORT).show();
                }
                else if(!holder.checkBox.isChecked())
                {
                    mStudentItemCardList.get(pos).setSelected(false);
                    absentStudents.remove(mStudentItemCardList.get(pos));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStudentItemCardList.size();
    }


}

