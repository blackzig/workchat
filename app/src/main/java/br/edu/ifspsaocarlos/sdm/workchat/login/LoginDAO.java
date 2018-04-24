package br.edu.ifspsaocarlos.sdm.workchat.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.edu.ifspsaocarlos.sdm.workchat.models.User;

/**
 * Created by zigui on 12/04/2018.
 */

public class LoginDAO extends SQLiteOpenHelper {

    public LoginDAO(Context context) {
        super(context, "WorkChat", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE User(id TEXT PRIMARY KEY, login TEXT not null, " +
                "password TEXT not null);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("login", user.getLogin());
        values.put("password", user.getPassword());

        db.insert("User", null, values);
    }

    public User login(User user) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            String args[] = {user.getLogin(), user.getPassword()};
            String columns[] = {"id", "login", "password"};
            String selection = "login = ?  AND password = ?";
            Cursor c = db.query("User", //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    args,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                       //filter by row groups
                    null);                      //The sort order

            c.moveToFirst();

            return new User(
                    c.getString(c.getColumnIndex("id")),
                    c.getString(c.getColumnIndex("login")),
                    c.getString(c.getColumnIndex("password"))
            );
        } catch (Exception e) {
            Log.i("Aviso login>", e.getMessage());
        }
        return null;
    }
}
