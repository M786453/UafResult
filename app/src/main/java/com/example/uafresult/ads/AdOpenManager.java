package com.example.uafresult.ads;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;


import com.example.uafresult.MyApplication;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import java.util.Date;

/** Prefetches App Open Ads. */
public class AdOpenManager implements LifecycleObserver,Application.ActivityLifecycleCallbacks {
    private static final String LOG_TAG = "AppOpenManager";
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";
    private AppOpenAd appOpenAd = null;
    private long loadTime = 0;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private static boolean isShowingAd = false;

    private final MyApplication myApplication;
    private Activity currentActivity;
    /** Constructor */
    public AdOpenManager(MyApplication myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    /** LifecycleObserver methods */

    @OnLifecycleEvent(ON_START)
    public void onStart(){
//        showAdIfAvailable();
        Log.i(LOG_TAG,"onStart");
    }


    /** Shows the ad if one isn't already showing.*/

    public void showAdIfAvailable(){
        //Only show ad if there is not already an app open
        //ad currently showing
        //and an ad is available.

        if(!isShowingAd && isAdAvailable()){
            Log.i(LOG_TAG,"Will show ad.");


            FullScreenContentCallback fullScreenContentCallback
                    = new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                   //set the reference to null so isAdAvailable()
                    //returns false
                    AdOpenManager.this.appOpenAd = null;
                    isShowingAd = false;
                    fetchAd();
                }
            };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);

            appOpenAd.show(currentActivity);


        }else{
            Log.i(LOG_TAG,"Can not show ad.");
            fetchAd();
        }


    }


    /** Request an ad */
    public void fetchAd() {
        //have unused ad, no need to fetch another
        if(isAdAvailable()){
            return;
        }

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {

            /**
             * Called when an app open ad has loaded
             *
             */

            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                AdOpenManager.this.appOpenAd = appOpenAd;
                AdOpenManager.this.loadTime = (new Date()).getTime();
            }

            /** Called when an app open add has failed to load
             */



            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                //handle the error
            }
        };

        AdRequest request = getAdRequest();
        AppOpenAd.load(myApplication,AD_UNIT_ID,request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                loadCallback);


    }


    /** utility method to check if ad was loaded more than n hours ago. */

    private boolean wasLoadTimeLessThanHoursAgo(long numHours){

        long dateDifference = (new Date()).getTime() - this.loadTime;

        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour)*numHours);


    }



    /** Creates and returns ad request. */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /** Utility method that checks if ad exists and can be shown. */
    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanHoursAgo(4);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;
    }
}