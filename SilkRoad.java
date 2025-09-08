/**
 * Write a description of class SilkRoad here.
 *
 * @author Maria Katalina Leyva Diaz - Michelle Dayanna Ruiz Carranza
 * @version 7.09.2025
 */

import java.lang.Math;
import java.util.HashMap;

public class SilkRoad{
    private static HashMap<Integer, Store> stores;
    private static HashMap <Integer, Store> initialStores;
    private static HashMap <Integer, Robot> robots;
    private static HashMap <Integer, Robot> initialRobots;
    private static int idRobot = 0;
    private final int lenRoad;
    private int[][] posicion;

    public SilkRoad(int lengthRoad) {
        this.lenRoad = lengthRoad;
        this.stores = new HashMap<>();
        this.initialStores = new HashMap<>();
        this.robots = new HashMap<>();
        this.initialRobots = new HashMap<>();
        this.posicion = Posicion.generateSpiral(lenRoad);
    
        System.out.println("Se creó la ruta de seda de longitud "+lenRoad);
        return;
    }

    public void placeStore(int location, int tenges){
        Store store = new Store(this.posicion[location], tenges, location);
        stores.put(location, store);
        initialStores.put(location, store);
        return;
    }

    public void removeStore(int location){
        Store store = stores.get(location);
        store.removeStore();
        stores.remove(location);
        return;
    }

    public void placeRobot(int location) {
        idRobot++;
        Robot robot = new Robot(idRobot, posicion[location], location);
        robots.put(robot.getId(), robot);
        initialRobots.put(robot.getId(), robot);
        return;
    }
    
    public void removeRobot(int location) {
        var it = robots.values().iterator();
        while (it.hasNext()) {
            var robot = it.next();
            if (robot.getLoc() == location) {
                robot.removeRobot();
                it.remove();
            }
        }
    }

    private Robot getFirstRobotAtLocation(int location) {
        var it = robots.values().iterator();
        while (it.hasNext()) {
            Robot robot = it.next();
            if (robot.getLoc() == location) {
                return robot;
            }
        }
        return null;
    }

    private Store getFirstStoreAtLocation(int location) {
        var it = stores.values().iterator();
        while (it.hasNext()) {
            Store store = it.next();
            if (store.getLoc() == location) {
                return store;
            }
        }
        return null;
    }

    private int takeTenges(Robot robot, Store store) {
        int newTenges = 0;
        int newRobotTenges = store.getTenge() + robot.getTenge();
        store.setTenge(newTenges);
        return newRobotTenges;
    }

    public void moveRobot(int location, int meters){
        if (meters != 0){
            Robot robot = getFirstRobotAtLocation(location);
            robot.removeRobot();
            int newLocation = location + meters;
            Robot robotNewLocation = new Robot(robot.getId(), posicion[newLocation], newLocation);
            
            robots.remove(getFirstRobotAtLocation(location).getId());
            robots.put(robot.getId(), robotNewLocation);
            
            if (stores.containsValue(getFirstStoreAtLocation(location))){
                takeTenges(robotNewLocation, getFirstStoreAtLocation(location));
                return;
            }
        } else {
            return;
        }

    }

    public void ressuplyStores(){
        for (var i : stores.entrySet()){
            if (i.getValue().getTenge() == 0){
                int location = i.getKey();
                Store initialTenge = initialStores.get(location);
                i.setValue(initialTenge);
            }
        }
          return;
    };

    public void returnRobots(){
        for (var i : robots.entrySet()){
            int robotId = i.getKey();
            Robot initialLocation = initialRobots.get(robotId);
            i.setValue(initialLocation);
        }
        return;
    }
}
