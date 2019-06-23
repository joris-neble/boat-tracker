package boat_tracker.joris.neble.boattracker;

import android.content.Intent;
import android.service.autofill.CharSequenceTransformation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddBoatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_boat);
        /**
         * Recupere tous les EditText
         * Ainsi que les spinners recuperant la liste dans le ficjiers Spinner.xm
         */
        EditText inputName =(EditText) findViewById(R.id.inputAddName);
        EditText inputCaptainName =(EditText) findViewById(R.id.inputAddCaptainName);
        EditText inputLatitude =(EditText) findViewById(R.id.inputAddLatitude);
        EditText inputLongitude = (EditText) findViewById(R.id.inputAddLongitude);

        Spinner inputType = (Spinner) findViewById(R.id.inputAddType);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.ArrayType, R.layout.support_simple_spinner_dropdown_item);
        adapterType.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        inputType.setAdapter(adapterType);

        Spinner inputPort = (Spinner) findViewById(R.id.inputAddPort);
        ArrayAdapter<CharSequence> adapterPort = ArrayAdapter.createFromResource(this,R.array.ArrayPort, R.layout.support_simple_spinner_dropdown_item);
        adapterPort.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        inputPort.setAdapter(adapterPort);

        /**
         * Button permettant d'ajouter un bateau
         * Si les champs ne sont pas remplit envoie un Toast
         * Sinon créé un containership en remplissant les valeurs dans les EditText
         * Envoie le bateau sur firebase
         * Et retourne sur l'activité principale
         */
        Button modificationButton =(Button) findViewById(R.id.buttonAddBoat);
        Containership containership = new Containership();
        modificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputCaptainName.getText().toString().isEmpty() || inputName.getText().toString().isEmpty() || inputLatitude.getText().toString().isEmpty() || inputLongitude.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Il vous manque des informations", Toast.LENGTH_LONG).show();
                else{
                    containership.setCaptainName(inputCaptainName.getText().toString());
                    containership.setName(inputName.getText().toString());
                    containership.setLatitude(Float.valueOf(inputLatitude.getText().toString()));
                    containership.setLongitude(Float.valueOf(inputLongitude.getText().toString()));
                    ContainershipType containershipType = new ContainershipType(1,inputType.getSelectedItem().toString(),50,50,50);
                    containership.setType(containershipType);
                    Port port = new Port(1, inputPort.getSelectedItem().toString(),Float.valueOf("5.364227"),Float.valueOf("43.294628"));
                    containership.setPort(port);
                    containership.pushToFirestore();
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    Containership.allContainerShip.clear();
                    startActivity(intent);
                }

            }
        });
    }
}
