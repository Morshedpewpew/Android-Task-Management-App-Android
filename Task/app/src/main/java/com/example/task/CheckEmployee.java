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

public class CheckEmployee extends AppCompatActivity {

    private TextView ename;
    private TextView eemail;
    private TextView edes;
    private TextView econtact;
    private TextView eaddress;
    private Button goback;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_employee);

        String value=null;


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");

        }

        ename=findViewById(R.id.ename);
        eemail=findViewById(R.id.eemail);
        edes=findViewById(R.id.edes);
        econtact=findViewById(R.id.ephone);
        eaddress=findViewById(R.id.eAddress);
        goback=findViewById(R.id.goback);


        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String x=sharedPreferences.getString(TEXT,"");

        DocumentReference docRef = db.collection("employee").document(value);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {

                        ename.setText(document.get("Name").toString());
                        eemail.setText(document.get("Email").toString());
                        econtact.setText(document.get("Contact").toString());
                        edes.setText(document.get("Designation").toString());
                        eaddress.setText(document.get("Address").toString());






                    } else {

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());

                }
            }
        });



        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(CheckEmployee.this,EmployeeList.class);
                startActivity(intent1);
                finish();
            }
        });



    }
}
