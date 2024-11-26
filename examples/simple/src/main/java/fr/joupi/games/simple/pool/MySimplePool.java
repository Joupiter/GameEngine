package fr.joupi.games.simple.pool;

import fr.joupi.games.api.DirectConnectStrategy;
import fr.joupi.games.api.pool.GamePool;
import fr.joupi.games.simple.MySimpleGame;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

public class MySimplePool extends GamePool<MySimpleGame> {

    public MySimplePool() {
        super(MySimpleGame.class, "MySimpleGame Pool", 1, 10, DirectConnectStrategy.CUSTOM);
    }

    @Override
    public Supplier<MySimpleGame> newGame() {
        return MySimpleGame::new;
    }

    @Override
    public void useCustomStrategy(Player player) {
        // JUST AN EXAMPLE OF USE CUSTOM STRATEGY
        gamesManager.getEmptyGames()
                .forEach(game -> game.joinGame(player));
    }

}