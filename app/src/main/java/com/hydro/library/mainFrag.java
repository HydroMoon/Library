package com.hydro.library;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class mainFrag extends Fragment {

    RecyclerView mainRecycler;
    MainAdapterGrid adapterGrid;
    List<Lectures> myLecList = new ArrayList<>();
    Button button;
    ProgressBar progressBar;
    String filePath = "";

    private final static int FILE_CHOOSER_RESULT = 1;

    public mainFrag() {
        // Required empty public constructor
    }

    public static mainFrag newInstance() {
        mainFrag fragment = new mainFrag();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fView = inflater.inflate(R.layout.fragment_main, container, false);

        mainRecycler = fView.findViewById(R.id.main_recyclerview);
        button = fView.findViewById(R.id.uploadfile);
        progressBar = fView.findViewById(R.id.progress_upload);

        myLecList.add(new Lectures("النظم الخبيرة", "ES"));
        myLecList.add(new Lectures("التجارة الالكترونية", "EC"));
        myLecList.add(new Lectures("صيانة البرمجيات", "ST"));
        myLecList.add(new Lectures("منهجيات البرمجيات", "SM"));
        myLecList.add(new Lectures("استرجاع البيانات", "IR"));
        myLecList.add(new Lectures("الحوسبة السحابية", "CC"));
        myLecList.add(new Lectures("التشفير وامن المعلومات", "IS"));
        myLecList.add(new Lectures("واجهات المستخدم", "UI"));

        adapterGrid = new MainAdapterGrid(myLecList, getActivity());
        GridLayoutManager flom = new GridLayoutManager(getActivity(), 2);
        mainRecycler.setLayoutManager(flom);
        mainRecycler.setAdapter(adapterGrid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Upload_File().execute();
                getFile();
                //button.setText(filePath);*/
            }
        });

        return fView;
    }

    private void getFile() {
        Intent fileChooser = new Intent(Intent.ACTION_GET_CONTENT);
        fileChooser.setType("*/*");
        fileChooser = Intent.createChooser(fileChooser, "Pick any document");
        startActivityForResult(fileChooser, FILE_CHOOSER_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_CHOOSER_RESULT:
                filePath = data.getDataString();
                new Upload_File().execute();
                break;
        }
    }

    private class Upload_File extends AsyncTask<Void, Integer, String> {

        final OkHttpClient client = new OkHttpClient();
        String path = Environment.getExternalStorageDirectory() + File.separator + "logo1.png";
        File file = new File(filePath);
        MediaType MEDIA_TYPE_JPEG = MediaType.parse("*/*");
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setIndeterminate(false);
            progressBar.setMax((int) file.length());
        }

        @Override
        protected String doInBackground(Void... voids) {

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("upfile", file.getName(), createCustomRequestBody(MEDIA_TYPE_JPEG, file))
                    .addFormDataPart("Path", "lec.txt")
                    .addFormDataPart("type", "M")
                    .addFormDataPart("subject", "SoftwareMeth")
                    .build();

            Request request = new Request.Builder()
                    .url("http://192.168.1.3/upload.php")
                    .header("Content-Type", "multipart/form-data")
                    .post(requestBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()){
                    Log.i("fsdsdsds", response.toString());
                }else {
                    return response.body().string();
                }
            }catch (IOException e){
                Log.i("fsdsdsds", e.getMessage());
            }
            return "error";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress((int) values[0]);
        }

        @Override
        protected void onPostExecute(String aVoid) {
            Toast.makeText(getContext(), aVoid, Toast.LENGTH_LONG).show();
            Log.i("TEST", aVoid);
        }

        public RequestBody createCustomRequestBody(final MediaType contentType, final File file) {
            return new RequestBody() {
                @Override public MediaType contentType() {
                    return contentType;
                }
                @Override public long contentLength() {
                    return (int) file.length();
                }
                @Override public void writeTo(BufferedSink sink) {
                    Source source = null;
                    try {
                        source = Okio.source(file);
                        //sink.writeAll(source);
                        Buffer buf = new Buffer();
                        int remaining = 0;
                        for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                            sink.write(buf, readCount);
                            publishProgress((remaining += readCount));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

    }





}
