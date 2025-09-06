import java.util.ArrayList;
import java.util.HashMap;


public class Store {
    private Rectangle base;
    private int location;
    private int tengeInitial;
    private int tenge;

    public Store(int location, int tenges) {
       base = new Rectangle();
       this.location = location;
       this.tengeInitial = tenges;
        this.tenge = tenges;
    }

    public int getLocation() {
        return this.location;
    }

    public int getTenge() {
        return this.tenge;
    }

    public int getTengeInitial() {
        return this.tengeInitial;
    }

    public void removeStore(){
        base.erase();
    }
}
