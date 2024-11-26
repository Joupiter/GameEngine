package fr.joupi.games.engine;

import fr.joupi.games.engine.board.GameBoard;
import fr.joupi.games.engine.board.GameBoardManager;
import fr.joupi.games.engine.map.MapManager;
import fr.joupi.games.engine.player.BaseGamePlayer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
public abstract class GameSettings<M extends MapManager<?>> {

    protected final GameSize gameSize;

    protected M gameMapManager;
    protected GameBoardManager boardManager;

    public GameSettings(GameSize gameSize) {
        this.gameSize = gameSize;
    }

    public GameSettings(GameSize gameSize, GameBoardManager boardManager) {
        this(gameSize);
        this.boardManager = boardManager;
    }

    public Optional<M> getMapManagerOptional() {
        return Optional.ofNullable(gameMapManager);
    }

    public GameBoard<?, ?> defaultBoard(MiniGame game, BaseGamePlayer gamePlayer) {
        throw new NoSuchMethodError();
    }

    public void endMapManager() {
        if (gameMapManager != null)
            gameMapManager.end();
    }

    public void addBoardViewer(MiniGame game, BaseGamePlayer gamePlayer) {
        if (boardManager != null)
            boardManager.onLogin(gamePlayer, defaultBoard(game, gamePlayer));
    }

    public void removeBoardViewer(UUID uuid) {
        if (boardManager != null)
            boardManager.onLogout(uuid);
    }

    public void disableBoard() {
        if (boardManager != null)
            boardManager.onDisable();
    }

}