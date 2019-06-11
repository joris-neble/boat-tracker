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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
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
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 231;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ArrayList<Containership> listContainers = new ArrayList<>();
        ContainershipType cargo = new ContainershipType(0, "Cargo", 10, 20, 21);
        ContainershipType barge = new ContainershipType(1, "barge", 5, 2, 3);
        Port marseille = new Port(0, "Marseille", (float) 5.364227, (float) 43.294628);
        Port somalie = new Port(1, "Somalie", (float) 11.844445, (float) 51.301045);
        Containership bato1 = new Containership(0, "Titanic", "Michel", (float) -84.411830, (float) 33.791677, cargo, marseille);
        Containership bato2 = new Containership(1, "Bato", "roiBateau", (float) 6.204019, (float) 44.076219, barge, somalie);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        Button signOutButton = findViewById(R.id.sign_out_button);
        signOutButton.setVisibility(View.INVISIBLE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });


        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.sign_out_button:
                        signOut();
                        break;
                }

            }
        });

        listContainers.add(bato1);
        listContainers.add(bato2);
        final AdapterContainership adapter = new AdapterContainership(listContainers, this);

        final ListView listView = (ListView) findViewById(R.id.listOfBoat);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, OptionsBoatActivity.class);
                intent.putExtra("boat", listContainers.get(position));
                startActivity(intent);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void sendObjectInDb(Object o, String collectionPath, String document) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collectionPath).document(document).set(o);

    }

    public boolean getObjectNameInDb(final String doc) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final boolean exists = false;
        db.collection("Containership").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        if (doc.equals(documentSnapshot.getId())) {
                            setExists(exists);
                            return;
                        }
                    }
                }
            }
        });
        return exists;
    }

    private void setExists(boolean exists) {
        exists = !exists;
    }

    public void checkBoatExists(Containership boat) {
        if (getObjectNameInDb(boat.getName()))
            writeInAllBases(boat);
        else
            updateObjectInDb(boat, "Containership", boat.getName());
    }

    public void updateObjectInDb(Containership boat, String collectionPath, String documentPath) {
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


    public void writeInAllBases(Containership boat) {
        sendObjectInDb(boat, "Containership", boat.getName());
        //sendObjectInDb(boat.getContainers(),"Container", boat.getName());
        sendObjectInDb(boat.getPort(), "Port", boat.getName());
        sendObjectInDb(boat.getType(), "ContainershipType", boat.getName());
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Déconnexion", Toast.LENGTH_LONG).show();
                updateUI(null);
            }
        });
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            SignInButton button = findViewById(R.id.sign_in_button);
            button.setVisibility(View.INVISIBLE);
            TextView textAccueil = findViewById(R.id.Bienvenue);
            textAccueil.setText("Bonjour " + account.getGivenName());
            Button signOutButton = findViewById(R.id.sign_out_button);
            signOutButton.setVisibility(View.VISIBLE);
        }
        else{
            SignInButton button = findViewById(R.id.sign_in_button);
            button.setVisibility(View.VISIBLE);
            TextView textAccueil = findViewById(R.id.Bienvenue);
            textAccueil.setText("");
            Button signOutButton = findViewById(R.id.sign_out_button);
            signOutButton.setVisibility(View.INVISIBLE);
        }
    }
}