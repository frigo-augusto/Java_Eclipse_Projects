package com.guto.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.guto.entities.Entity;
import com.guto.entities.Player;
import com.guto.graphics.Spritesheet;
import com.guto.world.World;
public class Game extends Canvas implements Runnable,KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private int i = 0;
	private boolean isRunning = true;
	private final int WIDTH = 160;
	private final int HEIGHT = 120;
	private final int SCALE = 4;
	
	private BufferedImage image;
	
	public List<Entity> entities;
	public static Spritesheet spritesheet;
	private Player player;
	public static World world;
	
	public Game()
	{
		
		addKeyListener(this);
		this.setPreferredSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		//inicializar
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/spritesheet.png");
		world = new World("/map.png");
		
		player = new Player(0,0,16,16, spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
	}
	public void initFrame()
	{
		frame = new JFrame("Meu jogo");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start()
	{
		thread = new Thread(this);
		isRunning = true;
		thread.start();
		
	}
	
	public synchronized void stop()
	{
		isRunning = false;
		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		Game game = new Game();
		game.start();
	}
	
	public void tick()
	{
		for (i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.tick();
		}
	}
	
	
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		/* renderização*/
		
		//Graphics2D g2 = (Graphics2D) g;
		//g2.drawImage(player[cur_animation], 20, 20, null);
		world.render(g);
		for (i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.render(g);
		}
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
		
	}
	
	public void run()
	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning)
		{
			long now = System.nanoTime();
			delta+= (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1)
			{
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000)
			{
				System.out.println("FPS: " + frames);
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}
		stop();
		
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			player.up = true;
		}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				player.down = true;
			}
		
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			player.left = true;
		}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				player.right = true;
			}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			player.up = false;
		}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				player.down = false;
			}
		
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			player.left = false;
		}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				player.right = false;
			}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}