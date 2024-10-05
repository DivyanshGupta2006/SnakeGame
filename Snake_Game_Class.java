package Snake_Game_Project;
//Imports
import javax.swing.JFrame;

//Class starts
public class Snake_Game_Class 
{
	
	//Variable declaration
	static int boardwidth = 1500;
	static int boardheight = 750;
	int tilesize = 25;
	int x,y;
	
	//main Method
public static void main(String[] args) throws Exception
{
		//Initialization
		JFrame f = new JFrame("Snake Game");
		snakeGame s = new snakeGame(boardwidth,boardheight);
		//Frame Block
		f.setVisible(true);
		f.setSize(boardwidth, boardheight);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(s);
		f.pack();
		//Operational Block
		s.requestFocus();
}
}