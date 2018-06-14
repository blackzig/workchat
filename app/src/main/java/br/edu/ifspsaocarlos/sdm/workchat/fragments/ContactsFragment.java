package br.edu.ifspsaocarlos.sdm.workchat.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.workchat.R;
import br.edu.ifspsaocarlos.sdm.workchat.adapter.ContatosAdapter;
import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;
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
        ContatosAdapter adapter = new ContatosAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TalksFragment t = new TalksFragment();
                // t.getActivity().getIntent().putExtra("key", "value");
                //Bundle args = new Bundle();
             
                //  getActivity().getIntent().putExtra("key", "value");
                TabLayout tabhost = getActivity().findViewById(R.id.tabs);
                tabhost.getTabAt(1).select();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createDialog();
                return false;
            }

        });
    }

    private void createDialog() {

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        //adb.setView(Main.this);
        adb.setTitle("Atenção!!!");
        adb.setMessage("Deseja remover esse contato.");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getActivity(), "OK", Toast.LENGTH_LONG).show();
            }
        });

        adb.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getActivity(), "cancel", Toast.LENGTH_LONG).show();
                //finish();
            }
        });

        AlertDialog alertDialog = adb.create();
        alertDialog.show();
    }


}