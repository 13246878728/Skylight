package Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Snake {
	private Node head = null;
	private Node tail = null;
	private int size = 0;
	private Node n = new Node(20,15,Dir.D);
	Yard yard;
	
	public Snake(Yard yard){
		head = n;
		tail = n;
		size = 1;
		this.yard=yard;
	}
	private class Node{
		
		private static final int STEP = Yard.Block;
		Node next = null;
		Node prev = null;
		private int x , y;
		Dir dir;
		public Node(int x, int y,Dir dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
		void draw(Graphics g){
			if(yard.isGameOver()== false){
			g.setColor(Color.black);
			g.fillRect(x*STEP, y*STEP, STEP, STEP);
			}else{
				g.setColor(Color.red);
				g.fillRect(x*STEP, y*STEP, STEP, STEP);
				
			}
		}			
	}
	public void addToHead(){
		Node node = null;
		switch(head.dir){
		case L:
			node = new Node(head.x-1,head.y,head.dir);
			break;
		case R:
			node = new Node(head.x+1,head.y,head.dir);
			break;
		case U:
			node = new Node(head.x,head.y-1,head.dir);
			break;
		case D:
			node = new Node(head.x,head.y+1,head.dir);
			break;

		}
		node.next = head;
		head.prev = node;
		head = node;
		size++;
		}
	
	public void draw(Graphics g){
		if(size <= 0) return;
		move();
		for(Node n = head; n != null ; n = n.next){
			n.draw(g);
		}
		
		
	}

	public void move() {
		// TODO Auto-generated method stub
		if(yard.isGameOver()==false){
		addToHead();
		deleteFromtail();
		checkdead();
		}
	}
	private void checkdead() {
		// TODO Auto-generated method stub
		if(head.x+1<=0||head.y+1<=0||head.x>=Yard.cols||head.y>=Yard.rows){
			yard.stop();	
		}
		for(Node n = head.next; n != null; n = n.next) {
			if(head.x == n.x && head.y == n.y) {
				yard.stop();
			}
		}
	}

	private void gameover() {
		// TODO Auto-generated method stub
		
	}

	private void deleteFromtail() {
		// TODO Auto-generated method stub
		if(size<=0) return;
		tail = tail.prev;
		tail.next = null;
	}
	public void eat(egg e){
		if(this.getRct().intersects(e.getRct())){
			e.reAppear();
			this.addToHead();
			yard.setScore(yard.getScore()+5);
			
		}
		
	}
	//蛇头的指定区域
	private Rectangle getRct(){
		return new Rectangle(head.x*Yard.Block, head.y*Yard.Block, Yard.Block, Yard.Block);
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key =e.getKeyCode();
		switch (key){
		case KeyEvent.VK_LEFT:
			if(head.dir != Dir.R)
			head.dir=Dir.L;
			break;
		case KeyEvent.VK_RIGHT:
			if(head.dir != Dir.L)
			head.dir=Dir.R;
			break;
		case KeyEvent.VK_UP:
			if(head.dir != Dir.D)
			head.dir=Dir.U;
			break;
		case KeyEvent.VK_DOWN:
			if(head.dir != Dir.U)
			head.dir=Dir.D;
			break;
		case KeyEvent.VK_F2:
			yard.restart();
			break;
		}
		
	}




}
