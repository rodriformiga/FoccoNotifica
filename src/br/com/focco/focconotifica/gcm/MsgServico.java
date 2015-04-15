package br.com.focco.focconotifica.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import br.com.focco.focconotifica.Util;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MsgServico extends IntentService {

   public MsgServico() {
      super("MsgServico");
   }

   @Override
   protected void onHandleIntent(Intent intent) {
      Bundle extras = intent.getExtras();
      GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
      String messageType = gcm.getMessageType(intent);
      //prefs = getSharedPreferences("Chat", 0);
      if (!extras.isEmpty()) {
         if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            Log.e("L2C", "Error");
         } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
            Log.e("L2C", "Error");
         } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            //Toast.makeText(getApplicationContext(), extras.getString("mensagem"), Toast.LENGTH_LONG).show();
            //Se o usuário não estiver no chat, dispara uma notificação.
            //if (!prefs.getString("CURRENT_ACTIVE", "").equals(extras.getString("fromu"))) {
               //sendNotification(extras.getString("msg"), extras.getString("fromu"), extras.getString("name"));
            //}
            //Log.i("TAG", "Usr: " + prefs.getString("CURRENT_ACTIVE", "") + " from="+extras.getString("fromu"));
            
            String str = "msg: " + extras.getString("mensagem")
                  +"\nid : " + extras.getString("id")
                  +"\nres: " + extras.getString("resposta");
            
            Util.criarNotificacao(getApplicationContext(), 55, str);
            Log.i("FOCCO", str);
         }
      }
      MsgRecebe.completeWakefulIntent(intent);
   }

}
