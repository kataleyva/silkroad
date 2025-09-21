import java.util.ArrayList;
import java.util.HashMap;


public class Store {
    public Rectangle base;
    private int[] location;
    private int loc;
    private static int tengeInitial;
    private int tenge;

    public Store(int[] location, int tenges, int loc) {
       this.base = new Rectangle(location[0], location[1]);
       this.location = location;
       this.loc = loc;
       this.tengeInitial = tenges;
       this.tenge = tenges;
       this.base.makeVisible();
    }

    public int[] getLocation() {
        return this.location;
    }

    public int getTenge() {
        return this.tenge;
    }
    
    public void setTenge(int tenge){
        this.tenge = tenge;
        return;
    }
    
    public int getInitialTenge(){
        return this.tengeInitial;
    }
    
    public void setInitialTenge(){
        this.tenge = this.tengeInitial;
        return;
    }

    public int getLoc(){
        return this.loc;
    }

    public void removeStore(){
        this.base.makeInvisible();
    }
}
