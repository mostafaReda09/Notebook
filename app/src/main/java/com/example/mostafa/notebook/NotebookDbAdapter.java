package com.example.mostafa.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Mostafa on 6/27/2018.
 */

public class NotebookDbAdapter {
    private static final String DATABASE_NAME="notebook.db";
    private static final int DATABASE_VERSION=1;

    public static final String NOTE_TABLE="note";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_TITTLE="tittle";
    public static final String COLUMN_BODY="body";
    public static final String COLUMN_CATEGORY="category";
    private String[] allColumns={COLUMN_ID,COLUMN_TITTLE,COLUMN_BODY,COLUMN_CATEGORY};

    public static final String CREATE_NOTE_DATABASE="create table "+NOTE_TABLE + " ( " +

                                COLUMN_ID +" integer primary key autoincrement, "+
                                COLUMN_TITTLE +" text not null, "+
                                COLUMN_BODY +" text not null, "+
                                COLUMN_CATEGORY +" text not null " +");";



    private SQLiteDatabase sqlDb;
    private Context context;
    private NotebookDbHelper notebookDbHelper;
    public NotebookDbAdapter(Context context){
        this.context=context;
    }
    public NotebookDbAdapter open() throws android.database.SQLException
    {
        notebookDbHelper=new NotebookDbHelper(context);
        sqlDb=notebookDbHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        notebookDbHelper.close();
    }

    public ArrayList<Note> getAllNotes(){
        ArrayList <Note> notes=new ArrayList <Note>();
        Cursor cursor=sqlDb.query(NOTE_TABLE,allColumns,null,null,null,null,null);
        for (cursor.moveToLast();!cursor.isBeforeFirst();cursor.moveToPrevious()){
            Note note=cursorToNote(cursor);
            notes.add(note);
        }
        cursor.close();
        return notes;
    }
    private Note cursorToNote(Cursor cursor){
        Note newNote=new Note(cursor.getString(1),cursor.getString(2),Note.Category.valueOf(cursor.getString(3)),cursor.getInt(0));
        return newNote;
    }
    public Note addNote(String tittle,String body,Note.Category category){
        ContentValues values=new ContentValues();
        values.put(COLUMN_TITTLE,tittle);
        values.put(COLUMN_BODY,body);
        values.put(COLUMN_CATEGORY,category.name());
        int id=(int) sqlDb.insert(NOTE_TABLE,null,values);
        Cursor cursor=sqlDb.query(NOTE_TABLE,allColumns,COLUMN_ID + "=" +id,null,null,null,null);
        cursor.moveToFirst();
        Note note=cursorToNote(cursor);
        cursor.close();
        return note;


    }
    public int updateNote(int id,String tittle,String body,Note.Category category)
    {
        ContentValues values=new ContentValues();
        values.put(COLUMN_TITTLE,tittle);
        values.put(COLUMN_BODY,body);
        values.put(COLUMN_CATEGORY,category.name());
        return  sqlDb.update(NOTE_TABLE,values,COLUMN_ID + "=" + id ,null);

    }
    public int deleteNote(int id){
        return sqlDb.delete(NOTE_TABLE,COLUMN_ID  + "=" +id,null);
    }
    private static class NotebookDbHelper extends SQLiteOpenHelper{

        public NotebookDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_NOTE_DATABASE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exist " + NOTE_TABLE);
            onCreate(db);
        }
    }
}
