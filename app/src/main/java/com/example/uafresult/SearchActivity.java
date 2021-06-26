package com.example.uafresult;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView txtLms,txtAttendancePortal;
    private EditText edtAg;
    private ImageView imgSearch;
    private boolean isLms=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        txtLms = findViewById(R.id.txtLms);
        txtAttendancePortal = findViewById(R.id.txtAttendancePortal);
        edtAg = findViewById(R.id.edtAg);
        imgSearch = findViewById(R.id.imgSearch);

        txtLms.setOnClickListener(this);
        txtAttendancePortal.setOnClickListener(this);
        imgSearch.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.txtLms:

                isLms = true;

                txtAttendancePortal.setBackgroundColor(Color.parseColor("#00ffffff"));
                txtAttendancePortal.setTextColor(Color.parseColor("#349fd8"));


                txtLms.setBackgroundResource(R.drawable.blue_rounded_shape);
                txtLms.setTextColor(getResources().getColor(R.color.white));


                break;
            case R.id.txtAttendancePortal:

                isLms = false;

                txtLms.setBackgroundColor(Color.parseColor("#00ffffff"));
                txtLms.setTextColor(Color.parseColor("#349fd8"));


                txtAttendancePortal.setBackgroundResource(R.drawable.blue_rounded_shape);
                txtAttendancePortal.setTextColor(getResources().getColor(R.color.white));


                break;
            case R.id.imgSearch:




                break;


        }


    }
}