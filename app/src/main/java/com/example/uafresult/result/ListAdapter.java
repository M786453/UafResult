package com.example.uafresult.result;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.uafresult.R;
import com.example.uafresult.SearchActivity;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context context;

    LayoutInflater layoutInflater;

    ArrayList<String> session_list;

    ArrayList<SubResultStuff> subjects_data_list;

    LinearLayout linearLayoutResultActivity;


    public ListAdapter(Context context, ArrayList<SubResultStuff> subjects_data_list, ArrayList<String> session_list,
                       LinearLayout linearLayoutResultActivity){

        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.session_list = session_list;

        this.subjects_data_list = subjects_data_list;

        this.linearLayoutResultActivity = linearLayoutResultActivity;


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
        RecyclerView result_recycler_view = view.findViewById(R.id.recyclerViewResult);
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
        StaggeredRecyclerViewAdapter adapter = new StaggeredRecyclerViewAdapter(context,sem_subs_data_list,session_list,linearLayoutResultActivity);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        result_recycler_view.setLayoutManager(manager);
        result_recycler_view.setAdapter(adapter);
        grd_subjects.setAdapter(resultGridViewAdapter);
//        int size = subjects_data_list.size();
//        int height=100;
//        if(size != 0 && size <=3)
//            height = 100*2;
//        else if (size <=6)
//            height = 100*3;
//        else if (size <=9)
//            height = 100*4;
//        else if(size<=12)
//            height = 100*5;
//
//        grd_subjects.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,height));
//
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(result_recycler_view.getVisibility()==View.GONE) {
                    img_drop_icon.setImageResource(R.drawable.drop_up_icon);
//                    grd_subjects.setVisibility(View.VISIBLE);
                    linearLayoutGpa.setVisibility(View.VISIBLE);
                    result_recycler_view.setVisibility(View.VISIBLE);

                }else{
                    img_drop_icon.setImageResource(R.drawable.drop_down_icon);
//                    grd_subjects.setVisibility(View.GONE);
                    linearLayoutGpa.setVisibility(View.GONE);
                    result_recycler_view.setVisibility(View.GONE);
                }
            }
        });

        txt_gpa.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                GpaCalculator gpaCalculator = new GpaCalculator(context, linearLayoutResultActivity, sem_subs_data_list, true);
                if (SearchActivity.isLms) {
                    gpaCalculator.calculateGpaLms();
                }else {
                    gpaCalculator.calculateGpaAttendancePortal();
                }
                }
        });

        return view;
    }
}
