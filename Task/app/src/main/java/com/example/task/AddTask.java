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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTask extends AppCompatActivity {

    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT="text";
    private ListView listView1;
    private TextView assignemp;
    private TextView task;
    private TextView taskNo;
    private Button assign;
    private Button cancel;
    private ArrayList<String> arrayList1;
    private ArrayList<String> names;
    private ArrayList<String> arr;
    private ArrayAdapter<String> arrayAdapter1;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        listView1=findViewById(R.id.listView1);
        assignemp=findViewById(R.id.assignemp);
        task=findViewById(R.id.task);
        assign=findViewById(R.id.assign);
        cancel=findViewById(R.id.cancel);
        taskNo=findViewById(R.id.taskNo);



        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String x=sharedPreferences.getString(TEXT,"");


        names=new ArrayList<>();
        arr=new ArrayList<>();

        arrayList1=new ArrayList<>();
        arrayAdapter1=new ArrayAdapter<>(AddTask.this,android.R.layout.simple_list_item_1,arrayList1);
        listView1.setAdapter(arrayAdapter1);

        db.collection("employer").document(x).collection("employeeList").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(!queryDocumentSnapshots.isEmpty())
                        {
                            List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot document : list)
                            {



                                arrayList1.add(document.getId()+"");
                                arrayAdapter1.notifyDataSetChanged();

                            }


                        }


                    }
                });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String a=assignemp.getText().toString();
                assignemp.setText(a+arrayList1.get(i)+"\n");
                System.out.println(arrayList1.get(i));
                names.add(arrayList1.get(i));
                count++;
                System.out.println(count);

            }
        });

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aa=task.getText().toString();
                System.out.println(aa);

                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                String y=sharedPreferences.getString(TEXT,"");

                CollectionReference employee = db.collection("employee");
                CollectionReference employer= db.collection("employer");


                for(int i=0;i<count;i++)
                {
                    Map<String, Object> user2 = new HashMap<>();
                    user2.put("TaskNo", taskNo.getText().toString());
                    user2.put("Task", aa);
                    user2.put("Employer", y);
                    employee.document(names.get(i)).collection("employeeTask").document(taskNo.getText().toString()).set(user2);
                }

                Map<String, Object> user3 = new HashMap<>();
                user3.put("Task", aa);
                user3.put("EmployeeNum",count);
                int j=0;
                for(int i=0;i<count;i++)
                {


                    user3.put("Employee"+i, names.get(i));

                }


                employer.document(y).collection("TaskList").document(taskNo.getText().toString()).set(user3);

                Intent intent1 = new Intent(AddTask.this,EmployerProfile.class);
                startActivity(intent1);
                finish();



            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(AddTask.this,EmployerProfile.class);
                startActivity(intent1);
                finish();
            }
        });





    }
}
