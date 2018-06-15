package br.edu.ifspsaocarlos.sdm.workchat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.workchat.R;
import br.edu.ifspsaocarlos.sdm.workchat.conf.ValuesStatics;

/**
 * Created by zigui on 26/04/2018.
 */

public class TalksFragment extends Fragment {

    private Long idContact;
    private TextView teste;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_talks, container, false);

        teste = rootView.findViewById(R.id.talks);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public Long getIdContact() {
        return idContact;
    }

    public void setIdContact(Long idContact) {
        this.idContact = idContact;
    }
}
