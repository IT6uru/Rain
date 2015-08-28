package com.thecherno.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.thecherno.rain.graphics.Screen;
import com.thecherno.rain.input.Keyboard;



public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public static int width = 300;
	public static int height = width / 16 * 9; // Keep 16:9 aspect
	public static int scale = 3;
	public static String title = "Rain";
	
	private Thread thread;
	private JFrame frame;
	private Keyboard key; 
	private boolean running = false;
	
	private Screen screen;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game () {  // constructor new Game() ---> executes everything here ONCE
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size); // found in Canvas
		screen = new Screen(width, height);
		
		frame = new JFrame();
		key = new Keyboard();
		
		addKeyListener(key); // after instanciating key
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display"); // game class runs in this thread
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() { //game loop called by start because its runnable
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1){
				update(); // logic tick ( 60 ticks per second)
				updates++;
				delta --;
			}
			render(); // graphics (unlimited)
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps ");
				frame.setTitle( title + "   |   " + updates + " ups, " + frames + " fps ");
				updates = 0;
				frames = 0;
				
			}
		}
		stop();
	}
	int x = 0, y = 0;
	public void update() {
		key.update();
		
		if (key.up)    y--;
		if (key.down)  y++;
		if (key.left)  x--;
		if (key.right) x++;
			
		
	}
	
	public void render() { // Buffer strategy
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) { // only create buffer strategy once
			createBufferStrategy(3); // triple buffering
			return;
		}
		screen.clear(); 
		screen.render(x , y);
		
		for (int i = 0; i < pixels.length; i++) {  //transfer screen array to render array
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics(); // link between graphics and buffer
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null); // Draw pixels (how is pixels getting to image? Magic.)
		g.dispose(); // drop current frame
		bs.show();  //show next frame
		
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); // centers window
		game.frame.setVisible(true); // shows  frame
		game.start();
		
	}

}
