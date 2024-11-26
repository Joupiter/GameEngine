package fr.joupi.games.engine.ability;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.utils.CacheUtils;
import fr.joupi.games.engine.utils.cache.Cooldown;
import fr.joupi.games.engine.utils.cache.CooldownCache;
import fr.joupi.games.engine.utils.concurrent.MultiThreading;
import fr.joupi.games.engine.utils.func.TriFunction;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public abstract class GameAbilityItemCooldown<M extends MiniGame, G extends BaseGamePlayer, E extends PlayerEvent> extends GameAbilityItem<M, G, E> {

    protected final int duration;
    protected final Cooldown cooldown;

    protected boolean showActionBar;
    protected TriFunction<Cooldown, UUID, G, String> actionBar;

    private ScheduledFuture<?> future;

    public GameAbilityItemCooldown(String name, Class<M> gameClass, Class<E> eventClass, int duration, TimeUnit timeUnit, boolean showActionBar, ItemStack itemStack) {
        super(name, gameClass, eventClass, itemStack);
        this.duration = duration;
        this.showActionBar = showActionBar;
        this.cooldown = CacheUtils.getCooldown(name,
                new CooldownCache(duration, timeUnit, (key, value, cause) -> future.cancel(false)));
    }

    @SuppressWarnings("unchecked")
    public void handleAbility(M game, UUID user, E event) {
        var gamePlayer = (G) game.getPlayers().get(user);

        if (gamePlayer == null || cooldown.contains(user)) return;

        if (future != null && !future.isDone())
            future.cancel(false);

        cooldown.put(user);

        future = MultiThreading.schedule(() -> {
            if (!cooldown.contains(user)) {
                future.cancel(false);
                return;
            }

            if (showActionBar)
                gamePlayer.sendActionBar(actionBar == null
                        ? "Cooldown: " + cooldown.getTimeLeftInSeconds(user) + " secondes"
                        : actionBar.apply(cooldown, user, gamePlayer));
        }, 0, 1, TimeUnit.SECONDS);

        onUse(game, gamePlayer, event);
        //CacheUtils.sendDebug();
    }

}