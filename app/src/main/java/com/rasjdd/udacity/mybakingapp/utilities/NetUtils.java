package com.rasjdd.udacity.mybakingapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {

    public static boolean testConnectivityBasic(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static String detectVideoUrl(String videoURL, String thumbnailURL) {
        if (videoURL.toLowerCase().endsWith(".mp4")) return  videoURL;
        else if (thumbnailURL.toLowerCase().endsWith(".mp4")) return thumbnailURL;
        else return Constants.InvalidString;
    }

    public static String detectThumbnailURL(String videoURL, String thumbnailURL) {
        if (isGraphicFileformat(thumbnailURL)) return thumbnailURL;
        else if (isGraphicFileformat(videoURL)) return videoURL;
        else return Constants.InvalidString;
    }

    public static boolean isGraphicFileformat(String s){
        if (s.toLowerCase().endsWith(".jpg")) return true;
//        else if (s.toLowerCase().endsWith(".jpeg")) return true; //Supposedly Picasso doesn't support this?  TEST!
        else if (s.toLowerCase().endsWith(".png")) return true;
        else if (s.toLowerCase().endsWith(".bmp")) return true;
        else return false;
    }
}
