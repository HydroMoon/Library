package com.hydro.library;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Downloader extends AsyncTask<String, Integer, String> {

    private final static OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(12, TimeUnit.SECONDS)
            .connectTimeout(12, TimeUnit.SECONDS)
            .build();

    private PowerManager.WakeLock mWL;

    private File downloadedFile;

    private WeakReference<Context> context;
    private WeakReference<ProgressBar> mProgress;

    public Downloader(Context context, ProgressBar mProgress) {
        this.context = new WeakReference<>(context);
        this.mProgress = new WeakReference<>(mProgress);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context ctx = this.context.get();

        mProgress.get().setVisibility(View.VISIBLE);
        mProgress.get().setIndeterminate(false);
        mProgress.get().setMax(100);
        mProgress.get().setProgress(0);


        //prevent power button from interrupting download
        PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        mWL = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        mWL.acquire(5*60*1000L /*5 minutes*/);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgress.get().setProgress(values[0]);
    }

    @Override
    protected String doInBackground(String... par) {

        downloadedFile = new File(Environment.getExternalStorageDirectory() + File.separator + "ELibrary" + File.separator + par[2] + File.separator + par[1] + File.separator + par[0]);

        Request request = new Request.Builder()
                .url("http://192.168.1.3/subjects/" + par[2] + "/" + par[1] + "/" + par[0])
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                return "ERROR " + response.code() + ": " + response.message();
            }

            InputStream inputStream = response.body().byteStream();

            int file_length = (int) response.body().contentLength();

//            if (!downloadedFile.exists()) {
//                downloadedFile.mkdirs();
//            }
            if (downloadedFile.length() == file_length) {
                return "File exist";
            }

            downloadedFile.getParentFile().mkdirs();

            OutputStream outputStream = new FileOutputStream(downloadedFile);
            byte[] bytes = new byte[4096];

            int count;
            long total = 0;

            while ((count = inputStream.read(bytes)) != -1) {
                total += count;
                publishProgress((int) (100 * total) / file_length);
                outputStream.write(bytes, 0, count);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            Log.i("SEL", e.getMessage());
        }

        return "successful";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.i("SEL", s);
        mProgress.get().setVisibility(View.GONE);
        mWL.release();
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }
}
