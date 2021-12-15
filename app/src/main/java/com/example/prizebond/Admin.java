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

public class Admin extends AppCompatActivity {
    TextView t2;
    EditText e2;
    Button b2;
    String getInputAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        t2=findViewById(R.id.viewOthersAdmin);
        e2=findViewById(R.id.editInputAdmin);
        b2=findViewById(R.id.SaveInputAdmin);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefa = database.getReference("Admin").child("test");

     t2.setText(MainActivity.getInputAdmin);

     getInputAdmin=MainActivity.getInputAdmin;




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // Write a message to the database

                if (getInputAdmin==null) {

                    myRefa.setValue(e2.getText().toString());

                }
                else {
                    myRefa.setValue(getInputAdmin+"\n"+e2.getText().toString());

                }




                // Read from the database
                myRefa.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        Log.d("Kush", "Value is: " + value);
                        t2.setText(value);
                        //MainActivity.getInputAdmin=value;
                        getInputAdmin=value;
                        MainActivity.getInputAdmin=getInputAdmin;
                    }

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