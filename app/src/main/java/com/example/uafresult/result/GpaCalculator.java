package com.example.uafresult.result;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.uafresult.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class GpaCalculator {

    Context context;
    LayoutInflater layoutInflater;
    LinearLayout linearLayoutParentGPA;
    ArrayList<SubResultStuff> subs_data_list;
    private String cgpaStr;
    private boolean canShowPopUp = true;


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


    public GpaCalculator(Context context, LinearLayout linearLayoutParentGPA, ArrayList<SubResultStuff> subs_data_list){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.linearLayoutParentGPA = linearLayoutParentGPA;
        this.subs_data_list = subs_data_list;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  void calculateGpa(){


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
                    totalqp += qpCalculator(marks, oneCreditArray);
                    break;
                case 2:
                    totalqp += qpCalculator(marks, twoCreditArray);
                    break;
                case 3:
                    totalqp += qpCalculator(marks, threeCreditArray);
                    break;
                case 4:
                    totalqp += qpCalculator(marks, fourCreditArray);
                    break;
                case 5:
                    totalqp += qpCalculator(marks, fiveCreditArray);
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



    private double qpCalculator(int marks, double[][] arr) {
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

                popupWindow.setFocusable(false);
                popupWindow.setOutsideTouchable(true);
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                linearLayoutParentGPA.setAlpha(1);
            }
        });

    }



}
