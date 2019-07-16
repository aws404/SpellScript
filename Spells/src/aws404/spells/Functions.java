package aws404.spells;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class Functions {
    private Main plugin;
    
    
	public Functions(Main plugin) {
        this.plugin = plugin;
	}
	
	public boolean teleportRelative(String[] args, LivingEntity target) {
    	Location newLocation = target.getLocation();
    	double x = (double) plugin.convertType(args[0], DataType.DOUBLE);
    	double y = (double) plugin.convertType(args[1], DataType.DOUBLE);
    	double z = (double) plugin.convertType(args[2], DataType.DOUBLE);
    	newLocation.add(x, y, z);
    		  
    	target.teleport(newLocation);
    	return true;
	}
	
	//Adjust Health
	public boolean adjustHealth(String[] args, LivingEntity target) {
		  Double amnt = (double) plugin.convertType(args[0], DataType.DOUBLE);
		  if (amnt < 0) {
			  target.damage(amnt*-1);
			  return true;
		  } else {
			  double newHealth = target.getHealth() + amnt;
			  if (newHealth > target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()) {
				  target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
				  return true;
			  } else {
				  target.setHealth(newHealth);
				  return true;
			  }
		  }
	}
	
	//Set Health
	public boolean setHealth(String[] args, LivingEntity target) {
		target.setHealth((double) plugin.convertType(args[0], DataType.DOUBLE));
		return true;
	}
	
	//Set Mana
    public boolean setMana(String[] args, LivingEntity target) {
    	if (target.getType().equals(EntityType.PLAYER)) {
    		Player p = (Player) target;
    		Integer mana = (Integer) plugin.convertType(args[0], DataType.INTEGER);
    		if (mana <= 0) mana = 0;
    		if (mana >= 20) mana = 20;
    		if(plugin.manaLevels.containsKey(p)) {
        		plugin.manaLevels.replace(p, mana);
        		return true;
        	} else {
        		plugin.manaLevels.put(p, mana);
        		return true;
        	}
    	} else {
    		return false;
    	}
    }
    
  //Set Mana
    public boolean adjustMana(String[] args, LivingEntity target) {
    	
       	if (target.getType().equals(EntityType.PLAYER)) {
    		Player p = (Player) target;
    		Integer addMana = (Integer) plugin.convertType(args[0], DataType.INTEGER);
    		Integer mana = plugin.manaLevels.get(p)+addMana;
    		if (mana <= 0) mana = 0;
    		if (mana >= 20) mana = 20;
    		if(plugin.manaLevels.containsKey(p)) {
        		plugin.manaLevels.replace(p, mana);
        		return true;
        	} else {
        		plugin.manaLevels.put(p, mana);
        		return true;
        	}
    	} else {
    		return false;
    	}
       	
    }
	
	//Adjust Hunger
	public boolean adjustHunger(String[] args, LivingEntity target) {
    	if (target.getType() == EntityType.PLAYER) {
    		Player player = (Player) target;
    		Integer newHealth = player.getFoodLevel() + (int) plugin.convertType(args[0], DataType.INTEGER);
    		
    		  if (newHealth < 0) {
    			  player.setFoodLevel(0);
    			  return true;
    		  } else if (newHealth > 20) {
    			  player.setFoodLevel(20);
    			  return true;
    		  } else {
    			  player.setFoodLevel(newHealth);
    			  return true;
    		  }
    	}
    	return false;
	}
	
	//Set Hunger
	public boolean setHunger(String[] args, LivingEntity target) {
		if (target.getType() == EntityType.PLAYER) {
			((Player) target).setFoodLevel((int) plugin.convertType(args[0], DataType.INTEGER));
			return true;
		}
		return false;
	}
	
	//Ray Trace Teleport
	public boolean rayTraceTeleport(String[] args, LivingEntity target) {
		 FluidCollisionMode fluidMode = FluidCollisionMode.ALWAYS;
		  RayTraceResult r = target.rayTraceBlocks((Double) plugin.convertType(args[0], DataType.DOUBLE), fluidMode);
		  		    		  
		  if(r == null) {
			  Location loc = target.getLocation();
			  Vector dir = loc.getDirection();
			  
			  dir.normalize();
			  dir.multiply((Double) plugin.convertType(args[0], DataType.DOUBLE)); 
			  loc.add(dir);
			  target.teleport(loc);
			  return true;
		  } else {
			  double x = r.getHitBlock().getX()+r.getHitBlockFace().getModX()+0.5;
			  double y = r.getHitBlock().getY()+r.getHitBlockFace().getModY();
			  double z = r.getHitBlock().getZ()+r.getHitBlockFace().getModZ()+0.5;
			  if (target.getWorld().getBlockAt(r.getHitBlock().getX(), r.getHitBlock().getY()+1, r.getHitBlock().getZ()).isPassable() && target.getWorld().getBlockAt(r.getHitBlock().getX(), r.getHitBlock().getY()+2, r.getHitBlock().getZ()).isPassable()) {
				  //Check blocks if avavliable
				  //hit block (free)
				  //free
				  Location newLoc = new Location(target.getWorld(), x, y, z, target.getLocation().getYaw(), target.getLocation().getPitch());
				  target.teleport(newLoc);
				  return true;
			  } else if (target.getWorld().getBlockAt(r.getHitBlock().getX(), r.getHitBlock().getY()+2, r.getHitBlock().getZ()).isPassable() && target.getWorld().getBlockAt(r.getHitBlock().getX(), r.getHitBlock().getY()+3, r.getHitBlock().getZ()).isPassable()) {
				  //Check blocks if avavliable
				  //free
				  //hit block (free)
				  //obstructed
				  Location newLoc = new Location(target.getWorld(), x, y+1, z, target.getLocation().getYaw(), target.getLocation().getPitch());
				  target.teleport(newLoc);
				  return true;
			  } else {
				  if (target.getType().equals(EntityType.PLAYER)) plugin.actionbar.sendActionbar((Player) target, ChatColor.RED + "Unable to Teleport, Destination is Obstructued");
				  return false;
			  }
		  }
	}
	
	
	
	
	//Send Message
	public boolean sendMessage(String[] args, LivingEntity target) {
		  target.sendMessage((String) plugin.convertType(args[0], DataType.STRING));
		  return true;
	}
	
	//Particle
	public boolean particle(String[] args, LivingEntity target) {
		double x = target.getLocation().getX() + (Double) plugin.convertType(args[0], DataType.DOUBLE);
    	double y = target.getLocation().getY() + (Double) plugin.convertType(args[1], DataType.DOUBLE);
    	double z = target.getLocation().getZ() + (Double) plugin.convertType(args[2], DataType.DOUBLE);
    	double xoff = (Double) plugin.convertType(args[3], DataType.DOUBLE);
    	double yoff = (Double) plugin.convertType(args[4], DataType.DOUBLE);
    	double zoff = (Double) plugin.convertType(args[5], DataType.DOUBLE);
    	Particle particle = Particle.valueOf(((String) plugin.convertType(args[6], DataType.STRING)).toUpperCase());
    	double speed = (double) plugin.convertType(args[7], DataType.DOUBLE);
    	int count = (int) plugin.convertType(args[8], DataType.INTEGER);    	
    	
    	target.getWorld().spawnParticle(particle, x, y, z, count, xoff, yoff, zoff, speed, null, true);
    	return true;
	}
	
	//Playsound - Singular
	public boolean playSoundPlayer(String[] args, LivingEntity target) {
		if (target.getType() == EntityType.PLAYER) {
			
			double x = target.getLocation().getX() + (Double) plugin.convertType(args[0], DataType.DOUBLE);
			double y = target.getLocation().getY() + (Double) plugin.convertType(args[1], DataType.DOUBLE);
			double z = target.getLocation().getZ() + (Double) plugin.convertType(args[2], DataType.DOUBLE);
			Location location = new Location(target.getWorld(), x, y, z);
			
			Sound sound = Sound.valueOf(((String) plugin.convertType(args[3], DataType.STRING)).toUpperCase());
			if (sound == null) {
				plugin.sendError((String) plugin.convertType(args[0], DataType.STRING) + " is not a valid sound name!");
				return false;
			}
			
			Float volume = (Float) plugin.convertType(args[4], DataType.FLOAT);
			Float pitch = (Float) plugin.convertType(args[5], DataType.FLOAT);
			
			((Player) target).playSound(location, sound, SoundCategory.NEUTRAL,volume, pitch);
			return true;
		}
		return false;
	}
	
	//Playsound - Aloud
	public boolean playSound(String[] args, LivingEntity target) {
			double x = target.getLocation().getX() + (Double) plugin.convertType(args[0], DataType.DOUBLE);
			double y = target.getLocation().getY() + (Double) plugin.convertType(args[1], DataType.DOUBLE);
			double z = target.getLocation().getZ() + (Double) plugin.convertType(args[2], DataType.DOUBLE);
			Location location = new Location(target.getWorld(), x, y, z);
			
			Sound sound = Sound.valueOf(((String) plugin.convertType(args[3], DataType.STRING)).toUpperCase());
			if (sound == null) {
				plugin.sendError((String) plugin.convertType(args[0], DataType.STRING) + " is not a valid sound name!");
				return false;
			}
			
			Float volume = (Float) plugin.convertType(args[4], DataType.FLOAT);
			Float pitch = (Float) plugin.convertType(args[5], DataType.FLOAT);
			
			target.getWorld().playSound(location, sound, SoundCategory.NEUTRAL,volume, pitch);
			return true;
	}
	
	//Fire 
	public boolean fire(String[] args, LivingEntity target) {
		target.setFireTicks((int) plugin.convertType(args[0], DataType.INTEGER)*2/100);
		return true;
	}
	
	//Potion Effect
	public boolean effect(String[] args, LivingEntity target) {
		PotionEffectType type = PotionEffectType.getByName(((String) plugin.convertType(args[0], DataType.STRING)).toUpperCase());
		if (type == null) {
			plugin.sendError((String) plugin.convertType(args[0], DataType.STRING) + " is not a valid potion effect!");
			return false;
		}
		int durration = (int) plugin.convertType(args[1], DataType.INTEGER)*2/100;
		int level = (int) plugin.convertType(args[2], DataType.INTEGER);
		boolean hideParticles = (boolean) plugin.convertType(args[3], DataType.BOOLEAN);
		
		
		PotionEffect potion = new PotionEffect(type, durration, level, hideParticles);
		
		target.addPotionEffect(potion);
		return true;
	}
	
	//Add Motion
	public boolean addMotion(String[] args, LivingEntity target) {
		double x = (double) plugin.convertType(args[0], DataType.DOUBLE);
		double y = (double) plugin.convertType(args[1], DataType.DOUBLE);
		double z = (double) plugin.convertType(args[2], DataType.DOUBLE);
		
		Vector vec = new Vector(x, y, z);
		
		target.setVelocity(vec);
		return true;
	}
	
	
	//Move in Dirrection
	public boolean addRelativeMotion(String[] args, LivingEntity target) {

		Vector dir = target.getLocation().getDirection();
		Vector y = new Vector(0, (double) plugin.convertType(args[1], DataType.DOUBLE), 0);
		  
		dir.normalize();
		dir.add(y);
		dir.multiply((Double) plugin.convertType(args[0], DataType.DOUBLE)); ;
		
		target.setVelocity(dir);
		return true;
	}
	
	//Set Inivisible
	public boolean setInvulnerable(String[] args, LivingEntity target) {
		Boolean value = (boolean) plugin.convertType(args[0], DataType.BOOLEAN);
		target.setInvulnerable(value);
		return true;
	}
	
}