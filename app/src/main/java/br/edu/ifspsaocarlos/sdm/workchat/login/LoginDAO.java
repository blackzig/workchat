package br.edu.ifspsaocarlos.sdm.workchat.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;
import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;
import br.edu.ifspsaocarlos.sdm.workchat.models.Talk;
import br.edu.ifspsaocarlos.sdm.workchat.models.User;

/**
 * Created by zigui on 12/04/2018.
 */

public class LoginDAO extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE User(" +
                    "id TEXT unique PRIMARY KEY, " +
                    "login TEXT not null, " +
                    "password TEXT not null);";

    private static final String CREATE_TABLE_CONTATO =
            "CREATE TABLE Contato(" +
                    "id TEXT PRIMARY KEY, " +
                    "nome_completo TEXT not null, " +
                    "id_user TEXT not null);";

    private static final String CREATE_TABLE_TALK =
            "CREATE TABLE Talk(" +
                    "id TEXT PRIMARY KEY, " +
                    "id_talk TEXT, " +
                    "id_user TEXT, " +
                    "id_contact TEXT);";

    public LoginDAO(Context context) {
        super(context, "WorkChat", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CONTATO);
        db.execSQL(CREATE_TABLE_TALK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Contato");
        db.execSQL("DROP TABLE IF EXISTS Talk");

        // create new tables
        onCreate(db);
    }

    public void insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("login", user.getLogin());
        values.put("password", user.getPassword());

        db.insert("User", null, values);
    }

    public User userData(String login) {
        SQLiteDatabase db = getReadableDatabase();
        User contato = new User();

        String columns[] = {"id", "login"};
        Cursor c = db.query("User", //Table to query
                columns,
                "login = ?",
                new String[]{login},              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        while (c.moveToNext()) {
            contato.setId(c.getString(0));
            contato.setLogin(c.getString(1));
        }
        c.close();
        db.close();
        return contato;
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

    public boolean deleteUser(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("User", "id" + "=" + id, null) > 0;
    }

    public void insertContato(Contato contato) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", contato.getId());
        values.put("nome_completo", contato.getNomeCompleto());
        values.put("id_user", contato.getIdUser());

        db.insert("Contato", null, values);
    }

    public List<Contato> buscaTodosContatos() {
        SQLiteDatabase db = getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        String columns[] = {"id", "nome_completo"};
        Cursor c = db.query("Contato", //Table to query
                columns,                    //columns to return
                "id_user = ?",                  //columns for the WHERE clause
                new String[]{ValuesStatics.getIdUser()},              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                "nome_completo");                      //The sort order

        while (c.moveToNext()) {
            Contato contato = new Contato();
            contato.setId(c.getString(0));
            contato.setNomeCompleto(c.getString(1));
            contatos.add(contato);
        }
        c.close();
        db.close();
        return contatos;
    }

    public Contato contactData(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Contato contato = new Contato();

        String columns[] = {"id", "nome_completo"};
        Cursor c = db.query("Contato", //Table to query
                columns,
                "id = ?",
                new String[]{id},              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        while (c.moveToNext()) {
            contato.setId(c.getString(0));
            contato.setNomeCompleto(c.getString(1));
        }
        c.close();
        db.close();
        return contato;
    }

    public boolean deleteContact(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Contato", "id" + "=" + id, null) > 0;
    }

    public Talk lastTalk(Long idContact) {
        Log.i("idContact>>>", String.valueOf(idContact));
        SQLiteDatabase db = getReadableDatabase();

        Talk talk = new Talk();

        String columns[] = {"id_talk"};
        Cursor c = db.query("Talk", //Table to query
                columns,
                "id_contact = ?",
                new String[]{String.valueOf(idContact)},              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        while (c.moveToNext()) {
            talk.setIdContact(c.getString(0));
        }
        c.close();
        db.close();
        return talk;
    }

    public void insertTalk(Talk talk) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_talk", talk.getIdTalk());
        values.put("id_user", talk.getIdUser());
        values.put("id_contact", talk.getIdContact());

        db.insert("Talk", null, values);
    }

}
