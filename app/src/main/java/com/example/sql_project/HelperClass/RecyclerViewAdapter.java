package com.example.sql_project.HelperClass;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sql_project.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<Student_Item_Card> mStudentItemCardList;
    private ArrayList<Student_Item_Card> mStudentItemCardListFull;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Context context;
        public TextView student_name;
        public TextView roll_no;


        public ViewHolder(View itemView) {
            super(itemView);

            student_name = itemView.findViewById(R.id.student);
            roll_no = itemView.findViewById(R.id.roll_no);
        }
    }

    public RecyclerViewAdapter(ArrayList<Student_Item_Card> exampleList) {
        mStudentItemCardList = exampleList;
        mStudentItemCardListFull = new ArrayList<>(mStudentItemCardList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        ViewHolder evh = new ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Student_Item_Card currentItem = mStudentItemCardList.get(position);
        holder.student_name.setText(currentItem.get_student_name());
        holder.roll_no.setText(currentItem.get_roll_no());

    }

    @Override
    public int getItemCount() {
        return mStudentItemCardList.size();
    }


}

