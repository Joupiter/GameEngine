package fr.joupi.games.engine.team;

import fr.joupi.games.engine.player.GamePlayer;
import fr.joupi.games.engine.utils.collection.SizedArrayList;
import fr.joupi.games.engine.utils.collection.SizedList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class GameTeam<G extends GamePlayer<?>> {

    protected final String name;
    protected final GameTeamColor color;

    protected int maxSize;

    protected final SizedList<G> members;

    public GameTeam(int maxSize, GameTeamColor color) {
        this.name = color.getName();
        this.color = color;
        this.maxSize = maxSize;
        this.members = new SizedArrayList<>(maxSize);
    }

    public GameTeam(GameTeamColor color) {
        this(5, color);
    }

    public void addMember(G gamePlayer) {
        members.add(gamePlayer);
        gamePlayer.setTeam(this);
    }

    public void removeMember(G gamePlayer) {
        members.removeIf(gamePlayer::equals);
        gamePlayer.setTeam(null);
    }

    public void broadcast(String message) {
        members.forEach(gamePlayer -> gamePlayer.sendMessage(message));
    }

    public void broadcast(Predicate<G> filter, String message) {
        members.stream()
                .filter(filter)
                .forEach(gamePlayer -> gamePlayer.sendMessage(message));
    }

    public void broadcast(String... messages) {
        members.forEach(gamePlayer -> gamePlayer.sendMessage(messages));
    }

    public void broadcast(Predicate<G> filter, String... messages) {
        members.stream()
                .filter(filter)
                .forEach(gamePlayer -> gamePlayer.sendMessage(messages));
    }

    public List<G> getAlivePlayers() {
        return members.stream().filter(G::isAlive).toList();
    }

    public List<G> getSpectators() {
        return members.stream().filter(G::isSpectator).toList();
    }

    public List<String> getMembersNames() {
        return members.stream().map(G::getName).toList();
    }

    public String getFormattedMembersNames() {
        return members.stream().map(G::getName).collect(Collectors.joining(", "));
    }

    public String getFormattedAlivePlayersNames() {
        return members.stream().filter(G::isAlive).map(G::getName).collect(Collectors.joining(", "));
    }

    public String getFormattedSpectatorsNames() {
        return members.stream().filter(G::isSpectator).map(G::getName).collect(Collectors.joining(", "));
    }

    public boolean isMember(G gamePlayer) {
        return members.contains(gamePlayer);
    }

    public boolean isMember(UUID uuid) {
        return members.stream().map(G::getUuid).anyMatch(uuid::equals);
    }

    public boolean isNotMember(G gamePlayer) {
        return !isMember(gamePlayer);
    }

    public boolean isNotMember(UUID uuid) {
        return !isMember(uuid);
    }

    public boolean canJoin() {
        return members.size() < maxSize;
    }

    public boolean hasPlayersAlive() {
        return !getAlivePlayers().isEmpty();
    }

    public boolean isNoPlayersAlive() {
        return !hasPlayersAlive();
    }

    public int getSize() {
        return members.size();
    }

    public String getColoredName() {
        return color.getChatColor() + name;
    }

}