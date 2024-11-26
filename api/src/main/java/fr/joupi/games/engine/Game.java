package fr.joupi.games.engine;

import fr.joupi.games.engine.event.player.GamePlayerLeaveEvent;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.player.GamePlayer;
import fr.joupi.games.engine.team.GameTeam;
import fr.joupi.games.engine.team.GameTeamColor;
import fr.joupi.games.engine.utils.CollectionUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class Game<G extends GamePlayer<T>, T extends GameTeam<G>, S extends GameSettings<?>> extends SimpleGame<G, S> {

    protected final List<T> teams;

    public Game(String name, S settings) {
        super(name, settings);
        this.teams = new ArrayList<>(gameSize.getTeamNeeded());
        this.addDefaultTeams();
    }

    public abstract T defaultGameTeam(int maxSize, GameTeamColor teamColor);

    @Override
    public void leaveGame(UUID uuid) {
        ifContainsPlayer(uuid, gamePlayer -> {
            this.playerCount -= 1;
            settings.removeBoardViewer(uuid);
            removePlayerFromTeam(gamePlayer);

            Bukkit.getPluginManager().callEvent(new GamePlayerLeaveEvent<>(this, gamePlayer));
            players.remove(uuid);

            log.info("[{}] {} leave game.", getFullName(), uuid.toString());
        });
    }

    private void addDefaultTeams() {
        GameTeamColor.getColorsAsStream(gameSize.getTeamNeeded())
                .map(color -> defaultGameTeam(gameSize.getTeamMaxPlayer(), color))
                .forEach(teams::add);
    }

    public void fillTeam() {
        getPlayersWithoutTeam().forEach(this::addPlayerToTeamWithLeastPlayer);
    }

    public void addPlayerToTeam(G gamePlayer, T gameTeam) {
        removePlayerFromTeam(gamePlayer);
        gameTeam.addMember(gamePlayer);
    }

    public void addPlayerToTeamWithLeastPlayer(G gamePlayer) {
        getTeamWithLeastPlayers()
                .filter(gameTeam -> gameTeam.isNotMember(gamePlayer))
                .ifPresent(gameTeam -> addPlayerToTeam(gamePlayer, gameTeam));
    }

    public void addPlayerToRandomTeam(G gamePlayer) {
        CollectionUtils.random(getReachableTeams())
                .ifPresent(team -> addPlayerToTeam(gamePlayer, team));
    }

    public void removePlayerFromTeam(G gamePlayer) {
        teams.forEach(gameTeam -> gameTeam.getMembers().removeIf(gamePlayer::equals));
    }

    public void removePlayerFromTeam(UUID uuid) {
        getPlayer(uuid).ifPresent(this::removePlayerFromTeam);
    }

    public List<G> getPlayersWithTeam() {
        return players.values().stream().filter(this::hasTeam).toList();
    }

    public List<G> getPlayersWithoutTeam() {
        return players.values().stream().filter(this::hasNoTeam).toList();
    }

    public List<T> getAliveTeams() {
        return teams.stream().filter(GameTeam::hasPlayersAlive).toList();
    }

    public List<T> getReachableTeams() {
        return teams.stream().filter(GameTeam::canJoin).toList();
    }

    public Optional<T> getTeam(GameTeamColor color) {
        return teams.stream().filter(gameTeam -> gameTeam.getColor().equals(color)).findFirst();
    }

    public Optional<T> getTeam(String teamName) {
        return teams.stream().filter(gameTeam -> gameTeam.getName().equalsIgnoreCase(teamName)).findFirst();
    }

    public Optional<T> getTeam(G gamePlayer) {
        return teams.stream().filter(gameTeam -> gameTeam.isMember(gamePlayer)).findFirst();
    }

    public Optional<T> getTeam(Player player) {
        return teams.stream().filter(gameTeam -> gameTeam.isMember(player.getUniqueId())).findFirst();
    }

    public Optional<T> getRandomReachableTeam() {
        return CollectionUtils.random(getReachableTeams());
    }

    public Optional<T> getTeamWithLeastPlayers() {
        return teams.stream()
                .filter(GameTeam::canJoin)
                .min(Comparator.comparingInt(GameTeam::getSize));
    }

    public Optional<T> getFirstTeamAlive() {
        return Optional.ofNullable(getAliveTeams().getFirst());
    }

    public boolean hasTeam(G gamePlayer) {
        return getTeam(gamePlayer).isPresent();
    }

    public boolean hasNoTeam(G gamePlayer) {
        return !hasTeam(gamePlayer);
    }

    public boolean oneTeamAlive() {
        return getAliveTeamsCount() == 1;
    }

    public int getAliveTeamsCount() {
        return getAliveTeams().size();
    }

    public int getReachableTeamsCount() {
        return getReachableTeams().size();
    }

    public int getTeamsCount() {
        return teams.size();
    }

    @Override
    public void sendDebugInfoMessage(CommandSender sender) {
        sender.sendMessage("§m-----------------------------");
        sender.sendMessage("Game: " + getFullName());
        sender.sendMessage("Size: type=" + gameSize.getName() + ", min=" + gameSize.getMinPlayer() + ", max=" + gameSize.getMaxPlayer() + ", tn=" + gameSize.getTeamNeeded() + ", tm=" + gameSize.getTeamMaxPlayer());
        sender.sendMessage("Condition: cj=" + canJoin() + ", cs=" + canStart() + ", isf=" + isFull() + ", ota=" + oneTeamAlive());
        sender.sendMessage("State: " + getState());

        //sender.sendMessage("Locations: ");
        //getSettings().getSpawnPoints().stream().map(SpawnPoint::getDebugMessage).forEach(sender::sendMessage);

        sender.sendMessage("Team Alive: " + getAliveTeamsCount());
        sender.sendMessage("Teams: " + getTeamsCount());
        teams.forEach(gameTeam -> sender.sendMessage(gameTeam.getName() + ": " + gameTeam.getMembers().stream().map(BaseGamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", "))));

        sender.sendMessage("Players: " + getSize() + " (" + getAlivePlayersCount() + "|" + getSpectatorsCount() + ")");
        sender.sendMessage("Alive players: " + getAlivePlayers().stream().map(BaseGamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", ")));
        sender.sendMessage("Spectator players: " + getSpectators().stream().map(BaseGamePlayer::getPlayer).map(Player::getName).collect(Collectors.joining(", ")));
        sender.sendMessage("§m-----------------------------");
    }

}