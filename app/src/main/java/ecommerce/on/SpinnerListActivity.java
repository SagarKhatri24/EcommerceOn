package ecommerce.on;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SpinnerListActivity extends AppCompatActivity {

    Spinner spinner;
    //String[] countryArray = {"India","Australia","Canada","UK"};
    ArrayList<String> arrayList;
    GridView gridView;

    AutoCompleteTextView autoTxt;
    MultiAutoCompleteTextView multiAutoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_list);

        arrayList = new ArrayList<>();
        arrayList.add("India");
        arrayList.add("Australia");
        arrayList.add("Canada");
        arrayList.add("Demo");
        arrayList.add("Ireland");
        arrayList.add("Franc");

        arrayList.remove(3);
        arrayList.set(4,"France");
        arrayList.add(2,"UK");

        spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SpinnerListActivity.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gridView = findViewById(R.id.listview);
        ArrayAdapter listAdapter = new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,arrayList);
        gridView.setAdapter(listAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SpinnerListActivity.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        autoTxt = findViewById(R.id.autocomplete);
        ArrayAdapter autoAdapter = new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,arrayList);
        autoTxt.setThreshold(1);
        autoTxt.setAdapter(autoAdapter);

        multiAutoTxt = findViewById(R.id.multiautocomplete);
        ArrayAdapter multiAdapter = new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,arrayList);
        multiAutoTxt.setThreshold(1);
        multiAutoTxt.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiAutoTxt.setAdapter(multiAdapter);

    }
}