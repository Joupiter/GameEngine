package fr.joupi.games.engine.ffa.lobby;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.ffa.player.FFAGamePlayer;
import fr.joupi.games.engine.ffa.player.FFAGamePlayerState;
import fr.joupi.games.engine.point.SinglePoint;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;

@Getter
@Setter
public abstract class FFAGameLobby<M extends MiniGame, G extends FFAGamePlayer, I extends Enum<I> & FFAGameLobbyItems> implements FFALobby {

    private final I[] items;

    private SinglePoint spawnPoint;

    public FFAGameLobby(Class<I> itemsClass) {
        this.items = itemsClass.getEnumConstants();
    }

    protected abstract void processJoin(M game, G gamePlayer);
    protected abstract void processSetup(M game, G gamePlayer);
    protected abstract void processPlay(M game, G gamePlayer);

    @Override
    public void onJoin(MiniGame game, FFAGamePlayer gamePlayer) {
        var castedGamePlayer = (G) gamePlayer;

        if (spawnPoint != null)
            spawnPoint.teleport(castedGamePlayer);

        cleanUp(castedGamePlayer);
        giveItems(castedGamePlayer);
        processJoin((M) game, castedGamePlayer);
    }

    @Override
    public void onSetup(MiniGame game, FFAGamePlayer gamePlayer) {
        var castedGamePlayer = (G) gamePlayer;

        castedGamePlayer.setState(FFAGamePlayerState.IN_LOBBY);

        if (spawnPoint != null)
            spawnPoint.teleport(castedGamePlayer);

        cleanUp(castedGamePlayer);
        giveItems(castedGamePlayer);
        processSetup((M) game, castedGamePlayer);
    }

    @Override
    public void onPlay(MiniGame game, FFAGamePlayer gamePlayer) {
        gamePlayer.setState(FFAGamePlayerState.IN_GAME);

        processPlay((M) game, (G) gamePlayer);
    }

    public void giveItems(G gamePlayer) {
        for (I item : items) {
            gamePlayer.getInventory().setItem(item.getSlot(), item.getItemStack());
        }
    }

    public void cleanUp(G gamePlayer) {
        var player = gamePlayer.getPlayer();
        var inventory = gamePlayer.getInventory();

        inventory.clear();
        inventory.setArmorContents(null);

        player.setHealth(20);
        player.getActivePotionEffects().clear();
        player.setGameMode(GameMode.ADVENTURE);
    }

}