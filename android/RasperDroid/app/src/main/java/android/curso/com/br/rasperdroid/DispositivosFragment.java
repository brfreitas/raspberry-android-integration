package android.curso.com.br.rasperdroid;

import android.app.Activity;
import android.content.SharedPreferences;
import android.curso.com.br.rasperdroid.Adapter.AdapterListView;
import android.curso.com.br.rasperdroid.model.Dispositivo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DispositivosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DispositivosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DispositivosFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listview;
    private static List<Dispositivo> ds;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DispositivosFragment newInstance(int sectionNumber, List<Dispositivo> ds) {
        DispositivosFragment fragment = new DispositivosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        DispositivosFragment.ds = ds;
        return fragment;
    }

    public DispositivosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dispositivos, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String url = prefs.getString(ConfiguracoesFragment.KEY_EDIT_TEXT_PREFERENCE, null);
        listview = (ListView) rootView.findViewById(R.id.listView);
        listview.setAdapter(new AdapterListView(getActivity(), ds, url));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}