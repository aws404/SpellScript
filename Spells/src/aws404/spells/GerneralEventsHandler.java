package aws404.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import net.md_5.bungee.api.ChatColor;



public class GerneralEventsHandler implements Listener{
	private Main plugin;
	private FileManager fileManager;
	private ManaHandler manaHandler;
	
    public GerneralEventsHandler(Main plugin) {
        this.plugin = plugin;
        this.fileManager = plugin.fileManager;
        this.manaHandler = plugin.manaHandler;
    }
    
    
    //Used for the setFrozen function
    @EventHandler
    public void moveEvent(PlayerMoveEvent e) {
    	if (plugin.frozenPlayers.contains(e.getPlayer())) {
	    	if (hasChangedBlockCoordinates(e.getFrom(), e.getTo())) {
		        Location from = e.getFrom();                  
		        double x = Math.floor(from.getX());
		        double z = Math.floor(from.getZ());
		
		        x += .5;
		        z += .5;
		        e.getPlayer().teleport(new Location(from.getWorld(), x, from.getY(), z, from.getYaw(), from.getPitch()));
	        }
    	}
    }
    
    public static boolean hasChangedBlockCoordinates(final Location fromLoc, final Location toLoc) {
        return !(fromLoc.getWorld().equals(toLoc.getWorld())
                && fromLoc.getBlockX() == toLoc.getBlockX()
                && fromLoc.getBlockY() == toLoc.getBlockY()
                && fromLoc.getBlockZ() == toLoc.getBlockZ());
    }

    
    
    
    
    
    //Mana setup on join	
	@EventHandler
	public void joinGame(PlayerJoinEvent e) {
		if (plugin.usingMana) {
			Player p = e.getPlayer();
			
	    	if(manaHandler.manaLevels.containsKey(p)) {
	    		manaHandler.manaLevels.replace(p, 0);
	    	} else {
	    		manaHandler.manaLevels.put(p, 0);
	    	}
		}
	}

	
	
	
	
	//Used for wand management
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
	    		NamespacedKey key = new NamespacedKey(plugin, "currentSpell");
	    		ItemMeta itemMeta = item.getItemMeta();
	    		if(itemMeta.getPersistentDataContainer().has(key , PersistentDataType.STRING)) {
					if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && p.isSneaking() && e.getHand() == EquipmentSlot.HAND) {
						//Open GUI
						p.openInventory(plugin.spellGUI);
						return;
					}
					if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
						//Cast Spell
			    		String foundValue = itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
			    		Integer manaCost = fileManager.getSpells().getInt(foundValue + ".manaCost");
			    		String displayName = fileManager.getSpells().getString(foundValue + ".displayName");
			    		    
			    		if (foundValue != "none" && foundValue != null) {
			    			LivingEntity target = null;
			    			
			    			if (fileManager.getSpells().getString(foundValue + ".target").equalsIgnoreCase("self")) 
			    				target = p;
			    			
			    			
			    			if (fileManager.getSpells().getString(foundValue + ".target").equalsIgnoreCase("raytrace")) 
			    				target = plugin.API.raytraceAs(p, 20);
			    			
			    			SpellScriptSpell spell = new SpellScriptSpell(foundValue, p, target, true);
			    			
			    			
	    		    		if (spell.castWithMana(manaCost)) 
	    		    			plugin.actionBarClass.sendActionbar(p, ChatColor.WHITE + displayName + " Spell Cast " + ChatColor.GRAY + "[-" + manaCost + " Mana] ");
	    		    		
	    		    		
	    		    		if (target.getType().equals(EntityType.ARMOR_STAND) && target.getName().contentEquals("Dummy" ))
	    		    			target.remove();
		    		    				    		    	
			    		   
			    		} else {
			    		  	p.sendMessage("No Spell Selected");
			    		}
		    		} else {
		    			p.sendMessage("Wand Data Error");
		    		}
					return;
	    		}
			}
		}
	}
		

}

	

