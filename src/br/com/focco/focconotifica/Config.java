package br.com.focco.focconotifica;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.MenuItem;

public class Config extends Activity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getActionBar().setDisplayHomeAsUpEnabled(true);
      getFragmentManager().beginTransaction().replace(android.R.id.content, new ConfigFrag()).commit();
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == android.R.id.home) {
         finish();
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   public class ConfigFrag extends PreferenceFragment implements OnSharedPreferenceChangeListener {
      @Override
      public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         addPreferencesFromResource(R.xml.config);
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
            String str = etp.getText() == null ? "" : "\n"+etp.getText();
            /*if (!str.isEmpty()) {
               pref.setSummary(str);
            }else*/
               pref.setSummary(getStrFromResourse(pref.getKey()+"Sum")+str);
         } else if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            listPref.setSummary(listPref.getEntry());
         }else if (pref instanceof PreferenceScreen){
            initSumPreference((PreferenceScreen)pref);
         }
      }
      
      private String getStrFromResourse(String id){
         int i = getResources().getIdentifier(id, "string", getPackageName());
         return i == 0 ? "" : (String) getResources().getText(i);
      }
   }
}
