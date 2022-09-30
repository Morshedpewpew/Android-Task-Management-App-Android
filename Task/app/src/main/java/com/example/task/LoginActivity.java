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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private Button registration;
    private TextView email;
    private TextView password;

    private String TAG;

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        registration = findViewById(R.id.registration);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail= email.getText().toString();
                final String pass= password.getText().toString();

                if(mail.equals(""))
                {
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Enter your email!!!",
                            Toast.LENGTH_SHORT);

                    toast1.show();
                }

                else if(pass.equals(""))
                {
                    Toast toast2 = Toast.makeText(getApplicationContext(),
                            "Enter your password!!!",
                            Toast.LENGTH_SHORT);
                    toast2.show();

                }
                else
                {
                    DocumentReference docRef = db.collection("employer").document(mail);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists() && document.get("Password").equals(pass))
                                {


                                    SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString(TEXT,mail);
                                    editor.apply();


                                    Toast toast2 = Toast.makeText(getApplicationContext(),
                                            "LOGGED IN AS EMPLOYER!!!",
                                            Toast.LENGTH_SHORT);
                                    toast2.show();

                                    Intent bariProIntent = new Intent(LoginActivity.this, EmployerProfile.class);
                                    startActivity(bariProIntent);
                                    finish();


                                } else {

                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });


                    DocumentReference docRef1 = db.collection("employee").document(mail);
                    docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists() && document.get("Password").equals(pass))
                                {


                                    SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString(TEXT,mail);
                                    editor.apply();


                                    Toast toast3 = Toast.makeText(getApplicationContext(),
                                            "LOGGED IN AS EMPLOYEE!!!",
                                            Toast.LENGTH_SHORT);
                                    toast3.show();

                                    Intent varaProIntent = new Intent(LoginActivity.this,EmployeeProfile.class);
                                    startActivity(varaProIntent);
                                    finish();


                                } else {

                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }







            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LoginActivity.this, EmployerReg.class);
                startActivity(regIntent);
                finish();
            }
        });
    }
}
