package com.orxtradev.swithuser.notification;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by hazem on 12/24/2016.
 * this class responsable to send notification to specific user with his token
 *
 */

public class DownstreamMessage extends AsyncTask<String, String, String>
{
//    AsyncResponse delegate = null;
    int responseCode;
    @Override
    protected String doInBackground(String... params)
    {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String server_key = "key=AAAAetqPbjM:APA91bGRfnLh2E6S5bJuyFGS3CuJHuX_B-AaRO_1aRsHLtL8Y1VY3NJAyjylCTuVZiAVsX0EhMMjOmU9fVXZ0U4KNkUvBNbw1ixZTAgAnApumXBCBl1iXFCC7g16lMx-NxuU5XO9HVF7";
        String topic;
        String content;
        String content_json_string;
        String modelId;
        String type;
        String notificationId;


        try
        {
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            topic = params[0];
            modelId=params[1];
            type=params[2];
            notificationId = params[3];


            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Authorization", server_key);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();





            JSONObject notification_json_object = new JSONObject();
            try
            {
                String notDesc = "";
                if (type.equals("2")){
                    notDesc = "يوجد طلب صيانة جديد اضغط لاظهار الطلب ";
                }else{
                    notDesc = "يوجد طلب معيانة جديد اضغط لاظهار الطلب ";
                }
                notification_json_object.put("body",notDesc);
                notification_json_object.put("title","ابلكيشن سويتش");
                notification_json_object.put("modelId",modelId);
                notification_json_object.put("type",type);
                notification_json_object.put("notificationId",notificationId);

            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JSONObject data=new JSONObject();

            try
            {

                data.put("sender_id","");
                data.put("favor_id","");
            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JSONObject content_json_object = new JSONObject();
            try
            {
                content_json_object.put("to","/topics/"+topic);
                content_json_object.put("data",notification_json_object);
//                content_json_object.put("data","");

            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            content_json_string = content_json_object.toString();

            OutputStream output = httpURLConnection.getOutputStream();
            output.write(content_json_string.getBytes());
            output.flush();
            output.close();

            responseCode = httpURLConnection.getResponseCode();


        } catch (ProtocolException e) {

        } catch (IOException e) {

        }

        return "" + responseCode;
    }

    @Override
    protected void onPostExecute(String result)
    {
//        delegate.processFinish(result);
        Log.d("hazem",result);
    }
}
