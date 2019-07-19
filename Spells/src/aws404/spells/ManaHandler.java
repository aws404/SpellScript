package aws404.spells;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;



public class ManaHandler {
	Main plugin;
	FileManager fileManager;
	
	public HashMap<Player,Integer> manaLevels;
	public HashMap<Player,BossBar> manaBars;
	public Integer manaRegenAmount;
	public Integer manaRegenTime;
	public Integer maxMana;
	
	public ManaHandler(Main plugin) {
		this.plugin = plugin;
		this.fileManager = plugin.fileManager;
		
		System.out.println("ENABLING MANA");
		
		manaLevels = new HashMap<Player,Integer>();
		manaBars = new HashMap<Player,BossBar>();
        manaRegenAmount = fileManager.getConfig().getInt("mana-regen-per");
        manaRegenTime = fileManager.getConfig().getInt("mana-regen-time");
        maxMana = fileManager.getConfig().getInt("max-mana");
        
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
    	//Mana Regener
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {@Override public void run() {doManaRegen();}}, 0L, manaRegenTime*20L);
        
        //Bars Updater
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {@Override public void run() {updateManaBars();}}, 0L, 10L);
    
	}
	
	
	
    private void doManaRegen() {
   	 Collection<? extends Player> playerList = Bukkit.getOnlinePlayers();
        
        for (Player p : playerList) {
        	Integer newMana = manaLevels.get(p)+manaRegenAmount;
        	if (newMana>maxMana) newMana = maxMana;
        	
        	manaLevels.replace(p, newMana);
        }
   }
    
    
    public boolean takeMana(Integer manaCost, Player p) {
    	Integer oldMana = manaLevels.get(p);
    	Integer newMana = oldMana - manaCost;
    	
    	if (newMana < 0) return false;
    	
	    manaLevels.replace(p, newMana);
	    return true;
    }
    
    private void updateManaBars() {
    	Collection<? extends Player> playerList = Bukkit.getOnlinePlayers();
        
        for (Player p : playerList) {
        	Integer mana = manaLevels.get(p);
        	
        	Double manaBarValue = (double) mana / (double) maxMana;
        	if (manaBarValue <= 0) manaBarValue = (double) 0;
        	BossBar ManaBar;
        	if (manaBars.containsKey(p)) {
        		ManaBar = manaBars.get(p);
        	} else {
        		ManaBar = Bukkit.createBossBar("Mana", BarColor.BLUE, BarStyle.SEGMENTED_20);
        		manaBars.put(p, ManaBar);
        		ManaBar.addPlayer(p);
        	}
        	ManaBar.setProgress(manaBarValue);
        }
    }
    
    public void giveMana(Integer amount, Player p) {
    	Integer oldMana = manaLevels.get(p);
    	Integer newMana = oldMana + amount;;
    	
    	if (newMana > maxMana) newMana = maxMana;
    	if (newMana < 0) newMana = 0;
    	
	    manaLevels.replace(p, newMana);
    }
    
    public void setMana(Integer amount, Player p) {
    	
    	if (amount > maxMana) amount = maxMana;
    	if (amount < 0) amount = 0;
  
	    manaLevels.replace(p, amount);
    }
    
    public Integer getMana(Player player) {
    	if (manaLevels.containsKey(player)) {
    		return manaLevels.get(player);
    	} else {
    		return null;
    	}
    }
    
    
    
}
