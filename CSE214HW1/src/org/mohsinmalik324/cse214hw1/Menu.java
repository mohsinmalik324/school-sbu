package org.mohsinmalik324.cse214hw1;

/**
 * The Menu class implements an array of MenuItem objects.
 * 
 * @author: Mohsin Malik
 *    <br>email: mohsin.malik@stonybrook.edu
 *    <br>Stony Brook ID: 110880864
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
	
	public int size() {
		int size = 0;
		for(MenuItem currentMenuItem : menuItems) {
			if(currentMenuItem != null) {
				size++;
			} else {
				break;
			}
		}
		return size;
	}
	
	
	
}