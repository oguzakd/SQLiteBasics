package com.oguzhanakduman.sqliteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Oluşturulmuş bir database'i açma, oluşturulmamış ise database'i oluşturma
            SQLiteDatabase database = this.openOrCreateDatabase("Musicians", MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS musicians(id INTEGER PRIMARY KEY,name VARCHAR, age INTEGER)");

            // VERİ EKLEME
            database.execSQL("INSERT INTO musicians (name,age) VALUES ('James',50)");
            database.execSQL("INSERT INTO musicians (name,age) VALUES ('Lars', 60)");
            database.execSQL("INSERT INTO musicians (name,age) VALUES ('Kirk', 54)");


            // GÜNCELLEME
            // James'in yaşı 61 olarak güncellendi
            database.execSQL("UPDATE musicians SET age = 61 WHERE name = 'James'");

            // id'si 3 olan verinin name'i 'Kirk Hammett' olarak güncellendi
            database.execSQL("UPDATE musicians SET name = 'Kirk Hammett' WHERE id = 3");


            // SİLME
            // id'si 2 olan silindi
            database.execSQL("DELETE FROM musicians WHERE id = 2");


            // VERİ ÇEKMEK
            // musicians içindeki age değeri 52'den büyük olan verileri çekmek
            Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE age > 52", null);

            // Name değerinin sonu s ile biten verileri çekmek
            Cursor cursor1 = database.rawQuery("SELECT * FROM musicians WHERE name LIKE '%s'",null);

            // Name değerinin sonu K ile  başlayan verileri çekmek
            Cursor cursor2 = database.rawQuery("SELECT * FROM musicians WHERE name LIKE 'K%'",null);


            // Çekilen verilere erişebilmek için gerekli index değerleri
            int nameIx = cursor.getColumnIndex("name");
            int ageIx = cursor.getColumnIndex("age");

            // Veriyi konsola yazdırma
            while (cursor.moveToNext()){
                System.out.println("Name: " + cursor.getString(nameIx));
                System.out.println("Age: " + cursor.getInt(ageIx));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}