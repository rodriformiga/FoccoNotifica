package br.com.focco.focconotifica;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

public class Config extends Activity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getActionBar().setDisplayHomeAsUpEnabled(true);
      
      Bundle b = new Bundle();
      b.putString("packageName", getPackageName());
      Fragment frag = new ConfigFrag();
      frag.setArguments(b);
      getFragmentManager().beginTransaction().replace(android.R.id.content, frag ).commit();
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == android.R.id.home) {
         finish();
         return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
