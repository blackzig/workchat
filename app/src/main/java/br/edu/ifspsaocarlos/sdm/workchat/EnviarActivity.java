package br.edu.ifspsaocarlos.sdm.workchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;
import br.edu.ifspsaocarlos.sdm.workchat.models.Mensagem;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnviarActivity extends AppCompatActivity {

    private EditText assuntoEt;
    private EditText corpoEt;
    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        assuntoEt = findViewById(R.id.et_assunto_enviar);
        corpoEt = findViewById(R.id.et_corpo_enviar);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getString(R.string.url_base));
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
        mensageiroApi = retrofit.create(MensageiroApi.class);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.bt_enviar) {
            Mensagem mensagem = new Mensagem(
                    ValuesStatics.getIdUser(),
                    "1",
                    assuntoEt.getText().toString(),
                    corpoEt.getText().toString());

            RequestBody mensagemRb = RequestBody.create(MediaType.parse("application/json"),
                    gson.toJson(mensagem));

            mensageiroApi.postMensagem(mensagemRb).enqueue(
                    new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call,
                                               Response<ResponseBody> response) {
                            Toast.makeText(EnviarActivity.this, "Mensagem enviada!",
                                    Toast.LENGTH_SHORT).show();
                            limparCampos();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(EnviarActivity.this, "Erro no envio da mensagem !",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }

    private void limparCampos() {
        assuntoEt.setText("");
        corpoEt.setText("");
    }

}
