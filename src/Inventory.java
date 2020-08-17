import plantables.*;
import java.util.ArrayList;

/**
 * Represents an inventory.
 *
 * @author katherineshambaugh
 * @version 1.0
 */
public class Inventory {
    /**
     * The ArrayList to store harvested plants.
     */
    private ArrayList<Plantable> inventoryList;

    /**
     * The inventory capacity.
     */
    private final int capacity;

    /**
     * Creates an Inventory object.
     */
    public Inventory() {
        inventoryList = new ArrayList<>();
        capacity = 15;
    }

    /**
     * Adds a seed to the inventory.
     *
     * @param type a String with the type of seed
     * @param x the number of seeds to add
     */
    public void addSeed(final String type, final int x) {
        if (x != 0) {
            for (int i = 0; i < x; i++) {
                inventoryList.add(new Seed(type));
            }
        }
    }

    /**
     * Adds a watermelon to the inventory.
     *
     * @param x the number of watermelons to add
     */
    public void addWatermelon(final int x) {
        if (x != 0) {
            for (int i = 0; i < x; i++) {
                inventoryList.add(new Watermelon());
            }
        }
    }

    /**
     * Adds a carrot to the inventory.
     *
     * @param x the number of carrots to add
     */
    public void addCarrot(final int x) {
        if (x != 0) {
            for (int i = 0; i < x; i++) {
                inventoryList.add(new Carrot());
            }
        }
    }

    /**
     * Adds wheat to the inventory.
     *
     * @param x the number of wheat to add
     */
    public void addWheat(final int x) {
        if (x != 0) {
            for (int i = 0; i < x; i++) {
                inventoryList.add(new Wheat());
            }
        }
    }

    /**
     * Returns the inventory list.
     *
     * @return inventory list
     */
    public ArrayList<Plantable> getInventoryList() {
        return inventoryList;
    }

    /**
     * Sets the inventory list.
     *
     * @param p an ArrayList of Plants
     */
    public void setInventoryList(final ArrayList<Plantable> p) {
        inventoryList = p;
    }

    /**
     * Gets inventory capacity.
     *
     * @return inventory capacity
     */
    public int getCapacity() {
        return capacity;
    }
}

