package br.edu.ifspsaocarlos.sdm.workchat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.workchat.R;
import br.edu.ifspsaocarlos.sdm.workchat.adapter.ChatAdapter;
import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;
import br.edu.ifspsaocarlos.sdm.workchat.login.LoginDAO;
import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;
import br.edu.ifspsaocarlos.sdm.workchat.models.Mensagem;
import br.edu.ifspsaocarlos.sdm.workchat.models.Talk;
import br.edu.ifspsaocarlos.sdm.workchat.service.Endpoint;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zigui on 26/04/2018.
 */

public class TalksFragment extends Fragment {

    private MensageiroApi mensageiroApi;
    private Gson gson;

    private Button sendMessage;
    private EditText messageSent;
    private EditText subject;

    ListView listaChat;
    LinearLayout listaLinearLayout;
    TextView cabecalhoMensagem;
    View rootView;

    List<Mensagem> listaMensagensFinal = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_enviar, container, false);

        subject = rootView.findViewById(R.id.et_assunto_enviar);
        messageSent = rootView.findViewById(R.id.et_corpo_enviar);
        sendMessage = rootView.findViewById(R.id.bt_enviar);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        listaChat = rootView.findViewById(R.id.lv_lista_do_chat_entre_remetente_e_destinatario);

        listaLinearLayout = rootView.findViewById(R.id.list_chat);

        cabecalhoMensagem = rootView.findViewById(R.id.tv_chat_entre);

        bringTalks();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
       // Toast.makeText(getActivity(), "bringTalks Resume", Toast.LENGTH_SHORT).show();
        bringTalks();
    }

    private void bringTalks() {
    //    Toast.makeText(getActivity(), "bringTalks", Toast.LENGTH_SHORT).show();
      /*  final Endpoint endpoint = new Endpoint();
        mensageiroApi = endpoint.mensageiroAPI();

        listaMensagensFinal.clear();

        Call<List<Mensagem>> mensagensDoRemetente = mensageiroApi.getMensagems(
                "0",
                ValuesStatics.getIdUser(),
                ValuesStatics.getIdContact());

        mensagensDoRemetente.enqueue(new Callback<List<Mensagem>>() {

            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                List<Mensagem> listaMensagensRemetente = response.body();

                if (!listaMensagensRemetente.isEmpty()) {
                    cabecalhoMensagem.setText("Mensagens entre " + listaMensagensRemetente.get(0).getOrigem().getNomeCompleto()
                            + " e " + listaMensagensRemetente.get(0).getDestino().getNomeCompleto());

                    for (Mensagem m : listaMensagensRemetente) {
                        listaMensagensFinal.add(m);
                    }

                    mensagensDoDestinatario();

                } else {
                    Toast.makeText(getActivity(), "Não há nenhuma mensagem.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {

            }
        });*/
    }


    public void mensagensDoDestinatario() {
        Call<List<Mensagem>> mensagensDoDestinatario = mensageiroApi.getMensagensDoDestinatario(
                "0",
                ValuesStatics.getIdContact(),
                ValuesStatics.getIdUser());

        mensagensDoDestinatario.enqueue(new Callback<List<Mensagem>>() {

            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                List<Mensagem> listaMensagensDestinatario = response.body();

                if (!listaMensagensDestinatario.isEmpty()) {

                    for (Mensagem m : listaMensagensDestinatario) {
                        listaMensagensFinal.add(m);
                    }

                    Collections.sort(listaMensagensFinal, new Comparator<Mensagem>() {
                        @Override
                        public int compare(Mensagem o1, Mensagem o2) {
                            return o1.getId().compareTo(o2.getId());
                        }
                    });
                }

                ChatAdapter messageAdapter = new ChatAdapter(listaMensagensFinal, TalksFragment.this);
                listaChat.setAdapter(messageAdapter);
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {

            }
        });

    }

    private void sendMessage() {

        if ((subject.getText().toString().isEmpty() || subject.getText().toString().equals(""))
                || (messageSent.getText().toString().isEmpty() || messageSent.getText().toString().equals(""))) {
            Toast.makeText(getActivity(), "Preencha o Assunto e a mensagem.", Toast.LENGTH_LONG).show();
        } else {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setLenient();
            gson = gsonBuilder.create();

            Endpoint endpoint = new Endpoint();
            mensageiroApi = endpoint.mensageiroAPI();

            Mensagem mensagem = new Mensagem(
                    ValuesStatics.getIdUser(),
                    ValuesStatics.getIdContact(),
                    subject.getText().toString(),
                    messageSent.getText().toString());

            RequestBody mensagemRb = RequestBody.create(MediaType.parse("application/json"),
                    gson.toJson(mensagem));

            mensageiroApi.postMensagem(mensagemRb).enqueue(
                    new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call,
                                               Response<ResponseBody> response) {
                            Toast.makeText(getActivity(), "Mensagem enviada!",
                                    Toast.LENGTH_LONG).show();
                            limparCampos();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "Erro no envio da mensagem !",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }
    }

    private void limparCampos() {
        subject.setText("");
        messageSent.setText("");
    }


}
