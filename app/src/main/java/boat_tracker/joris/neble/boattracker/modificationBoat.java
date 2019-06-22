package boat_tracker.joris.neble.boattracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class modificationBoat extends AppCompatActivity {
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_boat);
        final Containership boatClicked =(Containership) getIntent().getSerializableExtra("boat");

        TextView nomBateau = (TextView) findViewById(R.id.boatName);
        nomBateau.setText("Modification du bateau : " + boatClicked.getName());
        EditText inputName =(EditText) findViewById(R.id.inputName);
        EditText inputCaptainName =(EditText) findViewById(R.id.inputCaptainName);
        EditText inputLatitude =(EditText) findViewById(R.id.inputLatitude);
        EditText inputLongitude = (EditText) findViewById(R.id.inputLongitude);
        Button modificationButton =(Button) findViewById(R.id.buttonModif);
        modificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++id;
                boatClicked.setCaptainName(inputCaptainName.getText().toString());
                boatClicked.setName(inputName.getText().toString());
                boatClicked.setLatitude(Float.valueOf(inputLatitude.getText().toString()));
                boatClicked.setLongitude(Float.valueOf(inputLongitude.getText().toString()));
                boatClicked.pushToFirestore();
            }
        });



    }
}
