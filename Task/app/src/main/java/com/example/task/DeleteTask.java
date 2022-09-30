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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeleteTask extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";
    private ListView listView4;
    private Button back1;
    private TextView taskDetails1;
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList2;
    private ArrayList<String> arrr;
    private ArrayAdapter<String> arrayAdapter;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG;
    private int val=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);

        listView4=findViewById(R.id.listView4);
        taskDetails1=findViewById(R.id.taskDetails1);
        back1=findViewById(R.id.back1);

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String x=sharedPreferences.getString(TEXT,"");

        arrayList=new ArrayList<>();
        arrayList2=new ArrayList<>();
        arrr=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(DeleteTask.this,android.R.layout.simple_list_item_1,arrayList);
        listView4.setAdapter(arrayAdapter);

        db.collection("employer").document(x).collection("TaskList").get()
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

        listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                String y=sharedPreferences.getString(TEXT,"");

                DocumentReference docRef = db.collection("employer").document(y).collection("TaskList").document(arrayList2.get(i));
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists())
                            {

                                taskDetails1.setText(document.get("Task").toString());

                                val=Integer.parseInt(document.get("EmployeeNum").toString());




                            } else {


                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());

                        }
                    }
                });

            }
        });

        listView4.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                String y=sharedPreferences.getString(TEXT,"");

                DocumentReference docRef = db.collection("employer").document(y).collection("TaskList").document(arrayList2.get(i));


                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists())
                            {

                                for (int j=0;j<val;j++)
                                {
                                    //arrr.add(document.get("Employee"+j).toString());
                                    System.out.println(document.get("Employee"+j).toString());
                                }


                            } else {


                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());

                        }
                    }
                });


                    db.collection("employer").document(y).collection("TaskList").document(arrayList2.get(i)).delete();

                //System.out.println(arrr);

















               // System.out.println(val+"wwweeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

              Intent intent1 = new Intent(DeleteTask.this,EmployerProfile.class);
                startActivity(intent1);


                return false;
            }
        });



        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DeleteTask.this,EmployerProfile.class);
                startActivity(intent1);
                finish();
            }
        });


    }
}
