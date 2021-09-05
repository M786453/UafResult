package com.example.uafresult.result;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uafresult.R;

import java.util.ArrayList;

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.Holder>{
    Context context;

    LayoutInflater layoutInflater;

    ArrayList<String> session_list;

    ArrayList<SubResultStuff> subjects_data_list;

    LinearLayout linearLayoutResultActivity;

    public StaggeredRecyclerViewAdapter(Context context, ArrayList<SubResultStuff> subjects_data_list, ArrayList<String> session_list,
                                        LinearLayout linearLayoutResultActivity) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.session_list = session_list;
        this.subjects_data_list = subjects_data_list;
        this.linearLayoutResultActivity = linearLayoutResultActivity;
    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_row,parent,false);
        return new Holder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.e("onBindViewHolder","Started");
        ArrayList<SubResultStuff> sem_subs_data_list = new ArrayList<>();
        SubResultStuff model = subjects_data_list.get(position);
          holder.sub_code.setText(model.getCourseId().toUpperCase());
          holder.teacher_name.setText(model.getProfessorName().toUpperCase());
          holder.session.setText(model.getSemester().toUpperCase());

        for(SubResultStuff subject:subjects_data_list){

            if(subject.getSemester().equals(model.getSemester())) {
                sem_subs_data_list.add(subject);
                Log.i("sem_subs",sem_subs_data_list.toString()+"");
            }
        }

        holder.resultView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(context,SingleSubResultActivity.class);
                  ResultsStuff.single_sub_stuff = sem_subs_data_list.get(position);
                  context.startActivity(intent);

              }
          });

    }

    @Override
    public int getItemCount() {
        return subjects_data_list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView sub_code,teacher_name,session;
        CardView resultView;
        public Holder(@NonNull View itemView) {
            super(itemView);
        sub_code = itemView.findViewById(R.id.sub_code);
        teacher_name = itemView.findViewById(R.id.teacherName);
        session = itemView.findViewById(R.id.sessionTV);
        resultView = itemView.findViewById(R.id.resultView);
        }
    }

}
