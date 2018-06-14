package br.edu.ifspsaocarlos.sdm.workchat.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.workchat.R;
import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;

public class ContatosAdapter extends BaseAdapter {

    private final List<Contato> list;
    private Activity act;

    public ContatosAdapter(Activity act, List<Contato> list) {
        this.list = list;
        this.act = act;
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
        View view = act.getLayoutInflater()
                .inflate(R.layout.item_contact, parent, false);
        Contato contato = list.get(position);

        //pegando as referências das Views
        TextView name =
                view.findViewById(R.id.tv_name_contact);
        TextView nickname =
                view.findViewById(R.id.tv_id_contact);
        ImageView imagem =
                view.findViewById(R.id.iv_photo_contact);

        //populando as Views
        name.setText(contato.getNomeCompleto());
        nickname.setText(contato.getId());
        imagem.setImageResource(R.mipmap.ic_launcher);

        return view;
    }
}
