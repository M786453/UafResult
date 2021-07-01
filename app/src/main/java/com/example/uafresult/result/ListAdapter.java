package com.example.uafresult.result;

import android.content.Context;
import android.icu.text.StringSearch;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.uafresult.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context context;

    LayoutInflater layoutInflater;

    ArrayList<String> session_list;

    ArrayList<SubResultStuff> subjects_data_list;



    public ListAdapter(Context context, ArrayList<SubResultStuff> subjects_data_list, ArrayList<String> session_list){

        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.session_list = session_list;

        this.subjects_data_list = subjects_data_list;


    }




    @Override
    public int getCount() {
        return session_list.size();
    }

    @Override
    public Object getItem(int i) {
        return session_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ArrayList<SubResultStuff> sem_subs_data_list = new ArrayList<>();
        view = layoutInflater.inflate(R.layout.semester_row,null);
        TextView txtSemester = view.findViewById(R.id.txtSemester);
        GridView grd_subjects = view.findViewById(R.id.grd_subjects);
        ImageView img_drop_icon = view.findViewById(R.id.img_drop_icon);
        LinearLayout linearLayoutGpa = view.findViewById(R.id.linear_layout_gpa);
        TextView txt_gpa = view.findViewById(R.id.txt_gpa);
        txtSemester.setText(session_list.get(i));
        Log.i("subs_data_list",subjects_data_list.size()+"");
        for(SubResultStuff subject:subjects_data_list){

            if(subject.getSemester().equals(session_list.get(i))) {
                sem_subs_data_list.add(subject);
                Log.i("sem_subs",sem_subs_data_list.toString()+"");
            }
        }

        ResultGridViewAdapter resultGridViewAdapter = new ResultGridViewAdapter(context,sem_subs_data_list);
        grd_subjects.setAdapter(resultGridViewAdapter);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(grd_subjects.getVisibility()==View.GONE) {
                    img_drop_icon.setImageResource(R.drawable.drop_up_icon);
                    grd_subjects.setVisibility(View.VISIBLE);
                    linearLayoutGpa.setVisibility(View.VISIBLE);
                }else{
                    img_drop_icon.setImageResource(R.drawable.drop_down_icon);
                    grd_subjects.setVisibility(View.GONE);
                    linearLayoutGpa.setVisibility(View.GONE);
                }
            }
        });

        txt_gpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "GPA Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
