package br.com.focco.focconotifica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class Util {
   public static String getStringFromAsset(Context context, String fileName) {
      try {
         InputStream is = context.getAssets().open(fileName);
         int size = is.available();

         byte[] buffer = new byte[size];
         is.read(buffer);
         is.close();

         return new String(buffer).replaceAll("\r", "");
      } catch (IOException e) {
         e.printStackTrace();
         return "";
      }
   }
   
   public static String getStringFromAsset2(Context context, String fileName) {
      try {
         InputStream stream = context.getAssets().open(fileName);
         BufferedReader r = new BufferedReader(new InputStreamReader(stream));
         StringBuilder total = new StringBuilder();
         String line;
         while ((line = r.readLine()) != null) {
            total.append(line);
            total.append("\n");
         }
         return total.toString();
      } catch (IOException e) {
         e.printStackTrace();
         return "";
      }
   }
/*
   public String getStrFromRes(Context context, int id) {
      return context.getResources().getString(id);
   }
   
   public int getIntFromRes(Context context, int id) {
      return context.getResources().getInteger(id);
   }
   
   public String[] getStrArrFromRes(Context context, int id) {
      return context.getResources().getStringArray(id);
   }*/
}
