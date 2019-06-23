package boat_tracker.joris.neble.boattracker;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Containership implements Serializable {
    public static ArrayList<Containership> allContainerShip = new ArrayList<>();
    private int id;
    private String name;
    private String captainName;
    private float latitude;
    private float longitude;
    private Port Port;
    private ContainershipType ContainershipType;
    private Container containers;

    public  Containership(){
        allContainerShip.add(this);
        this.id = allContainerShip.size();
    }

    public Containership(String name, String captainName, float latitude, float longitude){
        this.name = name;
        this.captainName = captainName;
        this.latitude = latitude;
        this.longitude = longitude;
        allContainerShip.add(this);
        this.id = allContainerShip.size();
    }
    public Containership(int id,String name, String captainName, float latitude, float longitude){
        this.name = name;
        this.captainName = captainName;
        this.latitude = latitude;
        this.longitude = longitude;
        allContainerShip.add(this);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Port getPort() {
        return Port;
    }

    public void setPort(Port port) {
        this.Port = port;
    }

    public ContainershipType getType() {
        return ContainershipType;
    }

    public void setType(ContainershipType type) {
        this.ContainershipType = type;
    }

    public Container getContainers() {
        return containers;
    }

    public void setContainers(Container containers) {
        this.containers = containers;
    }

    @Override
    public String toString() {
        return  "Le Bateau n°"+ id + " s'appelle " + name + " est le capitaine est " + captainName;
    }

    /**
     * Envoie un bateau sur Firebase
     */
    public void pushToFirestore(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map boat = new HashMap();
        boat.put("name", this.name);
        boat.put("CaptainName", this.captainName);
        boat.put("latitude", this.latitude);
        boat.put("longitude", this.longitude);
        boat.put("id", this.id);
        boat.put("Port", "/Port/"+this.Port.getName());
        boat.put("ContainershipType", "/ContainershipType/" + this.ContainershipType.getName());
        FirebaseFirestore.getInstance().document("Containership/" + this.id).set(boat);
    }
}
