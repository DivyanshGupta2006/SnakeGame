package Snake_Game_Project;
//Imports
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

//Class Starts
public class snakeGame extends JPanel implements ActionListener , KeyListener{
 
	//Variable declaration
	int tilesize = 25;
	Timer gameLoop;
	int boardwidth,boardheight,velocityx,velocityy;//Dimensions and Velocity along different axes to move *snake*
	Tile snakeHead,food;
	Random r;
	ArrayList<Tile> snakeBody;
	boolean gameOver = false;
	
	//Sub-Class(For assigning coordinates to paint Graphics) begins
	private class Tile {
		
	//Variable declaration
	int x,y;
	
	//Constructor(Sub-Class) begins
	Tile(int x, int y){
			this.x=x; //These act as coordinates, and at these coordinates we print graphics 
			this.y=y;
	}
}
	
	// constructor(Main class) Begins
	snakeGame(int boardwidth,int boardheight){
		
		//Initialization
	food = new Tile(15,15);
	r = new Random();
	gameLoop = new Timer((100), this);
	snakeHead = new Tile(10,10);
	snakeBody = new ArrayList<Tile>();
	velocityx=0;
	velocityy=1;
	this.boardwidth = boardwidth;//Board Dimensions
	this.boardheight = boardheight;
	setPreferredSize(new Dimension(this.boardwidth, this.boardheight));
	setBackground(Color.black);
	addKeyListener(this);
	gameLoop.start();//Game Starting method call
}

	//Method to paint
	public void paintComponent(Graphics g) {
	super.paintComponent(g);
	draw(g);
 }

	//Method to paint *snake*,*food* and *grid*
	public void draw(Graphics g) {
		
//		//Code for grid Lines
//	 for(int i = 0;i<=boardwidth/tilesize;i++) {
//		 g.drawLine(i*tilesize, 0, i*tilesize, boardheight);
//		 g.drawLine(0, i*tilesize, boardwidth, i*tilesize);
//	 }
//	 
	//Drawing Body
	 g.setColor(Color.red);
	 g.fill3DRect(food.x*tilesize, food.y*tilesize, tilesize, tilesize,true);
	 
	 //Drawing Snake head
	 g.setColor(Color.blue);
	 g.fill3DRect(snakeHead.x*tilesize, snakeHead.y*tilesize, tilesize, tilesize,true);
	 
	 //Drawing Snake body using loop
	 for(int i = 0;i<snakeBody.size();i++)
	 {
		 Tile snakePart = snakeBody.get(i);
		 g.setColor(Color.green);
		 g.fill3DRect(snakePart.x*tilesize, snakePart.y*tilesize, tilesize, tilesize,true);
	 }
	 
	 //Displaying Instructions
	 g.setFont(new Font("Arial", Font.BOLD,16));
	 g.setColor(Color.darkGray);
	 g.drawString("For Restart , Press SPACE ", boardwidth - 250, tilesize);
	 
	 //Displaying Game Over Screen
	 if(gameOver)
	 {g.setFont(new Font("Arial", Font.BOLD,100));
		 g.setColor(Color.darkGray);
		 g.drawString ("Game Over !!! Score : " + String.valueOf(snakeBody.size()),200,365);
	 }
	 
	 //Displaying Score
	 else {
		 g.setFont(new Font("Arial", Font.BOLD,16));
		 g.setColor(Color.darkGray);
		 g.drawString("Score : "+ String.valueOf(snakeBody.size()),tilesize-15,tilesize);
	 }
 }
 
	//Method to place *food* randomly
	public void placeFood() {
	 food.x = r.nextInt(boardwidth/tilesize);
	 food.y = r.nextInt(boardheight/tilesize);
 }
 
	//Method to Progress the game and call moving function, and game over window
	public void actionPerformed(ActionEvent e) {
	move();
	repaint();
	if(gameOver==true)
	gameLoop.stop();
	}

	//Method to handle movements and conditions of *game over*
	public void move() {
		
	//Checking for eating of *food*
	if(collision(snakeHead,food))
	{
		snakeBody.add(new Tile(food.x,food.y));
		placeFood();
	}
	
	//Movement of *snake body* via utilisation of *snake head*
	for(int i = snakeBody.size()-1;i>=0;i--)
	{
		Tile snakePart = snakeBody.get(i);
		//Assigning coordinates of *snake head* to 1st *snake part* to faciliate movement via redrawing
				if(i==0)
				{
					snakePart.x=snakeHead.x;
					snakePart.y=snakeHead.y;
				}
				else {
		Tile prevSnakePart = snakeBody.get(i-1);
		
		
		
		//Assigning coordinates of Previous snake part to *snake part* to faciliate movement via redrawing
		
		{
			snakePart.x=prevSnakePart.x;
			snakePart.y=prevSnakePart.y;
		}
	}
	}
	//Updation of tile coordinates of snake head according to velocities
	snakeHead.x+=velocityx;
	snakeHead.y+=velocityy;
			
	//Condition for *game over*(collision of snake head with wall or snake body)
	for(int i=0;i < snakeBody.size();i++)
	{
		Tile snakePart = snakeBody.get(i);
		if(collision(snakePart , snakeHead ) || snakeHead.x*tilesize > boardwidth || snakeHead.y*tilesize> boardheight || snakeHead.x< 0 || snakeHead.y< 0)
		gameOver = true;
	}
}

	//Unrequited implemented Methods
	public void keyTyped(KeyEvent e) {
}
	public void keyReleased(KeyEvent e) {
}

	//Method to add controls(KeyPressed)
	public void keyPressed(KeyEvent e) {
	
	//+ve x = Right(pixel++)
	//+ve y = Down(pixel++)
	if(e.getKeyCode()==KeyEvent.VK_UP && velocityy!=1) {
		velocityx=0;
		velocityy=-1;
	}
	else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityy!=-1)
	{
		velocityx=0;
		velocityy=1;
	}
	else if (e.getKeyCode()==KeyEvent.VK_RIGHT && velocityx != -1)
	{
		velocityx=1;
		velocityy=0;
	}
	else if (e.getKeyCode()==KeyEvent.VK_LEFT && velocityx!=1)
	{
		velocityx=-1;
		velocityy=0;
	}
	//Block to restart the *game*
	else if (e.getKeyCode()==KeyEvent.VK_SPACE)
	{
		gameOver=false;
		velocityx=0;
		velocityy=1;
		snakeHead.x=10;
		snakeHead.y=10;
		food.x=15;
		food.y=15;
		snakeBody = new ArrayList<Tile>();
		gameLoop.start();
	}
}

	//Method to check collision between two tiles
	public boolean collision (Tile t1 , Tile t2)
{
	return t1.x==t2.x && t1.y==t2.y;
}
}