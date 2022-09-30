package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class EmpPassChange extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView empNewPass;
    private TextView empOldPass;
    private Button empUpPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_pass_change);

        empNewPass=findViewById(R.id.empNewPass);
        empOldPass=findViewById(R.id.empOldPass);
        empUpPass=findViewById(R.id.empUpPass);

        empUpPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpass=empNewPass.getText().toString();
                String oldpass=empOldPass.getText().toString();

                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                String x=sharedPreferences.getString(TEXT,"");

                db.collection("employee").document(x).update("Password",newpass);


                Intent intent1 = new Intent(EmpPassChange.this,LoginActivity.class);
                startActivity(intent1);
                finish();

            }
        });


    }
}
