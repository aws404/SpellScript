package aws404.spells;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;



public class GerneralEventsHandler implements Listener{
	private Main plugin;
	private FileManager fileManager;
	
    public GerneralEventsHandler(Main plugin) {
        this.plugin = plugin;
        this.fileManager = plugin.fileManager;
    }

	
	@EventHandler
	public void joinGame(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
    	if(plugin.manaLevels.containsKey(p)) {
    		plugin.manaLevels.replace(p, 0);
    	} else {
    		plugin.manaLevels.put(p, 0);
    	}
	}

	@EventHandler
	public void rightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		if (item == null || item.getAmount() == 0 || item.getType() == Material.AIR) {
			return;
		}
		if (item.getType() == Material.STICK && item.hasItemMeta()) {
			if (item.getItemMeta().getDisplayName().contains("Magic Wand")) {
				Action action = e.getAction();
				if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && p.isSneaking()) {
					//Open GUI
					p.openInventory(plugin.spellGUI);
					return;
				}
				if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
					//Cast Spell
		    		NamespacedKey key = new NamespacedKey(plugin, "currentSpell");
		    		ItemMeta itemMeta = item.getItemMeta();

		    		if(itemMeta.getPersistentDataContainer().has(key , PersistentDataType.STRING)) {
		    		    String foundValue = itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
		    		    Integer manaCost = fileManager.getSpells().getInt(foundValue + ".manaCost");
		    		    String displayName = fileManager.getSpells().getString(foundValue + ".displayName");
		    		    if (!plugin.takeMana(manaCost, p)) {
		    		    	plugin.actionBarClass.sendActionbar(p, ChatColor.RED + "Not Enough Mana!");
		    		    	return;
		    		    }
		    		    
	    		    	plugin.actionBarClass.sendActionbar(p, ChatColor.WHITE + displayName + " Spell Cast " + ChatColor.GRAY + "[-" + manaCost + " Mana] ");
	    		    	
	    		    	
		    		    if (plugin.debug) p.sendMessage("Spell Cast: " + foundValue + ", -" + manaCost + " mana");
		    		    
		    		    if (foundValue != "none" && foundValue != null) {
		    		    	if (fileManager.getSpells().getString(foundValue + ".target").equalsIgnoreCase("self")) {
		    		    		
		    		    		plugin.castSpell(p, p, foundValue);
		    		    	} else if (fileManager.getSpells().getString(foundValue + ".target").equalsIgnoreCase("raytrace")) {
		    		    		RayTraceResult rt = p.rayTraceBlocks(20, FluidCollisionMode.NEVER);
		    		    		Location loc1;
		    		    		if (rt == null) {
		    		    			loc1 = p.getLocation();
					    			Vector dir = loc1.getDirection();
					    			  
					    			dir.normalize();
					    			dir.multiply(20); 
					    			loc1.add(dir);
					    			
		    		    		} else {
		    		    			loc1 = rt.getHitBlock().getLocation();
		    		    		}
		    		    			Entity dummy = loc1.getWorld().spawnEntity(loc1, EntityType.ARMOR_STAND);
		    		    			dummy.setInvulnerable(true);
		    		    			((ArmorStand) dummy).setSmall(true);
		    		    			
		    		    			if (plugin.debug) {
		    		    				dummy.setCustomName("Dummy");
		    		    				dummy.setCustomNameVisible(true);
		    		    				dummy.setGlowing(true);
		    		    			} else {
		    		    				((ArmorStand) dummy).setVisible(false);
		    		    			}
		    		    			List<LivingEntity> nearbyList = new ArrayList<LivingEntity>();
		    		    			List<Entity> nearby3 = dummy.getNearbyEntities(3, 3, 3);
		    		    			if (nearby3.contains(p)) nearby3.remove(p);
		    		    			for (Entity entity : nearby3) {	
		    		    				if (entity.getType().isAlive()) nearbyList.add((LivingEntity) entity);
		    		    			}
		    		    			
		    		    			
		    		    			if (nearbyList.isEmpty()) {
		    		    				//Check for radius of 5
		    		    				List<Entity> nearby5 = dummy.getNearbyEntities(5, 5, 5);
		    		    				if (nearby5.contains(p)) nearby5.remove(p);
		    		    				for (Entity entity : nearby5) {	
			    		    				if (entity.getType().isAlive()) nearbyList.add((LivingEntity) entity);
			    		    			}

		    		    				if (nearbyList.isEmpty()) {
		    	   		    				//Check for radius of 7
			    		    				List<Entity> nearby7 = dummy.getNearbyEntities(7, 7, 7);
			    		    				if (nearby7.contains(p)) nearby7.remove(p);
			    		    				for (Entity entity : nearby7) {	
				    		    				if (entity.getType().isAlive()) nearbyList.add((LivingEntity) entity);
				    		    			}
				    		    			

		    		    					if (nearbyList.isEmpty()) {
		    		    						//No Living Entities nearby : use dummy
		    		    						if (plugin.debug) p.sendMessage("using dummy");
			    		    					plugin.castSpell((LivingEntity) dummy, p, foundValue);
		    		    					} else {
		    		    						if (plugin.debug) p.sendMessage("found nearby dummy");
				    		    				plugin.castSpell((LivingEntity) nearby7.get(0), p, foundValue);
		    		    					}
		    		    				} else {
		    		    					if (plugin.debug) p.sendMessage("found nearby dummy");
			    		    				plugin.castSpell((LivingEntity) nearby5.get(0), p, foundValue);
		    		    				}

		    		    			} else {
		    		    				if (plugin.debug) p.sendMessage("found nearby dummy");
		    		    				plugin.castSpell((LivingEntity) nearby3.get(0), p, foundValue);
		    		    			}
		    		    			dummy.remove();
		    		    		}
		    		    		
		    		    	}
		    		    } else {
		    		    	p.sendMessage("No Spell Selected");
		    		    }
		    		} else {
		    			p.sendMessage("error");
		    		}
					
					
					
					
					return;
				}
			}
		}
	
	

}

	

