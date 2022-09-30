package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class EmployerReg extends AppCompatActivity {

    private TextView employerName;
    private TextView password;
    private TextView employerMail;
    private TextView contactNo;
    private TextView designation;
    private Button registration;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_reg);


        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        employerName = findViewById(R.id.EmployerName);
        password = findViewById(R.id.password);
        employerMail= findViewById(R.id.EmployerMail);
        contactNo= findViewById(R.id.ContactNo);
        registration=findViewById(R.id.reg);
        designation=findViewById(R.id.designation);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= employerName.getText().toString();
                String pass= password.getText().toString();
                String email= employerMail.getText().toString();
                String des= designation.getText().toString();
                int contact= Integer.parseInt(contactNo.getText().toString());


                CollectionReference employer = db.collection("employer");

                Map<String, Object> user = new HashMap<>();
                user.put("Name", name);
                user.put("Password", pass);
                user.put("Email", email);
                user.put("Contact", contact);
                user.put("Designation", des);
                employer.document(email).set(user);






                Toast toast = Toast.makeText(getApplicationContext(),
                        "Registration Successfull!!!",
                        Toast.LENGTH_SHORT);

                toast.show();

                Intent logIntent = new Intent(EmployerReg.this, LoginActivity.class);
                startActivity(logIntent);
                finish();
            }
        });

    }
}
