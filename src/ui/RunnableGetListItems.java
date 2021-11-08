package ui;

import java.util.ArrayList;

import utils.Listable;

/**
 * 
 * @author linux
 *
 * @param <T>
 * TODO T extends Listable
 */
public interface RunnableGetListItems<T> {
	public ArrayList<T> getListItems();
}
