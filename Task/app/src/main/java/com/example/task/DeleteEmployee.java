package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeleteEmployee extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";
    private ListView listView9;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button gobackbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);

        listView9=findViewById(R.id.listView9);
        gobackbutton=findViewById(R.id.gobackbutton);


        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String x=sharedPreferences.getString(TEXT,"");



        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(DeleteEmployee.this,android.R.layout.simple_list_item_1,arrayList);
        listView9.setAdapter(arrayAdapter);

        db.collection("employer").document(x).collection("employeeList").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(!queryDocumentSnapshots.isEmpty())
                        {
                            List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot document : list)
                            {



                                arrayList.add(document.getId()+"");

                                arrayAdapter.notifyDataSetChanged();


                            }


                        }


                    }
                });

        listView9.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                System.out.println(arrayList.get(i));


                Intent intent1 = new Intent(DeleteEmployee.this,EmployerProfile.class);

                startActivity(intent1);
                finish();
            }
        });


        listView9.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                String y=sharedPreferences.getString(TEXT,"");


               db.collection("employer").document(y).collection("employeeList").document(arrayList.get(i)).delete();


                db.collection("employee").document(arrayList.get(i))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast toast1 = Toast.makeText(getApplicationContext(),
                                        "Successfully Deleted!!!",
                                        Toast.LENGTH_SHORT);
                                toast1.show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast toast2 = Toast.makeText(getApplicationContext(),
                                        "ERROR DELETING!!!",
                                        Toast.LENGTH_SHORT);
                                toast2.show();
                            }
                        });
                Intent intent1 = new Intent(DeleteEmployee.this,EmployerProfile.class);

                startActivity(intent1);
                finish();


                return false;
            }
        });






    }
}
