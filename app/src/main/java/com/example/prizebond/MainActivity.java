package com.example.prizebond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button c,s,a,r,e;
    TextView t;
    public static String getInputAdmin;
    public static String getInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c= findViewById(R.id.Input);
        s= findViewById(R.id.Scan);
        a= findViewById(R.id.Admin);
        r= findViewById(R.id.Check);
        t= findViewById(R.id.text);
        e= findViewById(R.id.Excel);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Admin").child("test");
        DatabaseReference myRef2 = database.getReference("User").child("test");




        // Read from the database
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("Kush2", "Value is: " + value);

                getInput=value;

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Kush2", "Failed to read value.", error.toException());
            }
        });

        //end read


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("Kush2", "Value is: " + value);

                getInputAdmin=value;

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Kush2", "Failed to read value.", error.toException());
            }
        });



        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Excel.class));

            }
        });




        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              startActivity(new Intent(MainActivity.this, Input.class));




            }







        });













        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this, Admin.class));

            }
        });


s.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, Scanning.class));

    }
});


r.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        String textR = "";

        StringBuilder aa = null;



        //System.out.println("Contains sequence 'is String': " + getInputAdmin.contains("TestAdmin"));


        String[] arrOfStr = getInput.split("\n");

        for (String a : arrOfStr) {
            Log.d("TestCon", "onClick: " + a);

            boolean testAdmin = getInputAdmin.contains(a);




            if (testAdmin) {

               textR = textR + (a + " is Winner\n");





            }

        }


        boolean testAdmin = getInputAdmin.contains("TestAdmin");
        Log.d("TestCon", "onClick: "+testAdmin);

       t.setText(textR);


    }
});

    }
}