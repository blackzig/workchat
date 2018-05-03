package br.edu.ifspsaocarlos.sdm.workchat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ifspsaocarlos.sdm.workchat.R;

/**
 * Created by zigui on 26/04/2018.
 */

public class TalksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_talks, container, false);
        return rootView;
    }
}
