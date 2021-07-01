package com.example.uafresult.result;

import android.util.Log;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class JsonResultParserLms {

    //get subjects result data from json object

       static void getSubResultStuff(String data)throws JSONException {
           if (data!=null){
               ResultsStuff.subsResultList.clear();
               ResultsStuff.result_subSessionsList.clear();
               ResultsStuff.result_subNamesList.clear();
           String session = "";
           String course_code = "";


           JSONObject resultJsonObject = new JSONObject(data);
           ResultActivity.setUsername_search_result(getString("studentName",resultJsonObject));
           ResultActivity.setAg_search_result(getString("studentAg",resultJsonObject));

           JSONArray subs_result_array = resultJsonObject.getJSONArray("results");

           for (int i = 0; i < subs_result_array.length(); i++) {
               JSONObject sub_result = subs_result_array.getJSONObject(i);
               SubResultStuff subResultStuff = new SubResultStuff();
               course_code = getString("course code", sub_result);
               if(ResultsStuff.result_subNamesList.contains(course_code))
               return;
               if(getString("teacher name", sub_result).length()>3) {
                   String name = getString("teacher name", sub_result);

                   subResultStuff.setProfessorName(name);
               }else{
                   subResultStuff.setProfessorName("");
               }
               subResultStuff.setAssignment(getString("assignment", sub_result));
               subResultStuff.setCourseId(course_code);
               if(getString("course title", sub_result).length()>3) {
                   String title = getString("course title", sub_result);

                   subResultStuff.setCourseTitle(title);
               }else{
                   subResultStuff.setCourseTitle("");
               }
               subResultStuff.setCreditHours(getString("credit hours", sub_result));
               subResultStuff.setMid(getString("mid", sub_result));
               subResultStuff.setFinal_marks(getString("final", sub_result));
               subResultStuff.setPractical(getString("practical", sub_result));
               subResultStuff.setTotal(getString("total", sub_result));
               subResultStuff.setGrade(getString("grade", sub_result));


               session = getString("semester", sub_result);
               int indexOfSubstring = session.indexOf("2");
               session = session.substring(indexOfSubstring);
               subResultStuff.setSemester(session);

               if(!ResultsStuff.result_subSessionsList.contains(session))
               ResultsStuff.result_subSessionsList.add(session);
               ResultsStuff.subsResultList.add(subResultStuff);
               ResultsStuff.result_subNamesList.add(course_code);

           }



       }else {
               Log.i("Json_error","json not working");
           }

    }

static void setCGPA(String data)throws JSONException {
    if (data!=null){
        JSONObject resultJsonObject = new JSONObject(data);
        CurrentUserData.setCgpa(getString("cgpa",resultJsonObject));
    }
}


    private static JSONObject getJsonObject(String tagName, JSONObject jsonObject)throws JSONException {
        return jsonObject.getJSONObject(tagName);
    }

    //get data from json object according to given tag name
    private static String getString(String tagName, JSONObject jsonObject)throws JSONException {
        return jsonObject.getString(tagName);
    }

//    private static float getFloat(String tagName,JSONObject jsonObject)throws JSONException{
//        return (float) jsonObject.getDouble(tagName);
//    }

}
