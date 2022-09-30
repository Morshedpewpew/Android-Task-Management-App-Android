package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

public class EmployeeProfile extends AppCompatActivity {

    private TextView empName;
    private TextView empMail;
    private TextView empDes;
    private TextView empContact;
    private TextView empAddress;
    private Button showTask;
    private Button employeePass;
    private Button empLogout;
    private Button call;


    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        empName=findViewById(R.id.empName);
        empMail=findViewById(R.id.empMail);
        empDes=findViewById(R.id.empDes);
        empContact=findViewById(R.id.empContact);
        empAddress=findViewById(R.id.empAddress);
        showTask=findViewById(R.id.showTask);
        employeePass=findViewById(R.id.employeePass);
        empLogout=findViewById(R.id.empLogout);
        call=findViewById(R.id.call);

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String x=sharedPreferences.getString(TEXT,"");


        DocumentReference docRef = db.collection("employee").document(x);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {

                        empName.setText(document.get("Name").toString());
                        empMail.setText(document.get("Email").toString());
                        empContact.setText(document.get("Contact").toString());
                        empDes.setText(document.get("Designation").toString());
                        empAddress.setText(document.get("Address").toString());



                    } else {

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        showTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(EmployeeProfile.this, ShowTask.class);
                startActivity(logIntent);
                finish();



            }
        });

        empLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(EmployeeProfile.this, LoginActivity.class);
                startActivity(logIntent);
                finish();


            }
        });

        employeePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(EmployeeProfile.this, EmpPassChange.class);
                startActivity(logIntent);
                finish();


            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCall();

            }
        });



    }




    public void createCall()
    {



        Intent callIntent = new Intent(Intent.ACTION_DIAL);

        startActivity(callIntent);
    }

}
