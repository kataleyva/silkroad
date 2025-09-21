import java.util.HashMap;

/**
 * Clase que representa el simulador de la Ruta de la Seda.
 * 
 * Esta clase permite administrar tiendas (Stores) y robots (Robots)
 * que interactúan en una ruta representada en espiral. 
 * Se pueden colocar y remover tiendas y robots, mover robots a través de la ruta,
 * reiniciar el sistema, calcular las ganancias totales y visualizar la simulación.
 * 
 * Además, mantiene registros iniciales de tiendas y robots para permitir
 * un reseteo o reinicio de las condiciones iniciales.
 */
public class SilkRoad {
    private static HashMap<Integer, Store> stores;
    private static HashMap<Integer, Store> initialStores;
    private static HashMap<Integer, Robot> robots;
    private static HashMap<Integer, Robot> initialRobots;
    private static int idRobot = 0;
    private final int lenRoad;
    private int[][] posicion;

    /**
     * Constructor de la clase SilkRoad.
     * 
     * @param lengthRoad Longitud de la ruta de seda.
     */
    public SilkRoad(int lengthRoad) {
        this.lenRoad = lengthRoad;
        this.stores = new HashMap<>();
        this.initialStores = new HashMap<>();
        this.robots = new HashMap<>();
        this.initialRobots = new HashMap<>();
        this.posicion = Posicion.generateSpiral(lenRoad);

        System.out.println("Se creó la ruta de seda de longitud " + lenRoad);
    }
    /**
    * Extensión para crear una ruta de seda con la entrada del problema de la maratón
    * Requisito 10: Debe permitir crear una ruta de seda con la entrada del problema de la maratón
    * 
    * @param input Array de enteros que representa la entrada del problema de la maratón
    *              Formato: [n, s1, t1, s2, t2, ..., sn, tn, r1, r2, ..., rk]
    *              donde n es el número de tiendas, si y ti son posición y tenges de cada tienda,
    *              y ri son las posiciones iniciales de los robots
    */

    public void create(int[] input) {
        if (input == null || input.length < 1) {
            throw new IllegalArgumentException("Input no puede ser null o vacío");
        }
        int n = input[0]; // número de tiendas
        if (input.length < 1 + 2*n) {
            throw new IllegalArgumentException("Input insuficiente para el número de tiendas especificado");
        }
        // Crear las tiendas según la entrada
        for (int i = 0; i < n; i++) {
            int storePosition = input[1 + 2*i];     // posición de la tienda
            int storeTenges = input[1 + 2*i + 1];   // tenges iniciales de la tienda
            // Validar que la posición esté dentro del rango válido
            if (storePosition < 0 || storePosition >= lenRoad) {
                throw new IllegalArgumentException("Posición de tienda fuera del rango válido: " + storePosition);
            }
            placeStore(storePosition, storeTenges);
        }
        // Crear los robots con las posiciones restantes del input
        int robotStartIndex = 1 + 2*n;
        for (int i = robotStartIndex; i < input.length; i++) {
             int robotPosition = input[i];
             // Validar que la posición esté dentro del rango válido
             if (robotPosition < 0 || robotPosition >= lenRoad) {
                 throw new IllegalArgumentException("Posición de robot fuera del rango válido: " + robotPosition);
                }
             placeRobot(robotPosition);
        }
        System.out.println("Ruta de seda creada con " + n + " tiendas y " + 
                           (input.length - robotStartIndex) + " robots");
    }

    /**
     * Coloca una tienda en la ruta en una ubicación específica.
     * 
     * @param location Posición en la ruta.
     * @param tenges   Cantidad inicial de tenges de la tienda.
     */
    public void placeStore(int location, int tenges){
        Store store = new Store(this.posicion[location], tenges, location);
        stores.put(location, store);
        initialStores.put(location, store);
    }

    /**
     * Elimina una tienda de la ruta en una ubicación específica.
     * 
     * @param location Posición de la tienda a eliminar.
     */
    public void removeStore(int location){
        Store store = stores.get(location);
        if (store != null) {
            store.removeStore();
            stores.remove(location);
        }
    }

    /**
     * Coloca un robot en la ruta en una ubicación específica.
     * 
     * @param location Posición inicial del robot.
     */
    public void placeRobot(int location) {
        idRobot++;
        Robot robot = new Robot(idRobot, posicion[location], location);
        robots.put(robot.getId(), robot);
        initialRobots.put(robot.getId(), robot);
    }

    /**
     * Elimina un robot de una ubicación específica en la ruta.
     * 
     * @param location Posición del robot a eliminar.
     */
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

    /**
     * Obtiene el primer robot que se encuentre en una ubicación dada.
     * 
     * @param location Posición en la ruta.
     * @return El primer robot en la posición o null si no existe.
     */
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

    /**
     * Obtiene la primera tienda en una ubicación dada.
     * 
     * @param location Posición en la ruta.
     * @return La primera tienda en la posición o null si no existe.
     */
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

    /**
     * Transfiere los tenges de una tienda a un robot.
     * 
     * @param robot Robot que recibe los tenges.
     * @param store Tienda que transfiere los tenges.
     * @return Cantidad total de tenges acumulados en el robot después de la transacción.
     */
    private int takeTenges(Robot robot, Store store) {
        int newTenges = 0;
        int newRobotTenges = store.getTenge() + robot.getTenge();
        store.setTenge(newTenges);
        return newRobotTenges;
    }

    /**
    * Extensión para mover robot de forma inteligente maximizando ganancia
    * Requisito 11: Debe permitir a los robots decidir sus movimientos buscando maximizar la ganancia
    * 
    * @param robotId ID del robot a mover
    * @param maxDistance Distancia máxima que puede moverse el robot
    * @return Array con [posiciónFinal, tengesTotalesObtenidos] o null si no se puede mover
    */
    public int[] moveRobot(int robotId, int maxDistance) {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            return null; // Robot no existe
        }
        int currentLocation = robot.getLoc();
        int bestMove = 0;
        int maxPotentialGain = 0;
        int bestFinalTenges = robot.getTenge();
        // Evaluar todas las posibles distancias de movimiento
        for (int distance = -maxDistance; distance <= maxDistance; distance++) {
             if (distance == 0) continue; // No moverse no es una opción
             int targetLocation = currentLocation + distance;
             // Verificar que la nueva posición esté dentro del rango válido
             if (targetLocation < 0 || targetLocation >= lenRoad) {
                 continue;
             }
             // Calcular ganancia potencial en esa posición
             int potentialGain = calculatePotentialGain(targetLocation, robot);
             // Si esta posición ofrece mejor ganancia, actualizarla como mejor opción
             if (potentialGain > maxPotentialGain) {
                 maxPotentialGain = potentialGain;
                 bestMove = distance;
                 bestFinalTenges = robot.getTenge() + potentialGain;
             }
        }
        // Si encontramos un movimiento beneficioso, ejecutarlo
        if (bestMove != 0) {
            moveRobot(currentLocation, bestMove);
            // Obtener la nueva información del robot después del movimiento
            Robot updatedRobot = robots.get(robotId);
            if (updatedRobot != null) {
                return new int[]{updatedRobot.getLoc(), updatedRobot.getTenge()};
            }
        }
        // Si no hay movimiento beneficioso, quedarse en la posición actual
        return new int[]{currentLocation, robot.getTenge()};
    }
//Metodos auxiliares de move robot
/**
 * Método auxiliar para calcular la ganancia potencial en una posición específica
 * 
 * @param location Posición a evaluar
 * @param robot Robot que está evaluando la posición
 * @return Ganancia potencial en tenges
 */
private int calculatePotentialGain(int location, Robot robot) {
    Store storeAtLocation = getFirstStoreAtLocation(location);
    
    if (storeAtLocation != null && storeAtLocation.getTenge() > 0) {
        return storeAtLocation.getTenge();
    }
    
    return 0; // No hay ganancia si no hay tienda o si la tienda está vacía
}

/**
 * Sobrecarga del método moveRobot para compatibilidad con múltiples robots
 * Mueve todos los robots de forma inteligente en una sola llamada
 * 
 * @param maxDistance Distancia máxima que pueden moverse los robots
 * @return Matriz con información de movimientos: [robotId, posiciónFinal, tengesTotales]
 */
public int[][] moveAllRobots(int maxDistance) {
    int[][] results = new int[robots.size()][3];
    int index = 0;
    // Crear una copia de las claves para evitar modificaciones concurrentes
    Integer[] robotIds = robots.keySet().toArray(new Integer[0]);
    for (Integer robotId : robotIds) {
        int[] moveResult = moveRobot(robotId, maxDistance);
        if (moveResult != null) {
            results[index][0] = robotId;           // ID del robot
            results[index][1] = moveResult[0];     // Posición final
            results[index][2] = moveResult[1];     // Tenges totales
            index++;
        }
    }
    // Redimensionar el array si algunos robots no se pudieron mover
    if (index < results.length) {
        int[][] finalResults = new int[index][3];
        System.arraycopy(results, 0, finalResults, 0, index);
        return finalResults;
    }
    return results;
}
    /**
     * Reabastece las tiendas que se quedaron sin tenges.
     */
    public void ressuplyStores(){
        for (var i : stores.entrySet()){
            if (i.getValue().getTenge() == 0){
                int location = i.getKey();
                Store initialTenge = initialStores.get(location);
                i.setValue(initialTenge);
            }
        }
    }
    
    /**
     * Retorna los robots a su ubicación inicial.
     */
    public void returnRobots(){
    for (var entry : robots.entrySet()){
        int robotId = entry.getKey();
        Robot currentRobot = entry.getValue();
        Robot initialRobot = initialRobots.get(robotId);
        if (initialRobot != null) {
            // Hacer invisible el robot actual
            currentRobot.makeInvisible();
            // Reemplazar con el robot inicial (posición y estado originales)
            robots.put(robotId, new Robot(robotId, initialRobot.getLocation().clone(), initialRobot.getLoc()));
        }
    }
    }

    /**
     * Reinicia la simulación de la Ruta de la Seda,
     * reabasteciendo tiendas y retornando robots a sus posiciones iniciales.
     */
    public void reboot(){
        ressuplyStores();
        returnRobots();
        System.out.println("Ruta de seda reiniciada - Tiendas reabastecidas y robots reposicionados");
    }

    /**
     * Calcula la ganancia total obtenida por los robots.
     * 
     * @return Cantidad total de tenges obtenida.
     */
    public int profit(){
        int totalProfit = 0;
        for(Robot robot : robots.values()){
            totalProfit += robot.getTenge();
        }
        return totalProfit;
    }

    /**
     * Devuelve información de las tiendas actuales en el simulador.
     * 
     * @return Matriz con ubicación y tenges de cada tienda.
     */
    public int[][] stores(){
        int[][] storesInfo = new int[stores.size()][2];
        int index = 0;
        for (var entry : stores.entrySet()){
            int location = entry.getKey();
            Store store = entry.getValue();
            storesInfo[index][0] = location;
            storesInfo[index][1] = store.getTenge();
            index++;
        }
        return storesInfo;
    }

    /**
     * Devuelve información de los robots actuales en el simulador.
     * 
     * @return Matriz con id, ubicación y tenges de cada robot.
     */
    public int[][] robots(){
        int[][] robotsInfo = new int[robots.size()][3];
        int index = 0;
        for (var entry : robots.entrySet()){
            int robotId = entry.getKey();
            Robot robot = entry.getValue();
            robotsInfo[index][0] = robot.getId();
            robotsInfo[index][1] = robot.getLoc();
            robotsInfo[index][2] = robot.getTenge();
            index++;
        }
        return robotsInfo;
    }

    /**
     * Hace visible la simulación gráfica de la Ruta de la Seda.
     */
    public void makeVisible(){
        for (var entry : stores.entrySet()){
            Store store = entry.getValue();
            if(store.base != null){
                store.base.makeVisible();
            }
        }
        for (var entry : robots.entrySet()){
            Robot robot = entry.getValue();
            robot.makeVisible();
        }
        System.out.println("Simulador de la Ruta de Seda ahora es visible");
    }

    /**
     * Hace invisible la simulación gráfica de la Ruta de la Seda.
     */
    public void makeInvisible(){
        for (var entry : stores.entrySet()){
            Store store = entry.getValue();
            if (store.base != null) {
                store.base.makeInvisible();
            }
        }
        for (var entry : robots.entrySet()){
            Robot robot = entry.getValue();
            robot.makeInvisible();
        }
        System.out.println("Simulador de la Ruta de Seda ahora es invisible");
    }

    /**
     * Termina la simulación de la Ruta de la Seda,
     * liberando todos los recursos (tiendas y robots).
     */
    public void finish(){
        for (var entry : stores.entrySet()){
            Store store = entry.getValue();
            store.removeStore();
        }
        for (var entry : robots.entrySet()){
            Robot robot = entry.getValue();
            robot.removeRobot();
        }
        stores.clear();
        initialStores.clear();
        robots.clear();
        initialRobots.clear();
        idRobot = 0;
        System.out.println("Simulador de la Ruta de Seda terminado - Todos los recursos liberados");
    }

    /**
     * Verifica si el simulador está correctamente configurado.
     * 
     * @return true si está en un estado válido, false en caso contrario.
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
