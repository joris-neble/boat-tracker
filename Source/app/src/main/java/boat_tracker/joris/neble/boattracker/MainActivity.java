package boat_tracker.joris.neble.boattracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static boat_tracker.joris.neble.boattracker.Functions.createBoat;

public class MainActivity extends AppCompatActivity {
    public String TAG = "BoatList";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 231;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Containership> listContainers = new ArrayList<>();
        /**
         * Connexion Google
         */
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
        /**
         * Création du bouton pour ajouter un bateau,
         * qui s'affiche que lorsqu'on est connecté
         * avec Google
         */
        Button addBoatButton = (Button) findViewById(R.id.addBoat);
        addBoatButton.setVisibility(View.INVISIBLE);
        addBoatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddBoatActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Creation du bouton qui afficher la liste des bateaux
         */
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
        createBoat(db ,listContainers, listView, adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
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

    /**
     *
     * @param account compte google
     *
     * Si le compte google est connecté alors on affiche le bouton deconnexion
     * Lui affiche Bonjour suivit du nom du compte et le bouton ajouter bateau
     * Et rend invisible le bouton connexion
     * Sinon rend invisible le bouton ajouter bateau
     * Rend visible le bouton connexion google
     * Met le text bonjour "", rend invisible le bouton deconnexion
     *
     */
    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            Button addBoatButton = (Button) findViewById(R.id.addBoat);
            addBoatButton.setVisibility(View.VISIBLE);
            SignInButton button = findViewById(R.id.sign_in_button);
            button.setVisibility(View.INVISIBLE);
            TextView textAccueil = findViewById(R.id.Bienvenue);
            textAccueil.setText("Bonjour " + account.getGivenName());
            Button signOutButton = findViewById(R.id.sign_out_button);
            signOutButton.setVisibility(View.VISIBLE);
        }
        else{
            Button addBoatButton = (Button) findViewById(R.id.addBoat);
            addBoatButton.setVisibility(View.INVISIBLE);
            SignInButton button = findViewById(R.id.sign_in_button);
            button.setVisibility(View.VISIBLE);
            TextView textAccueil = findViewById(R.id.Bienvenue);
            textAccueil.setText("");
            Button signOutButton = findViewById(R.id.sign_out_button);
            signOutButton.setVisibility(View.INVISIBLE);
        }
    }
}