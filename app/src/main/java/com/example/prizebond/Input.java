package com.example.prizebond;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Input extends AppCompatActivity {
    TextView t;
    EditText e;
    Button b;
    String getInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        t=findViewById(R.id.viewOthers);
        e=findViewById(R.id.editInput);
        b=findViewById(R.id.SaveInput);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefi = database.getReference("User").child("test");




        t.setText(MainActivity.getInput);
        getInput=MainActivity.getInput;

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // Write a message to the database

                if (getInput==null) {
                    myRefi.setValue(e.getText().toString());

                }

                else {

                    myRefi.setValue(getInput+"\n"+e.getText().toString());

                }





                // Read from the database
                myRefi.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        Log.d("Kush", "Value is: " + value);
                        t.setText(value);
                        getInput=value;
                        MainActivity.getInput=getInput;                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Kush", "Failed to read value.", error.toException());
                    }
                });

                //end read












            }
        });




    }
}