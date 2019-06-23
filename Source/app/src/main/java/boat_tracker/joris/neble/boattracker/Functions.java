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

import java.util.ArrayList;


public class Functions {
    /**
     *
     * @param db Base de donnée Firebase
     * @param containershipArrayList  List permettant de stocker les bateaux par la suite
     * @param listView ListView de bateaux
     * @param adapter Adapteur permettant d'afficher la listView
     *
     * Recupere tous les bateaux de firebase
     */
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
                                        Log.e("BoatList", "Erreur de type" + currentType);
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
                                        Log.e( "BoatList", "Erreur de port " + currentPort);
                                        Log.e("BoatList", e.toString());
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
}
