package bouncing.ball.dmultiple;

import java.awt.*;
import java.util.Random;

public class Ball {
	 float x, y;
	 float speedX, speedY;
	 float radius;
	 private Color color;
	 private Random rand;
	 private char letter;
	
	 public Ball(float x, float y, float radius, float speed, float angleInDegree, Color color, char letter) {
		 this.x = x;
		 this.y = y;
		 this.speedX = (speed * (float) Math.cos(Math.toRadians(angleInDegree)));
		 this.speedY = (-speed * (float) Math.sin(Math.toRadians(angleInDegree)));
		 this.radius = radius;
		 this.color = color;
		 this.rand = new Random();
		 this.letter = letter;
	 }
		
	 public void draw(Graphics g) {
		 g.setColor(color);
		 g.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));
		 g.setColor(Color.BLACK);
		 g.setFont(new Font("Verdana", Font.BOLD, 15)); 
		 g.drawString(String.valueOf(letter), (int) x - 3, (int) y + 3);
	 }
	 
	 public void collide(Ball ball) {
		 if (isColliding(ball.x, ball.y, ball.radius)) {
			 float xDiff = (float) (this.x - ball.x);
			 float yDiff = (float) (this.y - ball.y);
			 
			 float d = (float) Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)); 
			 float nx = xDiff / d; 
			 float ny = yDiff / d; 
			 float p = 2 * (this.speedX * nx + this.speedY * ny - ball.speedX * nx - ball.speedY * ny);
			 
			 this.speedX = this.speedX - p * (float) 0.5 * nx;
			 this.speedY = this.speedY - p * (float) 0.5 * ny; 
			 ball.speedX = ball.speedX + p * (float) 0.5 * nx;
			 ball.speedY = ball.speedY + p * (float) 0.5 * ny;
		 }
	 }
	 
	 public Boolean isColliding(float x, float y, float radius) {
		 float xDiff = (float) (this.x - x);
		 float yDiff = (float) (this.y - y);
		 float totalDiff = (float) (Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		 
		 if (totalDiff < (float) Math.pow(radius + this.radius, 2)) return true;
		 else return false;
	 }
		
	 public void collide(BallArea box) {
		 float ballMinX = box.minX + radius;
		 float ballMinY = box.minY + radius;
		 float ballMaxX = box.maxX - radius;
		 float ballMaxY = box.maxY - radius;
		
		 x += speedX;
		 y += speedY;
		
		 if (x < ballMinX) {
			 speedX = -speedX;
			 x = ballMinX;
		 } else if (x > ballMaxX) {
			 speedX = -speedX;
			 x = ballMaxX;
		 }
		
		 if (y < ballMinY) {
			 speedY = -speedY;
			 y = ballMinY;
		 } else if (y > ballMaxY) {
			 speedY = -speedY;
			 y = ballMaxY;
		 }
	 }
}