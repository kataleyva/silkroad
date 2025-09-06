import java.util.HashMap;

public class Storegroup {
    private HashMap<Integer, Store> stores = new HashMap<>();

    public void addStore(Store store) {
        stores.put(store.getLocation(), store);
    }

    public Store getStore(int location) {
        return stores.get(location);
    }
}
