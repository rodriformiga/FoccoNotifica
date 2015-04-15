package br.com.focco.focconotifica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import br.com.focco.focconotifica.db.Base;
import br.com.focco.focconotifica.db.data.Mensagem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class Principal extends Activity implements OnItemClickListener {
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

   List<Mensagem> msgs;
	private ListView listaMsgs;
	SQLiteDatabase db;
	MsgAdap adap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		if (checkPlayServices()) {
         new Registrar().execute();
      }		

		db = new Base(getApplicationContext()).getWritableDatabase();

		carrega();
		adap = new MsgAdap(this, msgs);

		listaMsgs = (ListView) findViewById(R.id.lstGeral);
		listaMsgs.setAdapter(adap);
		listaMsgs.setOnItemClickListener(this);
	}

	private void carrega() {
		if (msgs == null) {
			msgs = new ArrayList<Mensagem>();
		}
		//msgs.clear();

		for (int i = 1; i < 10; i++) {
			Mensagem tmp = new Mensagem();
			tmp.id = i;
			tmp.titulo = "Mensagem " + i;
			tmp.mensagem = "Esta é a mensagem " + i;

			switch (i) {
			case 1:
			case 2:
			case 3:
				tmp.status = 'N';
				break;
			case 4:
			case 5:
			case 6:
				tmp.status = 'P';
				break;
			default:
				tmp.status = 'R';
				break;
			}

			tmp.resposta = "";
			msgs.add(tmp);
		}
		if (adap != null)
			adap.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		menu.findItem(R.id.mnuBuscar).getActionView();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.mnuConfig) {
		   //Util.criarNotificacao(getApplicationContext(), 1);
		   startActivity(new Intent(this, Config.class));
		   /*Intent intent = new Intent();
	        intent.setClass(this, SetPreferenceActivity.class);
	        startActivityForResult(intent, 0); */
			return true;
		} else if (id == R.id.mnuAtualiza) {
			Toast.makeText(getApplicationContext(), "atualizando...",
					Toast.LENGTH_SHORT).show();
			carrega();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(getApplicationContext(), msgs.get(position).mensagem,
				Toast.LENGTH_LONG).show();
	}
	
   private boolean checkPlayServices() {
      int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
      if (resultCode != ConnectionResult.SUCCESS) {
         if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
         } else {
            ShowAlert("Este dispositivo não suporta o GooglePlayServices");
         }
         return false;
      }
      return true;
   }
   
   private class Registrar extends AsyncTask<String, String, String> {
      private static final String SENDER_ID = "210077989010";
      private Exception err;

      @Override
      protected String doInBackground(String... args) {
         GoogleCloudMessaging gcm;
         String regid;
         try {
            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            regid = gcm.register(SENDER_ID);
            Log.e("RegId", regid);
            return regid;
         } catch (IOException ex) {
            err = ex;
            return "Falha";
         }
      }

      @Override
      protected void onPostExecute(String str) {
         if (str.equals("Falha")){
            ShowAlert("Erro ao Registrar dispositivo: "+err.getMessage());
         }else{
            SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("confDevID", str);
            editor.commit();
         }
      }
   }

   private void ShowAlert(String msg) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);

      builder.setTitle("Google Play Services")
             .setMessage(msg)
             .setIcon(R.drawable.ic_focco)
             .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                        finish();
                     }
                  });

      builder.create().show();      
   }
}
