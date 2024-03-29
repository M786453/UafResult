package com.example.uafresult.result;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class JsonResultParserAttendancePortal {


    static void getSubResultStuff(String data)throws JSONException {
        if (data!=null){
            ResultsStuff.result_subNamesList.clear();
            ResultsStuff.subsResultList.clear();
            ResultsStuff.result_subSessionsList.clear();
            String session = "";
            String course_code = "";






            JSONArray subs_result_array = new JSONArray(data);
            ResultActivity.setAg_search_result(subs_result_array.getJSONObject(0).getString("registrationno"));
            ResultActivity.setUsername_search_result("");

            for (int i = 0; i < subs_result_array.length(); i++) {
                JSONObject sub_result = subs_result_array.getJSONObject(i);
                SubResultStuff subResultStuff = new SubResultStuff();
                if(getString("teachername", sub_result).length()>3) {
                    String name = getString("teachername", sub_result);
                    subResultStuff.setProfessorName(name);
                }else{
                    subResultStuff.setProfessorName("");
                }
                subResultStuff.setAssignment(getString("assigment", sub_result));
                subResultStuff.setCourseId(getString("coursecode", sub_result));
                if(getString("coursename", sub_result).length()>3) {
                    String title = getString("coursename", sub_result);

                    subResultStuff.setCourseTitle(title);
                }else{
                    subResultStuff.setCourseTitle("");
                }
                subResultStuff.setCreditHours("");
                subResultStuff.setMid(getString("mid", sub_result));
                subResultStuff.setFinal_marks(getString("final", sub_result));
                subResultStuff.setPractical(getString("practical", sub_result));
                subResultStuff.setTotal(getString("totalmark", sub_result));
                subResultStuff.setGrade(getString("grade", sub_result));

                course_code = getString("coursecode", sub_result);
                session = getString("semestername", sub_result);
                subResultStuff.setSemester(session);

                if(!ResultsStuff.result_subNamesList.contains(course_code)) {
                    ResultsStuff.result_subNamesList.add(course_code);


                    if (!ResultsStuff.result_subSessionsList.contains(session))
                        ResultsStuff.result_subSessionsList.add(session);


                    ResultsStuff.subsResultList.add(subResultStuff);


                    Log.i("subsResult", ResultsStuff.subsResultList.toString());
                }

            }



        }else {
            Log.i("Json_error","json not working");
        }

    }

    private static String getString(String tagName, JSONObject jsonObject)throws JSONException {
        return jsonObject.getString(tagName);
    }

}
