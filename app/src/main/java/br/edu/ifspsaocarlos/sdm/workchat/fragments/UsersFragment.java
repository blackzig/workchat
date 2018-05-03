package br.edu.ifspsaocarlos.sdm.workchat.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.workchat.MainActivity;
import br.edu.ifspsaocarlos.sdm.workchat.R;
import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;
import br.edu.ifspsaocarlos.sdm.workchat.login.LoginDAO;
import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;
import br.edu.ifspsaocarlos.sdm.workchat.service.Endpoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsersFragment extends Fragment implements View.OnClickListener {

    EditText idUserToSearch;
    Button goSearchUser;

    TextView idUser, nameUser;
    Button addUserToContacts;

    private MensageiroApi mensageiroApi;

    Contato contato;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);

        idUserToSearch = rootView.findViewById(R.id.et_idToAdd);
        goSearchUser = rootView.findViewById(R.id.bt_getDataUser);
        goSearchUser.setOnClickListener(this);

        idUser = rootView.findViewById(R.id.tv_idUser);
        nameUser = rootView.findViewById(R.id.tv_nameUser);

        addUserToContacts = rootView.findViewById(R.id.bt_addContact);
        addUserToContacts.setVisibility(View.INVISIBLE);
        addUserToContacts.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getDataUser:

                if (ValuesStatics.getIdUser().equalsIgnoreCase(idUserToSearch.getText().toString())) {
                    Toast.makeText(getActivity(), "Vai conversar com você mesmo!? \n " +
                            "Procure um psicólogo agora.", Toast.LENGTH_LONG).show();
                } else {
                    final Endpoint endpoint = new Endpoint();
                    mensageiroApi = endpoint.mensageiroAPI();

                    Call<Contato> callContato = mensageiroApi.getContato(idUserToSearch.getText().toString());
                    callContato.enqueue(new Callback<Contato>() {

                        @Override
                        public void onResponse(Call<Contato> call, Response<Contato> response) {
                            contato = response.body();
                            try {
                                Contato c = new Contato(contato.getNomeCompleto(), contato.getApelido(), contato.getId());

                                if (c != null) {
                                    idUser.setText("ID: " + c.getId());
                                    nameUser.setText("Nome: " + c.getNomeCompleto());
                                    addUserToContacts.setVisibility(View.VISIBLE);
                                }
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "Esse ID de usuário não existe.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Contato> call, Throwable t) {
                            Toast.makeText(getActivity(), "Esse ID de usuário não existe.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                break;
            case R.id.bt_addContact:
                //save contato no sqlite
                Contato c = new Contato();
                c.setId(contato.getId());
                c.setNomeCompleto(contato.getNomeCompleto());

                LoginDAO loginDAO = new LoginDAO(getActivity());
                loginDAO.insertContato(c);
                loginDAO.close();

                Toast.makeText(getActivity(), contato.getNomeCompleto() + " \n " +
                                "adicionado ao seus contatos",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }
}
