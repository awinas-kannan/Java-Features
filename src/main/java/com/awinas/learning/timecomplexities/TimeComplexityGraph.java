package com.awinas.learning.timecomplexities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TimeComplexityGraph extends JPanel {

	private ArrayList<Integer> inputSizes;
	private ArrayList<Long> constantTime;
	private ArrayList<Long> logTime;
	private ArrayList<Long> linearTime;
	private ArrayList<Long> linearLogTime;
	private ArrayList<Long> quadraticTime;

	public TimeComplexityGraph() {
		inputSizes = new ArrayList<>();
		constantTime = new ArrayList<>();
		logTime = new ArrayList<>();
		linearTime = new ArrayList<>();
		linearLogTime = new ArrayList<>();
		quadraticTime = new ArrayList<>();

		// Simulate different input sizes
		for (int n = 1; n <= 10; n += 2) {
			inputSizes.add(n);

			constantTime.add(1L); // O(1)
			logTime.add((long) (Math.log(n) / Math.log(2))); // O(log n)
			linearTime.add((long) n); // O(n)
			linearLogTime.add((long) (n * (Math.log(n) / Math.log(2)))); // O(n log n)
			quadraticTime.add((long) n * n); // O(n^2)
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));

		int width = getWidth();
		int height = getHeight();

		// Draw axes
		g2.drawLine(50, height - 50, width - 50, height - 50); // X-axis
		g2.drawLine(50, height - 50, 50, 50); // Y-axis

		// Find max Y value for scaling
		long maxY = Math.max(quadraticTime.get(quadraticTime.size() - 1), linearLogTime.get(linearLogTime.size() - 1));

		// Plot each complexity
		plotLine(g2, inputSizes, constantTime, width, height, maxY, Color.RED, "O(1)");
		plotLine(g2, inputSizes, logTime, width, height, maxY, Color.BLUE, "O(log n)");
		plotLine(g2, inputSizes, linearTime, width, height, maxY, Color.GREEN, "O(n)");
		plotLine(g2, inputSizes, linearLogTime, width, height, maxY, Color.MAGENTA, "O(n log n)");
		plotLine(g2, inputSizes, quadraticTime, width, height, maxY, Color.ORANGE, "O(n^2)");
	}

	private void plotLine(Graphics2D g2, ArrayList<Integer> xData, ArrayList<Long> yData, int width, int height,
			long maxY, Color color, String label) {
		g2.setColor(color);

		int prevX = -1, prevY = -1;

		for (int i = 0; i < xData.size(); i++) {
			int x = 50 + (int) ((double) (width - 100) * i / (xData.size() - 1));
			int y = height - 50 - (int) ((double) (height - 100) * yData.get(i) / maxY);

			if (prevX != -1) {
				g2.drawLine(prevX, prevY, x, y);
			}

			prevX = x;
			prevY = y;
		}

		// Draw label
		g2.setColor(color);
		g2.drawString(label, prevX + 5, prevY);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Time Complexity Graph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 600);
		frame.add(new TimeComplexityGraph());
		frame.setVisible(true);
	}
}
