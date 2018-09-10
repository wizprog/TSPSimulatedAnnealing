package main;



public class Grad {
	private double x,y;
private int update,choose;
	
	public Grad(double x,double y){
		this.x = x;
		this.y = y;
				
		update = 0;
		choose = 0;
		
	}
	
	public double rastojanje(Grad c){
		double dx = this.x - c.x;
		double dy = this.y - c.y;
		
		return Math.sqrt(dx*dx + dy*dy);
		
	}
	public double getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public double getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}
	
	public int getUpdate() {
		return update;
	}
	
	public void setUpdate(int update) {
		this.update = update;
		
	}
	
	public int getChoose() {
		return choose;
	}
	
	public void setChoose(int choose) {
		this.choose = choose;
	}
	
}
