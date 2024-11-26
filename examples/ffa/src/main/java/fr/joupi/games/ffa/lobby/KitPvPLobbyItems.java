package fr.joupi.games.ffa.lobby;

import fr.joupi.games.engine.ffa.lobby.FFAGameLobbyItems;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public enum KitPvPLobbyItems implements FFAGameLobbyItems {

    PLAY (4, new ItemStack(Material.DIAMOND_AXE));

    private final int slot;
    private final ItemStack itemStack;

    KitPvPLobbyItems(int slot, ItemStack itemStack) {
        this.slot = slot;
        this.itemStack = itemStack;
    }

}