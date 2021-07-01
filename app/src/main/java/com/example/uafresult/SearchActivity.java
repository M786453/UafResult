package com.example.uafresult;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uafresult.result.ResultScrapper;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView txtLms,txtAttendancePortal;
    private EditText edtAg;
    private ImageView imgSearch;
    private boolean isLms=true;
    private LinearLayout linearLayoutSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        txtLms = findViewById(R.id.txtLms);
        txtAttendancePortal = findViewById(R.id.txtAttendancePortal);
        edtAg = findViewById(R.id.edtAg);
        imgSearch = findViewById(R.id.imgSearch);
        linearLayoutSearchResult = findViewById(R.id.linearLayoutSearchResult);

        txtLms.setOnClickListener(this);
        txtAttendancePortal.setOnClickListener(this);
        imgSearch.setOnClickListener(this);


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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


                if(NetworkStatus.isOnline(this)) {

                    String ag_no = edtAg.getText().toString();

                    if (ag_no.isEmpty()) {

                        FancyToast.makeText(SearchActivity.this,"Ag No. Must Not Be Empty",
                                FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                        return;

                    }


                    ResultScrapper resultScrapper = new ResultScrapper(this,linearLayoutSearchResult);
                    if(isLms){

                        resultScrapper.getResultDataFromLms(ag_no);




                        }else{

                        resultScrapper.getDataFromAttendancePortal(ag_no);




                        }


                        edtAg.setText("");


                }else{

                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                }


                break;


        }


    }
}