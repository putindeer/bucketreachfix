package io.github.putindeer.bucketreachfix.integrations;

import io.github.putindeer.bucketreachfix.Main;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import ga.strikepractice.event.FightEndEvent;

public class StrikePractice implements Listener {
    private final Main plugin;

    public StrikePractice(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFightEnd(FightEndEvent event) {
        List<Player> players = event.getMatch().getPlayers();
        players.forEach(player -> plugin.removeFluidBoost(player));
    }
}
