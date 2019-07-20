package aws404.spells.functions;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import aws404.spells.SpellScriptVariable;
import net.md_5.bungee.api.ChatColor;

public class FunctionRayTraceTeleport extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptVariable[] args) {
		Double range = args[0].getDouble();
		
		return rayTraceTeleport(target, range);		
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
			  BlockFace face = r.getHitBlockFace();  
			  double x = r.getHitBlock().getX()+0.5;
			  double y = r.getHitBlock().getY();
			  double z = r.getHitBlock().getZ()+0.5;
			  
			  if (face == BlockFace.DOWN) y = y-2;
			  if (face == BlockFace.UP) y = y+1;
			  if (face == BlockFace.NORTH) z = z-1;
			  if (face == BlockFace.EAST) x = x+1;
			  if (face == BlockFace.SOUTH) z = z+1;
			  if (face == BlockFace.WEST) x = x-1;
			  
			  Location loc = new Location(target.getWorld(), x, y, z, target.getLocation().getYaw(), target.getLocation().getPitch());
			  
			  if (isSafeLocation(loc)) {
				  target.teleport(loc);
				  return true;
			  } else if (isSafeLocation(loc.add(0, -1, 0))) {
				  target.teleport(loc);
				  return true;
			  } else {
				  if (target.getType().equals(EntityType.PLAYER)) plugin.actionBarClass.sendActionbar((Player) target, ChatColor.RED + "Unable to Teleport, Destination is Obstructued");
				  return false;
			  }
		  }
	}
	
	
    @SuppressWarnings("deprecation")
	private boolean isSafeLocation(Location location) {
        Block feet = location.getBlock();
        if (!feet.getType().isTransparent()) {
            return false; // not transparent (will suffocate)
        }
        Block head = feet.getRelative(BlockFace.UP);
        if (!head.getType().isTransparent()) {
            return false; // not transparent (will suffocate)
        }
        return true;
    }
 

}
