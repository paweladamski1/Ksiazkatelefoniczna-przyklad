package pl.szkolenie.ksiazka_telefoniczna.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.szkolenie.ksiazka_telefoniczna.R;
import pl.szkolenie.ksiazka_telefoniczna.fragments.fragment_form_new;
import pl.szkolenie.ksiazka_telefoniczna.fragments.fragment_list_person;
import pl.szkolenie.ksiazka_telefoniczna.model.ModelAplication;
import pl.szkolenie.ksiazka_telefoniczna.model.beens.Person;


public class MainActivity extends ActionBarActivity {
    static private MainActivity activity=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        Fragment newFragment=fragment_list_person.newInstance("","");
        SetFragment(newFragment);
    }


    public static MainActivity GetMainActivity()
    {
        return activity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.show_persons_menu:
            {
                Fragment newFragment=fragment_list_person.newInstance("","");
                SetFragment(newFragment);
                return true;
            }
            case R.id.form_new_menu:
            {
                Fragment newFragment=new fragment_form_new();
                SetFragment(newFragment);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    void SetFragment(Fragment newFragment)
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setBreadCrumbTitle("test");
        ft.replace(R.id.container_fragment, newFragment, "mainFragment");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void OnSaveBtn_clicked(View view) {
        if( fragment_form_new.FirstName== null)
            return;

        List<Person> persons=new ArrayList<>();
        Person p= new Person();
        p.Id=-1;
        p.FirstName=   fragment_form_new.FirstName.getText().toString();
        p.LastName=    fragment_form_new.LastName.getText().toString();
        p.PhoneNumber= fragment_form_new.PhoneNumber.getText().toString();
        p.Street=      fragment_form_new.Street.getText().toString();
        p.StreetNumber=fragment_form_new.StreetNumber.getText().toString();
        p.HouseNumber= fragment_form_new.HouseNumber.getText().toString();
        p.City=        fragment_form_new.City.getText().toString();
        p.PostCode=    fragment_form_new.PostCode.getText().toString();

        persons.add(p);
        ModelAplication.getInstance().UpdatePersons(persons);

        Toast.makeText(getApplicationContext(),
                "Ok ilość w bazie: "+ModelAplication.getInstance().getCountPersons(),
                Toast.LENGTH_LONG).show();
    }

    public void OnCleanDb_clicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Potwierdź skasowanie!");
        builder.setMessage("Czy na pewno mam skasować wszystkie osoby?");

        builder.setPositiveButton("Tak - na pewno! Skasuje je", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                ModelAplication.getInstance().CleanPerson();
                dialog.dismiss();
            }

        });

        builder.setNegativeButton("Nie - nie kasuj", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

        Toast.makeText(getApplicationContext(),
                "Ok ilość w bazie: " + ModelAplication.getInstance().getCountPersons(),
                Toast.LENGTH_LONG).show();

    }

    public void OnCleanForm_clicked(View view) {
        if( fragment_form_new.FirstName== null)
            return;

        fragment_form_new.FirstName.setText("");
        fragment_form_new.LastName.setText("");
        fragment_form_new.PhoneNumber.setText("");
        fragment_form_new.Street.setText("");
        fragment_form_new.StreetNumber.setText("");
        fragment_form_new.HouseNumber.setText("");
        fragment_form_new.City.setText("");
        fragment_form_new.PostCode.setText("");
    }
}
