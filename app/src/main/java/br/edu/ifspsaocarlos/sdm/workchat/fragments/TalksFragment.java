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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.workchat.R;
import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;
import br.edu.ifspsaocarlos.sdm.workchat.models.Mensagem;
import br.edu.ifspsaocarlos.sdm.workchat.service.Endpoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zigui on 26/04/2018.
 */

public class TalksFragment extends Fragment {

    private MensageiroApi mensageiroApi;
    private Button sendMessage;
    private EditText messageSent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_talks, container, false);

        messageSent = rootView.findViewById(R.id.et_message_to_send);
        sendMessage = rootView.findViewById(R.id.bt_send);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void verifyTalk(Long idContact) {
     /*   LoginDAO loginDAO = new LoginDAO(getActivity());
        Talk talk = loginDAO.lastTalk(idContact);

        if (talk == null) {
            Toast.makeText(getActivity(), "NADA", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void sendMessage(){

    }


    public void lastTalks(Long idContact) {
        final Endpoint endpoint = new Endpoint();
        mensageiroApi = endpoint.mensageiroAPI();
////http://www.nobile.pro.br/sdm4/mensageiro/rawmensagens/1/958058/958059
        Call<List<Mensagem>> mensagensDoRemetente = mensageiroApi.getMensagems(
                "1", ValuesStatics.getIdUser(), String.valueOf(idContact));

        mensagensDoRemetente.enqueue(new Callback<List<Mensagem>>() {

            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                List<Mensagem> listaMensagensRemetente = response.body();
                Log.i("corpo>>>", String.valueOf(response.body()));

                if (!listaMensagensRemetente.isEmpty()) {
                    //  cabecalhoMensagem.setText("Mensagens entre " + listaMensagensRemetente.get(0).getOrigem().getNomeCompleto()
                    //         + " e " + listaMensagensRemetente.get(0).getDestino().getNomeCompleto());

                    for (Mensagem m : listaMensagensRemetente) {
                        //listaMensagensFinal.add(m);
                        Log.i("Mensagem ", m.getAssunto());
                    }

                    //  mensagensDoDestinatario();

                } else {
                  //  Toast.makeText(getActivity(), "Não há nenhuma mensagem.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {

            }
        });

    }


}
