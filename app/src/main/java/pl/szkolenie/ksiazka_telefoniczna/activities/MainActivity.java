package pl.szkolenie.ksiazka_telefoniczna.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.szkolenie.ksiazka_telefoniczna.R;
import pl.szkolenie.ksiazka_telefoniczna.adapters.MyAdapter;
import pl.szkolenie.ksiazka_telefoniczna.fragments.fragment_detail_person;
import pl.szkolenie.ksiazka_telefoniczna.fragments.fragment_form_new;
import pl.szkolenie.ksiazka_telefoniczna.fragments.fragment_list_person;
import pl.szkolenie.ksiazka_telefoniczna.model.ModelAplication;
import pl.szkolenie.ksiazka_telefoniczna.model.beens.Person;


public class MainActivity extends ActionBarActivity {
    static private MainActivity activity=null;
    static private Fragment activFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;

        Fragment newFragment=null;
        if (activFragment==null)
            newFragment=fragment_list_person.newInstance();
        else
            newFragment=activFragment;

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
                Fragment newFragment=fragment_list_person.newInstance();
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

    public static void SetFragment(Fragment newFragment)
    {
        activFragment=newFragment;
        FragmentTransaction ft = MainActivity.activity.getFragmentManager().beginTransaction();
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
        p.Id=fragment_detail_person.GetPersonId();
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
                "Ok ilość w bazie: " + ModelAplication.getInstance().getCountPersons(),
                Toast.LENGTH_LONG).show();
        SetFragment(new fragment_list_person());
    }

    public void OnCleanDb_clicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Potwierdź skasowanie!");
        builder.setMessage("Czy na pewno mam skasować wszystkie osoby?");

        builder.setPositiveButton("Tak - na pewno! Skasuje je", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                ModelAplication.getInstance().CleanPerson();
                fragment_list_person.RefreshData(MainActivity.this);
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

    public void OnBackToListPerson_clicked(View view) {
        SetFragment(new fragment_list_person());
    }

    public void OnDeletePerson_clicked(View view) {
        fragment_detail_person.Delete();
    }

    public void OnEditPerson_clicked(View view) {
        SetFragment(fragment_form_new.newInstance(fragment_detail_person.GetPersonId()));
    }
    public void OnAddPerson_clicked(View view) {
        SetFragment(fragment_form_new.newInstance(-1));
    }

    /**
     * Returns text from resource
     * @param resource - idResource
     * @return string
     */
    public static String S(int resource)
    {
        try {
            return GetMainActivity().getString(resource);

        } catch(Exception e) {
            return "";
        }

    }
    void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:+48785638815"));
            activity.startActivity(intent);
        }catch (Exception ex) {
            if(ex!=null)
                Toast.makeText(getApplicationContext(),
                        "Wystapil blad "+ex.getMessage(),
                        Toast.LENGTH_LONG).show();
        }
    }

    public void OnDeleteChecedItem_clicked(View view) {
         MyAdapter a= fragment_list_person.GetAdapter();
        for(int i=0 ; i<a.getCount() ; i++){
            Person p = a.getItem(i);
            if(p.getChecked())
            {
                ModelAplication.getInstance().DeletePerson(p.Id);
            }
        }

        fragment_list_person.RefreshData(this);
    }

    public void OnCallToPhoneNumber_clicked(View view) {
        call();
    }
}
