package android.curso.com.br.rasperdroid.Adapter;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.curso.com.br.rasperdroid.DispositivosFragment;
import android.curso.com.br.rasperdroid.HttpRequest;
import android.curso.com.br.rasperdroid.MainActivity;
import android.curso.com.br.rasperdroid.R;
import android.curso.com.br.rasperdroid.model.Dispositivo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 28/08/2015.
 */
public class AdapterListView extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Dispositivo> itens;
    private String urlBase;

    public AdapterListView(Context context, List<Dispositivo> itens, String url) {
        this.itens = itens;
        mInflater = LayoutInflater.from(context);
        this.urlBase = url;
    }


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
    public View getView(final int i, View view, ViewGroup viewGroup){
        final ItemSuporte itemHolder;
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
        final Dispositivo item = itens.get(i);
        itemHolder.description.setText(item.getDescription());
        itemHolder.state.setChecked(Dispositivo.State.on.equals(item.getState()) ? true : false);
        itemHolder.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = String.format("%s%s/%s/", urlBase, item.getId(), ((Switch) v).isChecked() ? 1 : 0);
                if(((Switch)v).isChecked()){
                    Log.i("######", "ACENDE "+item.getId());
                }else{
                    Log.i("######", "APAGA "+item.getId());
                }
                WebServiceClient ws = new WebServiceClient();
                ws.execute(url);

                /*int response = HttpRequest.post().send("name=kevin").code();
                Log.i("######", String.valueOf(response));*/
            }
        });

        return view;
    }

    private class ItemSuporte {
        TextView description;
        Switch state;
    }

    private class WebServiceClient extends AsyncTask<String, Void, Long> {

        private ProgressDialog progressDialog;

        @Override
        protected Long doInBackground(String... params) {
            String path = params[0];
            HttpRequest.post(path).ok();
            return 0L;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Long ds) {
        }
    }

}
