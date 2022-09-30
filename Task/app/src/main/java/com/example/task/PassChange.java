package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class PassChange extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView employerNewPass;
    private TextView employerOldPass;
    private Button employerUpPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_change);

        employerNewPass=findViewById(R.id.employerNewPass);
        employerOldPass=findViewById(R.id.employerOldPass);
        employerUpPass=findViewById(R.id.employerUpPass);



        employerUpPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpass=employerNewPass.getText().toString();
                String oldpass=employerOldPass.getText().toString();

                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                String x=sharedPreferences.getString(TEXT,"");

                db.collection("employer").document(x).update("Password",newpass);


                Intent intent1 = new Intent(PassChange.this,LoginActivity.class);
                startActivity(intent1);
                finish();

            }
        });




    }
}
