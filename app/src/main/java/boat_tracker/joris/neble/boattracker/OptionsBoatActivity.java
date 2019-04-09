package boat_tracker.joris.neble.boattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class OptionsBoatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options_boat);
        Containership boatClicked =(Containership) getIntent().getSerializableExtra("boat");
        TextView nameBoat = (TextView)findViewById(R.id.boatN);
        TextView typeBoat = (TextView)findViewById(R.id.boatType);
        nameBoat.setText("Information : \nLe nom du Bateau est :" + boatClicked.getName());
        typeBoat.setText("\nLe type du bateau est :" + boatClicked.getType());
    }


    public void Back(View view){
        finish();
    }
}
