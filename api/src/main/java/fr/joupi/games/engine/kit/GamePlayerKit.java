package fr.joupi.games.engine.kit;

import fr.joupi.games.engine.player.BaseGamePlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public abstract class GamePlayerKit<G extends BaseGamePlayer> extends GameKit {

    protected final G gamePlayer;
    protected final Player player;

    public GamePlayerKit(G gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.player = gamePlayer.getPlayer();
    }

    public void giveKit() {
        giveKit(gamePlayer);
    }

    public void giveItems() {
        giveItems(gamePlayer);
    }

    public void giveArmor() {
        giveArmor(gamePlayer);
    }

    public void editKit(ItemStack[] playerInventory, ItemStack[] armorContents) {
        inventory.clear();
        editInventory(playerInventory);
        editArmor(armorContents);
    }

    public void editKit() {
        editKit(player.getInventory().getContents(),
                player.getInventory().getArmorContents());
    }

    public void editInventory(ItemStack[] inventory) {
        for (int i = 0; i < 36; i++) {
            if (inventory[i] != null)
                setItem(i, inventory[i]);
        }
    }

    public void editArmor(ItemStack[] armorContents) {
        Arrays.stream(ArmorType.values())
                .forEach(armorType -> armors.put(armorType, armorContents[armorType.ordinal()]));
    }

}