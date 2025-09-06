/**
 * Write a description of class SilkRoad here.
 *
 * @author Maria Katalina Leyva Diaz - Michelle Dayanna Ruiz Carranza
 * @version 7.09.2025
 */

import java.lang.Math;
import java.util.HashMap;

public class SilkRoad{
    private static HashMap<Integer, Integer> stores;
    private static HashMap <Integer, Integer> initialStores;
    private static HashMap <Integer, Integer> robots;
    private static HashMap <Integer, Integer> initialRobots;
    private static int idRobot = 0;
    private final int lenRoad;

    public SilkRoad(int lengthRoad) {
        lenRoad = lengthRoad;
        stores = new HashMap<>();
        initialStores = new HashMap<>();
        robots = new HashMap<>();
        initialRobots = new HashMap<>();
        System.out.println("Se creó la ruta de seda de longitud "+lenRoad);
        return;
    }

    public void placeStore(int location, int tenges){
        stores.put(location, tenges);
        initialStores.put(location, tenges);
        return;
    }

    public void removeStore(int location){
        stores.remove(location);
        return;
    }

    public void placeRobot(int location){
        idRobot ++;
        Robot robot = new Robot(idRobot, location);
        robots.put(robot.getId(), robot.getLocation());
        initialRobots.put(robot.getId(), robot.getLocation());
        return;
    }

    public void ressuplyStores(){
        for (var i : stores.entrySet()){
            if (i.getValue() == 0){
                int location = i.getKey();
                int initialTenge = initialStores.get(location);
                i.setValue(initialTenge);
            }
        }
      return;
    };

    public void returnRobots(){
        for (var i : robots.entrySet()){
            int robotId = i.getKey();
            int initialLocation = initialRobots.get(robotId);
            i.setValue(initialLocation);
        }
        return;
    }
}
