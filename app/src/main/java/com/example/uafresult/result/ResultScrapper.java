package com.example.uafresult.result;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;


import com.example.uafresult.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultScrapper {

    Context context;
    WebView browser;
    LinearLayout linearLayout;
    ImageView imageView;
    ObjectAnimator rotateAnimation;
//    ProgressDialog progressDialog;
    PopupWindow popupWindow;

    private boolean isResultFromLms;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ResultScrapper(Context context, LinearLayout parent_layout){

        this.context = context;
        browser = new WebView(context);
        linearLayout = parent_layout;
//        imageView = new ImageView(context);
//        popupWindow = new PopupWindow(imageView,170,170);
        rotateAnimation = ObjectAnimator.ofFloat(imageView,"rotationY",0,360);
        /* JavaScript must be enabled if you want it to work, obviously */
        browser.getSettings().setJavaScriptEnabled(true);
        popup();
        /* Register a new JavaScript interface called HTMLOUT */
        browser.addJavascriptInterface(new MyJavaScriptInterface(context,linearLayout,popupWindow,rotateAnimation), "Android");
//        progressDialog = new ProgressDialog(context);

//        imageView.setImageResource(R.drawable.logo);
//
//        imageView.setPadding(10,10,10,10);
//
//        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
//        rotateAnimation.setDuration(1300);
//
//        popupWindow.setBackgroundDrawable(context.getDrawable(R.drawable.edt_background));


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getResultDataFromLms(String ag){
        this.isResultFromLms = true;
//        progressDialog.show();
        linearLayout.setAlpha(0.5f);
//        popupWindow.showAtLocation(linearLayout, Gravity.CENTER,0,0);
//        rotateAnimation.start();


        browser.loadUrl("http://lms.uaf.edu.pk/login/index.php");
        browser.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String url) {
                /* This call inject JavaScript into the page which just finished loading. */
                view.loadUrl("javascript:document.getElementById('REG').value= '"+ag+"' ; document.querySelector('#region-main > div > div > div > div.subcontent.loginsub > div.row > div:nth-child(2) > div > div:nth-child(1) > form > div.form-input > input.btn.btn-warning.pull-right').click();");
                view.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView mview, String url) {
                        super.onPageFinished(mview, url);
                        mview.loadUrl("javascript:window.Android.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    }
                });

            }

        });
    }


    public void getDataFromAttendancePortal(String ag){

        this.isResultFromLms = false;
//        progressDialog.show();
        linearLayout.setAlpha(0.5f);
//        popupWindow.showAtLocation(linearLayout, Gravity.CENTER,0,0);
//        rotateAnimation.start();
        browser.loadUrl("http://121.52.152.24/");
        browser.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {


                view.loadUrl("javascript:document.getElementById('ctl00_Main_txtReg').value = '"+ag+"' ; document.getElementById('ctl00_Main_btnShow').click();");

                view.setWebViewClient(new WebViewClient(){
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onPageFinished(WebView mview, String url) {

                        mview.loadUrl("javascript:window.Android.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                    }
                });

            }
        });


    }

    private class MyJavaScriptInterface{
        Context context;
        LinearLayout mlinearLayout;
        PopupWindow mPopUpWindow;
        ObjectAnimator mRotateAnimation;
        MyJavaScriptInterface(Context context, LinearLayout mlinearLayout, PopupWindow mPopUpWindow, ObjectAnimator mRotateAnimation){
            this.context = context;
            this.mlinearLayout = mlinearLayout;
            this.mPopUpWindow = mPopUpWindow;
            this.mRotateAnimation = mRotateAnimation;
        }


        @JavascriptInterface
        public void showHTML(String html)  {

            Log.i("response",html);

            if(isResultFromLms) {

                //Lms Result
                String data = parseResultLms(html);

                try {
                    JsonResultParserLms.getSubResultStuff(data);

//                    progressDialog.dismiss();

                    cancelPopup();
                    if (ResultsStuff.result_subSessionsList.size()==0){
                        FancyToast.makeText(context,"Invalid Ag No.",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    }else {
                        Intent intent = new Intent(context, ResultActivity.class);
                        context.startActivity(intent);
                    }

                } catch (JSONException e) {
                    Log.i("ERROR_JSON", e.getMessage());
//                    progressDialog.dismiss();
                      cancelPopup();
//                    popupWindow.dismiss();
                }
            }else{

                //Attendance portal result
                String data = parseResultAttendancePortal(html);

                try{
                    if(data.equals("")){
                        cancelPopup();
                        FancyToast.makeText(context,"Invalid Ag No.",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    }else {
                        JsonResultParserAttendancePortal.getSubResultStuff(data);

//                    progressDialog.dismiss();

                        cancelPopup();

                        Intent intent = new Intent(context, ResultActivity.class);
                        context.startActivity(intent);
                    }
                }catch (JSONException e){
                    Log.i("ERROR_JSON",e.getMessage());
//                    progressDialog.dismiss();

                 cancelPopup();popupWindow.dismiss();

                }
            }

        }


        void cancelPopup(){


                    mlinearLayout.post(new Runnable() {
                        @Override
                        public void run() {
//                            linearLayout.setAlpha(1f);
//                            mRotateAnimation.end();
                              mPopUpWindow.dismiss();
                              linearLayout.setAlpha(1);
                        }
                    });






                    
                }





        String parseResultLms(String html){


            Document document = Jsoup.parse(html);
            Elements tables = document.getElementsByClass("table tab-content");
            Element table_info = tables.first();
            Element table_result = tables.last();
            Elements info_elements = table_info.getAllElements();
            JSONObject student_data = new JSONObject();


            try {
                student_data.put("studentName",  info_elements.get(7).text().trim().toLowerCase());

                student_data.put("studentAg",info_elements.get(4).text().trim().toLowerCase());




            } catch (JSONException e) {
                e.printStackTrace();
            }


            //headings data
            Elements heading_elements = table_result.getElementsByTag("th");

            ArrayList<String> headings_list = new ArrayList<>();

            for (Element e: heading_elements){
                headings_list.add(e.text().trim().toLowerCase());
            }


            //result data
            JSONArray student_result = new JSONArray();

            Elements result_elements = table_result.getElementsByTag("tr");

            //removing heading row
            result_elements.remove(0);

            //looping through each result row
            for (Element e: result_elements){

                ArrayList<String> row_content_list = new ArrayList<>();

                Elements tdContents = e.getElementsByTag("td");

                for (Element element : tdContents){
                    row_content_list.add(element.text().trim().toLowerCase());
                }

                JSONObject subject_result = new JSONObject();

                for (int i=0;i<headings_list.size();i++){
                    try {
                        subject_result.put(headings_list.get(i),row_content_list.get(i));

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }

                student_result.put(subject_result);

            }
            try {
                student_data.put("results", student_result);
            }catch (JSONException e){
                e.printStackTrace();
            }
            Log.i("result_in_json",student_data.toString());

            return student_data.toString();
        }

        String parseResultAttendancePortal(String html){

            Document document = Jsoup.parse(html);
            Element table_result = document.getElementById("ctl00_Main_TabContainer1_tbResultInformation_gvResultInformation");



            //headings data
            Elements heading_elements;
            if(table_result==null) {
                return "";
            }else
                heading_elements = table_result.getElementsByTag("th");


            ArrayList<String> headings_list = new ArrayList<>();


            for (Element e: heading_elements){
                headings_list.add(e.text().trim().toLowerCase());
            }



            //result data
            JSONArray student_result = new JSONArray();

            Elements result_elements = table_result.getElementsByTag("tr");




            //removing heading row
            result_elements.remove(0);

            //looping through each result row
            for (Element e: result_elements){

                ArrayList<String> row_content_list = new ArrayList<>();

                Elements tdContents = e.getElementsByTag("td");

                for (Element element : tdContents){
                    row_content_list.add(element.text().trim().toLowerCase());
                }

                JSONObject subject_result = new JSONObject();

                for (int i=0;i<headings_list.size();i++){
                    try {
                        subject_result.put(headings_list.get(i),row_content_list.get(i));

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }

                student_result.put(subject_result);

            }

            return student_result.toString();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void popup() {
        linearLayout.setAlpha(0.2f);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.result_loading_popup, null);
        TextView gpaTVResult = view.findViewById(R.id.GPAResultTV);
        Button OKBtn = view.findViewById(R.id.OKBtn);



        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setElevation(20.0f);
        popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);
        popupWindow.setTouchable(false);




    }

}
