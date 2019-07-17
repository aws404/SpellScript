package aws404.spells.functions;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import aws404.spells.DataType;
import net.md_5.bungee.api.ChatColor;

public class FunctionRayTraceTeleport extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		Double range = (Double) plugin.convertType(args[0], DataType.DOUBLE);
		
		rayTraceTeleport(target, range);		
	}

	@Override
	public String name() {
		return "rayTraceTeleport";
	}
	
	public boolean rayTraceTeleport(LivingEntity target, Double range) {
		FluidCollisionMode fluidMode = FluidCollisionMode.ALWAYS;
		RayTraceResult r = target.rayTraceBlocks(range, fluidMode);
		
		if(r == null) {
			  Location loc = target.getLocation();
			  Vector dir = loc.getDirection();
			  
			  dir.normalize();
			  dir.multiply(range); 
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
				  if (target.getType().equals(EntityType.PLAYER)) plugin.actionBarClass.sendActionbar((Player) target, ChatColor.RED + "Unable to Teleport, Destination is Obstructued");
				  return false;
			  }
		  }
	}

}
