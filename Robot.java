import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.HashMap;

public class Robot {
    private Circle robot;
    private static int id;
    private int location;
    private int tenge;

    public Robot(int id, int location) {
        robot = new Circle(35);
        this.id = id;
        this.location = location;
        this.tenge = 0;
    }

    public int getId() {
        return id;
    }

    public int getLocation() {
        return this.location;
    }

    public int getTenge() {
        return this.tenge;
    }

    public void removeStore(){
        robot.erase();
    }
}
