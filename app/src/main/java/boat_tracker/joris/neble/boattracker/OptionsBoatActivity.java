package boat_tracker.joris.neble.boattracker;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;

public class OptionsBoatActivity extends AppCompatActivity {
    private Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options_boat);
        final Containership boatClicked =(Containership) getIntent().getSerializableExtra("boat");
        TextView nameBoat = (TextView)findViewById(R.id.boatN);
        TextView typeBoat = (TextView)findViewById(R.id.boatType);
        nameBoat.setText("Information : \nLe nom du Bateau est :" + boatClicked.getName());
        typeBoat.setText("Le type du bateau est :" + boatClicked.getType().getName());
        Button calculDistance = (Button) findViewById(R.id.calculDistance);
        calculDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location pointA = new Location("ptA");
                Location pointB = new Location("ptB");

                pointA.setLongitude(boatClicked.getLongitude());
                pointA.setLatitude(boatClicked.getLatitude());

                pointB.setLongitude(boatClicked.getPort().getLongitude());
                pointB.setLatitude(boatClicked.getPort().getLatitude());

                float distance = (pointA.distanceTo(pointB))/1000;
                TextView distanceCalcule = (TextView) findViewById(R.id.distCalcul);
                distanceCalcule.setText("La distance est de : " + distance);
            }
        });
        Button goToMap = (Button) findViewById(R.id.goMap);
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), MapsActivity.class);
                intent.putExtra("boat" , boatClicked);
                startActivity(intent);
            }
        });


    }


    public void Back(View view){
        finish();
    }


}
