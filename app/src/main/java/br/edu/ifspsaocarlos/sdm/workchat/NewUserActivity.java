package br.edu.ifspsaocarlos.sdm.workchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import java.io.IOException;

import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.workchat.login.LoginDAO;
import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;
import br.edu.ifspsaocarlos.sdm.workchat.models.User;
import br.edu.ifspsaocarlos.sdm.workchat.service.Endpoint;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewUserActivity extends AppCompatActivity {

    EditText fullName, nickname, password, passwordAgain;
    Button register;

    private MensageiroApi mensageiroApi;
    private Gson gson;

    Contato novoContato = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        fullName = findViewById(R.id.et_full_name);
        nickname = findViewById(R.id.et_nickname);
        password = findViewById(R.id.et_password_register);
        passwordAgain = findViewById(R.id.et_password_register_repeat);
        register = findViewById(R.id.bt_new_user);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save user no webservice nobile
                Endpoint endpoint = new Endpoint();
                mensageiroApi = endpoint.mensageiroAPI();

                Contato contato = new Contato(
                        fullName.getText().toString(),
                        nickname.getText().toString()
                );

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                        gson.toJson(contato));

                mensageiroApi.postNewContact(requestBody).enqueue(
                        new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call,
                                                   Response<ResponseBody> response) {

                                try {
                                    String responseBodyString = response.body().string();
                                    novoContato = gson.fromJson(responseBodyString, Contato.class);

                                    //save user no sqlite
                                    User user = new User(
                                            novoContato.getId(),
                                            nickname.getText().toString(),
                                            password.getText().toString()
                                    );

                                    LoginDAO loginDAO = new LoginDAO(NewUserActivity.this);
                                    loginDAO.insert(user);
                                    loginDAO.close();

                                    finish();

                                    Toast.makeText(NewUserActivity.this, "Contato registrado!",
                                            Toast.LENGTH_SHORT).show();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(NewUserActivity.this, "Erro ao registrar novo contato!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }
        });

    }
}
