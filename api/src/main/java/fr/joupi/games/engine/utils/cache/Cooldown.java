package fr.joupi.games.engine.utils.cache;

import java.time.temporal.TemporalUnit;
import java.util.UUID;

public interface Cooldown extends DynamicCache<UUID, Long> {

    void put(UUID uuid);

    long getTimeLeft(UUID uuid);

    long getTimeLeftInSeconds(UUID uuid);

    long getTimeLeft(UUID uuid, TemporalUnit unit);

}