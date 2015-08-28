package android.curso.com.br.rasperdroid;

import android.app.Activity;
import android.curso.com.br.rasperdroid.Adapter.AdapterListView;
import android.curso.com.br.rasperdroid.model.Dispositivo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DispositivosFragment newInstance(int sectionNumber) {
        DispositivosFragment fragment = new DispositivosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public DispositivosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dispositivos, container, false);
        List<Dispositivo> ds = new ArrayList<>(5);
        Dispositivo d1 = new Dispositivo();
        d1.setDescription("Luz 1");
        d1.setId(1L);
        d1.setState(Dispositivo.State.OFF);
        Dispositivo d2 = new Dispositivo();
        d2.setDescription("Luz 2");
        d2.setId(2L);
        d2.setState(Dispositivo.State.ON);
        Dispositivo d3 = new Dispositivo();
        d3.setDescription("Luz 3");
        d3.setId(3L);
        d3.setState(Dispositivo.State.OFF);
        Dispositivo d4 = new Dispositivo();
        d4.setDescription("Luz 4");
        d4.setId(4L);
        d4.setState(Dispositivo.State.ON);
        Dispositivo d5 = new Dispositivo();
        d5.setDescription("Luz 5");
        d5.setId(5L);
        d5.setState(Dispositivo.State.OFF);
        ds.add(d1);ds.add(d2);ds.add(d3);ds.add(d4);ds.add(d5);
        listview = (ListView) rootView.findViewById(R.id.listView);
        listview.setAdapter(new AdapterListView(getActivity(), ds));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}