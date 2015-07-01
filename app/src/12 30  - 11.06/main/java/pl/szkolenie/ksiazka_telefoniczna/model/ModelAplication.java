package pl.szkolenie.ksiazka_telefoniczna.model;

import android.util.Log;

import java.util.List;

import pl.szkolenie.ksiazka_telefoniczna.activities.MainActivity;
import pl.szkolenie.ksiazka_telefoniczna.datalayer.DBProxy;
import pl.szkolenie.ksiazka_telefoniczna.datalayer.PersonDAO;
import pl.szkolenie.ksiazka_telefoniczna.model.beens.Person;

public class ModelAplication {

    private static ModelAplication ourInstance = new ModelAplication();
    private DBProxy dbProxy;
    private PersonDAO personDAO;

    public static ModelAplication getInstance()
    {
        if (ourInstance==null)
            ourInstance=new ModelAplication();

        return ourInstance;
    }

    private ModelAplication(){
        try {
            dbProxy = new DBProxy("baza.db", MainActivity.GetMainActivity().getApplicationContext());
            if(!dbProxy.isDataFileAvailable()) {
                try {
                    dbProxy.createDatabase("ksiazkaTelefoniczna");

                }catch(Exception ex)
                {
                    Log.e("db", "blad bazy danych");
                }

            }
            initDAOs();

        }
        catch (Exception ex) {
            Log.d("db", "Błąd db");
        }


    }

    private void initDAOs()
    {
        personDAO = new PersonDAO(dbProxy).prepareCreateSelectSQL();

        dbProxy. createDatabaseStructure();
    }

    public boolean UpdatePersons(List<Person> persons)
    {
        return personDAO.updatePersons(persons, false);
    }

    public int getCountPersons()
    {
        return personDAO.getPersons().size();
    }

    public void CleanPerson() {
        personDAO.clear();
    }

    public List<Person> GetPersons() {
        return personDAO.getPersons();
    }
}
