package Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class egg {
	private static final int STEP = Yard.Block;
	private int x, y;
	private static Random r = new Random();
	Color color = Color.green; 
	public egg(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public egg(){
		this(r.nextInt(Yard.cols-3)+1,r.nextInt(Yard.rows-3)+1);
	}
	public void draw(Graphics g){
//		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(x*STEP, y*STEP, STEP, STEP);
		if(color == Color.green){
			color = Color.pink;
		}else{
			color = Color.green;
		}
//		g.setColor(c);
	}
	//egg的制定区域
	public Rectangle getRct(){
		return new Rectangle(x*Yard.Block, y*Yard.Block, Yard.Block, Yard.Block);
	}
	public void reAppear(){
		this.x = r.nextInt(Yard.cols-3)+1;
		this.y = r.nextInt(Yard.rows-3)+1;
	}
}
