package com.hydro.library;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by mohand on 08/06/2017.
 * Edited by Qasim on 23/06/2018.
 */

class ConnectInternet {
    private Context context;
    ConnectInternet(Context context) {
        this.context = context;
    }

    boolean is_connected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if (connectivityManager!=null){
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info!=null){
                if (info.getState()==NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

}

