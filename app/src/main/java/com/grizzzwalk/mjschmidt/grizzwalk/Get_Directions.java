package com.grizzzwalk.mjschmidt.grizzwalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.grizzzwalk.mjschmidt.grizzwalk.R.layout.white_text_list;

public class Get_Directions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__directions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Associates buttons in code with buttons in XML
        Button btnDirections = (Button) findViewById((R.id.btnDirections));

        /*
        Checks to see if the user input a location in both boxes, and if the destination isn't the same as the start
        Puts directions to destination in the ListView, provided both locations are valid.
         */
        btnDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> directionsList = new ArrayList<>();
                String startLoc = "";
                String endLoc = "";
                EditText txtLocation = (EditText) findViewById(R.id.txtLocation);
                EditText txtDestination = (EditText) findViewById(R.id.txtDestination);
                ListView lstDirections = (ListView) findViewById(R.id.lstDirections);
                if (txtLocation.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter a starting location!", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (txtDestination.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter a destination!", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (txtDestination.getText().toString().toUpperCase().equals(txtLocation.getText().toString().toUpperCase())){
                    Toast toast = Toast.makeText(getApplicationContext(), "You're already there!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    InputMethodManager keyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),keyboard.HIDE_NOT_ALWAYS);
                    startLoc = txtLocation.getText().toString();
                    endLoc = txtDestination.getText().toString();

                    Building building = Building.getInstance();
                    building.setStartLocation(startLoc.toUpperCase());
                    building.setEndLocation(endLoc.toUpperCase());
                    if (building.getStartLocation()[0] == -1 && building.getStartLocation()[1] == -1){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid starting location!", Toast.LENGTH_SHORT);
                        toast.show();
                    }else if (building.getEndLocation()[0] == -1 && building.getEndLocation()[1] == -1){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid destination!", Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        Mapping map = new Mapping();
                        map.run(building.getStartLocation()[0], building.getStartLocation()[1], building.getEndLocation()[0], building.getEndLocation()[1], building.getBlocked());

                        String indicies = map.getIndices();
                        DirectionList list = new DirectionList(indicies);
                        String[] generatedList = list.getDirectionList();

                        for (int i = 0, index = 0; i < generatedList.length; i++) {
                            if (generatedList[i] != null) {
                                directionsList.add(index, generatedList[i]);
                                index++;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), white_text_list, directionsList);
                        lstDirections.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }
            }
        });

    }
}
