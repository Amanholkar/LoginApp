package com.example.loginapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.loginapp.R;
import com.example.loginapp.modal.User;
import com.example.loginapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final Context context;
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_NAME + " TEXT,"
                + Util.KEY_EMAIL + " TEXT,"
                + Util.KEY_PASSWORD + " TEXT,"
                + Util.KEY_PHONE_NUMBER + " INTEGER,"
                + Util.KEY_DATE + " INTEGER,"
                + Util.KEY_GENDER + " TEXT,"
                + Util.KEY_HOBBIES + " TEXT "+")";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        //Create a table again
        onCreate(db);
    }


    //Add Contact
    public void addContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, user.getName());
        values.put(Util.KEY_EMAIL, user.getEmail());
        values.put(Util.KEY_PASSWORD, user.getPassword());
        values.put(Util.KEY_PHONE_NUMBER, user.getPhoneNumber());
        values.put(Util.KEY_DATE, user.getDate());
        values.put(Util.KEY_GENDER, user.getGender());
        values.put(Util.KEY_HOBBIES, user.getHobbies());
        //Insert to row
        db.insert(Util.TABLE_NAME, null, values);

        Log.d("DBHandler", "addContact: " + "item added");
        db.close(); //closing db connection!


    }


    //Get all Contacts
    public List<User> getAllContacts() {
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //Loop through our data
        if (cursor.moveToFirst()) {
            do {

                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setPhoneNumber(cursor.getString(4));
                user.setDate(cursor.getString(5));
                user.setGender(cursor.getString(6));
                user.setHobbies(cursor.getString(7));

                //add contact objects to our list
                userList.add(user);
            }while (cursor.moveToNext());
        }

        return userList;
    }


    public User Authenticate( User user) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.i(getClass().getName(),"user  "+user.getEmail());



        Cursor  cursor = db.rawQuery("SELECT *FROM "+Util.TABLE_NAME +" WHERE "+Util.KEY_EMAIL+"=? AND "+Util.KEY_PASSWORD +"=?",new String[] {user.getEmail(), user.getPassword()});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                Log.i(getClass().getName(),"ppp11  "+cursor.getCount());
                User user1 = new User(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
//           Log.i(getClass().getName(),"ppp2222  "+cursor.getString(0));
           Log.i(getClass().getName(),"ppp2222  "+cursor.getString(cursor.getColumnIndex(Util.KEY_EMAIL)));

             return user1;

//           Log.i(getClass().getName(),"ppp2222  "+user1.getEmail());
            }
        }




//        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
//            //if cursor has value then in user database there is user associated with this given email
//            Log.i(getClass().getName(),"ppp11  "+user.getPassword());
//           User user1 = new User(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
//            Log.i(getClass().getName(),"ppp2222  "+user.getPassword());
//            //Match both passwords check they are same or not
//            if (user.getPassword().equalsIgnoreCase(user1.getPassword())) {
//                return user1;
//            }
//        }

        //if user password does not matches or there is no record with that email then return @false
       return null;
    }

    //Delete single contact
    public void deleteContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(user.getId())});

        db.close();
    }


    //Update contact
    public int updateContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, user.getName());
        values.put(Util.KEY_EMAIL, user.getEmail());
        values.put(Util.KEY_PASSWORD, user.getPassword());
        values.put(Util.KEY_PHONE_NUMBER, user.getPhoneNumber());
        values.put(Util.KEY_DATE, user.getDate());
        values.put(Util.KEY_GENDER, user.getGender());
        values.put(Util.KEY_HOBBIES,user.getHobbies());

        //update the row
        //update(tablename, values, where id = 43)
        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{String.valueOf(user.getId())});
    }

}
