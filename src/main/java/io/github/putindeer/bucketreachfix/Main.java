package io.github.putindeer.bucketreachfix;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class Main extends JavaPlugin implements Listener {
    private final Set<Player> bucketHoldingPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        for (Player player : bucketHoldingPlayers) {
            AttributeInstance attribute = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
            if (attribute != null) {
                attribute.setBaseValue(4.5);
            }
        }
        bucketHoldingPlayers.clear();
    }

    @EventHandler
    public void onBucketHeld(PlayerItemHeldEvent event) {
        Player p = event.getPlayer();
        ItemStack oldItem = p.getInventory().getItem(event.getPreviousSlot());
        ItemStack newItem = p.getInventory().getItem(event.getNewSlot());
        boolean wasBucket = oldItem != null && isBucket(oldItem.getType());
        boolean isNowBucket = newItem != null && isBucket(newItem.getType());

        if (wasBucket && isNowBucket) return;
        if (wasBucket) removeFluidBoost(p);
        if (isNowBucket) applyFluidBoost(p);
    }

    @EventHandler
    public void onBucketClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player p)) return;
        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        if (item.getType() == Material.AIR && isBucket(p.getInventory().getItemInMainHand().getType())) {
            applyFluidBoost(p);
        }

        if (isBucket(item.getType())) {
            removeFluidBoost(p);
        }
    }

    private boolean isBucket(Material material) {
        return switch (material) {
            case WATER_BUCKET, COD_BUCKET, SALMON_BUCKET, TROPICAL_FISH_BUCKET, PUFFERFISH_BUCKET, AXOLOTL_BUCKET,
                 TADPOLE_BUCKET, LAVA_BUCKET -> true;
            default -> false;
        };
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        removeFluidBoost(player);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        removeFluidBoost(player);
    }

    private void applyFluidBoost(Player player) {
        if (!bucketHoldingPlayers.contains(player)) {
            AttributeInstance attribute = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
            if (attribute != null) {
                attribute.setBaseValue(5.0);
                bucketHoldingPlayers.add(player);
            }
        }
    }

    private void removeFluidBoost(Player player) {
        if (bucketHoldingPlayers.contains(player)) {
            AttributeInstance attribute = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
            if (attribute != null) {
                attribute.setBaseValue(4.5);
            }
            bucketHoldingPlayers.remove(player);
        }
    }
}
