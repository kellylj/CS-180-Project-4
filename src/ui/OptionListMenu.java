package ui;

import java.util.ArrayList;
import java.util.List;

import main.LearningManagementSystem;
import main.Listable;
import main.Quiz;
import main.QuizManager;
import main.UIManager;

/**
 * {@link OptionMenu} for selecting an item from a arraylist.
 * <p>
 * Uses pages to show only a certain number of items at a time,
 * and allows the user to change the page to show different items.
 * <p>
 * Item type must implement {@link Listable} in order to be used.
 * <p>
 * See {@link OptionListMenu#OptionListMemu(LearningManagementSystem, RunnableSelectListItem)} for creating the menu.
 * <p>
 * Runs {@link RunnableSelectListItem#selectItem(T)} whenever the item is selected.
 * <p>
 * Note: It is possible an item isn't selected if a user chooses "Exit"
 *
 * @param <T> the type of the item in the list.
 * @author Isaac Fleetwood
 * @version 1.0.0
 */
public class OptionListMenu<T extends Listable> extends OptionMenuWithResult<T> {

    private static final int AMT_ITEMS_PER_PAGE = 5;

    List<T> items;
    RunnableGetListItems<T> runnableGetListItems;
    RunnableSelectListItem<T> runnableSelectListItem;
    int page = 0;

    /**
     * Used to create the quiz list menu.
     *
     * @param lms                    Used for accessing {@link QuizManager} to get the list of quizzes.
     * @param runnableSelectListItem Used as a callback function for whenever a quiz is selected.
     *                               {@link RunnableSelectListItem#selectQuiz(Quiz)} is ran whenever a quiz is selected.
     * @see RunnableSelectListItem
     */
    public OptionListMenu(UIManager uiManager) {
        super(uiManager);
        this.page = 0;
        this.items = new ArrayList<T>();
    }

    public OptionListMenu<T> setItems(List<T> listItems) {
        this.items = listItems;
        return this;
    }

    public OptionListMenu<T> onRequestListItems(RunnableGetListItems<T> runnable) {
        this.runnableGetListItems = runnable;
        return this;
    }


    public OptionListMenu<T> onSelectListItem(RunnableSelectListItem<T> runnable) {
        this.runnableSelectListItem = runnable;
        return this;
    }

    @Override
    public void start() {
        super.start();
        if (runnableGetListItems == null)
            return;
        this.items = runnableGetListItems.getListItems();
    }

    @Override
    public void runMenu() {

        if (items.size() == 0) {
            System.out.println("No options are available.");
            System.out.println("Press Enter to go back.");
            uiManager.getScanner().nextLine();
            this.menuState = MenuState.CLOSE;
            return;
        }

        // Clear options so that all options are new.
        this.options.clear();

        // Add "Previous Page" option, which causes the page to decrease.
        this.addOption((new MenuOption("Previous Page"))
            .addVisibilityCondition(() -> {
        		return this.page > 0;
        	})
            .onSelect(() -> {
	            this.page -= 1;
	            return MenuState.RESTART;
	        })
        );

        for (int i = 0; i < AMT_ITEMS_PER_PAGE; i++) {
            int j = page * AMT_ITEMS_PER_PAGE + i;
            if (j < 0 || j >= items.size())
                continue;

            final T item = items.get(j);

            this.addOption((new MenuOption(item.getListName())).onSelect(() -> {
                this.setResult(item);
                return MenuState.CLOSE;
            }));
        }

        // Add Next Page option, which increases the page, but only if there's more quizzes to see.
        this.addOption((new MenuOption("Next Page"))
            .addVisibilityCondition(() -> {
        		return items.size() > (page + 1) * AMT_ITEMS_PER_PAGE;
        	})
            .onSelect(() -> {
	            this.page += 1;
	            return MenuState.RESTART;
	        })
        );

        // Add Exit option, which closes the menu.
        this.addOption((new MenuOption("Exit")).onSelect(() -> {
            this.setResult(null);
            return MenuState.CLOSE;
        }));

        // Now that all the options are added, run the menu.
        super.runMenu();

        // If the menu should be restart (page was changed), return, which will cause the menu to rerun.
        if (menuState.equals(MenuState.RESTART))
            return;
        // If the result is null, return, which will cause the page to exit without doing anything.
        if (this.getResult() == null)
            return;

        // Otherwise, a quiz was selected, so run the callback with that quiz.
        menuState = this.runnableSelectListItem.selectItem(this.getResult());
    }

}
