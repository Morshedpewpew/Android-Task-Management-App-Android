package com.example.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmployeeList extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;

    private Button backbutton;
    private Button search;
    private TextView searchText;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);



        listView=findViewById(R.id.listView);
        backbutton=findViewById(R.id.backbutton);
        search=findViewById(R.id.search);
        searchText=findViewById(R.id.searchText);

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String x=sharedPreferences.getString(TEXT,"");

        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(EmployeeList.this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

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


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    System.out.println(arrayList.get(i));


                    Intent intent1 = new Intent(EmployeeList.this,CheckEmployee.class);
                    intent1.putExtra("key",arrayList.get(i));
                    startActivity(intent1);
                    finish();
                }
            });

            backbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent1 = new Intent(EmployeeList.this,EmployerProfile.class);

                    startActivity(intent1);
                    finish();
                }
            });

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String xx=searchText.getText().toString();



                    SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                    String x=sharedPreferences.getString(TEXT,"");

                    arrayList.clear();

                    db.collection("employer").document(x).collection("employeeList").whereEqualTo("Email",xx).get()
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


                }
            });


    }
}
