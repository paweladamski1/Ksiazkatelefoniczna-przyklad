package pl.szkolenie.ksiazka_telefoniczna.datalayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.LinkedList;



public class DBProxy extends SQLiteOpenHelper
{

	private SQLiteDatabase database;


    @Override
    public void onCreate(SQLiteDatabase db) {
        database=db;
       // createDatabaseStructure();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        database=db;
    }



    private final File databaseFile;

	private LinkedList<String> createScripts;
	private LinkedList<String> checkConsistencyScripts;

	public DBProxy(String filename, Context context)
	{
        super(context, "baza.db", null, 1);
		//SQLiteDatabase.loadLibs(context);
		this.databaseFile = context.getDatabasePath(filename);
		createScripts = new LinkedList<String>();
		checkConsistencyScripts = new LinkedList<String>();
        database=getWritableDatabase();
	}

/*	public boolean openDatabase(String code) throws Exception
	{
		closeDatabaseIfOpened();
		if(!isDataFileAvailable())
			throw new Exception("Baza nie istnieje");
		
		try
		{
			SQLiteDatabase database2=
					SQLiteDatabase.openDatabase(
							databaseFile.getAbsolutePath(), code, null,
							SQLiteDatabase.OPEN_READONLY);
					// plik się prawidłowo otworzył - dobre hasło.
					database2.close();//to najpierw z readonly bo inaczej w stacktrace pisze o błędzie to poniżej 
			
			database = SQLiteDatabase.openDatabase(databaseFile.getAbsolutePath(),
				code, null, SQLiteDatabase.OPEN_READWRITE);
		}catch(Exception e)
		{
			database=null;
			return false;
		}
		
		if (!checkDBConsistency())
		{
			InitializeDatabase(code);
		}
		return true;
	}*/
	

	 public void deleteDatabase()
	 {
		 closeDatabaseIfOpened();
		 databaseFile.delete();
	 }
	
	public void createDatabase(String code) throws Exception
	{
		if (database != null || isDataFileAvailable())
			throw new Exception("MA: Database already exists");
		//InitializeDatabase(code);
	}
	
	private void closeDatabaseIfOpened()
	{
		if(database!=null)
		{
			if(database!=null)
			{
				try
				{
					database.close();
				}
				catch(Exception e){}
			}
			database=null;
		}
	}

	public boolean isDataFileAvailable()
	{
		boolean exists = databaseFile.exists();
		return exists;
	}
	
	boolean checkDBConsistency()
	{
		try
		{
			for (String s : checkConsistencyScripts)
			{
				database.rawQuery(s, null);
			}
		} 
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	public boolean isDatabaseOpened()
	{
		if(database != null)
			return true;					
		return false;
	}
	
	public void addCreateScript(String s)
	{
		createScripts.add(s);
	}

	public void addCheckConsistencyScript(String s)
	{
		checkConsistencyScripts.add(s);
	}
	
	public void createDatabaseStructure()
	{
        for (String s : createScripts) {
            try {
                database.execSQL(s);
            }
            catch(Exception ex){
                Log.d("db","blad db");
            }
        }
	}

	public void execSQL(String string, Object[] objects)
	{
		database.execSQL(string,objects);		
	}
	
	public void execSQL(String string)
	{
		database.execSQL(string);		
	}
	

	public Cursor rawQuery(String sql, String[] selectionArgs)
	{
		return database.rawQuery(sql, selectionArgs);
	}
	
	public void insertOrThrow(String table, String nullcolumnHack, ContentValues map)
	{
		database.insertOrThrow(table, nullcolumnHack, map);
	}
	
	public void delete(String arg0, String arg1, String[] arg2)
	{
		database.delete(arg0, arg1, arg2);
	}
	
	public String GetFieldVarSelect(String field, String table, String where_fieldName, String where_value, String defaultIfError)
	{
		try{
			Cursor c = rawQuery(
							"SELECT ? FROM ? where ?=? LIMIT 1",
							new String[]
							{
								field, table, where_fieldName, where_value
							});
			while (c.moveToNext())
			{
				return c.getString(0);
			}
			
		}
		catch(Exception ex)
		{
			Log.e("db", "GetFieldVarSelect: " + ex.getMessage());
		}
		return defaultIfError;
	}

	public int GetMax(String field, String table, String where_fieldName, String where_value, int defaultIfError)
	{
		try{
			Cursor c = rawQuery(
							"SELECT MAX "+field+" FROM ? where ?=? ",
							new String[]
							{
								table, where_fieldName, where_value
							});
			while (c.moveToNext())
			{
				return c.getInt(0);
			}
			
		}
		catch(Exception ex)
		{
			Log.e("db", "db.GetMax: " + ex.getMessage());
		}
		
		return defaultIfError;
	}
	
	public int GetMin(String field, String table, String where_fieldName, String where_value, int defaultIfError)
	{
		try{
			Cursor c = rawQuery(
							"SELECT MIN("+field+") FROM ? where ?=? ",
							new String[]
							{
								table+"", where_fieldName+"", where_value+""
							});
			while (c.moveToNext())
			{
				return c.getInt(0);
			}
			
		}
		catch(Exception ex)
		{
			Log.e("db", "db.GeMin: " + ex.getMessage());
		}
		
		return defaultIfError;
	}


    public SQLiteDatabase GetDB() {
        return database;
    }
}
