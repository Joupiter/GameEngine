package fr.joupi.games.engine.kit;

import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.utils.CollectionUtils;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public abstract class GameKit {

    protected final Map<Integer, ItemStack> inventory;
    protected final Map<ArmorType, ItemStack> armors;

    public GameKit() {
        this.inventory = new HashMap<>(35);
        this.armors = new HashMap<>(4);
    }

    public abstract void setup();

    public void addItem(ItemStack itemStack) {
        addItem(1, itemStack);
    }

    public void addItem(int quantity, ItemStack itemStack) {
        if (canAdd(quantity))
            CollectionUtils.putInEmptyIndex(quantity, 35, inventory, itemStack);
    }

    public void setItem(int slot, ItemStack itemStack) {
        if (canAddAt(slot))
            inventory.put(slot, itemStack);
    }

    public void setItem(Map<Integer, ItemStack> map) {
        map.forEach(this::setItem);
    }

    public void setItem(List<ItemStack> list) {
        list.stream()
                .collect(Collectors.toMap(list::indexOf, itemStack -> itemStack))
                .forEach(this::setItem);
    }

    public void setHelmet(ItemStack itemStack) {
        armors.put(ArmorType.HELMET, itemStack);
    }

    public void setChestplate(ItemStack itemStack) {
        armors.put(ArmorType.CHESTPLATE, itemStack);
    }

    public void setLeggings(ItemStack itemStack) {
        armors.put(ArmorType.LEGGINGS, itemStack);
    }

    public void setBoots(ItemStack itemStack) {
        armors.put(ArmorType.BOOTS, itemStack);
    }

    public void setArmor(Function<ArmorType, ItemStack> function) {
        Arrays.asList(ArmorType.values())
                .forEach(type -> armors.put(type, function.apply(type)));
    }

    public void giveKit(Player player) {
        player.getInventory().setArmorContents(armorToArray());
        inventory.forEach(player.getInventory()::setItem);
    }

    public void giveKit(BaseGamePlayer gamePlayer) {
        gamePlayer.getInventory().setArmorContents(armorToArray());
        inventory.forEach(gamePlayer.getInventory()::setItem);
    }

    public void giveItems(Player player) {
        inventory.forEach(player.getInventory()::setItem);
    }

    public void giveItems(BaseGamePlayer gamePlayer) {
        inventory.forEach(gamePlayer.getInventory()::setItem);
    }

    public void giveArmor(Player player) {
        player.getInventory().setArmorContents(armorToArray());
    }

    public void giveArmor(BaseGamePlayer gamePlayer) {
        gamePlayer.getInventory().setArmorContents(armorToArray());
    }

    public boolean canAdd(int quantity) {
        return (inventory.size() + quantity) <= 35;
    }

    public boolean canAddAt(int slot) {
        return slot >= 0 && slot <= 35;
    }

    private ItemStack[] armorToArray() {
        return Arrays.stream(ArmorType.values())
                .map(armors::get)
                .toArray(ItemStack[]::new);
    }

    public void sendDebugMessage(CommandSender sender) {
        sender.sendMessage("ITEMS: " + inventory.entrySet().stream().map(this::mapEntryToString).collect(Collectors.joining(", ")));
        sender.sendMessage("ARMOR: " + armors.entrySet().stream().map(this::mapEntryToString).collect(Collectors.joining(", ")));
    }

    private String mapEntryToString(Map.Entry<?, ItemStack> entry) {
        return "{" + entry.getKey() + ":" + entry.getValue().getType() + "}";
    }

    public enum ArmorType {

        BOOTS,
        LEGGINGS,
        CHESTPLATE,
        HELMET;

    }

}