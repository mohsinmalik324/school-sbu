package org.mohsinmalik324.cse214hw1;

/**
 * The <code>Menu</code> class implements an array of <code>MenuItem</code>
 * objects.
 * 
 * @author: Mohsin Malik
 *    <dd>email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class Menu {
	
	private static final int MAX_ITEMS = 50; // Max MenuItems in a Menu.
	private MenuItem[] menuItems; // An array of the MenuItems in a Menu.
	
	/**
	 * Returns an instance of <code>Menu</code>.
	 * 
	 * <dt>Postcondition:
	 *    <dd>This Menu has been initialized to an empty list of MenuItems.
	 */
	public Menu() {
		menuItems = new MenuItem[MAX_ITEMS];
		// Null all elements in the array.
		// Note: Null in the array represents the end of the Menu.
		// TODO: Check if I need to do this in the first place.
		for(int i = 0; i < MAX_ITEMS; i++) {
			menuItems[i] = null;
		}
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
		for(int x = 0; x < MAX_ITEMS; x++) {
			MenuItem currentMenuItem = menuItems[x];
			if(currentMenuItem != null) {
				MenuItem menuItem = new MenuItem(currentMenuItem.getName(),
				  currentMenuItem.getDescription(),
				  currentMenuItem.getPrice());
				clonedMenu.menuItems[x] = menuItem;
			} else {
				// Set this index as null to represent the end of the Menu.
				clonedMenu.menuItems[x] = null;
				break;
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
			// The Menu we are comparing to this Menu.
			Menu compareMenu = (Menu) obj;
			// Loop through all MenuItems in both Menus and compare MenuItems.
			for(int i = 0; i < MAX_ITEMS; i++) {
				MenuItem compareMenuItem = compareMenu.menuItems[i];
				MenuItem thisMenuItem = this.menuItems[i];
				// Check each MenuItem to see if it is null. Since null
				// represents the end of the Menu, there is nothing
				// after this point to compare.
				if(compareMenuItem == null && thisMenuItem == null) {
					isEqual = true;
					break;
				}
				// If both MenuItems are not null, we compare them with the
				// equals method. Otherwise, the loop is broken and as both
				// Menus don't end at the same index.
				if(compareMenuItem != null && thisMenuItem != null) {
					// If the MenuItems are not equal, break the loop.
					if(!compareMenuItem.equals(thisMenuItem)) {
						break;
					}
				} else {
					break;
				}
				// Check if this iteration is the last iteration.
				if(i == MAX_ITEMS - 1) {
					// If this block is ran, all MenuItems which have been
					// compared have been found to be equal.
					isEqual = true;
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
		int size = 0;
		// Loop through all MenuItems until end of array or null.
		for(MenuItem currentMenuItem : menuItems) {
			if(currentMenuItem != null) {
				size++;
			} else {
				break;
			}
		}
		return size;
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
	public void addItem(MenuItem menuItem, int position) throws FullListException {
		// TODO: Check if there should be a null check.
		int listSize = size();
		// Check if list is full.
		if(listSize < MAX_ITEMS) {
			// Check precondition.
			if(1 <= position && position <= listSize + 1) {
				if(position == listSize + 1) {
					menuItems[position - 1] = menuItem;
					menuItems[position] = null;
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
			if(position == listSize) {
				menuItems[position - 1] = null;
			} else {
				// TODO: Finish this method.
				MenuItem previousMenuItem = null;
				MenuItem nextMenuItem = null;
				for(int i = listSize - 1; i >= position - 1; i--) {
					
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid position.");
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
		MenuItem menuItem = null;
		int listSize = size();
		// Check precondition.
		if(1 <= position && position <= listSize) {
			menuItem = menuItems[position - 1];
		} else {
			throw new IllegalArgumentException("Invalid position.");
		}
		return menuItem;
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
		for(MenuItem currentMenuItem : menuItems) {
			// TODO: Check if case-sensitive.
			if(currentMenuItem.getName().equalsIgnoreCase(name)) {
				menuItem = currentMenuItem;
			}
		}
		if(menuItem == null) {
			throw new IllegalArgumentException("MenuItem does not exist.");
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
		System.out.printf("%-5s%-20s%-50s%8s\n\n", "#", "Name", "Description", "Price");
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
		String table = "";
		int position = 1;
		for(MenuItem menuItem : menuItems) {
			System.out.printf("%-5d%-20s%-47s%8-f\n", position++, menuItem.getName(),
			  menuItem.getDescription(), menuItem.getPrice());
		}
		return table;
	}
	
}