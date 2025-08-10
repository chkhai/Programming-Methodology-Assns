
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		entriesList.clear();
		update();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note
	 * that this method does not actually draw the graph, but simply stores the
	 * entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		entriesList.add(entry);
		update();
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of
	 * entries. Your application must call update after calling either clear or
	 * addEntry; update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		drawVerticalLines();
		drawHorizontalLines();
		drawDecades();
		drawEntries();
	}

	/*
	 * This method draws graphs for every single entry from entries array list.
	 */
	private void drawEntries() {
		for (int j = 0; j < entriesList.size(); j++) {
			drawNameGraph(entriesList.get(j), j);
		}

	}

	/*
	 * This method draws lines and labels for a particular person.
	 */
	private void drawNameGraph(NameSurferEntry entry, int j) {
		Color color = selectColor(j);
		for (int i = 0; i < NDECADES - 1; i++) {
			double x1 = i * getWidth() / NDECADES;
			double y1 = (getHeight() - 2 * GRAPH_MARGIN_SIZE) * entry.getRank(i) / MAX_RANK + GRAPH_MARGIN_SIZE;
			double x2 = (i + 1) * getWidth() / NDECADES;
			double y2 = (getHeight() - 2 * GRAPH_MARGIN_SIZE) * entry.getRank(i + 1) / MAX_RANK + GRAPH_MARGIN_SIZE;
			if (entry.getRank(i) == 0) {
				y1 = getHeight() - GRAPH_MARGIN_SIZE;
			}
			if (entry.getRank(i + 1) == 0) {
				y2 = getHeight() - GRAPH_MARGIN_SIZE;
			}
			GLine line = new GLine(x1, y1, x2, y2);
			line.setColor(color);
			add(line);
			if (entry.getRank(i) == 0) {
				GLabel nameLabel = new GLabel(entry.getName() + "*");
				nameLabel.setColor(color);
				add(nameLabel, x1 + 3, y1 - 3);
				if (i == NDECADES - 2) {
					GLabel nameLabel1 = new GLabel(entry.getName() + "*");
					nameLabel1.setColor(color);
					add(nameLabel1, x2 + 3, y2 - 3);
				}
			} else {
				GLabel nameLabel = new GLabel(entry.getName() + "  " + entry.getRank(i));
				nameLabel.setColor(color);
				add(nameLabel, x1 + 3, y1 - 3);
				if (i == NDECADES - 2) {
					GLabel nameLabel1 = new GLabel(entry.getName() + "  " + entry.getRank(i + 1));
					nameLabel1.setColor(color);
					add(nameLabel1, x2 + 3, y2 - 3);
				}
			}
		}
	}

	/*
	 * This method helps us detect the color of the lines for each player.
	 */
	private Color selectColor(int j) {
		Color color = Color.BLACK;
		if (j % 4 == 0) {
			color = Color.BLACK;
		} else if (j % 4 == 1) {
			color = Color.RED;
		} else if (j % 4 == 2) {
			color = Color.BLUE;
		} else if (j % 4 == 3) {
			color = Color.GREEN;
		}
		return color;
	}

	/*
	 * This method draws decade labels in the lower part of the window.
	 */
	private void drawDecades() {
		for (int i = 0; i < NDECADES; i++) {
			double y = getHeight() - 4;
			double x = i * getWidth() / NDECADES + 4;
			if (i >= 0 && i <= 9) {
				add(new GLabel("19" + i + "0", x, y));
			} else if (i == 10) {
				add(new GLabel("2000", x, y));
			}
		}

	}

	/*
	 * This method draws 2 horizontal lines.
	 */
	private void drawHorizontalLines() {
		GLine upperLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		GLine lowerLine = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(upperLine);
		add(lowerLine);
	}

	/*
	 * This method draws 12 vertical lines.
	 */
	private void drawVerticalLines() {
		for (int i = 0; i < 12; i++) {
			GLine line = new GLine(i * getWidth() / 11, 0, i * getWidth() / 11, getHeight());
			add(line);
		}

	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

	private ArrayList<NameSurferEntry> entriesList = new ArrayList<NameSurferEntry>();
}
