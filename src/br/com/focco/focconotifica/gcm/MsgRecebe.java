package br.com.focco.focconotifica.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.WakefulBroadcastReceiver;

public class MsgRecebe extends WakefulBroadcastReceiver {

   @Override
   public void onReceive(Context context, Intent intent) {
      //Esta classe faz a parte de escuta do GCM google, recebe a notificação e chama o Intent
      Bundle extras = intent.getExtras();

      Intent msgrcv = new Intent("MsgFoccoNotifica");
      msgrcv.putExtra("mensagem", extras.getString("mensagem"));
      msgrcv.putExtra("id",       extras.getString("id"));
      msgrcv.putExtra("resposta", extras.getString("resposta"));

      LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
      ComponentName comp = new ComponentName(context.getPackageName(), MsgServico.class.getName());
      startWakefulService(context, (intent.setComponent(comp)));
      setResultCode(Activity.RESULT_OK);
   }

}
