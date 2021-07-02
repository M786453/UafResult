package com.example.uafresult.result;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uafresult.R;
import com.example.uafresult.SearchActivity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class ResultActivity extends AppCompatActivity {
    private ListView result_list_view;
    private static ListAdapter adapter;
    private TextView txtNameAndag_no;
    private static String username_search_result="";
    private static String ag_search_result = "";
    private WebView browser;
    private ProgressDialog progressDialog;
    private TextView txtCgpa;
    private LinearLayout linearLayoutResultActivity;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//        setTitle("Result");
        getSupportActionBar().hide();





        loadAd();











        progressDialog = new ProgressDialog(ResultActivity.this);
        txtCgpa = findViewById(R.id.txtCgpa);
        txtNameAndag_no = findViewById(R.id.txtNameAndAg);
        result_list_view = findViewById(R.id.result_list_view);
        linearLayoutResultActivity = findViewById(R.id.linear_layout_result_activity);


        adapter = new ListAdapter(ResultActivity.this,ResultsStuff.subsResultList,ResultsStuff.result_subSessionsList,linearLayoutResultActivity);

        result_list_view.setAdapter(adapter);


        txtCgpa.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {



                loadAd();

                showAd();



            }
        });





            txtNameAndag_no.setText(getUsername_search_result().toUpperCase() + "\n"+ getAg_search_result().toUpperCase() );












    }


    private void loadAd(){

        AdRequest adRequest = new AdRequest.Builder().build();


        InterstitialAd.load(ResultActivity.this, "ca-app-pub-3940256099942544/1033173712",
                adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;


                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                //called when fullscreen content failed to show
                                Log.i("AD","The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                //called when fullscreen content is shown.
                                //make sure toset your refernce to null so you
                                //don't show it a second time
                                mInterstitialAd = null;
                                Log.i("AD","The ad was shown.");
                            }

                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                //Called when fullscreen content is dismissed.
                                GpaCalculator gpaCalculator = new GpaCalculator(ResultActivity.this, linearLayoutResultActivity, ResultsStuff.subsResultList, false);
                                if (SearchActivity.isLms) {

                                    gpaCalculator.calculateGpaLms();
                                }else{
                                    gpaCalculator.calculateGpaAttendancePortal();
                                }
                                Log.i("AD","The ad was dismissed.");
                            }
                        });


                        Log.i("AD","onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        //handle the error
                        Log.i("AD_Error",loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


    }

    private void showAd(){


        if(mInterstitialAd!=null){
            mInterstitialAd.show(ResultActivity.this);
        }else{
            Log.i("AD","The interstitial ad wasn't ready yet.");
        }

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