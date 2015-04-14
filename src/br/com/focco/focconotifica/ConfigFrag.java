package br.com.focco.focconotifica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;

public class ConfigFrag extends PreferenceFragment implements OnSharedPreferenceChangeListener {
   private String packageName;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.config);

      //busca o nome da package para pegar as informações das Preferences
      if (getArguments() != null) {
         packageName = getArguments().getString("packageName");
      }

      //Ao clicar na preferencia "confDevID" envia um email para registrar o ID do dispositivo.
      Preference chave = (Preference) findPreference("confDevID");
      chave.setOnPreferenceClickListener(new OnPreferenceClickListener() {
         @Override
         public boolean onPreferenceClick(Preference pref) {
            Intent intent = new Intent( Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            //intent.putExtra(Intent.EXTRA_EMAIL, new String[]{TRANSLATE_LNK} );
            intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name)+" - Dispositivo ID" );
            intent.putExtra(Intent.EXTRA_TEXT, "Dispositivo de numero XXXX tem ID: ");

            startActivity( Intent.createChooser(intent, "Enviar ID de dispositivo") );
            return false;
         }
      });
      
      initSumPreference(getPreferenceScreen());
   }

   @Override
   public void onResume() {
      super.onResume();
      getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
   }

   @Override
   public void onPause() {
      super.onPause();
      getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
   }

   @Override
   public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      Preference pref = findPreference(key);
      if (key.equals("confServEnder"))
         Log.d("Conf", "conf");
      //FIXME
      //Colocar aqui uma validacao para verificar se a URL e v�lida
      //if Util.isConnectedToServer(url, timeout)
      
      initPreference(pref);
   }

   private void initSumPreference(PreferenceScreen prefScreen) {
      for (int i = 0; i < prefScreen.getPreferenceCount(); i++) {
         Preference p = prefScreen.getPreference(i);
         if (p instanceof PreferenceCategory) {
            PreferenceCategory pCat = (PreferenceCategory) p;
            for (int j = 0; j < pCat.getPreferenceCount(); j++) {
               initPreference(pCat.getPreference(j));
            }
         } else {
            initPreference(p);
         }
      }
   }

   private void initPreference(Preference pref) {
      if (pref == null)
         return;

      if (pref instanceof EditTextPreference) {
         EditTextPreference etp = (EditTextPreference) pref;
         String str = getStrFromResourse(pref.getKey()+"Sum");
         
         str += etp.getText() == null ? "" : ": "+etp.getText();
         /*if (!str.isEmpty()) {
            pref.setSummary(str);
         }else*/
            pref.setSummary(str);
      } else if (pref instanceof ListPreference) {
         ListPreference listPref = (ListPreference) pref;
         listPref.setSummary(listPref.getEntry());
      }else if (pref instanceof PreferenceScreen){
         initSumPreference((PreferenceScreen)pref);
      }
   }
   
   private String getStrFromResourse(String id){
      int i = getResources().getIdentifier(id, "string", packageName);
      return i == 0 ? "" : (String) getResources().getText(i);
   }
}
