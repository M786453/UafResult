package com.example.uafresult.result;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uafresult.R;


public class SingleSubResultActivity extends AppCompatActivity {
    private TextView txtProfName,txtCourseId,txtCourseTitle,txtMid,
       txtAssignment,txtFinal,txtPractical,txtTotal,txtGrade;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_sub_result);
        getSupportActionBar().hide();



        txtProfName = findViewById(R.id.res_professor_name);
        txtCourseId = findViewById(R.id.res_course_id);
        txtCourseTitle = findViewById(R.id.res_course_title);

        txtMid = findViewById(R.id.res_mid);
        txtAssignment = findViewById(R.id.res_assignment);
        txtFinal = findViewById(R.id.res_final);
        txtPractical = findViewById(R.id.res_practical);
        txtTotal = findViewById(R.id.res_total);
        txtGrade = findViewById(R.id.res_grade);







        SubResultStuff subResultStuff = ResultsStuff.single_sub_stuff;

        // data to textviews
        if (ResultsStuff.subsResultList.size()>0){
            String prof_name = subResultStuff.getProfessorName().toUpperCase();
//            if(prof_name.length()>25) {
//                String[] name_array = prof_name.split(" ");
//                String format_name = "";
//                boolean canBreak = true;
//                for(String n:name_array){
//                    if ((format_name + n).length() > 25) {
//                        if (canBreak) {
//                            format_name += "\n" ;
//                        }
//                        canBreak = false;
//                    }
//                    format_name += " " + n;
//                }
//                prof_name = format_name;
//            }

            txtProfName.setText(prof_name);
            txtCourseId.setText(subResultStuff.getCourseId().toUpperCase());
            String course_title =  subResultStuff.getCourseTitle().toUpperCase();
//            if(course_title.length()>25) {
//                String[] course_array = course_title.split(" ");
//                String format_name = "";
//                boolean canBreak = true;
//                for(String n:course_array){
//                    if ((format_name + n).length() > 25) {
//                        if (canBreak) {
//                            format_name += "\n" ;
//                        }
//                        canBreak = false;
//                    }
//                    format_name += " " + n;
//                }
//                course_title = format_name;
//            }
            txtCourseTitle.setText( course_title);


            txtMid.setText(subResultStuff.getMid());
            txtAssignment.setText(subResultStuff.getAssignment());
            txtFinal.setText(subResultStuff.getFinal_marks());
            txtPractical.setText(subResultStuff.getPractical());
            txtTotal.setText(subResultStuff.getTotal());
            txtGrade.setText(subResultStuff.getGrade().toUpperCase());
        }



    }


}