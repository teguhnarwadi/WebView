package com.allsosmed.webview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by DEKSTOP on 25/12/2016.
 */
public class Helper {

    public static String url_world = "http://serverww.jasaallsosmed.co.id";
    public static String url_indo = "https://instagram.followergratis.co.id/masuk.php";


    public static boolean isOnline(Context c) {
        if (((ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
            return false;
        }
        return true;
    }

    public static void showToast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

}
