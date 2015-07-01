package pl.szkolenie.ksiazka_telefoniczna.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import pl.szkolenie.ksiazka_telefoniczna.R;
import pl.szkolenie.ksiazka_telefoniczna.activities.MainActivity;
import pl.szkolenie.ksiazka_telefoniczna.adapters.MyAdapter;
import pl.szkolenie.ksiazka_telefoniczna.model.ModelAplication;
import pl.szkolenie.ksiazka_telefoniczna.model.beens.Person;

public class fragment_list_person extends Fragment {

    public static ListView PersonsListView;
    private static MyAdapter adapter;

    public static fragment_list_person newInstance() {
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
        RefreshData(getActivity());
        PersonsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               if (adapter==null)
                   return false;

               Person p= adapter.getItem(position);
               MainActivity.SetFragment(fragment_detail_person.newInstance(p.Id));

               return false;
            }
        });


        /* {


            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position) {

                Person item = adapter.getItem(position);

            /*    Intent intent = new Intent(getActivity(), MainActivity.class);
//based on item add info to intent
                startActivity(intent);

            }


        });*/

        return view;
    }

    public static void RefreshData(Context context)
    {
        List data= ModelAplication.getInstance().GetPersons();
        adapter = new MyAdapter(context, data );
        if(PersonsListView!=null)
            PersonsListView.setAdapter(adapter);
    }

    public static MyAdapter GetAdapter() {
        return adapter;
    }
}
