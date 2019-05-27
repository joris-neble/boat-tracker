package boat_tracker.joris.neble.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<Containership> listContainers = new ArrayList<>();
        ContainershipType cargo = new ContainershipType(0, "Cargo", 10, 20, 21);
        ContainershipType barge = new ContainershipType(1, "barge", 5, 2, 3);
        Port marseille = new Port(0,"Marseille", (float) 5.364227, (float)43.294628);
        Port somalie = new Port(1, "Somalie", (float)11.844445, (float) 51.301045);
        Containership bato1 = new Containership(0, "Titanic", "Michel",(float) -84.411830, (float)33.791677, cargo, marseille);
        db.collection("Container").document("Cargo").set(bato1);
        Containership bato2 = new Containership(1, "Bato", "roiBateau",(float) 6.204019, (float)44.076219, barge, somalie);
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
}

