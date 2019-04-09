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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Containership> listContainers = new ArrayList<>();
        Containership bato1 = new Containership(0, "Titanic", "Michel",);
        Containership bato2 = new Containership(1, "Bato", "roiBateau");
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

