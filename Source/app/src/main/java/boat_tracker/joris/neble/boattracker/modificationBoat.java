package boat_tracker.joris.neble.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class modificationBoat extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_boat);
        final Containership boatClicked =(Containership) getIntent().getSerializableExtra("boat");

        /**
         * Recupere les EditText
         */
        TextView nomBateau = (TextView) findViewById(R.id.boatName);
        nomBateau.setText("Modification du bateau : " + boatClicked.getName());
        EditText inputName =(EditText) findViewById(R.id.inputName);
        EditText inputCaptainName =(EditText) findViewById(R.id.inputCaptainName);
        EditText inputLatitude =(EditText) findViewById(R.id.inputLatitude);
        EditText inputLongitude = (EditText) findViewById(R.id.inputLongitude);
        Button modificationButton =(Button) findViewById(R.id.buttonModif);
        modificationButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Check si l'input est vide ou non si oui
             * alors on garde la même valeur que le bateau a modifie
             * sinon on la modifie avec la valeur de l'input
             */
            @Override
            public void onClick(View v) {
                if(inputCaptainName.getText().toString().isEmpty())
                    boatClicked.setCaptainName(boatClicked.getCaptainName());
                else
                    boatClicked.setCaptainName(inputCaptainName.getText().toString());
                if(inputName.getText().toString().isEmpty())
                    boatClicked.setName(boatClicked.getName());
                else
                    boatClicked.setName(inputName.getText().toString());
                if(inputLatitude.getText().toString().isEmpty())
                    boatClicked.setLatitude(boatClicked.getLatitude());
                else
                    boatClicked.setLatitude(Float.valueOf(inputLatitude.getText().toString()));
                if(inputLongitude.getText().toString().isEmpty())
                    boatClicked.setLongitude(boatClicked.getLongitude());
                else
                    boatClicked.setLongitude(Float.valueOf(inputLongitude.getText().toString()));

                boatClicked.pushToFirestore();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                Containership.allContainerShip.clear();
                startActivity(intent);

            }
        });



    }
}
