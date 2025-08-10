
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		graph = new NameSurferGraph();
		add(graph);
		createButtons();
		addActionListeners();
		dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so
	 * you will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nameField || e.getSource() == graphButton) {
			String input = Character.toUpperCase(nameField.getText().charAt(0))
					+ nameField.getText().substring(1).toLowerCase();
			drawGraph(input);
		} else {
			graph.clear();
		}
	}

	/*
	 * This method checks if input is in names data base. If it's there, we draw
	 * the graph.
	 */
	private void drawGraph(String input) {
		NameSurferEntry entry = dataBase.findEntry(input);
		println(entry);
		if (entry != null) {
			graph.addEntry(entry);
			graph.update();
		}
		nameField.setText("");
	}

	/*
	 * This method creates all buttons and fields which are necessary for
	 * program.
	 */
	private void createButtons() {
		add(new JLabel("Name"), SOUTH);
		nameField = new JTextField(10);
		graphButton = new JButton("Graph");
		clearButton = new JButton("Clear");
		nameField.addActionListener(this);
		add(nameField, SOUTH);
		add(graphButton, SOUTH);
		add(clearButton, SOUTH);
	}

	private JButton graphButton;
	private JTextField nameField;
	private JButton clearButton;
	private NameSurferDataBase dataBase;
	private NameSurferGraph graph;
}
