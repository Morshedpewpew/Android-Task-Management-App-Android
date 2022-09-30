package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmployerProfile extends AppCompatActivity {

    private TextView employerProName;
    private TextView employerProEmail;
    private TextView employerProDes;
    private TextView employerProContact;
    private Button addEmployee;
    private Button employeeList;
    private Button addTask;
    private Button employerPass;
    private Button logout;
    private Button deleteTask;
    private Button delemp;
    private String TAG;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);




        employerProName = findViewById(R.id.employerproname);
        employerProEmail = findViewById(R.id.employerproemail);
        employerProDes = findViewById(R.id.employerprodes);
        employerProContact = findViewById(R.id.employerprophone);
        addEmployee= findViewById(R.id.addemployee);
        employeeList= findViewById(R.id.employeelist);
        addTask= findViewById(R.id.addtask);
        employerPass= findViewById(R.id.employerPass);
        logout= findViewById(R.id.logout);
        deleteTask= findViewById(R.id.deletetask);
        delemp= findViewById(R.id.delemp);


        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String x=sharedPreferences.getString(TEXT,"");

        DocumentReference docRef = db.collection("employer").document(x);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {

                        employerProName.setText(document.get("Name").toString());
                        employerProEmail.setText(document.get("Email").toString());
                        employerProDes.setText(document.get("Designation").toString());
                        employerProContact.setText(document.get("Contact").toString());







                    } else {

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(EmployerProfile.this, AddTask.class);
                startActivity(logIntent);
                finish();

            }
        });

        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(EmployerProfile.this, AddEmployee.class);
                startActivity(logIntent);
                finish();



            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(EmployerProfile.this, LoginActivity.class);
                startActivity(logIntent);
                finish();



            }
        });

        employeeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(EmployerProfile.this, EmployeeList.class);
                startActivity(logIntent);
                finish();

            }
        });

        employerPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent logIntent = new Intent(EmployerProfile.this, PassChange.class);
                startActivity(logIntent);
                finish();
            }
        });

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent logIntent = new Intent(EmployerProfile.this, DeleteTask.class);
                startActivity(logIntent);
                finish();

            }
        });

        delemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent logIntent = new Intent(EmployerProfile.this, DeleteEmployee.class);
                startActivity(logIntent);
                finish();

            }
        });



    }
}
