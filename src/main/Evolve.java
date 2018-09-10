package main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

public class Evolve extends Frame {
	
	public Label l, l1, l2;
	
	Font mF = new Font("Courier", Font.BOLD, 14);
	Font sF = new Font("Courier", Font.BOLD, 8);
	
	public static Button btn1 = new Button("Create");
	public static Button btn2 = new Button("Start");
	
	public Evolve() {
		super("Traveling salesman problem");
		setSize(1200,800);
		this.setBackground(Color.RED);
		addComponents();
		setVisible(true);
	}
	
	public void addComponents() {
		
		TextField txt1 = new TextField("Number of cities");
		this.setSize(1000,800);	
	//	som.kohonenInit();
		Panel p = new Panel();
		Panel p1 = new Panel();
		
		p1.setBackground(Color.GRAY);
		
		p1.add(btn1);
		p1.add(txt1);
		p1.add(btn2);
		
		p.setSize(WIDTH,30);
		l = new Label("Broj gradova : ");
		p.add(l);
		l2 = new Label("Ukupno rastojanje:");
		p.add(l2);
		l1 = new Label();
		p.add(l1);
		l2.setFont(mF);
		l.setFont(mF);
		l1 = new Label();
		p.add(l1);
		p.setBackground(Color.GRAY);
		add(p,BorderLayout.NORTH);
		add(p1,BorderLayout.SOUTH);
		

	}
	
	public boolean handleEvent(Event e) {
		if(e.id == Event.WINDOW_DESTROY) {
			System.exit(0);
			return true;
		} else 
			return false;
	}

	public static void main(String[] args) {
		
		Evolve e = new Evolve();
	}

}
