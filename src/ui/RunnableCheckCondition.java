package ui;

/**
 * Used to check if a condition is true or not. 
 * Usually is implemented by an anonymous function.
 * 
 * @author Isaac Fleetwood
 * 
 * @see {@link MenuOption#addVisibilityCondition(RunnableCheckCondition)}
 */
public interface RunnableCheckCondition {
	public boolean check();
}