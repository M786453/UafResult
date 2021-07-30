package com.example.uafresult.result;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.uafresult.MyApplication;
import com.example.uafresult.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;

public class GpaCalculator {

    Context context;
    LayoutInflater layoutInflater;
    LinearLayout linearLayoutParentGPA;
    ArrayList<SubResultStuff> subs_data_list;
    private String cgpaStr;
    private boolean canShowPopUp = true;
    boolean isGpa;

    HashMap<Double,Double> D;
    HashMap<Double,Double> C;
    HashMap<Double,Double> B;




    private static double[][] oneCreditArray =
            {{8, 1}, {9, 1.5}, {10, 2}, {11, 2.33}, {12, 2.67}, {13, 3}, {14, 3.33},
                    {15, 3.67}, {16, 4}
            };
    private static double[][] twoCreditArray =
            {{16, 2}, {17, 2.5}, {18, 3}, {19, 3.5}, {20, 4}, {21, 4.33}, {22, 4.67},
                    {23, 5}, {24, 5.33}, {25, 5.67},
                    {26, 6}, {27, 6.33}, {28, 6.67}, {29, 7}, {30, 7.33}, {31, 7.67}, {32, 8}
            };

    private static double[][] threeCreditArray = {{24, 3},
            {25, 3.5}, {26, 4}, {27, 4.5}, {28, 5}, {29, 5.5}, {30, 6}, {31, 6.33}, {32,
            6.67},
            {33, 7}, {34, 7.33}, {35, 7.67}, {36, 8},
            {37, 8.33}, {38, 8.67}, {39, 9}, {40, 9.33}, {41, 9.67}, {42, 10}, {43,
            10.33},
            {44, 10.67}, {45, 11}, {46, 11.33}, {47, 11.67},
            {48, 12}
    };


    private static double[][] fourCreditArray = {{32, 4},
            {33, 4.5}, {34, 5}, {35, 5.5}, {36, 6}, {37, 6.5}, {38, 7}, {39, 7.5}, {40,
            8},
            {41, 8.33}, {42, 8.67}, {43, 9}, {44, 9.33},
            {45, 9.67}, {46, 10}, {47, 10.33}, {48, 10.67}, {49, 11}, {50, 11.33}, {51,
            11.67},
            {52, 12}, {53, 12.33},
            {54, 12.67}, {55, 13}, {56, 13.33}, {57, 13.67}, {58, 14}, {59, 14.33}, {60,
            14.67},
            {61, 15}, {62, 15.33}, {63, 15.67},
            {64, 16}
    };

    private static double[][] fiveCreditArray =
            {{40, 5}, {41, 5.5}, {42, 6}, {43, 6.5}, {44, 7}, {45, 7.5}, {46, 8},
                    {47, 8.5},
                    {48, 9}, {49, 9.5}, {50, 10}, {51, 10.33}, {52, 10.67}, {53, 11}, {54,
                    11.33},
                    {55, 11.67}, {56, 12}, {57, 12.33}, {58, 12.67},
                    {59, 13}, {60, 13.33}, {61, 13.67}, {62, 14}, {63, 14.33}, {64, 14.67}, {65,
                    15},
                    {66, 15.33}, {67, 15.67}, {68, 16}, {69, 16.33},
                    {70, 16.67}, {71, 17}, {72, 17.33}, {73, 17.67}, {74, 18}, {75, 18.33}, {76,
                    18.67},
                    {77, 19}, {78, 19.33}, {79, 19.67},
                    {80, 20}
            };


    public GpaCalculator(Context context, LinearLayout linearLayoutParentGPA, ArrayList<SubResultStuff> subs_data_list,boolean isGpa){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.linearLayoutParentGPA = linearLayoutParentGPA;
        this.subs_data_list = subs_data_list;
        this.isGpa = isGpa;
        D = new HashMap<>();
        C = new HashMap<>();
        B = new HashMap<>();



        //values for Grade-D
        D.put(8.0,1.0);
        D.put(9.0,1.5);
        D.put(16.0,2.0);
        D.put(17.0,2.5);
        D.put(18.0,3.0);
        D.put(19.0,3.5);
        D.put(24.0,3.0);
        D.put(25.0,3.5);
        D.put(26.0,4.0);
        D.put(27.0,4.5);
        D.put(28.0,5.0);
        D.put(29.0,5.5);
        D.put(32.0,4.0);
        D.put(33.0,4.5);
        D.put(34.0,5.0);
        D.put(35.0,5.5);
        D.put(36.0,6.0);
        D.put(37.0,6.5);
        D.put(38.0,7.0);
        D.put(39.0,7.5);
        D.put(40.0,5.0);
        D.put(41.0,5.5);
        D.put(42.0,6.0);
        D.put(43.0,6.5);
        D.put(44.0,7.0);
        D.put(45.0,7.5);
        D.put(46.0,8.0);
        D.put(47.0,8.5);
        D.put(48.0,9.0);
        D.put(49.0,9.5);


        //values for C-Grade
        C.put(10.0,2.0);
        C.put(11.0,2.33);
        C.put(12.0,2.67);
        C.put(20.0,4.0);
        C.put(21.0,4.33);
        C.put(22.0,4.67);
        C.put(23.0,5.0);
        C.put(24.0,5.33);
        C.put(25.0,5.67);
        C.put(30.0,6.0);
        C.put(31.0,6.33);
        C.put(32.0,6.67);
        C.put(33.0,7.0);
        C.put(34.0,7.33);
        C.put(35.0,7.67);
        C.put(36.0,8.0);
        C.put(37.0,8.33);
        C.put(38.0,8.67);
        C.put(40.0,8.0);
        C.put(41.0,8.33);
        C.put(42.0,8.67);
        C.put(43.0,9.0);
        C.put(44.0,9.33);
        C.put(45.0,9.67);
        C.put(46.0,10.0);
        C.put(47.0,10.33);
        C.put(48.0,10.67);
        C.put(49.0,11.0);
        C.put(50.0,10.67);
        C.put(51.0,11.0);
        C.put(52.0,10.67);
        C.put(53.0,11.0);
        C.put(54.0,11.33);
        C.put(55.0,11.67);
        C.put(56.0,12.0);
        C.put(57.0,12.33);
        C.put(58.0,12.67);
        C.put(59.0,13.0);
        C.put(60.0,13.33);
        C.put(61.0,13.67);
        C.put(62.0,14.0);
        C.put(63.0,14.33);
        C.put(64.0,14.67);


        //values of B-Grade
        B.put(13.0,3.0);
        B.put(14.0,3.33);
        B.put(15.0,3.67);
        B.put(26.0,6.0);
        B.put(27.0,6.33);
        B.put(28.0,6.67);
        B.put(29.0,7.0);
        B.put(30.0,7.33);
        B.put(31.0,7.67);
        B.put(39.0,9.0);
        B.put(40.0,9.33);
        B.put(41.0,9.67);
        B.put(42.0,10.0);
        B.put(43.0,10.33);
        B.put(44.0,10.67);
        B.put(45.0,11.0);
        B.put(46.0,11.33);
        B.put(47.0,11.67);
        B.put(52.0,12.0);
        B.put(53.0,12.33);
        B.put(54.0,12.67);
        B.put(55.0,13.0);
        B.put(56.0,13.33);
        B.put(57.0,13.67);
        B.put(58.0,14.0);
        B.put(59.0,14.33);
        B.put(60.0,14.67);
        B.put(61.0,15.0);
        B.put(62.0,15.33);
        B.put(63.0,15.67);
        B.put(65.0,15.0);
        B.put(66.0,15.33);
        B.put(67.0,15.67);
        B.put(68.0,16.0);
        B.put(69.0,16.33);
        B.put(70.0,16.67);
        B.put(71.0,17.0);
        B.put(72.0,17.33);
        B.put(73.0,17.67);
        B.put(74.0,18.0);
        B.put(75.0,18.33);
        B.put(76.0,18.67);
        B.put(77.0,19.0);
        B.put(78.0,19.33);
        B.put(79.0,19.67);


    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  void calculateGpaLms(){


        double totalqp = 0.0;
        int totalCreditHrs = 0;

        for (int i = 0; i < subs_data_list.size(); i++) {


            int marks = Integer.parseInt(subs_data_list.get(i).getTotal());

            if(subs_data_list.get(i).getCreditHours().isEmpty()){
                FancyToast.makeText(context,"Credit Hrs. Not Found",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                canShowPopUp = false;
                break;
            }
            int creditHr = Integer.parseInt(String.valueOf(subs_data_list.get(i).getCreditHours().charAt(0)));

            totalCreditHrs += creditHr;
            switch (creditHr) {
                case 1:
                    totalqp += qpCalculatorLms(marks, oneCreditArray);
                    break;
                case 2:
                    totalqp += qpCalculatorLms(marks, twoCreditArray);
                    break;
                case 3:
                    totalqp += qpCalculatorLms(marks, threeCreditArray);
                    break;
                case 4:
                    totalqp += qpCalculatorLms(marks, fourCreditArray);
                    break;
                case 5:
                    totalqp += qpCalculatorLms(marks, fiveCreditArray);
                    break;
            }

        }


        if (canShowPopUp) {
            double cgpa = totalqp / totalCreditHrs;

            cgpaStr = String.format("%.2f", cgpa);
            closeKeyboard();
            popup();
        }





    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  void calculateGpaAttendancePortal(){

        double qps = 0;
        double credit_hrs = 0;

        for (SubResultStuff subject : subs_data_list){

           double[] res =  qpCalculatorAttendancePortal(Double.parseDouble(subject.getTotal()),subject.getGrade().toUpperCase());
           qps += res[0];
           credit_hrs += res[1];

        }
        double cgpa=0;
        cgpaStr = "0.0";
        if(qps !=0 && credit_hrs!=0) {
            cgpa = qps / credit_hrs;

            cgpaStr = String.format("%.2f", cgpa);
        }
        closeKeyboard();
        popup();

    }



    private double qpCalculatorLms(int marks, double[][] arr) {
        double qp = 0;

        int arraySize = arr.length;
        if (marks < arr[0][0]) {
            qp = 0;
        } else if (marks >= arr[1][0] && marks <= arr[arraySize - 1][0]) {
            for (int i = 0; i < arraySize; i++) {
                if (marks == arr[i][0]) {
                    qp = arr[i][1];
                    break;
                }
            }

        } else if (marks > arr[arraySize - 1][0]) {
            qp = arr[arraySize - 1][1];
        }
        return qp;
    }

    private double[] qpCalculatorAttendancePortal(double marks,String grade){


        double qp = 0;
        double credit_hrs=0;

        switch (grade){

            case "A":

                if (marks<=20) {
                    qp = 4;
                    credit_hrs = 1;
                }else if(marks<=40){
                    qp = 8;
                    credit_hrs = 2;
                }else if(marks<=60){
                    qp = 12;
                    credit_hrs = 3;
                }else if(marks<=80){
                    qp = 16;
                    credit_hrs = 4;
                }else if(marks<=100){
                    qp = 20;
                    credit_hrs = 5;
                }


                break;
            case "B":

                if (marks<=15) {
                    credit_hrs = 1;
                }else if(marks<=31){

                    credit_hrs = 2;
                }else if(marks<=47){

                    credit_hrs = 3;
                }else if(marks<=63){

                    credit_hrs = 4;
                }else if(marks<=79){

                    credit_hrs = 5;
                }


                if (B.containsKey(marks)){
                    qp = B.get(marks);
                }





                break;
            case "C":

                if (marks<=12) {
                    credit_hrs = 1;
                }else if(marks<=25){

                    credit_hrs = 2;
                }else if(marks<=38){

                    credit_hrs = 3;
                }else if(marks<=51){

                    credit_hrs = 4;
                }else if(marks<=64){

                    credit_hrs = 5;
                }


                if (C.containsKey(marks)){
                    qp = C.get(marks);
                }

                break;
            case "D":

                if (marks<=9) {
                    credit_hrs = 1;
                }else if(marks<=19){

                    credit_hrs = 2;
                }else if(marks<=29){

                    credit_hrs = 3;
                }else if(marks<=39){

                    credit_hrs = 4;
                }else if(marks<=49){

                    credit_hrs = 5;
                }


                if (D.containsKey(marks)){
                    qp = D.get(marks);
                }


                break;


        }



        return new  double[]{qp,credit_hrs};
    }


    private void closeKeyboard() {
        View view =((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void popup() {
        linearLayoutParentGPA.setAlpha(0.2f);


        View view = layoutInflater.inflate(R.layout.result_popup_anim, null);
        TextView gpaTVResult = view.findViewById(R.id.GPAResultTV);
        Button OKBtn = view.findViewById(R.id.OKBtn);

        TextView gpaResultTv = view.findViewById(R.id.GPAResultTV2);

        if (!isGpa)
            gpaResultTv.setText("CGPA");

        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setElevation(20.0f);
        popupWindow.showAtLocation(linearLayoutParentGPA, Gravity.CENTER, 0, 0);

        gpaTVResult.setText(cgpaStr);

        OKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutParentGPA.setAlpha(1);

                MyApplication.adOpenManager.showAdIfAvailable();

                popupWindow.setFocusable(false);
                popupWindow.setOutsideTouchable(true);
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                linearLayoutParentGPA.setAlpha(1);

                MyApplication.adOpenManager.showAdIfAvailable();

            }
        });

    }



}
