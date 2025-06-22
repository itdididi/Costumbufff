package me.khoi.custombuff;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityResurrectEvent;

public class CustomBuff extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("EliasParkBuff Enabled!");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    private void applyBuff(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1, true, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, true, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false, false));
    }

    private boolean isTargetPlayer(Player player) {
        String name = player.getName();
        return name.equalsIgnoreCase("Elias_YTB") || name.equalsIgnoreCase(".parkjonghuy");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (isTargetPlayer(player)) {
            applyBuff(player);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (player.isOnline() && isTargetPlayer(player)) {
                applyBuff(player);
            }
        }, 20L);
    }

    @EventHandler
    public void onTotemUse(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (isTargetPlayer(player)) {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                applyBuff(player);
            }, 5L);
        }
    }
}
