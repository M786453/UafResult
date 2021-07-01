package com.example.uafresult.result;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uafresult.R;


public class ResultActivity extends AppCompatActivity {
    private ListView result_list_view;
    private static ListAdapter adapter;
    private TextView txtNameAndag_no;
    private static String username_search_result="";
    private static String ag_search_result = "";
    private WebView browser;
    private ProgressDialog progressDialog;
    private TextView txtCgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//        setTitle("Result");
        getSupportActionBar().hide();



        progressDialog = new ProgressDialog(ResultActivity.this);
        txtCgpa = findViewById(R.id.txtCgpa);
        txtNameAndag_no = findViewById(R.id.txtNameAndAg);
        result_list_view = findViewById(R.id.result_list_view);

        adapter = new ListAdapter(ResultActivity.this,ResultsStuff.subsResultList,ResultsStuff.result_subSessionsList);

        result_list_view.setAdapter(adapter);







            txtNameAndag_no.setText(getUsername_search_result().toUpperCase() + "\n"+ getAg_search_result().toUpperCase() );












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