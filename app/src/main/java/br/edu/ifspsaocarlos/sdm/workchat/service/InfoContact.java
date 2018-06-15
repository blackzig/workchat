package br.edu.ifspsaocarlos.sdm.workchat.service;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.workchat.api.NameGenerator;
import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;
import br.edu.ifspsaocarlos.sdm.workchat.login.LoginDAO;
import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;
import br.edu.ifspsaocarlos.sdm.workchat.models.FullName;
import br.edu.ifspsaocarlos.sdm.workchat.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoContact {

    private MensageiroApi mensageiroApi;
    private NameGenerator nameGenerator;

    Contato contato;

    public void contactData(final Activity activity, Contato u) {
        final Endpoint endpoint = new Endpoint();
        mensageiroApi = endpoint.mensageiroAPI();
        final Contato[] c = {null};

        Call<Contato> callContato = mensageiroApi.getContato(u.getId());
        callContato.enqueue(new Callback<Contato>() {

            @Override
            public void onResponse(Call<Contato> call, Response<Contato> response) {
                contato = response.body();
                c[0] = new Contato(contato.getNomeCompleto(), contato.getApelido(), contato.getId());
                Log.i("contato>>>", String.valueOf(c[0]));

                LoginDAO loginDAO = new LoginDAO(activity);
                Contato contact = loginDAO.contactData(c[0].getId());

                Log.i("Nome>>>", contact.getNomeCompleto());

                if (!contact.getNomeCompleto().equals(c[0].getNomeCompleto())) {
                    //remove dos contatos porque alguém mudou.
                    loginDAO.deleteContact(contact.getId());
                }
            }

            @Override
            public void onFailure(Call<Contato> call, Throwable t) {
                Toast.makeText(activity, "Erro ao trazer as informações do usuário.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void userData(final Activity activity, final Contato u) {

        final Endpoint endpoint = new Endpoint();
        mensageiroApi = endpoint.mensageiroAPI();
        final Contato[] c = {null};

        Call<Contato> callContato = mensageiroApi.getContato(u.getId());
        callContato.enqueue(new Callback<Contato>() {

            @Override
            public void onResponse(Call<Contato> call, Response<Contato> response) {
                contato = response.body();
                c[0] = new Contato(contato.getNomeCompleto(), contato.getApelido(), contato.getId());
                Log.i("contatoUser>>>", String.valueOf(c[0]));

                LoginDAO loginDAO = new LoginDAO(activity);

                if (!u.getApelido().equals(c[0].getApelido())) {
                    //remove dos usuários porque alguém mudou no nobile
                    loginDAO.deleteUser(u.getId());
                }
            }

            @Override
            public void onFailure(Call<Contato> call, Throwable t) {
                String error = t.getMessage();

                LoginDAO loginDAO = new LoginDAO(activity);
                loginDAO.deleteUser(u.getId());

                Log.i("Falhou>>>", error);
            }
        });
    }

    public void returnUserData(final Activity activity, User u) {
        final Endpoint endpoint = new Endpoint();
        mensageiroApi = endpoint.mensageiroAPI();

        Call<Contato> callContato = mensageiroApi.getContato(u.getId());
        callContato.enqueue(new Callback<Contato>() {

            @Override
            public void onResponse(Call<Contato> call, Response<Contato> response) {
                contato = response.body();
                Contato c = new Contato(contato.getNomeCompleto(), contato.getApelido(), contato.getId());

                if (!ValuesStatics.getNICKNAME().equalsIgnoreCase(c.getApelido())) {
                    changedNickname(activity);
                }
            }

            @Override
            public void onFailure(Call<Contato> call, Throwable t) {
                Toast.makeText(activity, "Erro ao trazer as informações do usuário.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void changedNickname(final Activity activity) {
        final Endpoint endpoint = new Endpoint();
        nameGenerator = endpoint.nameGenerator();

        Call<FullName> callF = nameGenerator.getFullNameRaw();
        callF.enqueue(new Callback<FullName>() {

            @Override
            public void onResponse(Call<FullName> call, Response<FullName> response) {
                String fullName = response.body().getName();
                returnsToTheOldNickname(activity, fullName);
            }

            @Override
            public void onFailure(Call<FullName> call, Throwable t) {
                Toast.makeText(activity, "Erro ao gerar um nome.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void returnsToTheOldNickname(final Activity activity, String fullName) {
        final Endpoint endpoint = new Endpoint();
        mensageiroApi = endpoint.mensageiroAPI();

        Contato contatoU = new Contato(
                fullName,
                ValuesStatics.getNICKNAME(),
                ValuesStatics.getIdUser()
        );

        mensageiroApi.updateNameMyPerfil(
                ValuesStatics.getIdUser(),
                contatoU
        ).enqueue(new Callback<Contato>() {
            @Override
            public void onResponse(Call<Contato> call, Response<Contato> response) {
                String fullName = response.body().getNomeCompleto();
                Log.i("fullName>>>", fullName);
            }

            @Override
            public void onFailure(Call<Contato> call, Throwable t) {
                Toast.makeText(activity, "Erro ao tentar atualizar o nome do perfil.", Toast.LENGTH_LONG).show();
            }
        });
    }

}
