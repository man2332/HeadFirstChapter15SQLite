package com.example.man2332.headfirstchapter15sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//-we will create a animal database
public class ExampleSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "animals";
    private static final int DB_VERSION = 1;

    public ExampleSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //runs once when db file is created & user requests access to db - like .getReadableDatabase()
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE BUGS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "DESCRIPTION TEXT, "
                + "AGE INTEGER);");
        insertValues(db,"Stick","eeeeekk", 4);
        Log.d("MTag", "onCreate: ");
    }

    public void insertValues(SQLiteDatabase db, String name, String Description, int age){
        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        cv.put("DESCRIPTION",Description);
        cv.put("AGE", age);
        db.insert("BUGS",null,cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS BUGS");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onDowngrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS BUGS");
        onCreate(db);
    }
}
//-onCreate() - called when we first call to create our db
//  -an empty database is created on the device the first time you need a db - then the onCreate()
//  method is called. The Object passed in is the db that was just created.
//  -ONLY run once when the db file is first created or when u explicitly call it like in onUpgrade()
//-onUpgrade() - called when we modify structure of db after it's been released
//-CONSTRUCTOR needs 2 things(NAME and VERSION)
// -NAME - ensures db will remain on device after app closes-if no name given
//  the db will not be saved
//  -VERSION - Integer value starting at 1-SQLite helper uses this int to determine if
//      the db needs to be upgraded
//  -pass these 2 things to the ctor of the SQLIteOpenHelper superclass
//  -the SQLite database doesn't get created at ...
//-SQLiteDatabase execSQL(String)- this method executes sql on the db
//-CONTENTVALUES contentValues = new ContentValues()- holds set of data values that will be inserted in the db
//  -one content value = one row of data u can insert
//  -ContentValues.put(String Column, T Value) - yea....
//db.insert(DB_NAME, null, contentValues) - inserts a row into the db and returns id of the record
//  - returns -1 if fail to insert
//-use a insert method if u need many inserts like insert(dbName, values...){...}
//-what SQLite helper code does
//  -user installs app and launches it- when app needs access to database, the helper checks if db exists
//  -if db doesn't exist, it gets create-its given name and version number specified in SQLite helper ctor
//  -after database(file) is created-onCreate() is called to create tables
//-ONUPGRADE()-ONDOWNGRADE()-You may change the db structure-like add a new column or remove a column,etc
//-Two user scenarios that might happen you make changes to your db structure
//- 1. User first time installing app- 2.User has app but with old version
//  -1.if SQLite helper detects user has never installed app/db doesn't exist, it just calls onCreate()
//  -2.if SQLite detects user has old version of app when it's run-it calls onUpgrade() or onDownGrade()
//-change DB_VERSION to higher number if u want to call upgrade, or lower if you want to downgrade(undo changes)
//-once either onUpgrade() or onDowngrade() is called-it changes the SQLite helper's db version number to match DB_VERSION


//SQL-
//-some columns can be specified as primay keys-won't allow duplicates-these columns identifies a single row
//-android code is hardwired to expect a numeric _id column
//-each column is one type of data
//-INTEGER(Any integer type)-TEXT(Any character type)-REAL(Any floating point number)-
// NUMERIC(Booleans,dates, and date-times)-BLOB(binary large objects)
//-database language- SQL - talks to db -
