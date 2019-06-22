package boat_tracker.joris.neble.boattracker;

import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.Random;


public class Functions {

    private static String WAKANDA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static void createBoat(final FirebaseFirestore db , ArrayList<Containership> containershipArrayList, ListView listView, AdapterContainership adapter){

        db.collection("Containership").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                        for (final QueryDocumentSnapshot boat : task.getResult()){
                            final Containership containership = new Containership(Integer.valueOf(boat.get("id").toString()),boat.getString("name"), boat.getString("CaptainName"),
                                    Float.valueOf(boat.get("latitude").toString()), Float.valueOf(boat.get("longitude").toString()));
                            DocumentReference typeboat = db.document(boat.getString("ContainershipType"));
                            typeboat.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot currentType) {
                                    try {
                                        ContainershipType containershipType = new ContainershipType(Integer.valueOf(currentType.get("id").toString()), currentType.getString("name"),
                                                Integer.valueOf(currentType.get("lenght").toString()), Integer.valueOf(currentType.get("height").toString()), Integer.valueOf(currentType.get("width").toString()));
                                        containership.setType(containershipType);
                                    }catch (Exception e){
                                        Log.e("Wakanda", "Erreur de type" + currentType);
                                    }
                                }
                            });
                            DocumentReference PortBoat = db.document(boat.getString("Port"));
                            PortBoat.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot currentPort) {
                                    try {
                                        Port containershipPort = new Port(Integer.valueOf(currentPort.get("id").toString()), currentPort.getString("name"),
                                                Float.valueOf(currentPort.get("latitude").toString()), Float.valueOf(currentPort.get("longitude").toString()));
                                        containership.setPort(containershipPort);
                                        containershipArrayList.add(containership);
                                        listView.setAdapter(adapter);

                                    }catch (Exception e){
                                        Log.e("Wakanda", "Erreur de port " + currentPort);
                                        Log.e("MyBrother", e.toString());
                                    }
                                }
                            });
                        }
                }else{
                    Log.d("Firestore","Error getting documents", task.getException());
                }
            }
        });
    }

    public static void sendObjectInDb(Object o, String collectionPath, String document) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collectionPath).document(document).set(o);
    }
    public static float calculDistanceBetweenTwoPoints(Containership boatClicked){
        float distance;
        Location pointA = new Location("ptA");
        Location pointB = new Location("ptB");

        pointA.setLongitude(boatClicked.getLongitude());
        pointA.setLatitude(boatClicked.getLatitude());

        pointB.setLongitude(boatClicked.getPort().getLongitude());
        pointB.setLatitude(boatClicked.getPort().getLatitude());

        return distance = (pointA.distanceTo(pointB))/1000;
    }

    public static void createBOATS(Integer nbr){
        Port port = new Port(5,"Marseille", getRandomFloat(0,50), getRandomFloat(0,50));
        ContainershipType containershipType= new ContainershipType(5,"chalutier", 50,50,50);
        for (Integer cptBoatToCreate=0; cptBoatToCreate<nbr; ++cptBoatToCreate){
            Containership containership = new Containership(getRandomString(5), getRandomString(10), getRandomFloat(0,10), getRandomFloat(0,10));
            containership.setPort(port);
            containership.setType(containershipType);
            Log.e("WAKANDA", containership.toString());
            containership.pushToFirestore();
        }
    }




    public static Integer getRandomInt(Integer min, Integer max){
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
    public static Float getRandomFloat(Integer min, Integer max){
        return getRandomInt(min.intValue(), max.intValue()).floatValue();
    }
    public static String getRandomString(Integer length){
        String varToReturn = "";
        for(Integer cptString=0; cptString<length; cptString++){
            varToReturn += WAKANDA.charAt(getRandomInt(0, WAKANDA.length()-1));
        }
        return varToReturn;
    }
}
