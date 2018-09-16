package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.Rectangle;

public class Draw extends Component implements Runnable {

	private static final long serialVersionUID = 1L;

	public Image homeI, offscreen;
	public int imagewidth, imageheight;

	// boje
	public static final Color bkC = new Color(0x09600);
	public static final Color bk2C = new Color(0x000000);
	public static final Color lnC = new Color(0xffc900);
	public static final Color ln2C = new Color(0xcccc00);
	public static final Color fgC = new Color(0xffffff);

	// labele
	public Label l, l1, l2;

	// fontovi
	Font mF = new Font("Courier", Font.BOLD, 14);
	Font sF = new Font("Courier", Font.BOLD, 8);

	int counter = 0;
	public static SOM som = new SOM();
	public boolean please_stop = false;

	public static boolean drawConections = false;

	@Override
	public void run() {
		System.out.println("Pozvan");

		Evolve.btn1.setEnabled(false);
		Evolve.btn2.setEnabled(false);

		int count = 0;

		counter = 0;
		while (!please_stop) {

			counter++;

			som.Trening();

			count = (count++) % 10;
			if (count == 0) {

				// paint(this.getGraphics());
				repaint();

				int brojac = 0;
				for (int i = 0; i < som.grad.length; i++) {
					for (int j = 0; j < som.neuroni.length; j++) {
						if (som.grad[i].getX() == som.neuroni[j].wx && som.grad[i].getY() == som.neuroni[j].wy) {
							brojac++;
						}
					}
				}

				if (brojac == som.brojGradova || counter == 1300) {
					please_stop = true;
					Evolve.l1.setText("");
					Evolve.l2.setText("Ukupno rastojanje: "+ + som.UkupnaDistanca());
					// paint(this.getGraphics());
					repaint();
				}

				// l1.setText(""+som.UkupnaDistanca());

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
				;
			}

		}
		please_stop = false;
		Evolve.btn1.setEnabled(true);
		Evolve.btn2.setEnabled(true);
	}

	public void paintLeft(Graphics g) {

		int w = getSize().width;
		int h = getSize().height;
		g.setFont(mF);

		// CLEAR ALL
		g.setColor(Color.RED);
		g.fillRect(0, 0, w, h);

		// DRAW PATH

		if (drawConections) {
			g.setColor(Color.BLUE);
			for (int i = 0; i < som.brNerona; i++) {
				g.drawLine(toXReal(som.neuroni[i].wx), toYReal(som.neuroni[i].wy),
						toXReal(som.neuroni[(i + 1) % som.brNerona].wx),
						toYReal(som.neuroni[(i + 1) % som.brNerona].wy));
				// g.drawString(""+i+"-"+(gn[i].update*100/counter)+"%",toXReal(gn[i].wx),toYReal(gn[i].wy));
			}
		}

		g.setColor(fgC);
		
			for (int i = 0; i < SOM.brojGradova; i++) {
				g.fillOval(toXReal(som.grad[i].getX()) - 4, toYReal(som.grad[i].getY()) - 4, 9, 9);
				g.setColor(g.getColor().BLACK);
				g.drawString("" + i + "", toXReal(som.grad[i].getX()), toYReal(som.grad[i].getY()) - 8);
			}


	}

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		this.setSize(1000, 800);
		int w = getSize().width;
		int h = getSize().height;

		this.setBackground(Color.RED);

	/*	Rectangle clip = new Rectangle(0, 0, toXReal(som.COUNTRY), toYReal(som.COUNTRY));
		g2.clipRect(clip.x, clip.y, clip.width, clip.height);

		paintLeft(g2);

		System.gc();*/
		
		if ((offscreen == null) || ((imagewidth != w) || (imageheight != h))) {
			offscreen = this.createImage(w, h);
			imagewidth = w;
			imageheight = h;
		}
		
		Rectangle clip = new Rectangle(0, 0, toXReal(SOM.COUNTRY), toYReal(SOM.COUNTRY));

		
		Graphics goff = offscreen.getGraphics();
		goff.clipRect(clip.x, clip.y, clip.width, clip.height);
		Graphics g1 = this.getGraphics();
		
		g1.clipRect(clip.x, clip.y, clip.width, clip.height);

		paintLeft(goff);
		g1.drawImage(offscreen, 0, 0, this);

		// CLEAR ALL
		g.setColor(Color.RED);
		g.fillRect(w / 2 + 30, 0, w / 2 + 130, 20);
		g.setColor(fgC);

	}

	private int toXReal(double val) {
		int w = getSize().width;
		int d = (int) (val * ((double) w - 40.0) / som.COUNTRY + 20.0);
		return d;
	}
	
	private int toYReal(double val) {
		int h = getSize().height;
		int d = (int) (val * ((double) h - 200.00) / som.COUNTRY);
		d+=50;
		return d;
	}

}
