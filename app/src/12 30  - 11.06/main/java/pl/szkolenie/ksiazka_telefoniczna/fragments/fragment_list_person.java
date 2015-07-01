package pl.szkolenie.ksiazka_telefoniczna.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import pl.szkolenie.ksiazka_telefoniczna.R;
import pl.szkolenie.ksiazka_telefoniczna.adapters.MyAdapter;
import pl.szkolenie.ksiazka_telefoniczna.model.ModelAplication;

public class fragment_list_person extends Fragment {

    public static ListView PersonsListView;


    public static fragment_list_person newInstance(String param1, String param2) {
        fragment_list_person fragment = new fragment_list_person();
        return fragment;
    }

    public fragment_list_person() {
        super();
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_list_person, container, false);
        PersonsListView =(ListView)view.findViewById(R.id.PersonsListView);        ;

        List data= ModelAplication.getInstance().GetPersons();
        MyAdapter adapter = new MyAdapter(getActivity(), data );
        if(PersonsListView!=null)
            PersonsListView.setAdapter(adapter);
        return view;
    }


}
