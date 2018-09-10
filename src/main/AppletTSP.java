package main;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.LinkedList;
import java.util.Timer;

public class AppletTSP extends Applet implements Runnable {
	
	public Image homeI, offscreen;
	public int imagewidth, imageheight;

//boje
	public static final Color bkC = new Color(0x09600);
	public static final Color bk2C = new Color(0x000000);
	public static final Color lnC = new Color(0xffc900);
	public static final Color ln2C = new Color(0xcccc00);
	public static final Color fgC = new Color(0xffffff);

// labele
	public Label l, l1, l2;

//fontovi
	Font mF = new Font("Courier", Font.BOLD, 14);
	Font sF = new Font("Courier", Font.BOLD, 8);

	int counter = 0;
	SOM som = new SOM();
	public Thread animator = null;
	public boolean please_stop = false;

	public void init() {
		this.setName("TSP Simulated Annealing");
		this.setSize(500,500);
		som.kohonenInit();

		Panel p = new Panel();
		l = new Label("Broj gradova : " + som.brojGradova);
		p.add(l);
		l2 = new Label("Ukupno rastojanje:");
		p.add(l2);
		l1 = new Label();
		p.add(l1);

		l.setBackground(bkC);
		l1.setBackground(bkC);
		l2.setBackground(bkC);
		l2.setFont(mF);
		l.setFont(mF);
		
		l1 = new Label();
		add(l1);

		p.setBackground(Color.GRAY);
		add(p,BorderLayout.NORTH);
		


	}

	@Override
	public void run() {
		int count = 0;

		while (!please_stop) {

			counter++;

			som.Trening();

			count = (count++) % 10; // na stakih 10 iteracija na se prikaze
			if (count == 0) {

				paint(this.getGraphics());

				int brojac = 0;
				for (int i = 0; i < som.grad.length; i++) {
					for (int j = 0; j < som.neuroni.length; j++) {
						if (som.grad[i].getX() == som.neuroni[j].wx && som.grad[i].getY() == som.neuroni[j].wy) {
							brojac++;
						}
					}
				}

				if (brojac == som.brojGradova) {
					please_stop = true;
					l1.setText("" + som.UkupnaDistanca());
				}

				// l1.setText(""+som.UkupnaDistanca());

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
				;
			}

		}

		animator = null;

	}

	
	
	public void paintLeft(Graphics g) {
				int w = getSize().width;
		int h = getSize().height;

		g.setFont(mF);

		// CLEAR ALL
		g.setColor(bkC);
		g.fillRect(0, 0, w, h);
		

		// DRAW PATH
		g.setColor(lnC);
		for (int i = 0; i < som.brNerona; i++) {
			g.drawLine(toXReal(som.neuroni[i].wx), toYReal(som.neuroni[i].wy),
					toXReal(som.neuroni[(i + 1) % som.brNerona].wx), toYReal(som.neuroni[(i + 1) % som.brNerona].wy));
			// g.drawString(""+i+"-"+(gn[i].update*100/counter)+"%",toXReal(gn[i].wx),toYReal(gn[i].wy));
		}

		g.setColor(fgC);

	  // DRAW CITYS
		for (int i = 0; i < som.brojGradova; i++) {
			g.fillOval(toXReal(som.grad[i].getX()) - 4, toYReal(som.grad[i].getY()) - 4, 9, 9);
			g.setColor(g.getColor().BLACK);
			g.drawString("" + i + "", toXReal(som.grad[i].getX()), toYReal(som.grad[i].getY()) - 8);
		}
	}

	public void paint(Graphics g) {
		
		this.setSize(500, 500);
		int w = getSize().width;
		int h = getSize().height;

		this.setBackground(bkC);

		if ((offscreen == null) || ((imagewidth != w) || (imageheight != h))) {
			offscreen = this.createImage(w, h);
			imagewidth = w;
			imageheight = h;
		}
		
		Rectangle clip = new Rectangle(0, 0, toXReal(som.COUNTRY), toYReal(som.COUNTRY));

		
		Graphics goff = offscreen.getGraphics();
		goff.clipRect(clip.x, clip.y, clip.width, clip.height);
		Graphics g1 = this.getGraphics();
		
		g1.clipRect(clip.x, clip.y, clip.width, clip.height);

		paintLeft(goff);
		g1.drawImage(offscreen, 0, 0, this);

		clip = null;
		goff = null;
		g1 = null;
		System.gc();

		// CLEAR ALL
		g.setColor(bkC);
		g.fillRect(w / 2 + 30, 0, w / 2 + 130, 20);
		g.setColor(fgC);

		

	}
	
	

	private int toXReal(double val) {
		int w = getSize().width;
		return (int) (val * ((double) w / 2.0 - 50.0) / som.COUNTRY + 25.0);
	}

	private int toYReal(double val) {
		int h = getSize().height;
		return (int) (val * ((double) h - 50.0) / som.COUNTRY + 25.0);
	}

	// Start the animation
	public void start() {
		animator = new Thread(this);

		animator.start();

	}

	public void stop() {
		if (animator != null)
			animator.stop();
		animator = null;
	}

	public boolean mouseDown(Event e, int x, int y) {

		// if running, stop it. Otherwise, start it.
		if (animator != null) {
			please_stop = true;

		} else {
			please_stop = false;
			animator = new Thread(this);

			som.kohonenInit();
			animator.start();
		}
		return true;
	}

}
