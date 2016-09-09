package org.mohsinmalik324.menu;

/**
 * The <code>MenuItem</code> class implements an instance of the
 * <code>MenuItem</code> object, which represents an item on a menu.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class MenuItem {
	
	private String name;
	private String description;
	private double price;
	
	/**
	 * Returns an instance of <code>MenuItem</code>.
	 * 
	 * @param name
	 *    The name of this menu item.
	 *    
	 * @param description
	 *    A description of this menu item.
	 *    
	 * @param price
	 *    The price of this menu item.
	 *    
	 * <dt>Precondition:
	 *    <dd><code>price</code> must be greater than 0.
	 *    
	 * @exception IllegalArgumentException
	 *    <code>price</code> was not greater than 0.
	 */
	public MenuItem(String name, String description, double price) {
		setName(name);
		setDescription(description);
		setPrice(price);
	}
	
	
	/**
	 * Sets the name of this menu item.
	 * 
	 * @param name
	 *    The new name of this menu item.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the description of this menu item.
	 * 
	 * @param description
	 *    The new description of this menu item.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Sets the price of this menu item.
	 * 
	 * @param price
	 *    The new price of this menu item.
	 *    
	 * <dt>Precondition:
	 *    <dd><code>price</code> must be greater than 0.
	 *    
	 * @exception IllegalArgumentException
	 *    <code>price</code> was not greater than 0.
	 */
	public void setPrice(double price) {
		if(price > 0) {
			this.price = price;
		} else {
			throw new IllegalArgumentException("Invalid price.");
		}
	}
	
	/**
	 * Returns the name of this menu item.
	 * 
	 * @return
	 *    Name of this menu item.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the description of this menu item.
	 * 
	 * @return
	 *    Description of this menu item.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the price of this menu item.
	 * 
	 * @return
	 *    Price of this menu item.
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Compares this menu item to another object.
	 * 
	 * @return
	 *    true if both items are equal; otherwise false.
	 *    
	 * @param obj
	 *    The object to compare to this menu item.
	 */
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if(obj != null && obj instanceof MenuItem) {
			// The menu item we are comparing to this menu item.
			MenuItem compareMenuItem = (MenuItem) obj;
			// Compare components of each MenuItem.
			if(compareMenuItem.getName() == name &&
			  compareMenuItem.getDescription() == description &&
			  compareMenuItem.getPrice() == price) {
				isEqual = true;
			}
		}
		return isEqual;
	}
	
	/**
	 * Returns a clone of this MenuItem.
	 * 
	 * @return
	 *    A clone of this MenuItem.
	 */
	public Object clone() {
		return new MenuItem(name, description, price);
	}
	
	public String toString() {
		return "";
	}
	
}