package com.example.uafresult.result;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.uafresult.R;

import java.util.ArrayList;

public class ResultGridViewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<SubResultStuff> sem_sub_data_list;



    public ResultGridViewAdapter(Context context, ArrayList<SubResultStuff> sem_sub_data_list){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sem_sub_data_list = sem_sub_data_list;

    }



    @Override
    public int getCount() {

        return sem_sub_data_list.size();
    }

    @Override
    public Object getItem(int position) {
        return sem_sub_data_list.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {






        view = layoutInflater.inflate(R.layout.custom_result_grid_view,null);
        TextView txtSubCode = view.findViewById(R.id.txt_subject_code);
        txtSubCode.setText(sem_sub_data_list.get(position).getCourseId().toUpperCase());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Intent intent = new Intent(context,SingleSubResultActivity.class);
                 ResultsStuff.single_sub_stuff = sem_sub_data_list.get(position);
                 context.startActivity(intent);


            }
        });


        return view;
    }

}
