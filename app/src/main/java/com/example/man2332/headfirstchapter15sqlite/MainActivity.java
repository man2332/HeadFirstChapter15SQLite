package com.example.man2332.headfirstchapter15sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase db;
    Cursor cursor;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.testView);

        ////SQLite helper checks if there is already a db file & version of db
        sqLiteOpenHelper = new ExampleSQLiteHelper(this);


        update(5,"nOODLE");
        //deleteRow(6);

        query();

    }

    public void update(int row, String name){
        String id = Integer.toString(row);
        db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        db.update("BUGS",cv,"_id = ?",new String[]{id});
    }
    public void deleteRow(int id){
        String _id = Integer.toString(id);
        db = sqLiteOpenHelper.getWritableDatabase();
        db.delete("BUGS",
                "_id = ?",
                new String[]{_id});
    }
    public void query(){
        cursor = db.query("BUGS",
                new String[]{"_id","NAME","DESCRIPTION","AGE"},//_id will be column 0, name will be column 1, etc...
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            int id;
            String name;
            String desc;
            Integer age;
            id = cursor.getInt(0);//_id
            name = cursor.getString(1);//NAME
            desc = cursor.getString(2);//DEESCRIPTION
            age = cursor.getInt(3);//AGE
            Toast.makeText(this,"Row: "+id+" : " + name + " : " + desc + " : " + age, Toast.LENGTH_LONG).show();

            String table = "Row: "+id+" : " + name + " : " + desc + " : " + age+"\n";

            Log.d("MTag", "Row: "+cursor.getInt(0)+" : "+cursor.getString(1));
            while(cursor.moveToNext()){
                Log.d("MTag", "Row: "+cursor.getInt(0)+" : "+cursor.getString(1));
                table += "Row: "+cursor.getInt(0)+" : "+cursor.getString(1)+"\n";
            }
            textView.setText(table);
        }
    }
    public void addData(){
        db = sqLiteOpenHelper.getWritableDatabase();
        insertValues(db,"Spider","Scary",2);
        insertValues(db,"Snail","Slime",4);
    }
    public void insertValues(SQLiteDatabase db, String name, String Description, int age) {
        ContentValues cv = new ContentValues();
        //cv.put("_id", 1);
        cv.put("NAME", name);
        cv.put("DESCRIPTION", Description);
        cv.put("AGE", age);
        db.insert("BUGS", null, cv);
    }
}
//-lightweight-single user(no database-just a single file)
//-android auto creates folder(when we call SQLite to create it)for our database file and journal file(all changes made to your db) if
//  there is a problem android will use journal to undo your latest change
//-Android comes with SQLite
//-Android uses set of classes to manage the db - 3 main types of objects are
//-SQLiteHelper - create & manage database
//-SQLiteDatabase - gives access to the database

//-CURSORS - lets u read/write to the database
//  -you create cursors using a "Database query" - Cursor cursor = db.query(..)
//1.get a ref to the db 2.create a cursor to read from db 3.navigate to the record 4.profit
// SQLiteDatabase db = SQLiteOpenHelper.getReadableDatabase() or .getWriteableDatabase()
// DB.QUERY(STRING TABLENAME, NEW STRING[]{COLUMN,COLUMN}, STRING "CONDITION = ?", NEW STRING[]{ARG1},
//                    STRING GROUPBY, STRING HAVING, STRING ORDERBY)
//  -the cursor contains the records described by the query
//  -the query will return rows that match the conditions(if any)- the cursor will point to these rows
//-CUROSRS - cusor.moveToFirst() - .moveToLast() - .moveToPrevious() - .moveToNext()
//-CURSOR .get*() method: getString, getInt(), etc to get the value from the record the cursors pointing at
//  -getString(Column #) where Column # is the column in the cursors column

//-SQLiteDatabse delete() - db.delete()
//  -delete(tableName, String whereClause, new String[]{})

//WHAT HAPPENS WHEN CODE RUNS
//  -1.database first needs to be accessed, SQLite helper checks if db exists
//  -2a.if db doesn't exist, SQLite helper creats it and runs onCreate() method
//  -2b.if db exists, Sqlite helper checks version number of db file against version number in SQLite helper code(DB_VERSION)
//      -and calls onUpgrade() or onDowngrade() if version number is not same

//SQL commands
//ALTER TABLE - change an existing table
//RENAME TO - rename the table
//ADD COLUMN
//DROP TABLE
//-execute SQL using SQLiteDatabase execSQL(String) method

//-DON'T FORGET TO CLOSE THE CURSOE AND DB - CURSOR.CLOSE() - DB.CLOSE()