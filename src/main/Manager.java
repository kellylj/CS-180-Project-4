package main;
/**
 * Manager interface for CS 180 Project 4
 * All managers will come from this
 * The methods of this interface will help users initialize anything
 * before user does anything
 *
 * @author Aryan Jain
 * @version 1.0.0
 */
public interface Manager {

	/**
	 * Will be used to initialized anything before user does anything
	 */
	public void init();

	/**
	 * Makes sure everything is saved to a file (if not automatically)
	 */
	public void exit();
}