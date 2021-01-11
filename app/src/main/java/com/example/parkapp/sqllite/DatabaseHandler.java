package com.example.parkapp.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.parkapp.models.HistoryTicket;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements Serializable {
    public static final String DATABASE_NAME = "HistoryDatabase";
    public static final String HISTORY_TABLE_NAME = "TicketsHistory";
    private SQLiteDatabase mDb;

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(
                    "create table "+ HISTORY_TABLE_NAME +"(id INTEGER PRIMARY KEY, datefrom text,dateto text,montant REAL )"
            );
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public SQLiteDatabase open() {
        mDb = this.getWritableDatabase();
        return mDb;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ HISTORY_TABLE_NAME);
        onCreate(db);
    }

    public long insert(String datefrom,String dateto,Float montant) {
        mDb = this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put("datefrom", datefrom);
        contentValues.put("dateto", dateto);
        contentValues.put("montant", montant);
        long i = mDb.insert(HISTORY_TABLE_NAME, null, contentValues);
        return i;
    }

    public ArrayList<HistoryTicket> getAllHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<HistoryTicket> array_list = new ArrayList<HistoryTicket>();
        Cursor res = db.rawQuery( "select * from "+ HISTORY_TABLE_NAME , null );
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            HistoryTicket h=new HistoryTicket(res.getInt(res.getColumnIndex("id")),res.getString(res.getColumnIndex("datefrom")),res.getString(res.getColumnIndex("dateto")),res.getInt(res.getColumnIndex("montant")));
            array_list.add(h);
            res.moveToNext();
        }
        return (ArrayList<HistoryTicket>) array_list;
    }

    public long getTicketsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, HISTORY_TABLE_NAME);
        db.close();
        return count;
    }

    public int getTotalProfit()
    {
        int total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(montant) FROM " + HISTORY_TABLE_NAME, null);
        if (cursor.moveToFirst())
        {
            total = cursor.getInt(0);
        }
        while (cursor.moveToNext());
        return total;
    }

    public int mostExpensiveTicket(){

        int max_montant = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(montant) FROM " + HISTORY_TABLE_NAME, null);
        if (cursor.moveToFirst())
        {
            max_montant = cursor.getInt(0);
        }
        while (cursor.moveToNext());
        return max_montant;
    }



    public boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from "+ HISTORY_TABLE_NAME  );
        return true;
    }

    public boolean deleteSingle(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from "+ HISTORY_TABLE_NAME + " WHERE id = "+id);
        return true;
    }
}
