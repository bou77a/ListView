package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Achat> listAchats;
    private ArrayList<ArrayList<Achat>> allLists;
    private ArrayAdapter<Achat> adapter;
    private ListView listView;
    private EditText editText;
    private EditText editText2;
    private Button button;
    private TextView maintextView;
    private int currentListIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listAchats = new ArrayList<>();
        listAchats.add(new Achat("Farine", "10 kg"));
        listAchats.add(new Achat("Huile", "10 L"));
        listAchats.add(new Achat("Tomates", "4 kg"));
        listAchats.add(new Achat("Levures", "10"));
        listAchats.add(new Achat("Eeu", "10 L"));
        listAchats.add(new Achat("Extrait de vanille", "1"));
        listAchats.add(new Achat("poivre noir", "100 g"));
        listAchats.add(new Achat("lives noires", "200 g"));




        adapter = new AchatAdapter(this, listAchats);
        listView = findViewById(R.id.list_achat);
        listView.setAdapter(adapter);

        editText = findViewById(R.id.ajouter_item);
        editText2 = findViewById(R.id.ajouter_quantite);
        button = findViewById(R.id.ajouter_button);

        maintextView = findViewById(R.id.mainTextView);

        registerForContextMenu(maintextView); //


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToList(editText.getText().toString(), editText2.getText().toString());
            }
        });

        allLists = new ArrayList<>();
        allLists.add(listAchats);



        maintextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openContextMenu(v);
                return true;
            }
        });
    }



    //---------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addList) {
            addList();
            return true;

        } else if (item.getItemId() == R.id.clearList) {
            clearList();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // switch between lists second list code

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Add each list as a menu item
        for (int i = 0; i < allLists.size(); i++) {
            ArrayList<Achat> list = allLists.get(i);
            menu.add(R.id.list_group, i, i, "List " + (i + 1));
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int listIndex = item.getItemId();

        if (currentListIndex != listIndex) {  // Check if switching to a different list
            // Update current list index
            currentListIndex = listIndex;

            // Set the selected list to the listView (create new adapter only if needed)
            ArrayList<Achat> selectedList = allLists.get(listIndex);
            if (adapter.getCount() != selectedList.size()) { // Check if adapter needs update
                adapter = new AchatAdapter(this, selectedList);
            }
            listView.setAdapter(adapter);

            // Update the TextView to show the current list
            updateTextView();
        }

        return true;
    }

    private void updateTextView() {
        maintextView.setText("List " + (currentListIndex + 1));
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void addItemToList(String item, String qtn) {
        Achat achat = new Achat(item, qtn);
        listAchats.add(achat);
        adapter.notifyDataSetChanged();
    }

    public void clearList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to clear the shopping list?")
                .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listAchats.clear();
                        adapter.notifyDataSetChanged();
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }

    public void addList() {

        ArrayList<Achat> newList = new ArrayList<>();
        allLists.add(newList);

        // Mettez à jour currentListIndex pour pointer vers la nouvelle liste
        currentListIndex = allLists.size() - 1;

        // Mettre à jour listAchats et l'adaptateur avec la nouvelle liste
        listAchats = newList;
        adapter = new AchatAdapter(this, listAchats);
        listView.setAdapter(adapter);
    }
}