package Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Snake.Yard.PaintThread;



public class Yard extends Frame {
	public  static final int rows = 30;
	public static final int cols = 30;
	public static final int Block = 20;
	
	Image offScreenImage = null;
	Snake s = new Snake(this);
	egg e = new egg();
	PaintThread paintThread = new PaintThread();
	private boolean gameOver = false;
	public void launch(){
		this.setTitle("手撸贪吃蛇");
		this.setLocation(200, 80);
		this.setSize(Block*cols, Block*rows);
		this.setBackground(Color.GRAY);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyMonitor());
		new Thread(paintThread).start();
		
	}
	public boolean isGameOver() {
		return gameOver;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Yard().launch();
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, cols*Block, rows*Block);
		g.setColor(Color.GRAY);
		for (int i = 1; i < cols-1; i++) {
			g.drawLine(0, i*Block, cols*Block, i*Block);
		}
		for (int i = 1 ; i < rows-1; i++) {
			g.drawLine(i*Block, 0, i*Block, Block*rows);
		}
		s.draw(g);
		e.draw(g);
		s.eat(e);
		g.setColor(Color.red);
		g.setFont(new Font("方正喵呜体",Font.BOLD,50));
		g.drawString("score:"+score, 20, 80);
		if(gameOver){
		
			g.setColor(Color.yellow);
			g.setFont(new Font("方正喵呜体",Font.BOLD,50));
			g.drawString("GAME  OVER", 170, 250);
			g.drawString("按f2重新开始", 170, 300);
			paintThread.gameOver();
		}
	
	}
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if(offScreenImage == null){
			offScreenImage = this.createImage(cols*Block, cols*Block);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0,null );
	}
	public void stop(){
		gameOver = true;
	}
	private  int score = 0;
	public int getScore(){
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public class PaintThread implements Runnable{
		
		public void run(){
			while(true){
			repaint();
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}	
		}

		public void gameOver() {
			// TODO Auto-generated method stub
			gameOver = true;
		}
	}
	public class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			s.keyPressed(e);
		}
		
	}
	public void restart() {
		// TODO Auto-generated method stub
		gameOver=false;
		score = 0;
		s = new Snake(this);
	}
	
}

