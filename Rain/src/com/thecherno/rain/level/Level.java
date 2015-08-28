package com.thecherno.rain.level;

import com.thecherno.rain.graphics.Screen;

public class Level {
	
	protected int width, height;
	protected int[] tiles;
	
	public Level(int width, int height) {  // Random level generation
		this.width = width;
		this.height = height;
		tiles = new int [width * height];
		generateLevel();
	}
	
	public Level(String path) {			// Load level from file
		loadLevel(path);
	}
	
	
	protected void generateLevel() {
		
		
		
		
	}
	
	private void loadLevel(String path) {
		
	}
	
	public void update() {  //entities, AI, ETC 60 updates per second
		
	}
	
	private void time() {  //Handles day/night cycles in the level
		
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		
	}

}
