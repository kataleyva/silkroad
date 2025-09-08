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
        Store store = new Store(this.posicion[location], tenges);
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

    public void placeRobot(int location){
        idRobot ++;
        Robot robot = new Robot(idRobot, posicion[location], location);
        System.out.println(robot.getId());
        robots.put(robot.getId(), robot);
        initialRobots.put(robot.getId(), robot);
        System.out.println(location);
        return;
    }
    
    public void removeRobot(int location){
        for(Robot robot : robots.values()){
            robot.removeRobot();
            int id = robot.getId();
            this.robots.remove(id);
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
    /**
 * Reinicia la ruta de seda: tiendas y robots como fueron adicionados
 * Utiliza los métodos ya existentes ressuplyStores() y returnRobots()
 */
public void reboot(){
    ressuplyStores();
    returnRobots();
    System.out.println("Ruta de seda reiniciada - Tiendas reabastecidas y robots reposicionados");
}

/**
 * Consulta las ganancias totales obtenidas
 * En el contexto del problema: profit = tenges recolectados - costo de movimiento
 * @return ganancia total acumulada por todos los robots
 */
public int porfit(){
    int totalPorfit = 0;
    // Sumar todos los tenges recolectados por los robots
    for(Robot robot : robots.values()){
        totalPorfit += robot.getTenge();
    }
    return totalPorfit;
}

/**
 * Consulta la información de las tiendas en la ruta de seda
 * @return arreglo con información de las tiendas [ubicación, tenges actuales]
 */
public int[][] stores(){
    int[][] storesInfo = new int[stores.size()][2];
    int index = 0;
    // Recorrer el HashMap de tiendas usando el patrón ya establecido en ressuplyStores()
    for (var entry : stores.entrySet()){
        int location = entry.getKey();
        Store store = entry.getValue();  
        storesInfo[index][0] = location;           // posición en la ruta
        storesInfo[index][1] = store.getTenge();   // tenges disponibles actualmente
        index++;
    }
    
    return storesInfo;
}

/**
 * Consulta la información de los robots en la ruta de seda
 * @return arreglo con información de los robots [id, ubicación actual, tenges recolectados]
 */
public int[][] robots(){
    int[][] robotsInfo = new int[robots.size()][3];
    int index = 0; 
    // Recorrer el HashMap de robots usando el patrón ya establecido en returnRobots()
    for (var entry : robots.entrySet()){
        int robotId = entry.getKey();
        Robot robot = entry.getValue();
        
        robotsInfo[index][0] = robot.getId();    // id único del robot
        robotsInfo[index][1] = robot.getLoc();   // ubicación actual en la ruta
        robotsInfo[index][2] = robot.getTenge(); // tenges recolectados hasta ahora
        index++;
    }
    
    return robotsInfo;
}

/**
 * Hace visible el simulador mostrando todos los elementos
 * Permite ver la representación visual de la ruta de seda
 */
public void makeVisible(){
    // Hacer visibles todas las tiendas, recorriendo como en ressuplyStores()
    for (var entry : stores.entrySet()){
        Store store = entry.getValue();
        if(store.base != null){
            store.base.makeVisible();
        }
    }   
    // Hacer visibles todos los robots, recorriendo como en returnRobots()
    for (var entry : robots.entrySet()){
        Robot robot = entry.getValue();
        if(robot.robot != null){
            robot.robot.makeVisible();
        }
    } 
    System.out.println("Simulador de la Ruta de Seda ahora es visible");
}

/**
 * Hace invisible el simulador ocultando todos los elementos
 * Permite que el simulador funcione en modo invisible para mejor rendimiento
 */
public void makeInvisible(){
    // Hacer invisibles todas las tiendas, siguiendo el patrón de stores
    for (var entry : stores.entrySet()){
        Store store = entry.getValue();
        store.base.makeInvisible();
    } 
    // Hacer invisibles todos los robots, siguiendo el patrón de robots
    for (var entry : robots.entrySet()){
        Robot robot = entry.getValue();
        robot.robot.makeInvisible();
    }
    System.out.println("Simulador de la Ruta de Seda ahora es invisible");
}

/**
 * Termina el simulador y libera todos los recursos
 * Utiliza los métodos removeStore() y removeRobot() ya disponibles
 */
public void finish(){
    for (var entry : stores.entrySet()){
        Store store = entry.getValue();
        store.removeStore(); // Usa el método ya implementado
    }
    // Remover todos los robots, siguiendo el patrón de removeRobot() existente
    for (var entry : robots.entrySet()){
        Robot robot = entry.getValue();
        robot.removeRobot(); // Usa el método ya implementado
    }
    // Limpiar todas las colecciones usando clear() como en el constructor
    stores.clear();
    initialStores.clear();
    robots.clear();
    initialRobots.clear();
    // Resetear el contador de IDs siguiendo el patrón establecido
    idRobot = 0;
    System.out.println("Simulador de la Ruta de Seda terminado - Todos los recursos liberados");
}

/**
 * Verifica si la última operación se realizó correctamente
 * @return true si la última operación fue exitosa, false en caso contrario
 */
public boolean ok(){
    try {
        if(stores == null || robots == null || initialStores == null || initialRobots == null){
            return false;
        }
        if(lenRoad <= 0){
            return false;
        }
        if(posicion == null || posicion.length != lenRoad){
            return false;
        }
        return true;
    } catch (Exception e) {
        return false;
    }
}
  
}
