package pl.szkolenie.ksiazka_telefoniczna.datalayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import pl.szkolenie.ksiazka_telefoniczna.model.beens.Person;


public class PersonDAO
{
	private final DBProxy dbproxy;
	/**
	 * Id, FirstName, LastName, PhoneNumber, Street, StreetNumber, HouseNumber, City, PostCode
	 */
	private final String FIELDS="Id, FirstName, LastName, PhoneNumber, Street, StreetNumber, HouseNumber, City, PostCode";
	private final String FIELDS_WITHOUT_ID=FIELDS.replace("Id, ","");

	private final String TABLENAME="Persons";

	public PersonDAO()
	{
		dbproxy=null;
	}
	public PersonDAO(DBProxy dbproxy)
	{
		this.dbproxy = dbproxy;
	}

	/**
	 * test
	 * @return
	 */
	public PersonDAO prepareCreateSelectSQL()
	{
		dbproxy.addCheckConsistencyScript("SELECT " + FIELDS + " FROM " + TABLENAME);
		String sql="CREATE TABLE "+TABLENAME+" (" +
				"Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
				"FirstName TEXT, " +
				"LastName TEXT, " +
				"PhoneNumber TEXT, " +
				"Street TEXT, " +
				"StreetNumber TEXT, " +
				"HouseNumber TEXT, " +
				"City TEXT, " +
				"PostCode TEXT)";
		dbproxy.addCreateScript(sql);
		return this;
	}

	public List<Person> getPersons()
	{
		Cursor c = null;
		List<Person> res = new LinkedList<Person>();
		try
		{
			c = dbproxy.rawQuery("SELECT " + FIELDS + " FROM "+TABLENAME+" ORDER BY LastName COLLATE  LOCALIZED, FirstName COLLATE  LOCALIZED", null);
			while (c.moveToNext())
			{
				Person e = new Person(c);				
				res.add(e);
			};
		} catch (Exception ex)
		{
			Log.e("DB", "BLAD SQL");
		}
		if (c != null)
			c.close();
		return res;

	}

	public boolean updatePersons(List<Person> Persons, boolean clearAll)
	{
		boolean ret=true;
		SQLiteDatabase db = dbproxy.GetDB();
		db.beginTransaction();
		try
		{
			if (clearAll)
				db.execSQL("DELETE FROM "+TABLENAME);

			for (Person e: Persons)
			{
				if(!clearAll)
					db.execSQL("DELETE FROM "+TABLENAME+" where Id=?",
						new Object[]
						{
							e.Id
						});

					if (e.Id<=0)
						db.execSQL(
								"insert into "+TABLENAME+" ("+FIELDS_WITHOUT_ID+") values (?,?, ?,?,?,?,?,?)",
								new Object[]
								{
										//e.Id+"",
										e.FirstName,
										e.LastName,
										e.PhoneNumber,
										e.Street,
										e.StreetNumber,
										e.HouseNumber,
										e.City,
										e.PostCode
								});
					else
						db.execSQL(
								"insert into "+TABLENAME+" ("+FIELDS_WITHOUT_ID+") values (?, ?,?,?,?,?,?,?,?)",
								new Object[]
										{
												e.Id+"",
												e.FirstName,
												e.LastName,
												e.PhoneNumber,
												e.Street,
												e.StreetNumber,
												e.HouseNumber,
												e.City,
												e.PostCode
										});
			}
			db.setTransactionSuccessful();
		} catch (Exception ex)
		{
			ret=false;
			Log.e("DB", "BLAD BAZY DANYCH");
		} finally
		{
			db.endTransaction();
		}
		return ret;
	}

	public void clear()
	{
		dbproxy.execSQL("delete from "+TABLENAME);
	}

	public Person getPerson(int PersonId) {

		Cursor c = null;
		Person res = new Person();
		try
		{
			c = dbproxy.rawQuery("SELECT " + FIELDS + " FROM "+TABLENAME+" where Id="+PersonId, null);
			while (c.moveToNext())
			{
				res= new Person(c);	
			};
		} catch (Exception ex)
		{
			Log.e("DB", "B�AD BAZY DANYCH");
		}
		if (c != null)
			c.close();
		return res;
	}

}
