package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowTask extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";
    private ListView listView3;
    private Button back;
    private TextView taskDetails;
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList2;
    private ArrayAdapter<String> arrayAdapter;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        listView3=findViewById(R.id.listView3);
        taskDetails=findViewById(R.id.taskDetails);
        back=findViewById(R.id.back);

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String x=sharedPreferences.getString(TEXT,"");

        arrayList=new ArrayList<>();
        arrayList2=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(ShowTask.this,android.R.layout.simple_list_item_1,arrayList);
        listView3.setAdapter(arrayAdapter);

        db.collection("employee").document(x).collection("employeeTask").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(!queryDocumentSnapshots.isEmpty())
                        {
                            List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot document : list)
                            {



                                arrayList.add("Task - "+document.getId());
                                arrayList2.add(document.getId());
                                arrayAdapter.notifyDataSetChanged();

                            }


                        }


                    }
                });


        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                String y=sharedPreferences.getString(TEXT,"");

                DocumentReference docRef = db.collection("employee").document(y).collection("employeeTask").document(arrayList2.get(i));
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists())
                            {

                                taskDetails.setText(document.get("Task").toString());

                                System.out.println(document.get("Task").toString());



                            } else {


                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());

                        }
                    }
                });

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ShowTask.this,EmployeeProfile.class);
                startActivity(intent1);
                finish();
            }
        });





    }
}
