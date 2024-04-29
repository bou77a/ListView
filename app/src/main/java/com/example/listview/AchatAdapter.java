package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AchatAdapter extends ArrayAdapter<Achat> {

    public AchatAdapter(Context context, ArrayList<Achat> achats) {
        super(context, 0, achats);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Achat achat = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);
        }

        TextView quantityTextView = convertView.findViewById(R.id.quantityTextView);
        TextView itemTextView = convertView.findViewById(R.id.itemTextView);
        ImageButton editButton = convertView.findViewById(R.id.editButton);
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);





        // Set the quantity and item name
        quantityTextView.setText(achat.getQte());
        itemTextView.setText(achat.getItem());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem(position);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });

        return convertView;
    }

    private void editItem(int position) {

        MainActivity activity = (MainActivity) getContext();
        EditText editText = activity.findViewById(R.id.ajouter_item);
        EditText editText2 = activity.findViewById(R.id.ajouter_quantite);

        Achat achat = getItem(position);

        String newItem = editText.getText().toString().trim();
        String newQte = editText2.getText().toString().trim();


            achat.setItem(newItem);
            achat.setQte(newQte);


        editText.setText("");
        editText2.setText("");

        notifyDataSetChanged();

    }


    private void deleteItem(int position) {
        remove(getItem(position));
        notifyDataSetChanged();
    }


}