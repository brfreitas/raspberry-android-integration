package android.curso.com.br.rasperdroid.Adapter;

import android.content.Context;
import android.curso.com.br.rasperdroid.R;
import android.curso.com.br.rasperdroid.model.Dispositivo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bruno on 28/08/2015.
 */
public class AdapterListView extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Dispositivo> itens;

    public AdapterListView(Context context, List<Dispositivo> itens) {
    this.itens = itens;
    mInflater = LayoutInflater.from(context); }


    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Dispositivo getItem(int i) {
        return itens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return itens.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ItemSuporte itemHolder;
        //se a view estiver nula (nunca criada), inflamos o layout nela.
        if (view == null) {
            view = mInflater.inflate(R.layout.dispositivo_list_item, null);
            itemHolder = new ItemSuporte();
            itemHolder.description = ((TextView) view.findViewById(R.id.tv_description));
            itemHolder.state = ((Switch) view.findViewById(R.id.sw_state));
            view.setTag(itemHolder);
        } else {
            itemHolder = (ItemSuporte) view.getTag();
        }
        Dispositivo item = itens.get(i);
        itemHolder.description.setText(item.getDescription());
        itemHolder.state.setChecked(Dispositivo.State.ON.equals(item.getState())?true:false);
        return view;
    }

    private class ItemSuporte {
        TextView description;
        Switch state;
    }


}
