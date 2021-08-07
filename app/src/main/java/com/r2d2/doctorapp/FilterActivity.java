package com.r2d2.doctorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner genderSpinner;
    private TextView nbrResults;
    private ArrayList<Doctor> filteredResults = new ArrayList<>();
    private String[] genders = {"Select a gender...", "Any gender", "Male", "Female"};  // assuming we only allow male and female
    private String specFilter;
    private String genderFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        nbrResults = findViewById(R.id.resultsCountText);

        // Spinner (drop down menu) for selecting a specializaiton
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DoctorsSpecial");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                List<String> specs = new ArrayList<String>();
                // First item in the list will be disabled in the spinner, so we add this first
                specs.add("Select a specialization...");

                // Iterate through the DoctorsSpecial branch and get the specializations to display in the spinner
                for (DataSnapshot specSnapshot: snapshot.getChildren()){
                    String spec = specSnapshot.getKey();
                    specs.add(spec.substring(0, 1).toUpperCase() + spec.substring(1));
                }

                Spinner specSpinner = (Spinner) findViewById(R.id.specializationSpinner);
                SpinnerArrayAdapter specAdapter = new SpinnerArrayAdapter(FilterActivity.this, android.R.layout.simple_spinner_item, specs);
                specAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                specSpinner.setAdapter(specAdapter);
                specSpinner.setOnItemSelectedListener(FilterActivity.this);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        // Spinner (drop down menu) for selecting a gender
        genderSpinner = findViewById(R.id.genderSpinner);
        SpinnerArrayAdapter genderAdapter = new SpinnerArrayAdapter(this, android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(this);
    }

    public void search(View view){
        filteredResults.clear();
        if(specFilter.equals("select a specialization...")){
            Log.i("selected filter", "empty");
            return;
        }
        // Path is set to the specialization given by specFilter
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DoctorsSpecial/"+ specFilter);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Iterates through each doctor and adds them to filteredResults if gender matches genderFilter
                for (DataSnapshot child: snapshot.getChildren()){
                    Doctor doc = child.getValue(Doctor.class);
                    if (doc.getGender().toLowerCase().equals(genderFilter) || genderFilter.equals("any gender")) {
                        Log.i("found doctor", doc.toString());
                        filteredResults.add(doc);
                    }
                }
                // Display results
                nbrResults.setText(String.valueOf(filteredResults.size()) + " results");
                initRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.doctorRecycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, filteredResults);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.specializationSpinner) {
            specFilter = parent.getItemAtPosition(position).toString().toLowerCase();
            Log.i("selected", specFilter);
        } else if (parent.getId() == R.id.genderSpinner){
            genderFilter = parent.getItemAtPosition(position).toString().toLowerCase();
            Log.i("selected", genderFilter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}