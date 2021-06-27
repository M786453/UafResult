package com.example.uafresult.result;

import android.content.Context;
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
    ArrayList<String> subjectNames_Result_list;
    ArrayList<String> session_result_list;


    public ResultGridViewAdapter(Context context, ArrayList<String> subjectNames_Result_list, ArrayList<String> session_result_list){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.subjectNames_Result_list = subjectNames_Result_list;
        this.session_result_list = session_result_list;
    }



    @Override
    public int getCount() {

        return subjectNames_Result_list.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectNames_Result_list.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {






        view = layoutInflater.inflate(R.layout.custom_result_grid_view,null);
        TextView txtSubNameResult = view.findViewById(R.id.txtSubjectName_Result);
        TextView txtSubSessionResult = view.findViewById(R.id.txtSession_Result);
        TextView txtTeacherNameResult = view.findViewById(R.id.txtTeacherName_result);
        txtSubNameResult.setText(subjectNames_Result_list.get(position).toUpperCase());
        txtSubSessionResult.setText(session_result_list.get(position));

        String professorName =   ResultsStuff.subsResultHashMap.get(subjectNames_Result_list.get(position)).getProfessorName().toUpperCase();
        if (professorName.length()>15){
            professorName = professorName.substring(0,12)+"...";
        }

        txtTeacherNameResult.setText(professorName);

        return view;
    }

}
