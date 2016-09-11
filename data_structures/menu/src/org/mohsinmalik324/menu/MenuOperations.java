package org.mohsinmalik324.menu;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * The <code>MenuOperations</code> class implements the main method which
 * allows user interaction with this program.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class MenuOperations {
	
	/**
	 * The text which displays on the title.
	 */
	private static final String TITLE = "Menu";
	/**
	 * The names of the columns when listing items in a table.
	 */
	private static String[] columnNames = {"#", "Name", "Description",
	  "Price"};
	private static Menu menu = new Menu();
	private static Menu order = new Menu();
	private static boolean menuLoop = true;
	
	static {
		order.setName("Order");
	}
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *    Arguments passed through to the program.
	 */
	public static void main(String[] args) {
		startMenuLoop();
	}
	
	/**
	 * Runs the main menu loop to allow user input.
	 */
	public static void startMenuLoop() {
		menuLoop = true;
		// Continue looping until the user exits the program.
		while(menuLoop) {
			String operation = getInputString("Main menu:\n\nA) Add Item\n"
			  + "G) Get Item\nR) Remove Item\nP) Print All Items\n"
			  + "S) Size\nD) Update description\nC) Update price\n"
			  + "O) Add to order\nI) Remove from order\nV) View order\n"
			  + "Q) Quit\n\nSelect an operation:");
			if(operation == null || operation.equalsIgnoreCase("q")) {
				menuLoop = false;
			} else if(operation.equalsIgnoreCase("a")) {
				String name = getInputString("Enter the name:");
				String desc = getInputString("Enter the description:");
				try {
					double price = getInputDouble("Enter the price:");
					int position = getInputInt("Enter the position:");
					MenuItem menuItem = new MenuItem(name, desc, price);
					menu.addItem(menuItem, position);
					displayMessage("Added \"" + name + ": " + desc +
					  "\" for " + formatPrice(price) + " at position " +
					  position);
				} catch (FullListException e) {
					displayMessage(e.getMessage());
				} catch(NumberFormatException e) {
					displayMessage(e.getMessage());
				} catch(IllegalArgumentException e) {
					displayMessage(e.getMessage());
				}
			} else if(operation.equalsIgnoreCase("g")) {
				try {
					int position = getInputInt("Enter the position:");
					MenuItem menuItem = menu.getItem(position);
					displayTable(menuItem, position);
				} catch(NumberFormatException e) {
					displayMessage(e.getMessage());
				} catch(IllegalArgumentException e) {
					displayMessage(e.getMessage());
				}
			} else if(operation.equalsIgnoreCase("r")) {
				String name = getInputString("Enter the name:");
				try {
					MenuItem menuItem = menu.getItemByName(name);
					int position = menu.getPosition(menuItem);
					menu.removeItem(position);
					displayMessage("Removed \"" + name + "\"");
				} catch(IllegalArgumentException e) {
					displayMessage(e.getMessage());
				}
			} else if(operation.equalsIgnoreCase("p")) {
				displayTable(menu);
			} else if(operation.equalsIgnoreCase("s")) {
				displayMessage("There are " + menu.size() +
				  " item(s) in the menu");
			} else if(operation.equalsIgnoreCase("d")) {
				try {
					int position = getInputInt("Enter the position:");
					String desc = getInputString("Enter the new description:");
					MenuItem menuItem = menu.getItem(position);
					menuItem.setDescription(desc);
					displayMessage("New description set");
				} catch(NumberFormatException e) {
					displayMessage(e.getMessage());
				} catch(IllegalArgumentException e) {
					displayMessage(e.getMessage());
				}
			} else if(operation.equalsIgnoreCase("c")) {
				String name = getInputString("Enter the name of the item:");
				try {
					double price = getInputDouble("Enter the new price:");
					MenuItem menuItem = menu.getItemByName(name);
					menuItem.setPrice(price);
					displayMessage("Changed the price of \"" + name + "\" to "
					  + formatPrice(price));
				} catch(NumberFormatException e) {
					displayMessage(e.getMessage());
				} catch(IllegalArgumentException e) {
					displayMessage(e.getMessage());
				}
			} else if(operation.equalsIgnoreCase("o")) {
				try {
					int position = getInputInt("Enter the position of item "
					  + "to add to order:");
					MenuItem menuItem = menu.getItem(position);
					MenuItem clonedMenuItem = (MenuItem) menuItem.clone();
					order.addItem(clonedMenuItem, order.size() + 1);
					displayMessage("Added \"" + clonedMenuItem.getName() + "\" to order");
				} catch(NumberFormatException e) {
					displayMessage(e.getMessage());
				} catch (FullListException e) {
					displayMessage(e.getMessage());
				} catch(IllegalArgumentException e) {
					displayMessage(e.getMessage());
				}
			} else if(operation.equalsIgnoreCase("i")) {
				try {
					int position = getInputInt("Enter the position:");
					MenuItem menuItem = order.getItem(position);
					order.removeItem(position);
					displayMessage("Removed \"" + menuItem.getName() + "\" from order");
				} catch(NumberFormatException e) {
					displayMessage(e.getMessage());
				} catch(IllegalArgumentException e) {
					displayMessage(e.getMessage());
				}
			} else if(operation.equalsIgnoreCase("v")) {
				displayTable(order);
			} else {
				displayMessage("Unknown  operation \"" + operation + "\"");
			}
		}
	}
	
	/**
	 * Stops the main menu loop. User will not be prompted for input until
	 * startMenuLoop() is called.
	 */
	public static void stopMenuLoop() {
		menuLoop = false;
	}
	
	public static void displayTable(MenuItem menuItem, int position) {
		Object[][] data = {{Integer.toString(position), menuItem.getName(),
		  menuItem.getDescription(), menuItem.getPrice()}};
		displayTable(data);
	}
	
	public static void displayTable(Menu menu) {
		int listSize = menu.size();
		Object[][] data = new Object[listSize][4];
		for(int i = 0; i < listSize; i++) {
			MenuItem currentMenuItem = menu.getItem(i + 1);
			data[i][0] = i + 1;
			data[i][1] = currentMenuItem.getName();
			data[i][2] = currentMenuItem.getDescription();
			data[i][3] = currentMenuItem.getPrice();
		}
		displayTable(data);
	}
	
	public static void displayTable(Object[][] data) {
		// Create a new table with non-editable cells.
		JTable table = new JTable(data, columnNames) {
			
			private static final long serialVersionUID = 110431749943291610L;
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
		// Create a new frame. When the user exits the frame, the table is
		// disposed of and the menu loop starts again.
		JFrame tableFrame = new JFrame();
		tableFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent event) {
				event.getWindow().dispose();
				startMenuLoop();
			}
			
		});
		tableFrame.add(new JScrollPane(table));
		tableFrame.setSize(500, 200);
		// Center the table frame.
		tableFrame.setLocationRelativeTo(null);
		tableFrame.setVisible(true);
		// Temporarily stop the menu loop to allow user to view the table.
		stopMenuLoop();
	}
	
	public static String getInputString(String message) {
		return JOptionPane.showInputDialog(message);
	}
	
	public static int getInputInt(String message) throws NumberFormatException {
		try {
			return Integer.valueOf(getInputString(message));
		} catch(NumberFormatException e) {
			throw new NumberFormatException("Input must be a number.");
		}
	}
	
	public static double getInputDouble(String message) throws NumberFormatException {
		try {
			return Double.valueOf(getInputString(message));
		} catch(NumberFormatException e) {
			throw new NumberFormatException("Input must be a number.");
		}
	}
	
	public static void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message, TITLE, JOptionPane.PLAIN_MESSAGE);
	}
	
	public static String formatPrice(double price) {
		return String.format("$%.2f", price);
	}
	
}