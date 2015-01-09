package br.com.focco.focconotifica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

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

   // Exibe a notificão
   
   protected static void criarNotificacao(Context context, int id) {

      NotificationCompat.Builder notif = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_focco)
            .setContentTitle("FoccoNotifica")
            .setContentText("Você possui novas mensagens.")
            .setTicker("informação....")
            .setLights(Color.rgb(200, 20, 0), 1000, 10000) // cor laranja
            .setNumber(2);

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
}
