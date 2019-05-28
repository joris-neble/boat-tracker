package boat_tracker.joris.neble.boattracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public String TAG = "BoatList";
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ArrayList<Containership> listContainers = new ArrayList<>();
        ContainershipType cargo = new ContainershipType(0, "Cargo", 10, 20, 21);
        ContainershipType barge = new ContainershipType(1, "barge", 5, 2, 3);
        Port marseille = new Port(0,"Marseille", (float) 5.364227, (float)43.294628);
        Port somalie = new Port(1, "Somalie", (float)11.844445, (float) 51.301045);
        Containership bato1 = new Containership(0, "Titanic", "Michel",(float) -84.411830, (float)33.791677, cargo, marseille);
        Containership bato2 = new Containership(1, "Bato", "roiBateau",(float) 6.204019, (float)44.076219, barge, somalie);
        //writeInAllBases(bato1);
        //writeInAllBases(bato2);
        updateObjectInDb(bato1, "Containership", bato1.getName());
        getObjectInDb();




        listContainers.add(bato1);
        listContainers.add(bato2);
        final AdapterContainership adapter = new AdapterContainership(listContainers,this);

        final ListView listView = (ListView) findViewById(R.id.listOfBoat);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(MainActivity.this, OptionsBoatActivity.class);
                intent.putExtra("boat" , listContainers.get(position));
                startActivity(intent);
            }
        });
    }
    public void sendObjectInDb(Object o, String collectionPath){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collectionPath).add(o)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
    public void getObjectInDb() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Containership").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        documentSnapshot.getId()
                    }
                }
            }
        });
    }

    public void updateObjectInDb(Containership boat, String collectionPath, String documentPath){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection(collectionPath).document(documentPath);
        Map<String, Object> data = new HashMap<>();
        data.put("captainName", boat.getCaptainName());
        //data.put("containers", boat.getContainer());
        data.put("latitude", boat.getLatitude());
        data.put("longitude", boat.getLongitude());
        data.put("name", boat.getName());
        data.put("port", boat.getPort());
        data.put("type", boat.getType());

        ref.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });

    }


    public void writeInAllBases(Containership boat){
        sendObjectInDb(boat, "Containership");
        //sendObjectInDb(boat.getContainers(),"Container");
        sendObjectInDb(boat.getPort(), "Port");
        sendObjectInDb(boat.getType(), "ContainershipType");
    }

    public void updateInAllBases(Containership boat){
        //updateObjectInDb();
    }
}

