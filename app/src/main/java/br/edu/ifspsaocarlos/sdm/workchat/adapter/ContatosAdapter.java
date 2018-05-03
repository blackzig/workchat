package br.edu.ifspsaocarlos.sdm.workchat.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;

public class ContatosAdapter extends BaseAdapter {
    private final List<Contato> list;
    private View rootView;

    public ContatosAdapter(View rootView, List<Contato> list) {
        this.list = list;
        this.rootView = rootView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(list.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(rootView.getContext());
        String nomeContato = list.get(position).getNomeCompleto();
        textView.setTextSize(26);
        textView.setTextColor(0xFF00FF00);
        textView.setText(nomeContato);
        return textView;
    }
}
