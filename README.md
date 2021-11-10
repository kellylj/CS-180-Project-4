# CS-180-Project-4

# Classes Summary
Todo: Add our classes to here and explain what they do.

## Main Class
### LearningManagementSystem
(Abbreviated LMS for short) 
This is the main class that ties all of the managers together. It contains the `main()` method. Upon running, it instantiates all of the following managers:

- UserManager
- UserFileManager
- QuizManager
- QuizFileManager
- UIManager

Then, after instantiating them, it runs the `init()` method on all of them to notify them that the program is initializing. Once all the managers have initialized, the UIManager is ran, which opens the UI and prompts the user for input. Once the UI has exited, LMS then runs the `exit()` method on all of the managers to notify them that the program is exiting.
The LMS class is the glue that ties all of the parts of the program together. It holds all of the managers, lets them communicate with each other, and notifies them about the state of the overall program.

## Manager Classses
Classes that each handle a vital function of the program.

### Manager
Todo Explain Manager interface

### UIManager
The manager that is responsible for the User Interface (UI). It uses the User Interface Menu System to create menus that the user then interacts with. In “init()” it creates all of the menus, which sets up the structure of the UI, and then in `run()` it runs the start menu, which is used as the entry point to the rest of the UI.

### QuizManager

### other managers

## Datastructure Classes
Classes used for storing data that don't inherently do anything by themselves.

### Quiz

### User

#### Teacher

#### Student

### ... etc

## User Interface Menu System:
Each of the following classes are part of the User Interface Menu System. They all work together to form an abstract menu system that is then used to make the User Interface in UIManager.
Much of the system uses lambdas to pass callback functions to the menus. This allows for defining code during object creation that will run only after certain condition happen.

### Menu
A basic menu. All menus extend this class. Contains an `menu.open()` that is ran to open a menu. Requires all subclasses to implement `menu.runMenu()` to run their respective menu.

### MenuState
An enum representing the state of the Menu. Is used in Menu to determine if the Menu should continue running (`MenuState.RESTART`) or close (`MenuState.CLOSE`).

### InputMenu
A menu that prompts the user to enter input. Once all required input is received, it will run the callback function `onInputFinish(()->{})`.

### OptionMenu
A menu that prompts the user to select an option. When an option is selected, it will run the callback function `onSelect(()->{})` for that respective option. (See MenuOption)

### MenuQuickInput
A smaller input menu that only allows for one input question. The user input from that one question is then stored in a field and is accessible to any method with an instance of this menu.

### OptionMenuWithResult<T>
An OptionMenu that contains a result field (of parameterized type T) with accessor and mutator methods for it. Generally when the callback function `onSelect(()->{})` is ran (via OptionMenu running it), it will set the result. Then, once the menu exits, the surrounding code can access the result.
The reason this is desired is because it reduces the lambdas from nesting code too deeply.

### OptionMenuYesNo
An OptionMenuWithResult that is specifically a result of a boolean.
It automatically adds the options Yes and No, and sets the boolean result accordingly. (true for Yes, false for No)
It also adds functions for checking is the result was yes or no.

### OptionListMenu<T>
An OptionMenuWithResult that contains a list of options (of parameterized type T), and uses pages to seperate them out into easier to read options. Once an option in the list is selected, the RunnableSelectListItem.selectItem() callback function is ran with the item that is selected. The user is also given the choice to exit without making a selection.

### MenuInput
A basic input question that prompts the user to enter information. Used by InputMenu

### MenuInputInt
A more specific input question that only allows integer inputs. Extends MenuInput and is used by InputMenu.

### MenuInputOptions
A input that only allows you to select out of a list of specific options. Extends MenuInput and is used by InputMenu.

### MenuOption
A menu option that can be selected. When selected, will run the `onSelect(()->{})` callback function. Used by OptionMenu.

## Runnables
The following classes are used in the User Interface Menu System to define callback functions. They contain one method that is ran whenever the callback function is invoked.

### RunnableCheckCondition
Ran to check if a condition is true. Used by `MenuOption.setVisibilityCondition()`

### RunnableGetListItems
Ran when the `OptionListMenu` is opened to get the items in the menu (since they can be dynamic).

### RunnableInputFinish
Ran with a parameter of the map of the inputs when the inputs are finished being prompted. Used by InputMenu

### RunnableSelectListItem
Ran with a parameter of the selected item whenever a list item is selected in OptionListMenu.

### RunnableSelectOption
Ran whenever an option is selected in OptionMenu

## Utility Classes

### ANSICodes
This class contains ANSI codes that are used for modifying text (like making it bold, underlines, or a different color). Also, it contains some commands for clearing the terminal and moving the cursor. Then, it also contains a utility function for stripping the ANSI codes from a piece of text, which is used for comparing output in test cases.

### NumberUtils
A class containing number utility functions that have no specific other place and are used through the program. Specifically, it contains a function to check if a number is an integer or not.

### Listable
All classes that can be used in OptionListMenu have to implement this interface. It adds a function for how to represent the object in the list (`getListName()`).