/**
 * Manager interface for CS 180 Project 4
 * All managers will come from this
 * The methods of this interface will help users initialize anything
 * before user does anything
 *
 * @author jain455
 * @version 11/7/2021
 */
public interface Manager {
	//Useful for if you need to initialize anything before user does anything.
	public void init();
	//Make sure everything is saved to a file (if not automatically)!!!!
	public void exit();
}