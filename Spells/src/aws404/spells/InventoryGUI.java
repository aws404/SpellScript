package aws404.spells;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@SuppressWarnings("unused")
public class InventoryGUI implements Listener {
    private static Main plugin;
    
    
	public InventoryGUI(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
        InventoryGUI.plugin = plugin;
	}
	JSONParser parser = new JSONParser(); 

	
	
	public static Inventory createGUI() {
		Set<String> spellList = FileManager.getSpells().getKeys(false);
		
		Double guiSlots = Math.ceil((double)spellList.size()/(double)9)*(double)9;

		Inventory inv = Bukkit.createInventory(null, guiSlots.intValue(), "Spell Selection");
		
		for (String spell : spellList) {
			String displayName = FileManager.getSpells().getString(spell + ".displayName");
			Material icon = Material.getMaterial(FileManager.getSpells().getString(spell + ".icon").toUpperCase());
			Integer manaCost = FileManager.getSpells().getInt(spell + ".manaCost");
			String target = FileManager.getSpells().getString(spell + ".target");
			
			if (icon == null) icon = Material.STONE;
			
			ItemStack item = new ItemStack(icon);
			
			ItemMeta meta = item.getItemMeta();
			
			meta.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + displayName);
			
			
			List<String> lore = new ArrayList<String>();
			lore.add("");
			if (plugin.usingMana) lore.add(ChatColor.WHITE + "Mana Cost: " + ChatColor.GRAY +  manaCost);
			lore.add(ChatColor.WHITE + "Target Type: " + ChatColor.GRAY +  FileManager.getSpells().getString(spell + ".target"));
			if (plugin.debug) lore.add("Spell File: " + spell);
			
			meta.setLore(lore);
			
			NamespacedKey spellName = new NamespacedKey(plugin, "spellName");
    		meta.getPersistentDataContainer().set(spellName, PersistentDataType.STRING, spell);
    		
    		item.setItemMeta(meta);
			
			inv.addItem(item);
		}
		return inv;
	}
	

		//Click Actions
		@EventHandler
		public void click(InventoryClickEvent e) {
			org.bukkit.inventory.Inventory inv = e.getInventory();
						
			//Main
			if (e.getView().getTitle().contains("Spell Selection")) {
				Player p = (Player) e.getWhoClicked();
				ItemStack clicked = e.getCurrentItem();
				
				if (clicked == null || clicked.getType() == Material.AIR || clicked.getAmount() == 0 || !clicked.hasItemMeta() || e.getClickedInventory().getType() != InventoryType.CHEST) {
					e.setCancelled(true);
				} else {
					NamespacedKey spellName = new NamespacedKey(plugin, "spellName");
					String spell = clicked.getItemMeta().getPersistentDataContainer().get(spellName, PersistentDataType.STRING);
					String spellDisplay = clicked.getItemMeta().getDisplayName();
					ItemStack wand = plugin.getWandItem(spell);
		    		p.getInventory().setItemInMainHand(wand);		    		
		    		
					p.closeInventory();
					e.setCancelled(true);
				}
				
				
				
			}
		}
}
