package com.innowi.appburnin.utils;

import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Navdeep on 10/9/2017.
 */

public class JSONHelper {

    static String directory = Environment.getExternalStorageDirectory().getPath()+"/logFiles";
    private static File dirPath = new File(directory);
    private static File JSONLog = new File(dirPath,"JSONLogBURN.json");
    static JSONObject builder;
    //Add to the JSON Object based on the passed value
    public static void add(JSONObject obj, String key, String value){
        try{
            obj.put(key, value);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //For arraylist passed
    public static void add(JSONObject obj, String key, ArrayList<String> value){
        try{
            obj.put(key, value);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    //For JSON Array

    public static void add(JSONObject obj, String key, JSONArray array){
        try{
            obj.put(key, array);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //For double value
    public static void add(JSONObject obj, String key, double value){
        try{
            obj.put(key, value);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    //Init Json Object
    public static void initJSON(String current_test){
        builder = new JSONObject();

        JSONHelper.add(builder,"test_id", SharedAppData.getTestCount());
//        if (SharedAppData.getBurnInStatus()) {
//            JSONHelper.add(builder,"test_type","Post Burn In");
//        }else {
//            JSONHelper.add(builder,"test_type","Pre Burn In");
//        }
//        JSONHelper.add(builder,"test_type","closed");
        //check if the Item has been tested before or not
//        int item = SharedAppData.getResultColor(current_test);
//        Log.d("Checking shared values ", Boolean.toString(SharedAppData.getBurnInStatus()));
//        if(item == 0){
//            JSONHelper.add(builder,"flag","test");
//        }
//        //just being paranoid hence testing twice
//        else if( item == 1 || item == 2){
//            JSONHelper.add(builder,"flag","retest");
//        }
        //removing the redundant data from each activity
        JSONHelper.setId(current_test);

    }
    //Set ID
    public static void setId(String value){
        JSONHelper.add(builder,"id",value);
    }

    public static void setType(String value){
        JSONHelper.add(builder,"test_type",value);
    }
    //Set test Description
    public static void setDescription(String value){
        JSONHelper.add(builder,"test_description", value);
    }
    //Set Upper limit
    public static void setUpLimit(double value){
        JSONHelper.add(builder,"upper_limit", value);
    }
    public static void setUpLimit(int value){
        JSONHelper.add(builder,"upper_limit", value);
    }
    public static void setUpLimit(String value){JSONHelper.add(builder,"upper_limit",value);}
    public static void setUpLimit(double value1, double value2){
        JSONHelper.add(builder,"upper_limit", Double.toString(value1)+","+ Double.toString(value2));
    }
    //Set Lower Limit
    public static void setLowLimit(double value){
        JSONHelper.add(builder, "lower_limit", value);
    }
    public static void setLowLimit(int value){
        JSONHelper.add(builder, "lower_limit", value);
    }
    public static void setLowLimit(String value){
        JSONHelper.add(builder, "lower_limit", value);
    }
    public static void setLowLimit(double value1, double value2){
        JSONHelper.add(builder,"lower_limit", Double.toString(value1)+","+ Double.toString(value2));
    }
    //Set Actual Result
    public static void setResultValue(String value){
        JSONHelper.add(builder, "actual_result", value);
    }
    //Set Actual result
    public static void setResultValue(int value){
        JSONHelper.add(builder, "actual_result", value);
    }
    public static void setResultValue(Long value){
        JSONHelper.add(builder
        , "actual_result", value);
    }
    public static void setResultValue(double value1, double value2){
        JSONHelper.add(builder,"actual_result", Double.toString(value1)+","+ Double.toString(value2));
    }

    //Set Status of the test
    public static void setStatus(String value){
        JSONHelper.add(builder, "status", value);
    }
    //Set Miscelleneous Result Data
    public static void setMiscData(String value){
        JSONHelper.add(builder, "misc", value);
    }
    //Set TimeStamp
    public static void setTime(String value){
        JSONHelper.add(builder, "time", value);
    }
    //Set JSON data

//    public static void setData(String key, JSONArray array){
//        builder = new JSONObject();
//        JSONHelper.add(builder, key, array);
//    }

    public static void setData(String key, String value){
        try{
            builder = new JSONObject();
            JSONHelper.add(builder, key, value);
        }catch (Exception e){
            e.printStackTrace();
            Log.d("=========","TEST JSON ERROR");
        }
    }

    //get JSON data
    public static JSONObject getData(){
        try{
            return builder;
        }catch (Exception e){
            e.printStackTrace();
            Log.d("============","JSON ERROR");
        }
        return builder;
    }

    //write the data to a file
    public static void logJSON(){
        JSONParser jsonParser = new JSONParser();
        org.json.simple.JSONObject jsonObject ;
        JSONArray array = new JSONArray();
        try{
            if(!dirPath.exists()){
                dirPath.mkdir();
            }
            if(!JSONLog.exists()){
                JSONLog.createNewFile();
            }
            if(JSONLog.exists()){
                //JSON Parsing the array for objects


                try{
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(JSONLog));
                    System.out.println(".......=="+jsonArray.toString());
                    for(Object object: jsonArray){
                        jsonObject = (org.json.simple.JSONObject) object;
                        array.add(jsonObject);
                    }
                }catch (ParseException e){
                    e.printStackTrace();
                }

//                File previous = new File(dirPath,"JSONLog.json");
//                String test  =null;
//                try{
//                    FileInputStream fileInputStream = new FileInputStream(previous);
//                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//
////                JsonReader jsonReader = new JsonReader(inputStreamReader);
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                    while((test = bufferedReader.readLine())!=null){
//                        System.out.println("Bufferred ==>"+test);
//                    }
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//
//
//
//                jsonReader.beginObject();
//                while (jsonReader.hasNext()){
//                    System.out.println("+++=="+jsonReader.toString());
//                }
//
            }


            array.add(builder);


            System.out.println("=====>"+array);
//            Gson gson = new Gson();
//            String testGson = gson.toJson(builder);
//            System.out.println("====GSON==="+testGson);
//            FileWriter fileWriter = new FileWriter(JSONLog,true);
////            gson.toJson(array,fileWriter);
//            fileWriter.close();
            OutputStream fileOutputStream = new FileOutputStream(JSONLog, false);
//            JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(fileOutputStream,"UTF-8"));

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(array.toString());
//            outputStreamWriter.append(builder.toString());
            outputStreamWriter.close();
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
