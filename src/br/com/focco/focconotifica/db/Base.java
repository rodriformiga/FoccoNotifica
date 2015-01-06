package br.com.focco.focconotifica.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.focco.focconotifica.Util;

public class Base extends SQLiteOpenHelper {
   private static final int VERSAO = 1;
   private static final String NOME = "focco.db";
   private Context context;

   public Base(Context context) {
      super(context, NOME, null, VERSAO);
      this.context = context;
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      for (int i = 1; i <= VERSAO; i++) {
         atualiza(db, i);
      }
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      atualiza(db, newVersion);
   }

   @SuppressLint("DefaultLocale")
   private void atualiza(SQLiteDatabase db, int version) {
      String arq = String.format("db/%d.sql", version);
      String sql = Util.getStringFromAsset(context, arq);
      //String sql2 = Util.getStringFromAsset(context, arq);
      db.execSQL(sql);
   }

}
