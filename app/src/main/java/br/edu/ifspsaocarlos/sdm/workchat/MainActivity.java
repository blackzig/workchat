package br.edu.ifspsaocarlos.sdm.workchat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;

import br.edu.ifspsaocarlos.sdm.workchat.api.NameGenerator;
import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;
import br.edu.ifspsaocarlos.sdm.workchat.login.LoginDAO;
import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;

import br.edu.ifspsaocarlos.sdm.workchat.models.User;

import br.edu.ifspsaocarlos.sdm.workchat.service.InfoContact;
import br.edu.ifspsaocarlos.sdm.workchat.service.VerifyMessages;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView newRegister;
    EditText etLogin, etPassword;
    Button login;

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

        VerifyMessages vm = new VerifyMessages();
        vm.execute();
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
        // notifyThis();
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

    public void notifyThis() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icons8red)
                .setTicker("Hearty365")
                .setContentTitle("Default notification")
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }
}



