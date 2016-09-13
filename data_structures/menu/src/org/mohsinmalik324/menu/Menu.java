package org.mohsinmalik324.menu;

/**
 * The <code>Menu</code> class implements an array of <code>MenuItem</code>
 * objects.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class Menu {
	
	/**
	 * Max numbers of items that can fit in a menu.
	 */
	private static final int MAX_ITEMS = 50;
	private int listSize = 0; // Number of MenuItems in the Menu.
	private MenuItem[] menuItems; // An array of the MenuItems in a Menu.
	private String name = "Menu"; // The name of the menu.
	
	/**
	 * Returns an instance of <code>Menu</code>.
	 * 
	 * <dt>Postcondition:
	 *    <dd>This Menu has been initialized to an empty list of MenuItems.
	 */
	public Menu() {
		menuItems = new MenuItem[MAX_ITEMS];
		listSize = 0;
	}
	
	/**
	 * Set the name of the menu.
	 * 
	 * @param name
	 *    The new name of the menu.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the menu.
	 * 
	 * @return
	 *    The name of the menu.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns a clone of this Menu.
	 * 
	 * @return
	 *    A clone of this Menu.
	 */
	public Object clone() {
		// Create new Menu object.
		Menu clonedMenu = new Menu();
		// Loop through MenuItems in this Menu and copy into the new Menu.
		for(int x = 0; x < size(); x++) {
			MenuItem currentMenuItem = menuItems[x];
			MenuItem menuItem = (MenuItem) currentMenuItem.clone();
			try {
				clonedMenu.addItem(menuItem, x + 1);
			} catch (FullListException e) {
				e.printStackTrace();
			}
		}
		return clonedMenu;
	}
	
	/**
	 * Compares this Menu to another object.
	 * 
	 * @return
	 *    true if both Menus are equal; otherwise false.
	 *    
	 * @param obj
	 *    The object to compare to this Menu.
	 */
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if(obj != null && obj instanceof Menu) {
			Menu compareMenu = (Menu) obj;
			int listSize = size();
			if(compareMenu.size() == listSize) {
				for(int i = 0; i < listSize; i++) {
					MenuItem thisMenuItem = menuItems[i];
					MenuItem compareMenuItem = compareMenu.menuItems[i];
					// If the items aren't equal, the loop is broken and
					// false returned.
					if(!thisMenuItem.equals(compareMenuItem)) {
						break;
					}
					// Checks if loop is on the last iteration.
					if(i == listSize - 1) {
						// If this block is ran, all comparisons ran returned
						// true.
						isEqual = true;
					}
				}
			}
		}
		return isEqual;
	}
	
	/**
	 * Returns the number of MenuItems in the Menu.
	 * 
	 * @return
	 *    The number of MenuItems in the Menu.
	 *    
	 * <dt>Precondition:
	 *    <dd>This Menu object has been instantiated.
	 */
	public int size() {
		return listSize;
	}
	
	/**
	 * Returns the position of the menu item in the list.
	 * 
	 * @param menuItem
	 *    The menu item in which the position will be returned.
	 *    
	 * @return
	 *    The position of the menu item in the list.
	 *    
	 * @exception IllegalArgumentException
	 *    The menu item is not in the list.
	 */
	public int getPosition(MenuItem menuItem) {
		int listSize = size();
		// Loop through array until desired item is found and returns its
		// position.
		for(int i = 0; i < listSize; i++) {
			MenuItem currentMenuItem = menuItems[i];
			if(currentMenuItem.equals(menuItem)) {
				return i + 1;
			}
		}
		throw new IllegalArgumentException("Invalid menu item.");
	}
	
	/**
	 * Adds a MenuItem to the Menu at a certain position.
	 * 
	 * @param menuItem
	 *    The new MenuItem to add to this Menu.
	 *    
	 * @param position
	 *    The position in the Menu where <code>menuItem</code> will be placed.
	 *    
	 * <dt>Precondition:
	 *    <dd>This Menu object has been instantiated, 1 <=
	 *    <code>position</code> <= items_currently_in_list + 1, and
	 *    items_currently_in_list < <code>MAX_ITEMS</code>.
	 *    
	 * <dt>Postcondition:
	 *    <dd>The new MenuItem is now stored at the desired position in the
	 *    Menu. All MenuItems that were originally in positions greater than
	 *    or equal to <code>position</code> are moved back one position.
	 *    
	 * @exception IllegalArgumentException
	 *    Indicates the <code>position</code> is not within valid range.
	 *    
	 * @exception FullListException
	 *    Indicates that there is no more room inside of the Menu to store the
	 *    new MenuItem object.
	 *    
	 * <dt>Note:
	 *    <dd><code>position</code> refers to the position in the Menu and not
	 *    the position inside the array.
	 *    <dd>Inserting an item to position (items_currently_in_list + 1) is
	 *    effectively the same as adding an item to the end of the Menu.
	 */
	public void addItem(MenuItem menuItem, int position)
	  throws FullListException {
		int listSize = size();
		// Check if list is full.
		if(listSize < MAX_ITEMS) {
			// Check precondition.
			if(1 <= position && position <= listSize + 1) {
				if(position == listSize + 1) {
					menuItems[position - 1] = menuItem;
				} else {
					// Stores the MenuItem from the previous loop.
					MenuItem previousMenuItem = menuItems[position - 1];
					// Stores the MenuItem of the current loop.
					MenuItem currentMenuItem = null;
					menuItems[position - 1] = menuItem;
					for(int i = position; i <= listSize; i++) {
						currentMenuItem = menuItems[i];
						menuItems[i] = previousMenuItem;
						previousMenuItem = currentMenuItem;
					}
				}
				this.listSize++;
			} else {
				throw new IllegalArgumentException("Invalid position.");
			}
		} else {
			throw new FullListException("List is full.");
		}
	}
	
	/**
	 * Removes a MenuItem from the Menu at a certain position.
	 * 
	 * @param position
	 *    The position in the Menu where the MenuItem will be removed from.
	 *    
	 * <dt>Precondition:
	 *    <dd>This Menu object has been instantiated and
	 *    1 <= <code>position</code> <= items_currently_in_list.
	 *    
	 * <dt>Postcondition:
	 *    <dd>The MenuItem at the desired position in the Menu has been
	 *    removed. All MenuItems that were originally in positions greater
	 *    than or equal to <code>position</code> are moved forward one
	 *    position.
	 *    
	 * @exception IllegalArgumentException
	 *    Indicates that <code>position</code> is not within the valid range.
	 *    
	 * <dt>Note:
	 *    <dd><code>position</code> refers to the position in the Menu and
	 *    not the position inside the array.
	 */
	public void removeItem(int position) {
		int listSize = size();
		// Check precondition.
		if(1 <= position && position <= listSize) {
			// Check if removing last item in list.
			if(position != listSize) {
				// Stores the menu item of the current loop.
				MenuItem currentMenuItem = menuItems[listSize - 1];
				// Stores the menu item of the next iteration.
				MenuItem nextMenuItem = null;
				for(int i = listSize - 1; i >= position; i--) {
					nextMenuItem = menuItems[i - 1];
					menuItems[i - 1] = currentMenuItem;
					currentMenuItem = nextMenuItem;
				}
			}
			this.listSize--;
		} else {
			throw new IllegalArgumentException("No item at position " +
			  position);
		}
	}
	
	/**
	 * Get the MenuItem at the given position in this Menu object.
	 * 
	 * @param position
	 *    Position of the MenuItem to retrieve.
	 * 
	 * @return
	 *    The MenuItem at the specified position in this Menu object.
	 *    
	 * <dt>Precondition:
	 *    <dd>This Menu object has been instantiated and
	 *    1 <= <code>position</code> <= items_currently_in_list.
	 *    
	 * @exception IllegalArgumentException
	 *    Indicates that <code>position</code> is not within the valid range.
	 */
	public MenuItem getItem(int position) {
		int listSize = size();
		// Check precondition.
		if(1 <= position && position <= listSize) {
			return menuItems[position - 1];
		} else {
			throw new IllegalArgumentException("No item at position " +
			  position);
		}
	}
	
	/**
	 * Return the MenuItem with the given name.
	 * 
	 * @param name
	 *    Name of the item to retrieve.
	 * 
	 * @return
	 *    The MenuItem with the specified name.
	 *    
	 * <dt>Precondition:
	 *    <dd>This Menu object has been instantiated.
	 *    
	 * @exception IllegalArgumentException
	 *    Indicates that the given item does not exist in this Menu.
	 */
	public MenuItem getItemByName(String name) {
		MenuItem menuItem = null;
		int listSize = size();
		// Loop through array until item with the name is found.
		for(int i = 0; i < listSize; i++) {
			MenuItem currentMenuItem = menuItems[i];
			if(currentMenuItem.getName().equalsIgnoreCase(name)) {
				menuItem = currentMenuItem;
				break;
			}
		}
		if(menuItem == null) {
			throw new IllegalArgumentException("No menu item with name: " +
			  name);
		}
		return menuItem;
	}
	
	/**
	 * Prints a neatly formatted table of each item in the Menu on its own
	 * line with its position number.
	 * 
	 * <dt>Precondition:
	 *    <dd>This Menu object has been instantiated.
	 *    
	 * <dt>Postcondition:
	 *    <dd>A neatly formatted table of each MenuItem in the Menu on its own
	 *    line with its position number has been displayed to the user.
	 *    
	 * <dt>Note:
	 *    <dd><code>position</code> refers to the position in the Menu and not
	 *    the position inside the array.
	 */
	public void printAllItems() {
		System.out.println(toString());
	}
	
	/**
	 * Gets the String representation of this Menu object, which is a neatly
	 * formatted table of each MenuItem in the Menu on its own line with its
	 * position number.
	 * 
	 * @return
	 *    The String representation of this Menu object.
	 */
	public String toString() {
		// Format is table based.
		String table = String.format("%-5s%-25s%-75s%8s\n\n", "#", "Name",
		  "Description", "Price");
		int position = 1;
		int listSize = size();
		// Loop through items, create formatted row and add to table string.
		for(int i = 0; i < listSize; i++) {
			MenuItem menuItem = menuItems[i];
			String row = String.format("%-5d%-25s%-75s%8.2f", position++,
			  menuItem.getName(), menuItem.getDescription(),
			  menuItem.getPrice());
			if(i < listSize) {
				row += "\n";
			}
			table += row;
		}
		return table;
	}
	
}