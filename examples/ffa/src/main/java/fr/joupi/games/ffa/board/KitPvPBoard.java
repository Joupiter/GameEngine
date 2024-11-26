package fr.joupi.games.ffa.board;

import fr.joupi.games.engine.board.GameBoard;
import fr.joupi.games.ffa.KitPvPGame;
import fr.joupi.games.ffa.player.KitPvPPlayer;

public class KitPvPBoard extends GameBoard<KitPvPGame, KitPvPPlayer> {

    private int kills;

    public KitPvPBoard(KitPvPGame game, KitPvPPlayer gamePlayer) {
        super(game, gamePlayer);
    }

    @Override
    public void reloadData() {
        this.kills = gamePlayer.getKills();
    }

    @Override
    public void setLines(String ip) {
        objectiveSign.clearScores();
        objectiveSign.setDisplayName("Exemple");

        objectiveSign.setLine(0, "§a");
        objectiveSign.setLine(1, "§eKills: §c" + kills);
        objectiveSign.setLine(2, "§b");

        objectiveSign.updateLines();
    }
}