package boat_tracker.joris.neble.boattracker;

import java.io.Serializable;

public class ContainershipType implements Serializable {
    private int id;
    private String name;
    private int lenght;
    private int height;
    private int width;

    public ContainershipType(int id, String name, int lenght, int height, int width) {
        this.id = id;
        this.name = name;
        this.lenght = lenght;
        this.height = height;
        this.width = width;
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

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
