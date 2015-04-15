package br.com.focco.focconotifica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;

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

   @Deprecated
   public static String getStringFromAssetAnt(Context context, String fileName) {
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

   // Exibe a notificçao
   
   @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
   public static void criarNotificacao(Context context, int id, String txt) {

      NotificationCompat.Builder notif = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_focco)
            .setContentTitle("FoccoNotifica")
            .setContentText(txt)
            //.setTicker("informação....")
            .setLights(Color.rgb(200, 20, 0), 1000, 10000) // cor laranja
            .setNumber(1)
            .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);

      // The stack builder object will contain an artificial back stack for
      // the started Activity. This ensures that navigating backward from the
      // Activity leads out of your application to the Home screen.
      // Creates an explicit intent for an Activity in your app
      TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
      stackBuilder.addParentStack(Principal.class);
      stackBuilder.addNextIntent(new Intent(context, Principal.class));
      PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
      notif.setContentIntent(resultPendingIntent);

      NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

      mNotificationManager.notify(id, notif.build());
   }

   public static String getFoneNumber(Context context){
      TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
      return tm.getLine1Number();
   }

   public static boolean isConnectedToServer(String url, int timeout) {
      try{
          URL myUrl = new URL(url);
          URLConnection connection = myUrl.openConnection();
          connection.setConnectTimeout(timeout);
          connection.connect();
          return true;
      } catch (Exception e) {
          return false;
      }
   }

   public static class Validar{
      private static Pattern pattern;
      private static Matcher matcher;
      //Email Pattern
      private static final String EMAIL_PATTERN =
              "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
              + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
   
      /**
       * Validar e-mail com Expressão Regular
       *
       * @param email
       * @return true para e-mail válido e false para e-mail Inválido
       */
      public static boolean validate(String email) {
          pattern = Pattern.compile(EMAIL_PATTERN);
          matcher = pattern.matcher(email);
          return matcher.matches();
      }
   }
}
