package pl.szkolenie.ksiazka_telefoniczna.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.szkolenie.ksiazka_telefoniczna.R;
import pl.szkolenie.ksiazka_telefoniczna.model.beens.Person;

//import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Person> {

    private final Context context;
    private final List<Person> itemsArrayList;

    public MyAdapter(Context context, List<Person> itemsArrayList) {

        super(context, R.layout.person_list_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.person_list_row, parent, false);

        // 3. Get the two text view from the rowView
        TextView nameView = (TextView) rowView.findViewById(R.id.Name);
        TextView adressView = (TextView) rowView.findViewById(R.id.Adress);
        Person p=itemsArrayList.get(position);

        // 4. Set the text for textView
        nameView.setText(p.FirstName+" "+p.LastName);
        adressView.setText(p.Street+" "+p.StreetNumber+"/"+" "+p.HouseNumber+" "+ p.City);

        // 5. retrn rowView
        return rowView;
    }
}