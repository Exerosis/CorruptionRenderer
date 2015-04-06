package renderer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public static JavaPlugin plugin;
	public static Render renderer;
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		plugin = this;	
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event){	
		if(event.getPlayer().getItemInHand().getType().equals(Material.APPLE)){
			Bukkit.broadcastMessage("Rendering!");
			try {
				renderer = new Render(event.getPlayer().getLocation());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static JavaPlugin getPlugin(){
		return plugin;
	}
}