package br.edu.ifspsaocarlos.sdm.workchat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.workchat.R;
import br.edu.ifspsaocarlos.sdm.workchat.adapter.ContatosAdapter;
import br.edu.ifspsaocarlos.sdm.workchat.login.LoginDAO;
import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;

/**
 * Created by zigui on 26/04/2018.
 */

public class ContactsFragment extends Fragment {

    List<Contato> list = new ArrayList<>();
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contacts, container, false);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoginDAO loginDAO = new LoginDAO(getActivity());
        list = loginDAO.buscaTodosContatos();
        loginDAO.close();

        ListView listView = rootView.findViewById(R.id.lv_list_contacts);
        ContatosAdapter adapter = new ContatosAdapter(rootView, list);

      /*  ArrayAdapter<Contato> adapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);*/
        listView.setAdapter(adapter);
    }
}