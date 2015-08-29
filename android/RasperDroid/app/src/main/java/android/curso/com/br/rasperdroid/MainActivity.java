package android.curso.com.br.rasperdroid;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.curso.com.br.rasperdroid.model.Dispositivo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private DispositivosFragment dispositivosFragment;
    private List<Dispositivo> dispositivos;
    private boolean validData;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        dispositivosFragment = new DispositivosFragment();
        dispositivosFragment.setArguments(savedInstanceState);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String url = prefs.getString(ConfiguracoesFragment.KEY_EDIT_TEXT_PREFERENCE, null);
        this.position = position;
        if(url != null && !url.trim().equals("")){
            WebServiceClient ws = new WebServiceClient();
            ws.execute(url);
        }else{
            Toast.makeText(getApplicationContext(), "URL Invalida", Toast.LENGTH_LONG ).show();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mTitle = getString(R.string.action_settings);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new ConfiguracoesFragment())
                    .commit();
            restoreActionBar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    private class WebServiceClient extends AsyncTask<String, Void, List<Dispositivo>> {

        private ProgressDialog progressDialog;

        @Override
        protected List<Dispositivo> doInBackground(String... params) {
            String path = params[0];
            String json = HttpRequest.get(path).body();
            List<Dispositivo> dispositivos = null;
            try {
                JSONArray jsonArray = new JSONArray(json);
                dispositivos = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String desc = jsonObj.getString("Descricao");
                    String id = jsonObj.getString("Id");
                    String status = jsonObj.getString("Status");
                    Dispositivo dispositivo = new Dispositivo();
                    dispositivo.setDescription(desc);
                    dispositivo.setId(Long.parseLong(id));
                    dispositivo.setState(Dispositivo.State.valueOf(status));
                    dispositivos.add(dispositivo);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "URL Invalida", Toast.LENGTH_LONG ).show();
                return null;
            }
            return dispositivos;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(List<Dispositivo> ds) {
            progressDialog.dismiss();
            dispositivos = ds;
            if(dispositivos != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, DispositivosFragment.newInstance(position + 1, dispositivos))
                        .commit();
            }
        }
    }


}
