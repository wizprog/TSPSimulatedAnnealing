package main;

import java.util.Random;

public class SimuliranoKaljenje {
	// FIELDS
	public double temperature = 5;
	public double coolingRate = 0.997;
	public int best = -1;
	public Neuron neurons[];

	public SimuliranoKaljenje(Neuron input[]) {
		this.neurons = input;
	}

	// METHODS

	// Simulated Annealing algorithm to find closest neuron to given coordinates
	public int anneal(double x, double y) {

		int current = getRandomElementIndex();
		this.best = current;

		while (temperature > 1.0) {
			int comparison = getRandomElementIndex();
			if (findProbability(x, y, current, comparison) > Math.random()) {
				current = comparison;
			}
			if (distance(x, y, neurons[current]) < distance(x, y, neurons[this.best])) {
				this.best = current;
			}
			this.temperature *= this.coolingRate;
		}
		return best;
	}

	private int getRandomElementIndex() {
		Random gen = new Random();
		int index = gen.nextInt(this.neurons.length - 1);
		return index;
	}

	public double distance(double x, double y, Neuron n) {
		return Math.sqrt(Math.pow(x - n.wx, 2) + Math.pow(y - n.wy, 2));
	}

	public double findProbability(double x, double y, int current, int comparison) {
		if (distance(x, y, this.neurons[current]) > distance(x, y, this.neurons[comparison])) {
			return 1.0;
		}
		return Math.exp(
				(distance(x, y, this.neurons[current]) - distance(x, y, this.neurons[comparison])) / this.temperature);
	}
}
