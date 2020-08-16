package com.guto.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.guto.main.Game;

public class Player extends Entity {
	
	public boolean up, down, left, right;
	public double speed = 1.1;
	public int left_dir = 0, right_dir = 1;
	public int dir = 0;

	private int frames = 0, maxFrames = 15, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		for (int i = 0; i < 4; i++)
		{
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		for (int i = 0; i < 4; i++)
		{
			leftPlayer[i] = Game.spritesheet.getSprite(80 - (i*16), 16, 16, 16);
		}
	}

	public void tick()
	{
		moved = false;
		if (right == true)
		{
			moved = true;
			dir = right_dir;
			x+= speed;
		}
			else if (left == true)
			{
				moved = true;
				dir = left_dir;
				x-= speed;
			}
		if (up == true)
		{
			moved = true;
			y-= speed;
		}
		else if (down == true)
		{
			moved = true;
			y+= speed;
		}
		if (moved == true)
		{
			frames++;
			if (frames == maxFrames)
			{
				frames = 0;
				index++;
				if (index > maxIndex)
				{
					index = 0;
				}
			}
		}
	}
	
	public void render (Graphics g)
	{
		if (dir == right_dir)
		{
			g.drawImage(rightPlayer[index], this.getX(), this.getY(), null);
		}
			else if (dir == left_dir)
			{
				g.drawImage(leftPlayer[index], this.getX(), this.getY(), null);
			}
	}
	
	
	
}
