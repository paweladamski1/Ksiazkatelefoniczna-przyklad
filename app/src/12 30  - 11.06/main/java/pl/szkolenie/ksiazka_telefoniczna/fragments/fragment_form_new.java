package pl.szkolenie.ksiazka_telefoniczna.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.szkolenie.ksiazka_telefoniczna.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class fragment_form_new extends Fragment {

    public fragment_form_new() {
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_form_new, container, false);

        FirstName=(TextView)view.findViewById(R.id.FirstNameText);
        LastName=(TextView)view.findViewById(R.id.LastNameText);
        PhoneNumber=(TextView)view.findViewById(R.id.PhoneNumberText);
        Street=(TextView)view.findViewById(R.id.StreetText);
        StreetNumber=(TextView)view.findViewById(R.id.StreetNumberText);
        HouseNumber=(TextView)view.findViewById(R.id.HouseNumberText);
        City=(TextView)view.findViewById(R.id.CityText);
        PostCode=(TextView)view.findViewById(R.id.PostCodeText);

        return view;
    }
}
