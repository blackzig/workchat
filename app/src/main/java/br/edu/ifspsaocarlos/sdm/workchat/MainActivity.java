package br.edu.ifspsaocarlos.sdm.workchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;
import br.edu.ifspsaocarlos.sdm.workchat.login.LoginDAO;
import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;
import br.edu.ifspsaocarlos.sdm.workchat.models.User;
import br.edu.ifspsaocarlos.sdm.workchat.service.Endpoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView newRegister;
    EditText etLogin, etPassword;
    Button login;

    private MensageiroApi mensageiroApi;

    Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                User user = new User(etLogin.getText().toString(), etPassword.getText().toString());

                LoginDAO loginDAO = new LoginDAO(this);
                User u = loginDAO.login(user);
                loginDAO.close();

                if (u != null) {
                    ValuesStatics.setIdUser(u.getId());
                    ValuesStatics.setNICKNAME(u.getLogin());

                    //buscar usuário no webservice nobile
                    Endpoint endpoint = new Endpoint();
                    mensageiroApi = endpoint.mensageiroAPI();

                    Call<Contato> call = mensageiroApi.getContato(u.getId());
                    call.enqueue(new Callback<Contato>() {
                        @Override
                        public void onResponse(Call<Contato> call, Response<Contato> response) {
                            try {
                                contato = response.body();
                                Contato c = new Contato(contato.getNomeCompleto(), contato.getApelido(), contato.getId());

                                if (ValuesStatics.getNICKNAME().equalsIgnoreCase(c.getApelido())) {
                                    Log.i("Aviso", "Certo");
                                } else {
                                    Log.i("Aviso", "Errou");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<Contato> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Erro ao trazer as informações do usuário.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    startActivity(new Intent(MainActivity.this, MainTabActivity.class));

                } else {
                    Toast.makeText(this, "Login ou senha erradas.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_make_new_register:
                startActivity(new Intent(MainActivity.this, NewUserActivity.class));
                break;
        }
    }


}
