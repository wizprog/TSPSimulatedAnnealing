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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evolve extends Frame {

	public static Label l, l1, l2;

	public static boolean created = false;

	Font mF = new Font("Courier", Font.BOLD, 14);
	Font sF = new Font("Courier", Font.BOLD, 8);

	private Draw main = new Draw();

	public static Button btn1 = new Button("Create");
	public static Button btn2 = new Button("Start");

	public Evolve() {
		super("Traveling salesman problem");
		setSize(1200, 800);
		this.setBackground(Color.RED);
		addComponents();
		setVisible(true);
	}

	public void addComponents() {

		TextField txt1 = new TextField("Number of cities");
		this.setSize(1000, 800);
		Panel p = new Panel();
		Panel p1 = new Panel();

		p1.setBackground(Color.GRAY);
		p1.add(btn1);
		p1.add(txt1);
		p1.add(btn2);
		p.setSize(WIDTH, 30);

		l = new Label("Broj gradova : ");
		p.add(l);

		l2 = new Label("Ukupno rastojanje:");
		p.add(l2);

		l1 = new Label();
		p.add(l1);

		l2.setFont(mF);
		l.setFont(mF);

		l1 = new Label();
		l1.setFont(mF);

		p.add(l1);
		p.setBackground(Color.GRAY);
		add(p1, BorderLayout.NORTH);
		add(p, BorderLayout.SOUTH);
		add(main);

		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txt1.getText().matches("-?\\d+(\\.\\d+)?")) {
					Draw.drawConections = false;
					int count = Integer.parseInt(txt1.getText());
					SOM.brojGradova = count;
					Draw.som.kohonenInit(getSize().height, getSize().width);
					l1.setText("Cities created");
					System.out.println("Broj gradova: " + SOM.brojGradova);
					l.setText("Broj gradova: " + SOM.brojGradova);
					repaint();
					//main.repaint();
					created = true;
				} else {
					l1.setText("Enter a number");
					created = false;
					main.repaint();
				}

			}
		});

		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (created) {
					new Thread(main).start();
					Draw.drawConections = true;
				}else {
					l1.setText("Create cities");
					main.repaint();
				}
			}
		});

	}

	public boolean handleEvent(Event e) {
		if (e.id == Event.WINDOW_DESTROY) {
			System.exit(0);
			return true;
		} else
			return false;
	}

	public static void main(String[] args) {

		Evolve e = new Evolve();
	}

}
