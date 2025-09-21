package io.github.putindeer.bucketreachfix.integrations;

import io.github.putindeer.bucketreachfix.Main;
import org.bukkit.event.Listener;

public class StrikePractice implements Listener {
    private final Main plugin;

    public StrikePractice(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFightEnd(FightEndEvent event) {
        Player winner = event.getWinner();
        Player loser = event.getLoser();

        if (winner != null) {
            plugin.removeFluidBoost(winner);
        }
        if (loser != null) {
            plugin.removeFluidBoost(loser);
        }
    }
}
