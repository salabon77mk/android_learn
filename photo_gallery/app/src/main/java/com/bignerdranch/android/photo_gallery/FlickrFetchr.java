package com.bignerdranch.android.photo_gallery;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FlickrFetchr {
    private static final String TAG = "flickrfetcher";

    private static final int BUFFER_SIZE = 1024;

    private static final String API_KEY = setApiKey();

    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() +
                        ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            while((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }
        finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    private static String setApiKey() {
        String file ="/src/main/assets/flickrapikey.txt";
        String currentLine = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            currentLine = reader.readLine();
            reader.close();
            return currentLine;
        }
        catch (FileNotFoundException fnfe){
            Log.e(TAG, "FileNotFoundException in setApieKey", fnfe);
        }
        catch (IOException ioe){
            Log.e(TAG, "IOException in setApiKey", ioe);
        }
        return currentLine;
    }
}
