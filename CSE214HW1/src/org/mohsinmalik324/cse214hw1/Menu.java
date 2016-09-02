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
				clonedMenu.menuItems[x] = null;
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
			for(int x = 0; x < MAX_ITEMS; x++) {
				MenuItem compareMenuItem = compareMenu.menuItems[x];
				MenuItem thisMenuItem = this.menuItems[x];
				// Check each MenuItem to see if it is null. If both are null
				// they are equal. If one is not null, the equals method is
				// ran from the not null MenuItem to compare.
				if(compareMenuItem != null) {
					if(!compareMenuItem.equals(thisMenuItem)) {
						break;
					}
				}
				if(thisMenuItem != null) {
					if(!thisMenuItem.equals(compareMenuItem)) {
						break;
					}
				}
				// Check if this iteration is the last iteration.
				if(x == MAX_ITEMS - 1) {
					// If this block is ran, all MenuItems which have been
					// compared have been found to be equal.
					isEqual = true;
				}
			}
		}
		return isEqual;
	}
	
}