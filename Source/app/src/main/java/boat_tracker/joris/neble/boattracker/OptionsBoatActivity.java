package boat_tracker.joris.neble.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class OptionsBoatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options_boat);
        final Containership boatClicked =(Containership) getIntent().getSerializableExtra("boat");
        TextView nameBoat = (TextView)findViewById(R.id.boatN);
        TextView typeBoat = (TextView)findViewById(R.id.boatType);
        nameBoat.setText("Information : \nLe nom du Bateau est :" + boatClicked.getName());
        typeBoat.setText("Le type du bateau est :" + boatClicked.getType().getName());

        /**
         * Permet d'utiliser le calcul de distance entre les port et le point actuel du bateau
         */
        Button calculDistance = (Button) findViewById(R.id.calculDistance);
        calculDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float distance = Functions.calculDistanceBetweenTwoPoints(boatClicked);
                TextView distanceCalcule = (TextView) findViewById(R.id.distCalcul);
                distanceCalcule.setText("La distance est de : " + distance);
            }
        });
        /**
         * Permet d'acceder a la map Google
         */
        Button goToMap = (Button) findViewById(R.id.goMap);
        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(v.getContext(), MapsActivity.class);
                intent.putExtra("boat" , boatClicked);
                startActivity(intent);
            }
        });
        /**
         * Permet d'acceder a la modification des bateaux
         */
        Button modificationBoat = (Button) findViewById(R.id.modifBoat);
        modificationBoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), boat_tracker.joris.neble.boattracker.modificationBoat.class);
                intent.putExtra("boat", boatClicked);
                startActivity(intent);
            }
        });


    }
}
