package com.example.mohamedahmedgomaa.servieclyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginData extends SQLiteOpenHelper {
    private static final String DbName="DbMaster";
    private static final String TableName="LoginCitizen";
    private static final String col1="LoginCitizen_Id";
    private static final String cols2="LoginCitizen_NId";
    private static final String cols3="LoginCitizen_Pass";
    private static final String cols4="LoginCitizen_Pin";


    public LoginData(Context context) {
        super(context, DbName, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TableName+" ("+col1+" INTEGER PRIMARY KEY ,"+cols2+" TEXT,"+cols3+" TEXT , "+cols4+" TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);

    }

            public  String insertLoginData( int id,String NId,String Pass,String PIN)
    {  try {


        ContentValues cv = new ContentValues();
        cv.put(col1, id);
        cv.put(cols2, NId);
        cv.put(cols3, Pass);
        cv.put(cols4, PIN);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TableName, null, cv);
        return "Success Data  Saved";
    }
    catch (Exception e)
    {
        return e.getMessage().toString();
    }
    }



    public Cursor selectAllData()
    {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TableName, null);
            return c;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public Cursor selectCitizen(String NId,String Pass)
    {    try {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = (Cursor) db.rawQuery("SELECT * FROM " + TableName + " WHERE " + cols2 + " = '" + NId+"' and "+cols3+" = '"+Pass+"' ", null);
        return c;

    } catch (Exception e)
    {
        return null;
    }

    }

    public Cursor selectCitizen(String PIN) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor c = (Cursor) db.rawQuery("SELECT * FROM " + TableName + " WHERE " + cols4 + " = '" + PIN + "' ", null);
            return c;

        } catch (Exception e) {
            return null;
        }
    }
}
