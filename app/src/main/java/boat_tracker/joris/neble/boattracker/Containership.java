package boat_tracker.joris.neble.boattracker;

public class Containership {
    private int id;
    private String name;
    private String captainName;
    private float latitude;
    private float longitude;
    private Port port;
    private ContainershipType type;
    private Container containers;

    public Containership(int id, String name, String captainName) {
        this.id = id;
        this.name = name;
        this.captainName = captainName;
    }

    public Containership() {}

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
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public ContainershipType getType() {
        return type;
    }

    public void setType(ContainershipType type) {
        this.type = type;
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
}
