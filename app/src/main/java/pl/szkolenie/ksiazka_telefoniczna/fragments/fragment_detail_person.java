package pl.szkolenie.ksiazka_telefoniczna.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import pl.szkolenie.ksiazka_telefoniczna.R;
import pl.szkolenie.ksiazka_telefoniczna.activities.MainActivity;
import pl.szkolenie.ksiazka_telefoniczna.model.ModelAplication;
import pl.szkolenie.ksiazka_telefoniczna.model.beens.Person;

public class fragment_detail_person extends Fragment {

    private static final String PERSONIDARG = "PersonId";
    public static TextView FirstNameTXT,
            LastNameTXT,PhoneNumberTXT,AdressTXT,
            PostCodeTXT, CityTXT;
    private static Person person=null;
    private static int personId;

    public static fragment_detail_person newInstance(int personId) {
        fragment_detail_person fragment = new fragment_detail_person();
        Bundle args = new Bundle();
        args.putInt(PERSONIDARG, personId);
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_detail_person() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personId = getArguments().getInt(PERSONIDARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_detail_person, container, false);

        FirstNameTXT=(TextView)v.findViewById(R.id.FirstNameTXT);
        LastNameTXT=(TextView)v.findViewById(R.id.LastNameTXT);
        PhoneNumberTXT=(TextView)v.findViewById(R.id.PhoneNumberTXT);
        AdressTXT=(TextView)v.findViewById(R.id.AdressTXT);
        PostCodeTXT=(TextView)v.findViewById(R.id.PostCodeTXT);
        CityTXT=(TextView)v.findViewById(R.id.CityTXT);

        EditText tmp=(EditText)v.findViewById(R.id.editText);
        registerForContextMenu(tmp);

        UptateInfo(personId);
        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.setHeaderTitle("Przykładowe menu kontekstowe");
        menu.add(200, 200, 200, "element1");
    }

    public static void UptateInfo()
    {
       UptateInfo(personId);
    }

    public static void UptateInfo(int personId) {
        if(FirstNameTXT==null || LastNameTXT==null ||PhoneNumberTXT==null ||AdressTXT==null ||PostCodeTXT==null|| CityTXT==null)
            return;

        person= ModelAplication.getInstance().GetPerson(personId);

        if (person==null) {
            FirstNameTXT.setText("Nie znaleziono osoby");
            LastNameTXT.setText("");
            PhoneNumberTXT.setText("");
            AdressTXT.setText("");
            PostCodeTXT.setText("");
            CityTXT.setText("");

            return;
        }

        FirstNameTXT.setText(person.FirstName);
        LastNameTXT.setText(person.LastName);
        PhoneNumberTXT.setText(person.PhoneNumber);
        AdressTXT.setText(person.GetAdress());
        PostCodeTXT.setText(person.PostCode);
        CityTXT.setText(person.City);
    }


    public static void Delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.GetMainActivity());
                      MainActivity.GetMainActivity().getString(R.string.LastName);

        builder.setTitle("Czy jesteś pewien?");

        builder.setMessage("Czy na pewno skasować " + person.FirstName + " " + person.LastName + "?");

        builder.setPositiveButton("Tak - na pewno! Skasuj!", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                ModelAplication.getInstance().DeletePerson(personId);
                MainActivity.SetFragment(fragment_list_person.newInstance());
                dialog.dismiss();
            }

        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static int GetPersonId() {
        return personId;

    }
}
