package com.thecherno.rain.level;

import java.util.Random;

public class RandomLevel extends Level{
	
	private final Random random = new Random();

	public RandomLevel(int width, int height) {
		super(width, height);   // super class level calls Level(int,int)
		
	}
	
	protected void generateLevel() {     //overrides level.generateLevel() when RandomLevel.generateLevel() is called
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x + y * width] = random.nextInt(4);   //Random from 0 - 3
			}
				
		}
		
	}

}
