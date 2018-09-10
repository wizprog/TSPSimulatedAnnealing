package main;

public class Neuron{
	
	public double x,y;
	public double wx,wy;
	public int update,choose;
	
	public Neuron(double x,double y){
		this.x = x;
		this.y = y;
		
		this.wx = Math.random();
		this.wy = Math.random();
		
		update = 0;
		choose = 0;
		
	}
	
	public double rastojanjeIzmedjuNeurona(Neuron c){
		double dx = this.x - c.x;
		double dy = this.y - c.y;
		
		return Math.sqrt(dx*dx + dy*dy);
		
	}
	
	public double TezinskoRastojanje(Neuron c){
		double dx = this.wx - c.wx;
		double dy = this.wy - c.wy;
		
		return Math.sqrt(dx*dx + dy*dy);
		
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWx() {
		return wx;
	}

	public void setWx(double wx) {
		this.wx = wx;
	}

	public double getWy() {
		return wy;
	}

	public void setWy(double wy) {
		this.wy = wy;
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