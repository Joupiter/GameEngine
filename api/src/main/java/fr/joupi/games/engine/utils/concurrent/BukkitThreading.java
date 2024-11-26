package fr.joupi.games.engine.utils.concurrent;

import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@UtilityClass
public class BukkitThreading {

    public JavaPlugin plugin;
    public BukkitScheduler scheduler;

    public int scheduleSyncDelayedTask(Runnable runnable, long delay) {
        return scheduler.scheduleSyncDelayedTask(plugin, runnable, delay);
    }

    @Deprecated
    public int scheduleSyncDelayedTask(BukkitRunnable bukkitRunnable, long delay) {
        return scheduler.scheduleSyncDelayedTask(plugin, bukkitRunnable, delay);
    }

    public int scheduleSyncDelayedTask(Runnable runnable) {
        return scheduler.scheduleSyncDelayedTask(plugin, runnable);
    }

    @Deprecated
    public int scheduleSyncDelayedTask(BukkitRunnable bukkitRunnable) {
        return scheduler.scheduleSyncDelayedTask(plugin, bukkitRunnable);
    }

    public int scheduleSyncRepeatingTask(Runnable runnable, long initialDelay, long delay) {
        return scheduler.scheduleSyncRepeatingTask(plugin, runnable, initialDelay, delay);
    }

    @Deprecated
    public int scheduleSyncRepeatingTask(BukkitRunnable bukkitRunnable, long initialDelay, long delay) {
        return scheduler.scheduleSyncRepeatingTask(plugin, bukkitRunnable, initialDelay, delay);
    }

    @Deprecated
    public int scheduleAsyncDelayedTask(Runnable runnable, long delay) {
        return scheduler.scheduleAsyncDelayedTask(plugin, runnable, delay);
    }

    @Deprecated
    public int scheduleAsyncDelayedTask(Runnable runnable) {
        return scheduler.scheduleAsyncDelayedTask(plugin, runnable);
    }

    @Deprecated
    public int scheduleAsyncRepeatingTask(Runnable runnable, long initialDelay, long delay) {
        return scheduler.scheduleAsyncRepeatingTask(plugin, runnable, initialDelay, delay);
    }

    public <T> Future<T> callSyncMethod(Callable<T> callable) {
        return scheduler.callSyncMethod(plugin, callable);
    }

    public void cancelTask(int id) {
        scheduler.cancelTask(id);
    }

    public void cancelTasks() {
        scheduler.cancelTasks(plugin);
    }

    public void cancelAllTasks() {
        scheduler.cancelAllTasks();
    }

    public boolean isCurrentlyRunning(int id) {
        return scheduler.isCurrentlyRunning(id);
    }

    public boolean isQueued(int id) {
        return scheduler.isQueued(id);
    }

    public List<BukkitWorker> getActiveWorkers() {
        return scheduler.getActiveWorkers();
    }

    public List<BukkitTask> getPendingTasks() {
        return scheduler.getPendingTasks();
    }

    public BukkitTask runTask(Runnable runnable) throws IllegalArgumentException {
        return scheduler.runTask(plugin, runnable);
    }

    @Deprecated
    public BukkitTask runTask(BukkitRunnable bukkitRunnable) throws IllegalArgumentException {
        return scheduler.runTask(plugin, bukkitRunnable);
    }

    public BukkitTask runTaskAsynchronously(Runnable runnable) throws IllegalArgumentException {
        return scheduler.runTaskAsynchronously(plugin, runnable);
    }

    @Deprecated
    public BukkitTask runTaskAsynchronously(BukkitRunnable bukkitRunnable) throws IllegalArgumentException {
        return scheduler.runTaskAsynchronously(plugin, bukkitRunnable);
    }

    public BukkitTask runTaskLater(Runnable runnable, long delay) throws IllegalArgumentException {
        return scheduler.runTaskLater(plugin, runnable, delay);
    }

    @Deprecated
    public BukkitTask runTaskLater(BukkitRunnable bukkitRunnable, long delay) throws IllegalArgumentException {
        return scheduler.runTaskLater(plugin, bukkitRunnable, delay);
    }

    public BukkitTask runTaskLaterAsynchronously(Runnable runnable, long delay) throws IllegalArgumentException {
        return scheduler.runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    @Deprecated
    public BukkitTask runTaskLaterAsynchronously(BukkitRunnable bukkitRunnable, long delay) throws IllegalArgumentException {
        return scheduler.runTaskLaterAsynchronously(plugin, bukkitRunnable, delay);
    }

    public BukkitTask runTaskTimer(Runnable runnable, long initialDelay, long delay) throws IllegalArgumentException {
        return scheduler.runTaskTimer(plugin, runnable, initialDelay, delay);
    }

    @Deprecated
    public BukkitTask runTaskTimer(BukkitRunnable bukkitRunnable, long initialDelay, long delay) throws IllegalArgumentException {
        return scheduler.runTaskTimer(plugin, bukkitRunnable, initialDelay, delay);
    }

    public BukkitTask runTaskTimerAsynchronously(Runnable runnable, long initialDelay, long delay) throws IllegalArgumentException {
        return scheduler.runTaskTimerAsynchronously(plugin, runnable, initialDelay, delay);
    }

    @Deprecated
    public BukkitTask runTaskTimerAsynchronously(BukkitRunnable bukkitRunnable, long initialDelay, long delay) throws IllegalArgumentException {
        return scheduler.runTaskTimerAsynchronously(plugin, bukkitRunnable, initialDelay, delay);
    }

    public void setPlugin(JavaPlugin plugin) {
        BukkitThreading.plugin = plugin;
        BukkitThreading.scheduler = plugin.getServer().getScheduler();
    }

}