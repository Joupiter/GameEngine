package fr.joupi.games.engine.point;

import fr.joupi.games.engine.map.Map;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.utils.CollectionUtils;
import fr.joupi.games.engine.utils.location.LocationCoordinates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class MultiplePoint implements SpawnPoint {

    private final String name;
    private final Set<SinglePoint> points;

    public MultiplePoint(String name) {
        this(name, new HashSet<>());
    }

    public MultiplePoint(String name, SinglePoint... points) {
        this(name, new HashSet<>(Arrays.asList(points)));
    }

    public MultiplePoint addPoint(SinglePoint point) {
        points.add(point);
        return this;
    }

    public MultiplePoint addPoint(Location location) {
        return addPoint(new SinglePoint(location));
    }

    public MultiplePoint addPoints(SinglePoint... singlePoints) {
        points.addAll(Arrays.asList(singlePoints));
        return this;
    }

    public MultiplePoint addPoints(Location... locations) {
        Arrays.stream(locations).map(SinglePoint::new).forEach(this::addPoint);
        return this;
    }

    public MultiplePoint addPoints(Map map, LocationCoordinates... coordinates) {
        Arrays.stream(coordinates)
                .map(coordinate ->  coordinate.toSinglePoint(map))
                .forEach(this::addPoint);
        return this;
    }

    public Optional<SinglePoint> getPoint(String name) {
        return points.stream().filter(point -> point.getName().equals(name)).findFirst();
    }

    public Optional<SinglePoint> getRandomPoint() {
        return CollectionUtils.random(points);
    }

    public void teleport(String pointName, Player player) {
        getPoint(pointName).map(SinglePoint::getLocation).ifPresent(player::teleport);
    }

    public void teleport(String pointName, BaseGamePlayer gamePlayer) {
        getPoint(pointName).map(SinglePoint::getLocation).ifPresent(gamePlayer::teleport);
    }

    public void randomTeleport(Player player) {
        getRandomPoint().map(SinglePoint::getLocation).ifPresent(player::teleport);
    }

    public void randomTeleport(BaseGamePlayer gamePlayer) {
        getRandomPoint().map(SinglePoint::getLocation).ifPresent(gamePlayer::teleport);
    }

    public int getSize() {
        return points.size();
    }

    public boolean contains(SinglePoint point) {
        return points.contains(point);
    }

    public boolean contains(Location location) {
        return points.stream().map(SinglePoint::getLocation).anyMatch(location::equals);
    }

    public boolean contains(String pointName) {
        return getPoint(pointName).isPresent();
    }

    @Override
    public String getDebugMessage() {
        return getName() + ": " + points.stream().map(SinglePoint::toString).collect(Collectors.joining(", "));
    }

}