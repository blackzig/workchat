package br.edu.ifspsaocarlos.sdm.workchat;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;

import br.edu.ifspsaocarlos.sdm.workchat.api.NameGenerator;
import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;
import br.edu.ifspsaocarlos.sdm.workchat.login.LoginDAO;
import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;

import br.edu.ifspsaocarlos.sdm.workchat.models.FullName;
import br.edu.ifspsaocarlos.sdm.workchat.models.User;
import br.edu.ifspsaocarlos.sdm.workchat.service.Endpoint;

import br.edu.ifspsaocarlos.sdm.workchat.service.InfoContact;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView newRegister;
    EditText etLogin, etPassword;
    Button login;

    private MensageiroApi mensageiroApi;
    private NameGenerator nameGenerator;

    Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        newRegister = findViewById(R.id.tv_make_new_register);
        newRegister.setOnClickListener(this);

        login = findViewById(R.id.bt_make_login);
        login.setOnClickListener(this);

        etLogin = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_make_login:
                login();
                break;
            case R.id.tv_make_new_register:
                startActivity(new Intent(MainActivity.this, NewUserActivity.class));
                break;
        }
    }

    private void login() {
        User user = new User(etLogin.getText().toString().trim(), etPassword.getText().toString().trim());

        Boolean itsOk = userData();

        if (itsOk == true) {
            LoginDAO loginDAO = new LoginDAO(this);
            User u = loginDAO.login(user);
            loginDAO.close();

            if (u != null) {
                ValuesStatics.setIdUser(u.getId());
                ValuesStatics.setNICKNAME(u.getLogin());

                InfoContact infoContact = new InfoContact();
                infoContact.returnUserData(this, u);

                startActivity(new Intent(MainActivity.this, MainTabActivity.class));
            } else {
                //pode fazer uma rotina para caso alguém zere o web service nobile
                Toast.makeText(this, "Login ou senha erradas.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private Boolean userData() {
        Boolean itsOk = true;
        User user;
        LoginDAO loginDAO = new LoginDAO(this);
        user = loginDAO.userData(etLogin.getText().toString().trim());
        Log.i("User>>>", String.valueOf(user));

        Contato contato = new Contato();
        contato.setId(user.getId());
        contato.setApelido(user.getLogin());

        InfoContact infoContact = new InfoContact();
        infoContact.userData(this, contato);

        return itsOk;
    }
}



