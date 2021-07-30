package com.example.uafresult.result;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uafresult.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

import java.util.Objects;


public class SingleSubResultActivity extends AppCompatActivity {
    private TextView txtProfName,txtCourseId,txtCourseTitle,txtMid,
       txtAssignment,txtFinal,txtPractical,txtTotal,txtGrade,ad_headline,
            ad_body,ad_price,ad_store;

    private Button btnAdCallToAction;

    private TextView ratingBar;
    private ImageView ad_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_sub_result);
        getSupportActionBar().hide();

        adLoader().loadAd(new AdRequest.Builder().build());

        // ad's ui components
        ad_headline = findViewById(R.id.ad_headline);

        ad_body = findViewById(R.id.ad_body);
        ad_price = findViewById(R.id.ad_price);
        ad_store = findViewById(R.id.ad_store);

        btnAdCallToAction = findViewById(R.id.call_to_action);

        ratingBar = findViewById(R.id.ad_ratingBar);
        ad_icon = findViewById(R.id.ad_icon);





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


    private AdLoader adLoader(){

        return new AdLoader.Builder(this,"ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener(){
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onNativeAdLoaded(@NonNull  NativeAd nativeAd) {

                        //show the add
                        if(nativeAd.getIcon()!=null)
                        ad_icon.setImageDrawable(nativeAd.getIcon().getDrawable());
                        ad_headline.setText(nativeAd.getHeadline());
                        ad_body.setText(nativeAd.getBody());
                        ad_price.setText(nativeAd.getPrice());
                        ad_store.setText(nativeAd.getStore());
                        btnAdCallToAction.setText(nativeAd.getCallToAction());




                        if(nativeAd.getStarRating()!=null) {
                            double rating = nativeAd.getStarRating();
                            ratingBar.setText((float) rating + "");
                        }

                        findViewById(R.id.ad_layout).setVisibility(View.VISIBLE);

                        if (SingleSubResultActivity.this.isDestroyed()){
                            nativeAd.destroy();
                            return;
                        }
                    }




                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                            //Handle the failure by logging, alerting the ui
                        Log.i("loadAdError",loadAdError.toString());

                    }

                                }
                )
                .withNativeAdOptions(new NativeAdOptions.Builder().build()).build();
    }






}