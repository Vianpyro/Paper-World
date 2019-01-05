package dev.vianpyro.paperworld.entities.statics.structures;

import java.awt.Graphics;

import dev.vianpyro.paperworld.Handler;
import dev.vianpyro.paperworld.entities.statics.StaticEntity;
import dev.vianpyro.paperworld.graphics.Assets;
import dev.vianpyro.paperworld.tiles.Tile;

public class DirtPath extends StaticEntity {

	public DirtPath(Handler handler, float x, float y) {
		super(handler, x, y, Tile.DEFAULT_TILE_WIDTH, Tile.DEFAULT_TILE_HEIGHT);
		
		bounds.x = bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
	}
	
	public boolean isWalkable() {
		return true;
	}

	public void die() {
		
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		g.drawImage(Assets.dirt_path, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
	}
}
