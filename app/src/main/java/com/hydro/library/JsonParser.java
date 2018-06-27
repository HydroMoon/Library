package com.hydro.library;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonParser extends AsyncTask<String, Void, List<Object>> {

    private final OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();
    private List<Object> bookList = new ArrayList<>();
    private AsyncResponse myResponse;

    JsonParser(AsyncResponse Response) {
        myResponse = Response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<Object> doInBackground(String... parms) {
        Request request = new Request.Builder()
                .url("http://192.168.1.3/list.php?lec=" + parms[0])
                .build();
        Response response = null;

        try {
            response = client.newCall(request).execute();

            JSONObject lecturesJson = new JSONObject(response.body().string());

            //JSONArray lecturesArray = lecturesJson.getJSONArray("nsarray");

            JSONArray SubjectArray;

            bookList.clear();

            String[] subjects = {"M", "L", "E", "O"};

            JSONObject lecturesIterator = lecturesJson.getJSONObject("nsarray");

            for (String sub : subjects) {

                Object test = lecturesIterator.get(sub);
                if (test instanceof JSONArray) {
                    SubjectArray = lecturesIterator.getJSONArray(sub);
                    bookList.add(getFullName(sub));
                    Log.i("JSON", sub);
                } else {
                    continue;
                }
                for (int i = 0; i < SubjectArray.length(); i++) {
                    JSONObject sub_inside = SubjectArray.getJSONObject(i);
                    String name = sub_inside.getString("name");
                    String size = sub_inside.getString("size");
                    Log.i("JSON", name + "    " + size);
                    bookList.add(new BooksInfo(name, size, sub, parms[0]));
                }

            }

            return bookList;

        } catch (IOException e) {
            Log.e("OKERR", "Unexpected code " + response);
        } catch (JSONException e) {
            Log.e("JSONERR", e.toString());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Object> bookList) {
        myResponse.AResponse(bookList);
    }

    private String getFullName(String sub) {
        String name = "";
        switch (sub) {
            case "M":
                name = "Lectures";
                break;
            case "L":
                name = "Labs";
                break;
            case "E":
                name = "Exams";
                break;
            case "O":
                name = "Others";
                break;
        }
        return name;
    }
}
