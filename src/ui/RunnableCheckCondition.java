package ui;

/**
 * Lambda used to check if a condition is true or not.
 * Returns a boolean based on whether the condition is true or not.
 * 
 * @author Isaac Fleetwood
 * @version 1.0.0
 * 
 * @see {@link MenuOption#addVisibilityCondition(RunnableCheckCondition)}
 */
public interface RunnableCheckCondition {
	public boolean check();
}