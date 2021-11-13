package main;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Queue;

import ui.InputMenu;
import ui.Menu;
import ui.MenuOption;
import ui.MenuQuickInput;
import ui.OptionListMenu;
import ui.MenuState;
import ui.OptionMenu;
import ui.OptionMenuYesNo;
import utils.ANSICodes;

/**
 * The manager for the UI of the application.
 * <p>
 * Is instantiated inside of {@link LearningManagementSystem} 
 * and should not be instantiated anywhere else.
 * <p>
 * Initializes all the {@link Menu}s that are used in the UI,
 * and opens the Start Menu ({@link #MENU_START}) whenever {@link #run()} is called.
 * 
 * @authors Isaac Fleetwood
 * @authors Aryan Jain
 * @see LearningManagementSystem
 * @see Manager
 */
public class UIManager implements Manager {

	private LearningManagementSystem lms;
	
	private Scanner scanner;
	private User currentUser;
	
	private Menu MENU_START;
	private Menu MENU_LOGIN;
	private Menu MENU_CREATE_USER;

	private Menu MENU_MAIN;
	private Menu MENU_QUIZ_LIST_INFO; // Quiz List for Show Quiz Info
	private Menu MENU_QUIZ_LIST_TAKE; // Quiz List for Take Quiz
	private Menu MENU_USER_SETTINGS;
	
	private Menu MENU_TEACHER;
	private Menu MENU_QUIZ_LIST_MODIFY; // Quiz List for Modify Quiz
	private Menu MENU_ADD_QUIZ;
	
	/**
	 * Constructor for the UIManager.
	 * <p>
	 * All initialization is done in {@link #init()}, so the
	 * constructor solely sets the {@link #lms} field.
	 * 
	 * @param lms The LearningManagementSystem instance used for accessing the rest of the managers.
	 */
	public UIManager(LearningManagementSystem lms) {
		this.lms = lms;
	}

	/**
	 * Initializes all the static {@link Menu}'s and all of their relevant headers, options, inputs, and callbacks.
	 * <p>
	 * Contains most of the relevant structure of the UI of the application.
	 * <p>
	 * Uses {@link InputMenu}, {@link OptionMenu}, and other menus to create the UI.
	 * <p>
	 * See {@link #run()} for the entry point to the UI.
	 * 
	 */
	@Override
	public void init() {
		this.scanner = new Scanner(System.in);
		MENU_START = (new OptionMenu(this))
			.addHeading("Welcome to the Learning Management System!")
			.addSubheading("Please select one of the following options:")
			.addOption((new MenuOption("Login")).onSelect(() -> {
				MENU_LOGIN.open();
				return MenuState.RESTART;
			}))
			.addOption((new MenuOption("Create User")).onSelect(() -> {
				MENU_CREATE_USER.open();
				return MenuState.RESTART;
			}))
			.addOption((new MenuOption("Exit")).onSelect(() -> {
				System.out.println("Okay! Bye!");
				return MenuState.CLOSE;
			}));
		
		MENU_CREATE_USER = (new InputMenu(this))
			.addHeading("Creating a new user")
			.addSubheading("Please answer the following questions")
			.addInput("What is your name?", "Name")
			.addInput("What username do you want?", "Username")
			.addInput("What password do you want?", "Password")
			.addInputWithOptions(
				"Will you be a teacher or a student?", 
				new String[] { "Teacher", "Student" },
				"User Type"
			)
			.addValidationRequest()
			.onInputFinish((Map<String, String> values) -> {
				String name = values.get("Name");
				String username = values.get("Username");
				String password = values.get("Password");
				String userType = values.get("User Type");
				User user = lms.getUserManager().getUser(username);
				
				if(user != null) {
					OptionMenuYesNo retryMenu = new OptionMenuYesNo(this);
					retryMenu.addHeading("A user with those details already exists.");
					retryMenu.addSubheading("Would you like to try creating a new user again?");
					retryMenu.open();
					if(retryMenu.resultWasNo()) {
						System.out.println("Cancelling creation of a new user.");
						return MenuState.CLOSE;
					} else {
						System.out.println("Restarting...");
						return MenuState.RESTART;
					}
				}
				
				switch(userType) {
					case "Teacher":
						user = new Teacher(0, name, username, password);
						break;
					case "Student":
						user = new Student(0, name, username, password);
						break;
				}
				lms.getUserManager().addUser(user);
				System.out.println("Succesfully created the user.");
				return MenuState.CLOSE;
			});
		
		MENU_LOGIN = (new InputMenu(this))
			.addHeading("Logging into the Learning Management System.")
			.addSubheading("Please enter your login details.")
			.addInput("Username: ", "Username")
			.addInput("Password: ", "Password")
			.onInputFinish((Map<String, String> values) -> {
				String username = values.get("Username");
				String password = values.get("Password");
				
				// TODO UserManager - Authenticate username password and determine if valid
				// lms.getUserManager().authenticateUser(username, password):
				boolean correctUsernamePassword = lms.getUserManager().authenticator(username, password);
				if(correctUsernamePassword) {
					// TODO UserManager get User from Username and Password
					User user = lms.getUserManager().getUser(username);
					// User user = new User(0, username, password);
					this.setCurrentUser(user);
					System.out.println("You have successfully logged in!");
					MENU_MAIN.open();
					return MenuState.CLOSE;
				}
				
				OptionMenuYesNo tryAgainMenu = new OptionMenuYesNo(this);
				tryAgainMenu.addHeading("Invalid login credentials.");
				tryAgainMenu.addSubheading("Would you like to try again?");
				tryAgainMenu.open();
				boolean isYes = tryAgainMenu.resultWasYes();
				if(isYes) {
					System.out.println("Okay.");
					return MenuState.RESTART;
				}
				
				System.out.println("Okay. Going back to the main menu.");
				return MenuState.CLOSE;
			});

		//Displays the teacher menu in the GUI
		MENU_MAIN = (new OptionMenu(this))
			.setCheckLogin(true)
			.addHeading("Main Menu")
			.addSubheading("Please select one of the following options:")
			.addOption((new MenuOption("Teacher Menu"))
				.addVisibilityCondition(() -> {
					return this.getCurrentUser() instanceof Teacher;
				})
				.onSelect(() -> {
					//When teacher menu is selected, the GUI shows the next options
					MENU_TEACHER.open();
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("List All Quizzes"))
				.onSelect(() -> {
					//Displays the list of all quizzes once this option is selected
					MENU_QUIZ_LIST_INFO.open();
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("Take Quiz"))
				.onSelect(() -> {
					//Helps users take a quiz
					MENU_QUIZ_LIST_TAKE.open();
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("User Settings"))
				.onSelect(() -> {
					//Guides users to view/edit the user settings
					MENU_USER_SETTINGS.open();
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("Logout"))
				.onSelect(() -> {
					/*
					When the logout option is selected, the GUI
					goes back to the show the initialize screen
					*/
					this.setCurrentUser(null);
					return MenuState.CLOSE;
				}));

		/*
		Guides users to the user settings menu in which users have the ability to edit various fields
		 */
		MENU_USER_SETTINGS = (new OptionMenu(this))
			.setCheckLogin(true)
			.addHeading("User Settings Menu")
			.addSubheading("Select one of the following options:")
			.addOption((new MenuOption("Edit User"))
				.onSelect(() -> {
					//Leads users to select different fields that can be modified in their account
					OptionMenu editUserMenu = (new OptionMenu(this))
						.addHeading("Edit User Menu")
						.addOption ((new MenuOption("Username"))
								.onSelect (() -> {
									//Helps the current user change their account's username
									MenuQuickInput menuUsername = (new MenuQuickInput(this, "Type in your new username"));
									menuUsername.open();
									this.currentUser.setUsername(menuUsername.getResult());
									System.out.println("Successfully changed your username");
									return MenuState.RESTART;
								}))
						.addOption ((new MenuOption("Password"))
								.onSelect (() -> {
									//Helps the current user change their account's password
									MenuQuickInput menuPassword = (new MenuQuickInput(this, "Type in your new password"));
									menuPassword.open();
									this.currentUser.setPassword(menuPassword.getResult());
									System.out.println("Successfully changed your password");

									return MenuState.RESTART;
								}))
						.addOption ((new MenuOption("Name"))
								.onSelect (() -> {
									//Helps the current user change their account's name
									MenuQuickInput menuName = (new MenuQuickInput(this, "Type in your new name"));
									menuName.open();
									
									// TODO User - Will be good once name is added. 
									// this.currentUser.setName(menuName.getResult());
									System.out.println("Successfully changed your name");
									return MenuState.RESTART;
								}))
						.addOption ((new MenuOption("Save Changes"))
								.onSelect (() -> {
									/*
									Prompts a message that mentions that all changes were saved
									Goes back to the menu before the User Settings Menu
									*/
									System.out.println("All changes were successfully saved");
									return MenuState.CLOSE;
								}));
					editUserMenu.open();
					
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("Delete User"))
				.onSelect(() -> {
					/*
					Allows the current user to delete their account along with
					all the information associated with their account
					 */
					OptionMenuYesNo verifyMenu = new OptionMenuYesNo(this);
					verifyMenu.addHeading("Are you sure you want to delete your account?");
					/*
					Reconfirms with the current user by asking them
					if they want to delete their account or not
					 */
					verifyMenu.open();
					if(verifyMenu.resultWasYes()) {
						System.out.println("Your account has been successfully deleted.");
						// TODO UserManager - Done - will be good once we can remove a user. 
						lms.getUserManager().removeUser(this.currentUser);
						this.setCurrentUser(null);
						// TODO GradedQuizManager - remove all graded quizzes for deleted user?
						return MenuState.CLOSE;
					} else {
						System.out.println("Okay. Your account has not been deleted.");
					}
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("Exit"))
				.onSelect(() -> {
					//Goes back to the previous menu
					return MenuState.CLOSE;
				}));
					
		
		MENU_TEACHER = (new OptionMenu(this))
			.addHeading("Teacher Menu")
			.addOption((new MenuOption("List Quizzes"))
				.onSelect(() -> {
					MENU_QUIZ_LIST_INFO.open();
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("Add New Quiz"))
				.onSelect(() -> {
					MENU_ADD_QUIZ.open();
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("Modify Quizzes"))
				.onSelect(() -> {
					MENU_QUIZ_LIST_MODIFY.open();
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("Exit Teacher Menu"))
				.onSelect(() -> {
					return MenuState.CLOSE;
				}));
		
		MENU_QUIZ_LIST_INFO = (new OptionListMenu<Quiz>(this))
			.onRequestListItems(() -> {
				return lms.getQuizManager().getQuizList();
			})
			.onSelectListItem(
				(Quiz q) -> {
					System.out.println(ANSICodes.BOLD + "\nQuiz Info" + ANSICodes.RESET);
					// TODO Quiz - get quiz info... maybe a toString method?
					System.out.println(q.toString());
					System.out.println("Press Enter to go back.");
					this.getScanner().nextLine();
					return MenuState.RESTART;
				}
			)
			.addHeading("List of All Quizzes");
		
		MENU_QUIZ_LIST_TAKE = (new OptionListMenu<Quiz>(this))
			.onRequestListItems(() -> {
				return lms.getQuizManager().getQuizList();
			})
			.onSelectListItem(
				(Quiz q) -> {
					System.out.println(ANSICodes.BOLD + "\nQuiz Info" + ANSICodes.RESET);
					// TODO Quiz - get quiz info... maybe a toString method?
					System.out.println(q.toString());
					OptionMenuYesNo menuTakeQuizQuestion = new OptionMenuYesNo(this);
					menuTakeQuizQuestion.addHeading("Would you like to take this quiz");
					menuTakeQuizQuestion.open();
					if(menuTakeQuizQuestion.resultWasYes()) {
						getMenuTakeQuiz(q).open();
						return MenuState.CLOSE;
					}
					System.out.println("Okay. Going back");
					return MenuState.RESTART;
				}
			)
			.addHeading("List of Quizzes to Take");
		
		MENU_QUIZ_LIST_MODIFY = (new OptionListMenu<Quiz>(this))
			.onRequestListItems(() -> {
				return lms.getQuizManager().getQuizList();
			})
			.onSelectListItem(
				(Quiz q) -> {
					return MenuState.CLOSE;
				}
			)
			.addHeading("List of Quizzes to Change");
		
		MENU_ADD_QUIZ = (new InputMenu(this))
			.addInput("What would you like the name of this quiz to be?", "Name")
			.addInputWithOptions("What type of quiz would you like?", new String[] {
				"Multiple Choice", "True or False", "Dropdown"
			}, "QuizType")
			.addInput("What is the name of the course this quiz will be in?", "Course")
			.onInputFinish((Map<String, String> results) -> {
				String name = results.get("Name");
				String quizType = results.get("QuizType");
				String course = results.get("Course");
				OptionMenu menu = getMenuAddQuiz(name, quizType, course);
				menu.open();
				return MenuState.CLOSE;
			});
		
	}
	
	private OptionMenu getMenuAddQuiz(String name, String quizType, String course) {
		// TODO Will we be doing the question bank?
		// TODO Add simple constructor
		Quiz quiz = new Quiz(
			name, 
			this.currentUser.getName(), 
			0,
			lms.getQuizManager().getUniqueID(), 
			new ArrayList<Question>(), 
			quizType, 
			false, 
			course
		);
		OptionMenu menu = (new OptionMenu(this))
			.onHeadingPrint(() -> {
				System.out.println(ANSICodes.BOLD + "\nCreating Quiz: '" + quiz.getName() + "'" + ANSICodes.RESET);
				System.out.println("Current Amount of Questions: " + quiz.getQuestions().size());
			})
			.addOption((new MenuOption("Import Quiz From File"))
				.onSelect(() -> {
					InputMenu changeNameMenu = ((new InputMenu(this)))
						.addHeading("Importing the quiz from a file.")
						.addInput("What is the file path?", "FilePath")
						.onInputFinish((Map<String, String> results) -> {
							String filePath = results.get("FilePath");
							// TODO QuizFileManager
							// boolean success = lms.getQuizFileManager().importQuiz(filePath);
							boolean success = true;
							if(success) {
								System.out.println("Successfully imported the quiz.");
								return MenuState.CLOSE;
							}
							OptionMenuYesNo verifyMenu = new OptionMenuYesNo(this);
							verifyMenu.addHeading("Invalid file.");
							verifyMenu.addSubheading("Would you like to try again?");
							verifyMenu.open();
							boolean isYes = verifyMenu.getResult();
							if(isYes)
								return MenuState.RESTART;
							return MenuState.CLOSE;
						});
					changeNameMenu.open();
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("Export Quiz to File"))
					.onSelect(() -> {
						InputMenu changeNameMenu = ((new InputMenu(this)))
							.addHeading("Exporting the quiz to a file.")
							.addInput("What is the file path?", "FilePath")
							.onInputFinish((Map<String, String> results) -> {
								String filePath = results.get("FilePath");
								// TODO QuizFileManager.canExport(filePath)
								// TODO Do we want to be able to export? lms.getQuizFileManager().
								if(Boolean.getBoolean("true")) {
									// TODO QuizFileManager.export(filePath, quiz);
									System.out.println("Successfully exported the quiz.");
									return MenuState.CLOSE;
								}
								OptionMenuYesNo verifyMenu = new OptionMenuYesNo(this);
								verifyMenu.addHeading("Invalid file.");
								verifyMenu.addSubheading("Would you like to try again?");
								verifyMenu.open();
								boolean isYes = verifyMenu.getResult();
								if(isYes)
									return MenuState.RESTART;
								return MenuState.CLOSE;
							});
						changeNameMenu.open();
						return MenuState.RESTART;
					}))
			.addOption((new MenuOption("Change Name"))
				.onSelect(() -> {
					MenuQuickInput quickInput = new MenuQuickInput(this, "What would you like the new name of the quiz to be?");
					quickInput.open();
					String newName = quickInput.getResult();
					quiz.setName(newName);
					System.out.println("Changed the quiz name to " + name);
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("Add Question"))
				.onSelect(() -> {
					InputMenu addQuestionMenu = getAddQuestionMenu(quiz);
					addQuestionMenu.open();
					return MenuState.RESTART;
				}))
			.addOption((new MenuOption("Save Quiz"))
				.onSelect(() -> {
					// TODO Save Quiz in Quiz Manager
					lms.getQuizManager().addQuiz(quiz);
					return MenuState.CLOSE;
				}))
			.addOption((new MenuOption("Cancel without saving"))
				.onSelect(() -> {
					OptionMenuYesNo verifyMenu = new OptionMenuYesNo(this);
					verifyMenu.addHeading("Are you sure you want to cancel making this quiz?");
					verifyMenu.open();
					boolean isYes = verifyMenu.getResult();
					if(isYes)
						return MenuState.CLOSE;
					System.out.println("Okay. Going back to creating the quiz.");
					return MenuState.RESTART;
				}));
		return menu;
	}

	private InputMenu getAddQuestionMenu(Quiz quiz) {
		InputMenu questionMenu = (new InputMenu(this))
			.addInputWithOptions("What type of question do you want to add?",
				new String[] {"Multiple Choice", "True or False", "Dropdown"},
				"Type"
			)
			.addInput("What is the question?", "Question")
			.onInputFinish((Map<String, String> questionInfo) -> {
				String type = questionInfo.get("Type");
				String questionString = questionInfo.get("Question");
				// TODO Question Create question object
				int maxQuestionId = 0;
				for(Question question: quiz.getQuestions()) {
					if(question.getId() > maxQuestionId)
						maxQuestionId = question.getId();
				}
				Question question = new Question(
					new ArrayList<Answer>(), 
					questionString, 
					maxQuestionId + 1
				);
				// TODO Question - Type? -question.setType(type);
				OptionMenu menu = (new OptionMenu(this))
					.addHeading("Adding a new question to quiz: " + quiz.getName())
					//.addSubheading("Current point value: 0") // TODO Question -> "value: " + question.getValue()
					.addOption((new MenuOption("Add Answer"))
						.onSelect(() -> {
							InputMenu inpMenu = (new InputMenu(this))
								.addInput("What is the answer?", "Answer")
								.addIntInput("How much is this answer worth?", "PointValue")
								.onInputFinish((Map<String, String> answerInfo) -> {
									String answer = answerInfo.get("Answer");
									// TODO Float not int
									int pointvalue = Integer.parseInt(answerInfo.get("PointValue"));
									int maxId = 0;
									for(Answer a: question.getAnswers()) {
										if(a.getId() > maxId)
											maxId = a.getId();
									}
									question.getAnswers().add(
										new Answer(
											answer, 
											pointvalue > 0, 
											pointvalue, 
											maxId+1
										)
									);
									return MenuState.CLOSE;
								});
							inpMenu.open();
							return MenuState.RESTART;
						}))
					.addOption((new MenuOption("Remove Answer"))
						.onSelect(() -> {
							OptionMenu answerList = (new OptionMenu(this));
							answerList.addHeading("Select an answer to remove.");
							for(Answer a: question.getAnswers()) {
								answerList.addOption((new MenuOption(a.toString()))
									.onSelect(() -> {
										question.getAnswers().remove(a);
										return MenuState.CLOSE;
									}));
							}
							answerList.addOption((new MenuOption("Cancel")
								.onSelect(() -> {
									return MenuState.CLOSE;
								})));
							answerList.open();
							return MenuState.RESTART;
						}))
					.addOption((new MenuOption("View Question"))
							.onSelect(() -> {
								OptionMenu quizMenu = (new OptionMenu(this));
								quizMenu.addHeading("The following is how the question will look on the quiz.");
								for(Answer a: question.getAnswers()) {
									quizMenu.addOption((new MenuOption(a.getAnswer()))
										.onSelect(() -> {
											return MenuState.CLOSE;
										}));
								}
								quizMenu.open();
								return MenuState.RESTART;
							}))
					.addOption((new MenuOption("Finish Adding Question"))
						.onSelect(() -> {
							return MenuState.CLOSE;
						}));
				menu.open();
				return MenuState.CLOSE;
			});
		return questionMenu;
	}
	
	private OptionMenu getMenuTakeQuiz(Quiz quiz) {
		// Queue is used to go from first to last question in order.
		Queue<OptionMenu> questionsMenus = new LinkedList<OptionMenu>();
		ArrayList<Question> questions = quiz.getQuestions();
		GradedQuiz gradedQuiz = new GradedQuiz(quiz.getId(), this.getCurrentUser().getID());
		for(Question question: questions) {
			OptionMenu menu = (new OptionMenu(this));
			menu.addHeading(question.getQuestion());
			for(Answer a: question.getAnswers()) {  // TODO Question - question.getAnswers();
				menu.addOption((new MenuOption(a.toString())) // TODO Answer - answer.getName();
					.onSelect(() -> {
						// TODO GradedQuiz gradedQuiz.addAnswer(question, answer);
						questionsMenus.poll().open();
						return MenuState.CLOSE;
					}));
			}
			questionsMenus.add(menu);
		}
		OptionMenu menu = (new OptionMenu(this));
		menu.addHeading("You have finished the quiz.");
		menu.addSubheading("Would you like to submit it?");
		menu.addOption((new MenuOption("Submit"))
			.onSelect(() -> {
				// TODO GradedQuizManager - add graded quiz.
				// lms.getGradedQuizManager().addGradedQuiz(gradedQuiz);
				System.out.println("Successfully submitted the quiz.");
				return MenuState.CLOSE;
			}))
			.addOption((new MenuOption("Cancel"))
				.onSelect(() -> {
					OptionMenuYesNo exitMenu = new OptionMenuYesNo(this);
					exitMenu.addHeading("Are you sure you want to cancel submitting this quiz?");
					exitMenu.addSubheading("If you cancel now, all your answers will be gone.");
					exitMenu.open();
					if(exitMenu.resultWasYes()) {
						System.out.println("Going back to the main menu.");
						return MenuState.CLOSE;
					} else {
						return MenuState.RESTART;
					}
				}));
		questionsMenus.add(menu);
		return questionsMenus.poll();
	}

	/**
	 * Ran whenever the UI has exited.
	 * <p>
	 * Closes the {@link UIManager#scanner} scanner.
	 */
	@Override
	public void exit() {
		this.scanner.close();
	}
	
	
	/**
	 * Runs the UI Loop by opening the Start Menu.
	 * <p>
	 * Most (if not all) of the menus that are branched from here are initialized in {@link #init()}
	 * 
	 * @see LearningManagementSystem#run()
	 * @see Menu#open()
	 */
	public void run() {
		System.out.print(ANSICodes.CLEAR_SCREEN);
		System.out.print(ANSICodes.CURSOR_TO_HOME);
		
		MENU_START.open();
	}
	
	/**
	 * Accesses the scanner used for user input.
	 * 
	 * @return Scanner The Scanner used for user input.
	 */
	public Scanner getScanner() {
		return this.scanner;
	}
	
	/**
	 * Accesses the current {@link User} that is logged in.
	 * 
	 * TODO Determine if we want to move this to a {@link SessionManager} class.
	 * 
	 * @return User The current User logged in. Can be null if no user is logged in.
	 */
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	/**
	 * Sets the current {@link User}
	 * <p>
	 * Is used inside {@link #MENU_LOGIN} to set the logged-in user, 
	 * and is set to null inside {@link #MENU_MAIN} to logout the user.
	 * 
	 * @param currentUser The current user. Can be null.
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
