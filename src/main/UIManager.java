package main;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

import ui.ANSICodes;
import ui.InformationMenu;
import ui.InputMenu;
import ui.Menu;
import ui.MenuOption;
import ui.MenuQuickInput;
import ui.MenuState;
import ui.OptionListMenu;
import ui.OptionMenu;
import ui.OptionMenuYesNo;
import ui.RunnableSelectListItem;

/**
 * The manager for the UI of the application.
 * <p>
 * Is instantiated inside of {@link LearningManagementSystem} 
 * and should not be instantiated anywhere else.
 * <p>
 * Initializes all the {@link Menu}s that are used in the UI,
 * and opens the Start Menu ({@link #MENU_START}) whenever {@link #run()} is called.
 * 
 * @author Isaac Fleetwood
 * @author Aryan Jain
 * @version 1.0.0
 * @see LearningManagementSystem
 * @see Manager
 */
public class UIManager implements Manager {

	private LearningManagementSystem lms;
	
	private Scanner scanner;
	private User currentUser;
	
	private Menu menuStart;
	private Menu menuLogin;
	private Menu menuCreateUser;

	private Menu menuSelectQuizView; // Quiz List for Show Quiz Info
	private Menu menuUserSettings;
	private Menu menuEditUser;
	
	private Menu menuMainStudent;
	private Menu menuSelectQuizTake; // Quiz List for Take Quiz
	
	private Menu menuMainTeacher;
	private Menu menuAddQuiz;
	private Menu menuSelectQuizModify; // Quiz List for Modify Quiz
	
	private Menu menuSelectQuizSubmissions;
	
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
		this.scanner = new Scanner(System.in);
		this.currentUser = null;
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
		menuStart = (new OptionMenu(this))
		    .addHeading("Welcome to the Learning Management System!")
		    .addSubheading("Please select one of the following options:")
		    .addOption((new MenuOption("Login")).onSelect(() -> {
				menuLogin.open();
				return MenuState.RESTART;
			}))
		    .addOption((new MenuOption("Create User")).onSelect(() -> {
		    	menuCreateUser.open();
				return MenuState.RESTART;
			}))
		    .addOption((new MenuOption("Exit")).onSelect(() -> {
				System.out.println("Okay! Bye!");
				return MenuState.CLOSE;
			}));
		
		menuCreateUser = (new InputMenu(this))
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
				
				if (user != null) {
					OptionMenuYesNo retryMenu = new OptionMenuYesNo(this);
					retryMenu.addHeading("A user with that username already exists.");
					retryMenu.addSubheading("Would you like to try creating a new user again?");
					retryMenu.open();
					if (retryMenu.resultWasNo()) {
						System.out.println("Cancelling creation of a new user.");
						return MenuState.CLOSE;
					} else {
						System.out.println("Restarting...");
						return MenuState.RESTART;
					}
				}
				int id = lms.getUserManager().getUniqueID();
				switch(userType) {
					case "Teacher":
						user = new Teacher(id, name, username, password);
						break;
					case "Student":
						user = new Student(id, name, username, password);
						break;
				}
				lms.getUserManager().addUser(user);
				lms.getUserFileManager().save();
				System.out.println("Successfully created the user.");
				return MenuState.CLOSE;
			});
		
		menuLogin = (new InputMenu(this))
		    .addHeading("Logging into the Learning Management System.")
		    .addSubheading("Please enter your login details.")
		    .addInput("Username: ", "Username")
		    .addInput("Password: ", "Password")
		    .onInputFinish((Map<String, String> values) -> {
				String username = values.get("Username");
				String password = values.get("Password");
				
				boolean correctUsernamePassword = lms.getUserManager().authenticator(username, password);
				if (correctUsernamePassword) {
					User user = lms.getUserManager().getUser(username);
					this.setCurrentUser(user);
					System.out.println("You have successfully logged in!");
					if (user instanceof Teacher) {
						menuMainTeacher.open();
					} else {
						menuMainStudent.open();
					}
					return MenuState.CLOSE;
				}
				
				OptionMenuYesNo tryAgainMenu = new OptionMenuYesNo(this);
				tryAgainMenu.addHeading("Invalid login credentials.");
				tryAgainMenu.addSubheading("Would you like to try again?");
				tryAgainMenu.open();
				boolean isYes = tryAgainMenu.resultWasYes();
				if (isYes) {
					System.out.println("Okay.");
					return MenuState.RESTART;
				}
				
				System.out.println("Okay. Going back to the main menu.");
				return MenuState.CLOSE;
			});

		menuMainStudent = (new OptionMenu(this))
		    .setCheckLogin(true)
		    .addHeading("Main Menu")
		    .addSubheading("Please select one of the following options:")
		    .addOption((new MenuOption("View Quizzes"))
			    .onSelect(() -> {
					// Displays the list of all quizzes once this option is selected
					menuSelectQuizView.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Take Quiz"))
			    .onSelect(() -> {
					// Helps users take a quiz
					menuSelectQuizTake.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("View Your Quiz Submissions"))
			    .onSelect(() -> {
					Menu submissionsMenu = (new OptionListMenu<ListableGradedQuiz>(this))
					    .setItems(
						   lms.getGradedQuizManager()
						   .getGradedQuizList()
						   .stream()
						   .filter(gradedQuiz -> 
						    gradedQuiz.getStudentID() == this.getCurrentUser().getID()
						   )
						   .map(gradedQuiz -> 
						    new ListableGradedQuiz(lms, gradedQuiz)
						   )
						   .collect(Collectors.toList())
					    )
					    .onSelectListItem((ListableGradedQuiz listableGradedQuiz) -> {
							Menu submissionMenu = getSubmissionMenu(listableGradedQuiz.getGradedQuiz());
							submissionMenu.open();
							return MenuState.CLOSE;
						})
					    .addHeading("Please Select one of the following quiz submissions.");
					submissionsMenu.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("User Settings"))
			    .onSelect(() -> {
					// Guides users to view/edit the user settings
					menuUserSettings.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Logout"))
			    .onSelect(() -> {
					/*
					When the logout option is selected, the GUI
					goes back to show the initialize screen
					*/
					this.setCurrentUser(null);
					return MenuState.CLOSE;
				}));

		/*
		Guides users to the user settings menu in which users have the ability to edit various fields
		 */
		menuUserSettings = (new OptionMenu(this))
		    .setCheckLogin(true) // Check if they have logged out
		    .addHeading("User Settings Menu")
		    .addSubheading("Select one of the following options:")
		    .addOption((new MenuOption("Edit User"))
			    .onSelect(() -> {
					/*
					 * Opens the "Edit User" menu so that they can select things to 
					 * change in their account. (Or delete it)
					 */
					menuEditUser.open();
					
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
					if (verifyMenu.resultWasYes()) {
						System.out.println("Your account has been successfully deleted.");
						lms.getUserManager().removeUser(this.currentUser);
						lms.getGradedQuizManager().deleteAllByStudentID(this.currentUser.getID());
						this.setCurrentUser(null);
						return MenuState.CLOSE;
					} else {
						System.out.println("Okay. Your account has not been deleted.");
					}
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Exit"))
			    .onSelect(() -> {
					// Goes back to the previous menu
					return MenuState.CLOSE;
				}));
					
		// Leads users to select different fields that can be modified in their account
		menuEditUser = (new OptionMenu(this))
		    .addHeading("Edit User Menu")
		    .addOption((new MenuOption("Username"))
			    .onSelect(() -> {
					// Helps the current user change their account's username
					MenuQuickInput menuUsername = new MenuQuickInput(this, "Type in your new username");
					menuUsername.open();
					this.currentUser.setUsername(menuUsername.getResult());
					System.out.println("Successfully changed your username");
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Password"))
			    .onSelect(() -> {
					// Helps the current user change their account's password
					MenuQuickInput menuPassword = new MenuQuickInput(this, "Type in your new password");
					menuPassword.open();
					this.currentUser.setPassword(menuPassword.getResult());
					System.out.println("Successfully changed your password");

					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Name"))
			    .onSelect(() -> {
					// Helps the current user change their account's name
					MenuQuickInput menuName = new MenuQuickInput(this, "Type in your new name");
					menuName.open();
					
					this.currentUser.setName(menuName.getResult());
					System.out.println("Successfully changed your name");
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Save Changes"))
			    .onSelect(() -> {
				 	/*
					This doesn't actually do anything because 
					the changes were already saved
					
					Sends a message that mentions that all changes were saved
					Goes back to the menu before the User Settings Menu
					*/
					System.out.println("All changes were successfully saved");
					return MenuState.CLOSE;
				}));
		
		// Displays the teacher menu in the GUI
		menuMainTeacher = (new OptionMenu(this))
		    .setCheckLogin(true) // Check if they have logged out when it refreshes
		    .addHeading("Teacher Menu")
		    .addOption((new MenuOption("List Quizzes"))
			    .onSelect(() -> {
					menuSelectQuizView.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("List All Quiz Submissions"))
			    .onSelect(() -> {
					menuSelectQuizSubmissions.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Add New Quiz"))
			    .onSelect(() -> {
					menuAddQuiz.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Modify Quizzes"))
			    .onSelect(() -> {
					menuSelectQuizModify.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("User Settings"))
			    .onSelect(() -> {
					// Guides users to view/edit the user settings
					menuUserSettings.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Logout"))
			    .onSelect(() -> {
					return MenuState.CLOSE;
				}));
		
		menuSelectQuizView = getCourseQuizSelectionMenu("Please select a quiz to view.",
			(Quiz quiz) -> {
				InformationMenu viewQuizMenu = (new InformationMenu(this))
				    .addHeading("Quiz Info")
				    .addText(quiz.toString())
				    .requireEnter();
				viewQuizMenu.open();
				return MenuState.RESTART;
			}
		);
		
		menuSelectQuizSubmissions = (new OptionListMenu<ListableGradedQuiz>(this))
		    .onRequestListItems(() -> {
				return lms
				    .getGradedQuizManager()
				    .getGradedQuizList()
				    .stream()
				    .map(gradedQuiz -> new ListableGradedQuiz(lms, gradedQuiz))
				    .collect(Collectors.toList());
			})
		    .onSelectListItem((ListableGradedQuiz listableGradedQuiz) -> {
				Menu submissionMenu = getSubmissionMenu(listableGradedQuiz.getGradedQuiz());
				submissionMenu.open();
				return MenuState.CLOSE;
			});
		
		menuSelectQuizTake = getCourseQuizSelectionMenu("Please select a quiz to take.",
			(Quiz quiz) -> {
				InformationMenu viewQuizMenu = (new InformationMenu(this))
				    .addHeading("Quiz Info")
				    .addText(quiz.toString());
				viewQuizMenu.open();
				OptionMenuYesNo menuTakeQuizQuestion = new OptionMenuYesNo(this);
				menuTakeQuizQuestion.addHeading("Would you like to take this quiz");
				menuTakeQuizQuestion.open();
				if (menuTakeQuizQuestion.resultWasYes()) {
					System.out.println("Okay. Beginning the quiz.");
					getMenuTakeQuiz(quiz).open();
					return MenuState.CLOSE;
				}
				System.out.println("Okay. Going back");
				return MenuState.RESTART;
			}
		);
		
		menuSelectQuizModify = getCourseQuizSelectionMenu("Please select a quiz to modify.",
			(Quiz quiz) -> {
				OptionMenu menu = getMenuModifyQuiz(quiz);
				menu.open();
				return MenuState.CLOSE;
			}
		);
		
		menuAddQuiz = (new InputMenu(this))
		    .addHeading("Creating a new quiz.")
		    .addInput("What would you like the name of this quiz to be?", "Name")
		    .addInput("What is the name of the course this quiz will be in?", "Course")
		    .addInputWithOptions("Would you like to import this quiz from a file?", 
				new String[] {"Yes", "No"}, 
				"FileImport")
		    .onInputFinish((Map<String, String> results) -> {
				String name = results.get("Name");
				String course = results.get("Course");
				boolean wantsFileImport = results.get("FileImport").equals("Yes");
				
				if (wantsFileImport) {
					
					InputMenu importMenu = ((new InputMenu(this)))
					    .addHeading("Importing the quiz from a file.")
					    .addInput("What is the file path?", "FilePath")
					    .onInputFinish((Map<String, String> resultsImport) -> {
							String filePath = resultsImport.get("FilePath");
							
							Quiz newQuiz = lms.getQuizFileManager().importQuiz(filePath, name, course);
							
							if (newQuiz != null) {
								System.out.println("Successfully imported the quiz.");
								lms.getQuizManager().addQuiz(newQuiz);
								OptionMenu menu = getMenuModifyQuiz(newQuiz);
								menu.open();
								return MenuState.CLOSE;
							}
							
							OptionMenuYesNo verifyMenu = new OptionMenuYesNo(this);
							verifyMenu.addHeading("Invalid file.");
							verifyMenu.addSubheading("Would you like to try again?");
							verifyMenu.open();
							if (verifyMenu.resultWasYes())
								return MenuState.RESTART;
							System.out.println("Okay. Cancelling adding a new quiz.");
							return MenuState.CLOSE;
						});
					importMenu.open();
					return MenuState.CLOSE;
				}
				
				Quiz quiz = new Quiz(lms, name, course);
				lms.getQuizManager().addQuiz(quiz);
				System.out.println("Successfully created the quiz.");
				OptionMenu menu = getMenuModifyQuiz(quiz);
				menu.open();
				return MenuState.CLOSE;
			});
		
	}

	private Menu getCourseQuizSelectionMenu(String heading, RunnableSelectListItem<Quiz> callback) {
		
		/**
		 * Quick class to let a string be listable.
		 * This allows selection of courses in
		 * the form of a string object.
		 * 
		 * @author Isaac Fleetwood
		 * @version 1.0.0
		 */
		class StringListable implements Listable {
			String item;
			public StringListable(String item) {
				this.item = item;
			}
			public String getListName() {
				return this.item;
			}
		}
		
		return (new OptionListMenu<StringListable>(this))
		    .onRequestListItems(() -> {
				return lms
				    .getQuizManager()
				    .getListOfCourses()
				    .stream()
				    .map(
				    	course -> new StringListable(course)
				    )
				    .collect(Collectors.toList());
			})
		    .onSelectListItem(
				(StringListable courseListable) -> {
					String course = courseListable.getListName();
					Menu modifyQuizMenu = (new OptionListMenu<Quiz>(this))
					    .setItems(lms.getQuizManager().searchQuizByCourse(course))
					    .onSelectListItem(callback)
					    .addHeading(heading);
					modifyQuizMenu.open();
					return MenuState.CLOSE;
				}
			)
		    .addHeading("Please select a course.");
	}

	private OptionMenu getMenuModifyQuiz(Quiz quiz) {
		return (new OptionMenu(this))
		    .onHeadingPrint(() -> {
				System.out.println(ANSICodes.BOLD + "\nModifying Quiz: '" + quiz.getName() + "'" + ANSICodes.RESET);
				System.out.println("Current Amount of Questions: " + quiz.getQuestions().size());
			})
		    .addOption((new MenuOption("View Questions"))
				    .onSelect(() -> {
						Menu menu = (new OptionListMenu<Question>(this))
						    .setItems(quiz.getQuestions())
						    .onSelectListItem((Question question) -> {
								InformationMenu infoMenu = (new InformationMenu(this))
								    .requireEnter()
								    .addHeading("Question Info")
								    .addText(question.getQuestion());
								
								for (Answer answer: question.getAnswers()) {
									infoMenu.addListItem(answer.getAnswer());
								}
								infoMenu.addText("");
								infoMenu.open();
								return MenuState.RESTART;
							})
						    .addHeading("Select a question to view it.");
						menu.open();
						return MenuState.RESTART;
					}))
		    .addOption((new MenuOption("Add Question"))
			    .onSelect(() -> {
					InputMenu addQuestionMenu = getAddQuestionMenu(quiz);
					addQuestionMenu.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Edit Questions"))
			    .onSelect(() -> {
					Menu menu = (new OptionListMenu<Question>(this))
					    .setItems(quiz.getQuestions())
					    .onSelectListItem((Question question) -> {
							Menu questionMenu = getMenuModifyQuestion(question);
							
							questionMenu.open();
							return MenuState.CLOSE;
						})
					    .addHeading("Select a question to view it.");
					menu.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Remove Question"))
			    .onSelect(() -> {
					Menu menu = (new OptionListMenu<Question>(this))
					    .setItems(quiz.getQuestions())
					    .onSelectListItem((Question question) -> {
							InformationMenu infoMenu = (new InformationMenu(this))
							    .addHeading("Question Info")
							    .addText(question.getQuestion());
							
							for (Answer answer: question.getAnswers()) {
								infoMenu.addListItem(answer.getAnswer());
							}
							infoMenu.addText("");
							infoMenu.open();
							
							OptionMenuYesNo verifyMenu = new OptionMenuYesNo(this);
							verifyMenu.addHeading("Are you sure you want to delete this question?");
							verifyMenu.open();
							
							if (verifyMenu.resultWasYes()) {
								quiz.getQuestions().remove(question);
								System.out.println("The question was succesfully deleted.");
								return MenuState.CLOSE;
							}
							System.out.println("Okay.");
							return MenuState.CLOSE;
						})
					    .addHeading("Select a question to delete it.");
					menu.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Change Name"))
			    .onSelect(() -> {
					MenuQuickInput quickInput = new MenuQuickInput(this, 
					    "What would you like the new name of the quiz to be?");
					quickInput.open();
					String newName = quickInput.getResult();
					quiz.setName(newName);
					System.out.println("Changed the quiz name to: " + quiz.getName());
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Change Course"))
			    .onSelect(() -> {
					MenuQuickInput quickInput = new MenuQuickInput(this, 
					    "What course would you like this quiz to be in?");
					quickInput.open();
					String newCourse = quickInput.getResult();
					quiz.setCourse(newCourse);
					System.out.println("Changed the quiz course to: " + quiz.getCourse());
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Set Scrambled"))
			    .onSelect(() -> {
					OptionMenuYesNo setScrambledMenu = (new OptionMenuYesNo(this));
					setScrambledMenu.addHeading("Should the quiz be scrambled?");
					setScrambledMenu.open();
					quiz.setScrambled(setScrambledMenu.getResult());
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Save Quiz"))
			    .onSelect(() -> {
			    	if (quiz.getQuestions().size() == 0) {
			    		System.out.println("You cannot save a quiz that has no questions!");
			    		return MenuState.RESTART;
			    	}
					lms.getQuizFileManager().save();
					return MenuState.CLOSE;
				}))
		    .addOption((new MenuOption("Delete Quiz"))
			    .onSelect(() -> {
					OptionMenuYesNo verifyMenu = new OptionMenuYesNo(this);
					verifyMenu.addHeading("Are you sure you want to delete this quiz?");
					verifyMenu.open();
					boolean isYes = verifyMenu.getResult();
					if (isYes) {
						lms.getQuizManager().removeQuiz(quiz.getId());
						return MenuState.CLOSE;
					}
					System.out.println("Okay. Going back to modifying the quiz.");
					return MenuState.RESTART;
				}));
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
				
				Question question = new Question(
				    new ArrayList<Answer>(), 
				    questionString, 
				    quiz.generateUniqueQuestionId(),
				    type
				);
				quiz.getQuestions().add(question);
				
				if (type.equals("True or False")) {
					question.getAnswers().add(new Answer("True", false, 0, 0));
					question.getAnswers().add(new Answer("False", false, 0, 1));
				}
				
				Menu menu = getMenuModifyQuestion(question);
				
				menu.open();
				return MenuState.CLOSE;
			});
		return questionMenu;
	}
	
	private OptionMenu getMenuModifyQuestionMultipleChoice(Question question) {
		return (new OptionMenu(this))
			    .addHeading("Modifying Question: " + question.getQuestion())
			    .addOption((new MenuOption("Add Answer"))
				    .onSelect(() -> {
						InputMenu inpMenu = (new InputMenu(this))
						    .addInput("What is the answer?", "Answer")
						    .addIntInput("How many points is this answer worth?", "PointValue")
						    .onInputFinish((Map<String, String> answerInfo) -> {
								String answer = answerInfo.get("Answer");
								
								int pointvalue = Integer.parseInt(answerInfo.get("PointValue"));
								
								question.getAnswers().add(
								    new Answer(
								       answer, 
									   pointvalue > 0, 
									   pointvalue, 
									   question.generateUniqueAnswerId() + 1
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
						for (Answer answer: question.getAnswers()) {
							answerList.addOption((new MenuOption(answer.toString()))
							    .onSelect(() -> {
									question.getAnswers().remove(answer);
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
			    .addOption((new MenuOption("Modify Answer"))
				    .onSelect(() -> {
						OptionMenu answerList = (new OptionMenu(this));
						answerList.addHeading("Select an answer to modify.");
						for (Answer answer: question.getAnswers()) {
							answerList.addOption((new MenuOption(answer.toString()))
							    .onSelect(() -> {
									InputMenu inpMenu = (new InputMenu(this))
									    .addHeading("Modifying the selected answer.")
									    .addInput("What should the answer be?", "AnswerString")
									    .addIntInput("How many points should this answer be worth?", "PointValue")
									    .onInputFinish((Map<String, String> answerInfo) -> {
											String answerString = answerInfo.get("AnswerString");
											int pointValue = Integer.parseInt(answerInfo.get("PointValue"));
											
											answer.setAnswer(answerString);
											answer.setPointValue(pointValue);
											return MenuState.CLOSE;
										});
									inpMenu.open();
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
							InformationMenu menu = (new InformationMenu(this))
							    .requireEnter()
							    .addHeading("The following is how the question will look on the quiz.")
							    .addHeading(question.getQuestion());
								
							for (Answer answer: question.getAnswers()) {
								menu.addListItem(answer.getAnswer());
							}
							menu.addText("");
							menu.open();
							return MenuState.RESTART;
						}))
			    .addOption((new MenuOption("View Point Values"))
					    .onSelect(() -> {
							InformationMenu menu = (new InformationMenu(this))
							    .requireEnter()
							    .addHeading("The following is the point values of each answer.")
							    .addHeading(question.getQuestion());
								
							for (Answer answer: question.getAnswers()) {
								menu.addListItem(answer.getAnswer() + " - Worth " + answer.getPoints() + " Points");
							}
							menu.addText("");
							menu.open();
							return MenuState.RESTART;
						}))
			    .addOption((new MenuOption("Save Question"))
				    .onSelect(() -> {
				    	if (question.getAnswers().size() == 0) {
				    		System.out.println("You cannot save a question that has no answers!");
				    		return MenuState.RESTART;
				    	}
						return MenuState.CLOSE;
					}));
	}
	
	private Menu getMenuModifyQuestion(Question question) {
		switch(question.getQuestionType()) {
			case "Multiple Choice":
			case "Dropdown":
				return getMenuModifyQuestionMultipleChoice(question);
			case "True or False":
				return getMenuModifyQuestionTrueFalse(question);
			default:
				return (new InformationMenu(this))
				    .addHeading("An error occurred.")
				    .addText("Unable to create the question.")
				    .addText("Please press Enter to go back.")
				    .requireEnter();
		}
	}
	
	private OptionMenu getMenuModifyQuestionTrueFalse(Question question) {
		return (new OptionMenu(this))
			    .addHeading("Modifying Question: " + question.getQuestion())
			    .addOption((new MenuOption("Set Correct Answer"))
				    .onSelect(() -> {
						InputMenu correctAnswerMenu = (new InputMenu(this))
						    .addHeading("Please answer the following questions.")
						    .addInputWithOptions("What is the correct answer?", 
						       new String[] {"True", "False"}, 
						       "CorrectAnswer"
						    )
						    .addIntInput("How many points is the correct answer worth?", "Points")
						    .onInputFinish((Map<String, String> results) -> {
								ArrayList<Answer> answers = question.getAnswers();
								answers.clear();
								int truePoints = 0;
								int falsePoints = 0;
								if (results.get("CorrectAnswer").equals("True")) {
									truePoints = Integer.parseInt(results.get("Points"));
								} else { // False is correct.
									falsePoints = Integer.parseInt(results.get("Points"));
								}
								answers.add(new Answer("True", truePoints > 0, truePoints, 0));
								answers.add(new Answer("False", falsePoints > 0, falsePoints, 1));
								
								return MenuState.CLOSE;
							});
						correctAnswerMenu.open();
						return MenuState.RESTART;
					}))
			    .addOption((new MenuOption("View Question"))
				    .onSelect(() -> {
						InformationMenu menu = (new InformationMenu(this))
						    .requireEnter()
						    .addHeading("The following is how the question will look on the quiz.")
						    .addHeading(question.getQuestion());
						
						for (Answer answer: question.getAnswers()) {
							menu.addListItem(answer.getAnswer());
						}
						menu.addText("");
						menu.open();
						return MenuState.RESTART;
					}))
			    .addOption((new MenuOption("View Point Values"))
				    .onSelect(() -> {
						InformationMenu menu = (new InformationMenu(this))
						    .requireEnter()
						    .addHeading("The following is the point values of each answer.")
						    .addHeading(question.getQuestion());
							
						for (Answer answer: question.getAnswers()) {
							menu.addListItem(answer.getAnswer() + " - Worth " + answer.getPoints() + " Points");
						}
						menu.addText("");
						menu.open();
						return MenuState.RESTART;
					}))
			    .addOption((new MenuOption("Save Question"))
				    .onSelect(() -> {
						return MenuState.CLOSE;
					}));
	}
	
	private OptionMenu getMenuTakeQuiz(Quiz quiz) {
		// Queue is used to go from first to last question in order.
		Queue<OptionMenu> questionsMenus = new LinkedList<OptionMenu>();
		ArrayList<Question> questions = new ArrayList<>(quiz.getQuestions());
		if (quiz.isScrambled())
			Collections.shuffle(questions);
		GradedQuiz gradedQuiz = new GradedQuiz(quiz.getId(), this.getCurrentUser().getID());
		int i = 1;
		for (Question question: questions) {
			OptionMenu menu = (new OptionMenu(this));
			menu.addHeading("Question " + i);
			i += 1;
			menu.addHeading(question.getQuestion());
			ArrayList<Answer> answers = new ArrayList<>(question.getAnswers());
			if (quiz.isScrambled())
				Collections.shuffle(answers);
			for (Answer answer: answers) {
				final Question currentQuestion = question;
				final Answer chosenAnswer = answer;
				menu.addOption((new MenuOption(answer.getAnswer()))
				    .onSelect(() -> {
						gradedQuiz.addQuestion(currentQuestion, chosenAnswer);
						questionsMenus.poll().open();
						return MenuState.CLOSE;
					}));
			}
			menu.addOption((new MenuOption("Import Response From File"))
			    .onSelect(() -> {
					OptionMenuYesNo verifyMenu = new OptionMenuYesNo(this);
					verifyMenu.addHeading("Are you sure you want to import your response from a file?");
					verifyMenu.addSubheading("The format of the file should be the response you wish to give.");
					verifyMenu.addSubheading("Ex. \"1\" to select Answer #1");
					verifyMenu.open();
					if (verifyMenu.resultWasYes()) {
						InputMenu importMenu = ((new InputMenu(this)))
						    .addHeading("Importing the answer from a file.")
						    .addInput("What is the file path?", "FilePath")
						    .onInputFinish((Map<String, String> resultsImport) -> {
								String filePath = resultsImport.get("FilePath");
								
								ArrayList<String> response = FileWrapper.readFile(filePath);

								OptionMenuYesNo invFileMenu = new OptionMenuYesNo(this);
								if (response != null) {
									String choice = response.get(0);
									Answer selectedAnswer = null;
									for (int j = 0; j < question.getAnswers().size(); j++) {
										Answer answer = question.getAnswers().get(j);
										if (choice.equals(Integer.toString(j + 1))) {
											selectedAnswer = answer;
											break;
										}
										if(answer.getAnswer().equalsIgnoreCase(choice)) {
											selectedAnswer = answer;
											break;
										}
									}
									if(selectedAnswer != null) {
										gradedQuiz.addQuestion(question, selectedAnswer);
										questionsMenus.poll().open();
										menu.open();
										return MenuState.CLOSE;
									}
									invFileMenu.addHeading("Invalid file format (must contain the desired answer).");
								} else {
									invFileMenu.addHeading("Invalid file path.");
								}
								invFileMenu.addSubheading("Would you like to try again?");
								invFileMenu.open();
								if (invFileMenu.resultWasYes())
									return MenuState.RESTART;
								System.out.println("Okay. Cancelling importing a response from a file.");
								return MenuState.CLOSE;
							});
						importMenu.open();
						return MenuState.CLOSE;
					}
					System.out.println("Okay.");
					return MenuState.RESTART;
				}));
			questionsMenus.add(menu);
		}
		OptionMenu menu = (new OptionMenu(this))
		    .addHeading("You have finished the quiz.")
		    .addSubheading("Would you like to submit it?")
		    .addOption((new MenuOption("Submit"))
			    .onSelect(() -> {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
					LocalDateTime now = LocalDateTime.now();  
					String time = dtf.format(now);
					gradedQuiz.setSubmissionTime(time);
					
					lms.getGradedQuizManager().addGradedQuiz(gradedQuiz);
					lms.getGradedQuizFileManager().save();
					
					System.out.println("Successfully submitted the quiz.");
					OptionMenuYesNo viewSubmission = new OptionMenuYesNo(this);
					viewSubmission.addHeading("Would you like to see your score?");
					viewSubmission.open();
					if (viewSubmission.resultWasNo()) {
						System.out.println("Okay.");
						return MenuState.CLOSE;
					}
					Menu submissionMenu = getSubmissionMenu(gradedQuiz);
					submissionMenu.open();
					return MenuState.CLOSE;
				}))
		    .addOption((new MenuOption("Cancel"))
			    .onSelect(() -> {
					OptionMenuYesNo exitMenu = new OptionMenuYesNo(this);
					exitMenu.addHeading("Are you sure you want to cancel submitting this quiz?");
					exitMenu.addSubheading("If you cancel now, all your answers will be gone.");
					exitMenu.open();
					if (exitMenu.resultWasYes()) {
						System.out.println("Going back to the main menu.");
						return MenuState.CLOSE;
					} else {
						return MenuState.RESTART;
					}
				}));
		questionsMenus.add(menu);
		return questionsMenus.poll();
	}

	private void showQuestionInfoMenu(Question question, int chosenAnswerId) {
		InformationMenu menu = (new InformationMenu(this))
		    .requireEnter()
		    .addHeading("Question Info")
		    .addText(question.getQuestion());
		
		Answer chosen = null;
		Answer best = question.getAnswers().get(0);
		for (Answer answer: question.getAnswers()) {
			menu.addListItem(answer.getAnswer());
			
			if (answer.getId() == chosenAnswerId) {
				chosen = answer;
			}
			if (answer.getPoints() > best.getPoints()) {
				best = answer;
			}
		}
		menu.addText("The given answer was: " + (question.getAnswers().indexOf(chosen) + 1) + ": " + 
		    (chosen == null ? "Unknown" : chosen.getAnswer()));
		menu.addText("The best answer was: " + (question.getAnswers().indexOf(best) + 1) + ": " + best.getAnswer());
		menu.addText("Points earned: " + chosen.getPoints() + "/" + best.getPoints());
		menu.open();
	}
	
	private Menu getSubmissionMenu(GradedQuiz gradedQuiz) {
		User student = lms.getUserManager().getUserById(gradedQuiz.getStudentID()); 
		if (student == null) {
			return (new InformationMenu(this))
			    .addText("An error occurred when getting the submitted quiz.")
			    .addText("The student doesn't exist.")
			    .requireEnter();
		}
		Quiz quiz = lms.getQuizManager().searchQuizByID(gradedQuiz.getQuizID());
		if (quiz == null) {
			return (new InformationMenu(this))
			    .addText("An error occurred when getting the submitted quiz.")
			    .addText("The quiz doesn't exist.")
			    .requireEnter();
		}
		String heading = "Viewing " + student.getName() + "'s submission of Quiz: '" + 
		    quiz.getName() + "' in course: " + quiz.getCourse();
		if (student == this.getCurrentUser())
			heading = "Viewing your submission of Quiz: " + quiz.getName() + " in course: " + quiz.getCourse();
		
		int earnedPoints = 0;
		int possiblePoints = 0;
		
		for (Question question: quiz.getQuestions()) {
			int chosenAnswerId = gradedQuiz.getGradedQuizMap().get(question.getId());
			Answer chosen = null;
			Answer best = question.getAnswers().get(0);
			for (Answer answer: question.getAnswers()) {
				if (answer.getId() == chosenAnswerId) {
					chosen = answer;
				}
				if (answer.getPoints() > best.getPoints()) {
					best = answer;
				}
			}
			if (chosen == null) {
				return (new InformationMenu(this))
				    .addText("Error: The quiz has been modified since this submission was given.")
				    .addText("An answer has been chosen that no longer exists.")
				    .addText("Unable to proceed.")
				    .requireEnter();
			}
			earnedPoints += chosen.getPoints();
			possiblePoints += best.getPoints();
		}
		
		return (new OptionMenu(this))
		    .addHeading(heading)
		    .addSubheading("Total Score: " + earnedPoints + "/" + possiblePoints)
		    .addOption((new MenuOption("View All Questions"))
			    .onSelect(() -> {
					Menu menu = (new OptionListMenu<Question>(this))
					    .setItems(quiz.getQuestions())
					    .onSelectListItem((Question question) -> {
							showQuestionInfoMenu(question, gradedQuiz.getGradedQuizMap().get(question.getId()));
							return MenuState.RESTART;
						})
					    .addHeading("Select a question to view it.");
					menu.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("View Incorrect Answers"))
			    .onSelect(() -> {
					ArrayList<Question> questionsWithUnoptimalAnswers = new ArrayList<Question>();
					for (Question question: quiz.getQuestions()) {
						int chosenAnswerId = gradedQuiz.getGradedQuizMap().get(question.getId());
						Answer chosen = null;
						for (Answer answer: question.getAnswers()) {
							if (answer.getId() == chosenAnswerId) {
								chosen = answer;
							}
						}
						if (!chosen.isCorrect()) {
							questionsWithUnoptimalAnswers.add(question);
						}
					}
					Menu menu = (new OptionListMenu<Question>(this))
						    .setItems(questionsWithUnoptimalAnswers)
						    .onSelectListItem((Question question) -> {
								showQuestionInfoMenu(question, gradedQuiz.getGradedQuizMap().get(question.getId()));
								return MenuState.RESTART;
							})
						    .addHeading("Select a question to view it.");
					menu.open();
					return MenuState.RESTART;
				}))
		    .addOption((new MenuOption("Exit"))
			    .onSelect(() -> {
					return MenuState.CLOSE;
				}));
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
		
		menuStart.open();
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
	 * @return User The current User logged in. Can be null if no user is logged in.
	 */
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	/**
	 * Sets the current {@link User}
	 * <p>
	 * Is used inside {@link #menuLogin} to set the logged-in user, 
	 * and is set to null inside {@link #menuMainStudent} and {@link #menuMainTeacher} to logout the user.
	 * 
	 * @param currentUser The current user. Can be null.
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
