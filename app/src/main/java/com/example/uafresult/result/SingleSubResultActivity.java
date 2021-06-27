package com.example.uafresult.result;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uafresult.R;


public class SingleSubResultActivity extends AppCompatActivity {
    private TextView txtSubName,txtProfName,txtCourseId,txtCourseTitle,txtCreditHours,txtMid,
       txtAssignment,txtFinal,txtPractical,txtTotal,txtGrade,subNameTV;
    private String sub_code;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_sub_result);
        getSupportActionBar().hide();
        sub_code = getIntent().getStringExtra("SUB_CODE");

        txtSubName = findViewById(R.id.res_Sub_name);
        txtProfName = findViewById(R.id.res_professor_name);
        txtCourseId = findViewById(R.id.res_course_id);
        txtCourseTitle = findViewById(R.id.res_course_title);
        txtCreditHours = findViewById(R.id.res_credit_hours);
        txtMid = findViewById(R.id.res_mid);
        txtAssignment = findViewById(R.id.res_assignment);
        txtFinal = findViewById(R.id.res_final);
        txtPractical = findViewById(R.id.res_practical);
        txtTotal = findViewById(R.id.res_total);
        txtGrade = findViewById(R.id.res_grade);
        subNameTV=findViewById(R.id.subNameTV);

        txtSubName.setText(sub_code.toUpperCase());
        subNameTV.setText(sub_code.toUpperCase());


        txtProfName.setY(+2000);
        txtCourseId.setY(+2000);
        txtCourseTitle.setY(+2000);
        txtCreditHours.setY(+2000);
        txtMid.setY(+2000);
        txtAssignment.setY(+2000);
        txtFinal.setY(+2000);
        txtPractical.setY(+2000);
        txtTotal.setY(+2000);
        txtGrade.setY(+2000);

        txtProfName.animate().translationY(+50).setDuration(500);
        txtCourseId.animate().translationY(+60).setDuration(650);
        txtCourseTitle.animate().translationY(+70).setDuration(750);
        txtCreditHours.animate().translationY(+80).setDuration(850);
        txtMid.animate().translationY(+90).setDuration(950);
        txtAssignment.animate().translationY(+100).setDuration(1050);
        txtFinal.animate().translationY(+110).setDuration(1150);
        txtPractical.animate().translationY(+120).setDuration(1250);
        txtTotal.animate().translationY(+130).setDuration(1350);
        txtGrade.animate().translationY(+140).setDuration(1450);

        // data to textviews
        if (ResultsStuff.subsResultHashMap.size()>0){
            String prof_name = ResultsStuff.subsResultHashMap.get(sub_code).getProfessorName().toUpperCase();
            if(prof_name.length()>25) {
                String[] name_array = prof_name.split(" ");
                String format_name = "";
                boolean canBreak = true;
                for(String n:name_array){
                    if ((format_name + n).length() > 25) {
                        if (canBreak) {
                            format_name += "\n" ;
                        }
                        canBreak = false;
                    }
                    format_name += " " + n;
                }
                prof_name = format_name;
            }
            txtProfName.setText(prof_name);
            txtCourseId.setText("Course ID: " + sub_code.toUpperCase());
            String course_title =  ResultsStuff.subsResultHashMap.get(sub_code).getCourseTitle().toUpperCase();
            if(course_title.length()>25) {
                String[] course_array = course_title.split(" ");
                String format_name = "";
                boolean canBreak = true;
                for(String n:course_array){
                    if ((format_name + n).length() > 25) {
                        if (canBreak) {
                            format_name += "\n" ;
                        }
                        canBreak = false;
                    }
                    format_name += " " + n;
                }
                course_title = format_name;
            }
            txtCourseTitle.setText( course_title);

            txtCreditHours.setText("Credit Hours: " + ResultsStuff.subsResultHashMap.get(sub_code).getCreditHours());
            txtMid.setText("Mid: " + ResultsStuff.subsResultHashMap.get(sub_code).getMid());
            txtAssignment.setText("Assignment: " + ResultsStuff.subsResultHashMap.get(sub_code).getAssignment());
            txtFinal.setText("Final: " + ResultsStuff.subsResultHashMap.get(sub_code).getFinal_marks());
            txtPractical.setText("Practical: " + ResultsStuff.subsResultHashMap.get(sub_code).getPractical());
            txtTotal.setText("Total: " + ResultsStuff.subsResultHashMap.get(sub_code).getTotal());
            txtGrade.setText("Grade: " + ResultsStuff.subsResultHashMap.get(sub_code).getGrade().toUpperCase());
        }


    findViewById(R.id.backBtnResult3).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }


}