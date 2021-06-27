package com.example.uafresult.result;

public class CurrentUserData {

    private static String cgpa;


    public static void setCgpa(String cgpa) {
        CurrentUserData.cgpa = cgpa;
    }

    public static String getCgpa() {
        return cgpa;
    }
}
