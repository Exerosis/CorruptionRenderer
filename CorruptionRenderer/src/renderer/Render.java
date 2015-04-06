package renderer;

import generator.GeneratorMain;
import generator.util.BatchQueue;
import generator.util.MathUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Render {
	private int sizeX;
	private int sizeZ;
	private int x;
	private int z;
	private int width = 10;
	private int renderer;
	private BatchQueue queue;
	private int batch;

	public Render(Location location) {
		sizeX = GeneratorMain.getData().getMapSizeX();
		sizeZ = GeneratorMain.getData().getMapSizeY();
		x = location.getBlockX();
		z = location.getBlockZ();

		batch = sizeX/2;
		queue = new BatchQueue(batch);

		render();
	}

	public void render(){
		renderer = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable(){
			int percent;
			@Override
			public void run() {
				if (batch <= 0 || sizeX <= 0 || sizeZ <= 0) {
					Bukkit.getServer().getScheduler().cancelTask(renderer);
					Bukkit.broadcastMessage("Done rendering!");
				}

				for(int layer = 0; layer < width; layer++){
					percent = 100 -(10*layer);
					for(int changeX = 0; changeX < sizeX; changeX++){
						if(MathUtil.percent(percent))
						queue.add(x + changeX, z + layer);
						if(MathUtil.percent(percent))
						queue.add(x + changeX, z - (layer + 1) + sizeZ);
					}
					for(int changeZ = 0; changeZ < sizeZ; changeZ++){
						if(MathUtil.percent(percent))
						queue.add(x + layer, z + changeZ);
						if(MathUtil.percent(percent))
						queue.add(x - (layer + 1) + sizeX, z + changeZ);
					}
				}	

				queue.sendQueue();
				batch--;
				sizeX--;
				sizeZ--;
				sizeX--;
				sizeZ--;
				x++;
				z++;
			}

		}, 0, (long)10);
	}

}