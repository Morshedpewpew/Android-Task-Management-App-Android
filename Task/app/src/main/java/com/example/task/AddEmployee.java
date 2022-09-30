package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class AddEmployee extends AppCompatActivity {

    private StorageReference mStorageRef;
    private TextView employeeName;
    private TextView employeeEmail;
    private TextView employeeAddress;
    private TextView employeeDesignation;
    private TextView employeePassword;
    private TextView employeeContact;
    private Button confirmReg;

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        employeeName= findViewById(R.id.empName);
        employeeEmail= findViewById(R.id.empMail);
        employeeAddress= findViewById(R.id.empAddress);
        employeeDesignation= findViewById(R.id.empDes);
        employeePassword= findViewById(R.id.empProPass);
        employeeContact= findViewById(R.id.empContact);

        confirmReg=findViewById(R.id.confirmReg);

        confirmReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=employeeName.getText().toString();
                String email=employeeEmail.getText().toString();
                String address=employeeAddress.getText().toString();
                String des=employeeDesignation.getText().toString();
                String pass=employeePassword.getText().toString();
                String contact=employeeContact.getText().toString();



                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                String employer=sharedPreferences.getString(TEXT,"");

                CollectionReference employee = db.collection("employee");

                Map<String, Object> user = new HashMap<>();
                user.put("Name", name);
                user.put("Address", address);
                user.put("Contact",contact );
                user.put("Email", email);
                user.put("Password", pass);
                user.put("Designation", des);
                user.put("Employer Mail", employer);
                employee.document(email).set(user);

                CollectionReference employerr = db.collection("employer");
                employerr.document(employer).collection("employeeList").document(email).set(user);

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Employee Added Successfullyl!!!",
                        Toast.LENGTH_SHORT);

                toast.show();

                Intent setIntent = new Intent(AddEmployee.this, EmployerProfile.class);
                startActivity(setIntent);
                finish();


            }
        });


    }
}
