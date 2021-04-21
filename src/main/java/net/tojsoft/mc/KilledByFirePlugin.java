package net.tojsoft.mc;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class KilledByFirePlugin extends JavaPlugin  {
    
    private List<Material> torches = new ArrayList<>();
    private List<EntityType> zombies = new ArrayList<>();

    private void setDefaults() {
        torches.clear();
        zombies.clear();

        torches.add(Material.TORCH);
        zombies.add(EntityType.ZOMBIE);
        zombies.add(EntityType.ZOMBIE_VILLAGER);
        zombies.add(EntityType.ZOMBIE_HORSE);
        zombies.add(EntityType.ZOMBIFIED_PIGLIN);
    }



    @Override
    public void onEnable() {

        PluginManager pluginManager = getServer().getPluginManager();

        setDefaults();

        pluginManager.registerEvents(new Listener() {

            @EventHandler
            void someoneDies(EntityDeathEvent event) {
                LivingEntity entity = event.getEntity();
                EntityType type = event.getEntityType();
                if (zombies.contains(type)) {
                    // death of zombie
                    if (entity.getFireTicks() <= 0) {
                        event.setCancelled(true);
                    }
                }
            }

            @EventHandler
            void someoneGetDamaged(EntityDamageByEntityEvent event) {
                // set on fire?
                if (event.getDamager() instanceof HumanEntity) {
                    ItemStack itemInMainHand = ((HumanEntity) event.getDamager()).getInventory().getItemInMainHand();
                    if (torches.contains(itemInMainHand.getType())) {
                        event.getEntity().setFireTicks(60);
                    }
                }
            }

        }, this);



    }
}
