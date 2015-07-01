package pl.szkolenie.ksiazka_telefoniczna.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.szkolenie.ksiazka_telefoniczna.R;
import pl.szkolenie.ksiazka_telefoniczna.model.ModelAplication;
import pl.szkolenie.ksiazka_telefoniczna.model.beens.Person;


/**
 * A placeholder fragment containing a simple view.
 */
public class fragment_form_new extends Fragment {
    private static final String PERSONIDARG = "PersonId";
    private static int personId=-1;

    public static fragment_form_new newInstance(int personId) {
        fragment_form_new fragment = new fragment_form_new();
        Bundle args = new Bundle();
        args.putInt(PERSONIDARG, personId);
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_form_new() {
        personId=-1;
        Log.d("fragment", "ok");
    }
    public static TextView
                        FirstName,
                        LastName,
                        PhoneNumber,
                        Street,
                        StreetNumber,
                        HouseNumber,
                        City,
                        PostCode;

    Button btnSave=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_form_new, container, false);
        btnSave=(Button)view.findViewById(R.id.SaveButton);

        FirstName=(TextView)view.findViewById(R.id.FirstNameText);
        FirstName.addTextChangedListener(onTextChanging);

        LastName=(TextView)view.findViewById(R.id.LastNameText);
        LastName.addTextChangedListener(onTextChanging);

        PhoneNumber=(TextView)view.findViewById(R.id.PhoneNumberText);
        PhoneNumber.addTextChangedListener(onTextChanging);

        Street=(TextView)view.findViewById(R.id.StreetText);
        StreetNumber=(TextView)view.findViewById(R.id.StreetNumberText);
        HouseNumber=(TextView)view.findViewById(R.id.HouseNumberText);
        City=(TextView)view.findViewById(R.id.CityText);
        PostCode=(TextView)view.findViewById(R.id.PostCodeText);

        fillFields();


        return view;
    }

    private void fillFields() {
        Person p= ModelAplication.getInstance().GetPerson(personId);


        if (p==null)
            return;

        FirstName.setText(p.FirstName);
        LastName.setText(p.LastName);
        PhoneNumber.setText(p.PhoneNumber);
        Street.setText(p.Street);
        StreetNumber.setText(p.StreetNumber);
        HouseNumber.setText(p.HouseNumber);
        City.setText(p.City);
        PostCode.setText(p.PostCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personId = getArguments().getInt(PERSONIDARG);
        }else
            personId=-1;
    }

    TextWatcher onTextChanging= new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s){ }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            btnSave.setEnabled(CheckFormFieldsRequired());
        }
    };

    public static boolean CheckFormFieldsRequired()
    {
        if(FirstName==null || LastName==null ||  PhoneNumber==null)
            return false;

        if(FirstName.getText().length()<3 || LastName.length()<3 ||  PhoneNumber.length()<3)
            return false;

        return true;
    }

}
