import plantables.Plantable;
import java.util.ArrayList;

/**
 * Represents a player.
 *
 * @author katherineshambaugh
 * @version 1.0
 */
public class Player {
    /**
     * The player's inventory.
     */
    private final Inventory inventory;

    /**
     * The money owned by the player.
     */
    private int money;

    /**
     * The name of the player.
     */
    private static String name;

    /**
     * Constructs a player object.
     */
    public Player() {
        name = "";
        inventory = new Inventory();
    }

    /**
     * Returns the player's name.
     *
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     *
     * @param name a String containing the name
     */
    public void setName(final String name) {
        Player.name = name;
    }

    /**
     * Returns the player's inventory.
     *
     * @return Inventory object
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns the player's inventory list.
     *
     * @return ArrayList containing plants in inventory
     */
    public ArrayList<Plantable> getInvenList() {
        return inventory.getInventoryList();
    }

    /**
     * Sets the player's inventory.
     *
     * @param inventory ArrayList containing plants in inventory
     */
    public void setInventoryList(final ArrayList<Plantable> inventory) {
        this.inventory.setInventoryList(inventory);
    }

    /**
     * Returns the plant in inventory at
     * index.
     *
     * @param index the index to look at
     * @return Plant at index
     */
    public Plantable getInventoryAt(final int index) {
        ArrayList<Plantable> holder = inventory.getInventoryList();
        return holder.get(index);
    }

    /**
     * Returns the player's money.
     *
     * @return player's current money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the player's money.
     *
     * @param money new money value
     */
    public void setMoney(final int money) {
        this.money = money;
    }
}
