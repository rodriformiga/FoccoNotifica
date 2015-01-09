package br.com.focco.focconotifica;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

public class Config extends Activity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getActionBar().setDisplayHomeAsUpEnabled(true);
      getFragmentManager().beginTransaction()
            .replace(android.R.id.content, new ConfigFrag()).commit();
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == android.R.id.home) {
         finish();
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   private class ConfigFrag extends PreferenceFragment implements
         OnSharedPreferenceChangeListener {
      @Override
      public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         addPreferencesFromResource(R.xml.configurar);
         SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
         sp.registerOnSharedPreferenceChangeListener(this);
         // FIXME Ajustar para que quando carregue as configurações também exiba os valores de campos texto.
      }

      @Override
      public void onSharedPreferenceChanged(
            SharedPreferences sharedPreferences, String key) {
         Preference pref = findPreference(key);
         if (pref instanceof EditTextPreference) {
            EditTextPreference etp = (EditTextPreference) pref;
            pref.setSummary(etp.getText());
         }
      }
   }
}
