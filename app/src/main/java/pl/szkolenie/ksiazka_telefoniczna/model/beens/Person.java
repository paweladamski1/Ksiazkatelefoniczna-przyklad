package pl.szkolenie.ksiazka_telefoniczna.model.beens;


import android.database.Cursor;

/**
 * Created by JAVA on 2015-06-09.
 */
public class Person {
    public int Id;
    public String FirstName;
    public String LastName;
    public String PhoneNumber;
    public String Street;
    public String StreetNumber;
    public String HouseNumber;
    public String City;
    public String PostCode;
    private Boolean checked = false;

    public Person() {
    }

    public Person(Cursor c) {
        Id = c.getInt(0);
        FirstName = c.getString(1);
        LastName = c.getString(2);
        PhoneNumber = c.getString(3);
        Street = c.getString(4);
        StreetNumber = c.getString(5);
        HouseNumber = c.getString(6);
        City = c.getString(7);
        PostCode = c.getString(8);
    }

    @Override
    public String toString() {
        return LastName + " " + FirstName;
    }


    public String GetAdress() {
        return Street+" "+StreetNumber+ "/"+HouseNumber;
    }


    public void setChecked(boolean c )
    {
        this.checked=c;
    }

    public boolean getChecked()
    {
        return this.checked;
    }
}
