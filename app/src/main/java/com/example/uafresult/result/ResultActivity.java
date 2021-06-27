package com.example.uafresult.result;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uafresult.R;


public class ResultActivity extends AppCompatActivity {
    private GridView result_Grid_View;
    private static ResultGridViewAdapter adapter;
    private TextView name,ag_no;
    private static String username_search_result="";
    private static String ag_search_result = "";
    private WebView browser;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//        setTitle("Result");
        getSupportActionBar().hide();



        progressDialog = new ProgressDialog(ResultActivity.this);
        name = findViewById(R.id.txt_student_name_result);


        ag_no = findViewById(R.id.txt_ag_result_activity);

        result_Grid_View = findViewById(R.id.result_Grid_view);


        adapter = new ResultGridViewAdapter(ResultActivity.this,ResultsStuff.result_subNamesList,ResultsStuff.result_subSessionsList);

        result_Grid_View.setAdapter(adapter);






        if (!(getAg_search_result().isEmpty()) && !(getUsername_search_result().isEmpty())){
            name.setText(getUsername_search_result().toUpperCase());
            ag_no.setText(getAg_search_result().toUpperCase());


        }



        result_Grid_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ResultActivity.this,SingleSubResultActivity.class);
                intent.putExtra("SUB_CODE",ResultsStuff.result_subNamesList.get(position));
                startActivity(intent);

            }
        });
        findViewById(R.id.backBtnResult2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






    }



    public static String getUsername_search_result() {
        return username_search_result;
    }

    public static void setUsername_search_result(String username_search_result) {
        ResultActivity.username_search_result = username_search_result;
    }

    public static String getAg_search_result() {
        return ag_search_result;
    }

    public static void setAg_search_result(String ag_search_result) {
        ResultActivity.ag_search_result = ag_search_result;
    }


}