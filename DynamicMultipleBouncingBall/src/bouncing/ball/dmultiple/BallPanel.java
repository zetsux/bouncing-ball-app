package bouncing.ball.dmultiple;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BallPanel extends JPanel implements KeyListener {
	private static final long serialVersionUID = -5010745477304530586L;
	private static final int REFRESH_RATE = 120;
	private ArrayList<Ball> balls = new ArrayList<Ball>();
	private BallArea box;
	private int areaWidth;
	private int areaHeight;
	private Boolean isChecking = false;
	
	public BallPanel(int width, int height) {
		this.areaWidth = width; this.areaHeight = height;
		this.setPreferredSize(new Dimension(areaWidth, areaHeight));
		
		box = new BallArea(0, 0, width, height, Color.BLACK, Color.WHITE);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Component c = (Component)e.getSource();
				Dimension dim = c.getSize();
				areaWidth = dim.width;
				areaHeight = dim.height;
				box.set(0, 0, areaWidth, areaHeight);
			}
		});
		
		this.addKeyListener(this);
		this.setFocusable(true);
		startThread();
	}
	
	public void collideCheck()
	{
		isChecking = true;
		for (Ball b : balls)
		{
			b.collide(box);
			for (Ball b2 : balls)
			{
				if(b == b2) continue;
				b.collide(b2);
			}	
		}
	}
	
	public void startThread() {
		Thread gameThread = new Thread() {
			public void run() {
				while (true) {
					collideCheck();
					isChecking = false;
					repaint();
					try {
						Thread.sleep(1000 / REFRESH_RATE);
					} catch (InterruptedException ex) {}
				}
			}
		};
		gameThread.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		box.draw(g);
		for (Ball b : balls) b.draw(g);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//Do Nothing...
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char keyChar = e.getKeyChar();
		if (Character.isAlphabetic(keyChar) || Character.isDigit(keyChar))
		{
			if (isChecking) return;
			Random rand = new Random();
			int radius = 50;
			int speed = 2;
			int angleInDegree = rand.nextInt(360);
			int r = rand.nextInt(230);
			int g = rand.nextInt(230);
			int b = rand.nextInt(230);
			
			Boolean flag = true;
			while (flag)
			{
				int x = rand.nextInt(areaWidth - radius * 2 - 20) + radius + 10;
				int y = rand.nextInt(areaWidth - radius * 2 - 20) + radius + 10;
				for (Ball ba : balls)
				{
					if(ba.isColliding(x, y, 140)) flag = false;
				}
				
				if (flag)
				{
					balls.add(new Ball(x, y, radius, speed, angleInDegree, new Color(r, g, b), keyChar));
					break;
				}		
				else flag = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Do Nothing...
	}
}