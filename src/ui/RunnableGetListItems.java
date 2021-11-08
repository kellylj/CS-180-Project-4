package ui;

import java.util.ArrayList;

import utils.Listable;

public interface RunnableGetListItems<T extends Listable> {
	public ArrayList<T> getListItems();
}
