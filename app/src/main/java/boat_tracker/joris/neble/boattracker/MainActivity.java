package boat_tracker.joris.neble.boattracker;

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

        final ArrayList<String> listContainers = new ArrayList<>();
        Containership Bato1 = new Containership(0, "Titanic", "Michel");
        Containership Bato2 = new Containership(1, "Bato", "roiBateau");
        listContainers.add(Bato1.toString());
        listContainers.add(Bato2.toString());
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, listContainers);

        final ListView listView = (ListView) findViewById(R.id.listOfBoat);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), listContainers.get(position) , Toast.LENGTH_SHORT).show();
            }
        });
    }
}

