package net.satisfy.wildernature.core.util;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.Comparator;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

/** Tasks advance in game ticks, so pausing the server also pauses reload sounds. */
public final class ServerTasks {
    private record Task(int tick, Runnable action) {}
    private static final Map<MinecraftServer, PriorityQueue<Task>> TASKS = new HashMap<>();
    static {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            var queue = TASKS.get(server);
            while (queue != null && !queue.isEmpty() && queue.peek().tick() <= server.getTickCount()) {
                queue.remove().action().run();
            }
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(TASKS::remove);
    }
    public static void after(MinecraftServer server, int ticks, Runnable task) {
        TASKS.computeIfAbsent(server, ignored -> new PriorityQueue<>(Comparator.comparingInt(Task::tick)))
            .add(new Task(server.getTickCount() + ticks, task));
    }
    private ServerTasks() {}
}
