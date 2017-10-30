package com.example.shenxuesong.sanyueyuekaoajuan;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shenxuesong on 2017/9/20.
 */

public class JavaUtils {
    //判断当前的网络状态
    public boolean getInfo(Context context){
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conn!=null){
            NetworkInfo info = conn.getActiveNetworkInfo();
            if(info!=null&&info.isConnected()){
                if(info.getState()==NetworkInfo.State.CONNECTED){
                    //当前的链接的网络可以用
                    return true;
                }
            }
        }
        return false;
    }
    public String getString(String surl){
        String str="";
            try {
                URL url = new URL(surl);
                HttpURLConnection conne= (HttpURLConnection) url.openConnection();

                conne.setConnectTimeout(5000);
                conne.setReadTimeout(5000);
                int responseCode = conne.getResponseCode();
                if (responseCode==200){
                    InputStream in = conne.getInputStream();

                    byte[] by=new byte[1024];
                    int len=0;

                    while ((len=in.read(by))!=-1){
                        str+=new String(by,0,len);
                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
}
