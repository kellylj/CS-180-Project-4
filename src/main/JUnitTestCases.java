package main;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import ui.ANSICodes;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Fall 2021</p>
 *
 * @author Purdue CS
 * @version August 23, 2021
 */
public class JUnitTestCases {

    //private static Object JUnitCore;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
        	for (Failure failure : result.getFailures()) {
            	System.out.println(failure.toString());
        	}
        }
    }
    /**
     * A framework to run public test cases.
     *
     * Contains various test cases
     *
     * @author Isaac Fleetwood and Liam Kelly
     * @version Nov 14, 2021
     */
    public static class TestCase {
    	/*
    	 * Requirements to be tested:
    	 * 
    	 * Data must persist regardless of whether or not a user is connected.
    	 * If a user disconnects and reconnects, their data should still be present. 
    	 * - Shown in this.testPersistance();
    	 * 
    	 * Descriptive errors should appear as appropriate. 
    	 * For example, if someone tries to log in with an invalid account. 
    	 * The application should not crash under any circumstances. 
    	 * - Shown in this.testInvalidUser();
    	 * - And in this.testInvalidInput();
    	 * 
    	 * Users can create, edit, and delete accounts for themselves.
    	 * - Shown in this.testUserStuff();
    	 * 
         * Any number of quizzes can be added to a course. 
         * - Shown in this.testQuizzes();
         * Quizzes will have one or more multiple choice questions. 
         * - Shown in this.testQuizzes();
         * 
         * Quiz questions will appear on the same page in the order 
         * they are added to the quiz. 
         * - Shown in this.testQuizzes();
         * - And this.testSubmit(); (take quiz)
         * 
    	 * Teachers can create, edit, and delete quizzes. 
    	 * - Shown in this.testQuizzes()
    	 * - And this.testEditQuiz()
    	 * 
         * Teachers can view student submissions for the quiz. 
         * - Shown in this.testSubmissionTimestamp()
    	 * 
    	 * Students can take any created quiz. 
    	 * Students can select their responses to each question. 
    	 * - Shown in this.testSubmissionTimestamp()
    	 * 
         * After completing a quiz, students can submit it. 
         * Each submission must be timestamped. 
		 * - Shown in this.testSubmissionTimestamp()
		 * 
		 * -- Selection Requirements
    	 * All file imports must occur as a prompt to enter the file path.  
         * - Shown in this.testImportQuiz();
         * 
         * Teachers can import a file with the quiz title and 
         * quiz questions to create a new quiz. 
         * - Shown in this.testImportQuiz();
         * 
         * Students can attach a file to any question as a response. 
         * - Shown in this.testImportResponse();
         * 
    	 * Teachers can choose to randomize the order of questions 
    	 * and the order of potential options for a question.
         * - Shown in this.testEditQuiz();
         * 
         * Students will receive a different order with every attempt. 
         * - Due to the nature of probability, this can't be tested. 
         * - There is always the possibility that the order is exactly the same.
         * 
         * Teachers can view the quiz submissions for individual students 
         * and assign point values to each answer. 
         * - Shown in this.testQuizzes(); (creating quiz assigns points)
         * - Shown in this.testSubmissionTimestamp(); (viewing submission shows points)
         * 
         * Students can view their graded quizzes, with the points for each 
         * individual question and their total score listed. 
    	 * - Shown in this.testSubmissionTimestamp();
         * 
    	 */
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

	    private ByteArrayOutputStream testOut;

	    @Before
	    public void outputStart() {
		    testOut = new ByteArrayOutputStream();
		    System.setOut(new PrintStream(testOut));
	    }

        @After
	    public void restoreInputAndOutput() {
		    System.setIn(originalSysin);
		    System.setOut(originalOutput);
	    }

        public void checkGenericClass(String className, Class<?> clazz) {
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;


            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `" + className + "` is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "` is NOT `abstract`!", Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `" + className + "` extends `Object`!", Object.class, superclass);
            Assert.assertEquals("Ensure that `" + className + "` implements no interfaces!", 0, superinterfaces.length);
        }

        public void checkManagerClass(String className, Class<?> clazz) {
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;


            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `" + className + "` is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "` is NOT `abstract`!", Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `" + className + "` extends `Object`!", Object.class, superclass);
            Assert.assertEquals("Ensure that `" + className + "` implements the Manager interface " + 
            	   "(it doesn't implement any)!", 1, superinterfaces.length);
            Assert.assertEquals("Ensure that `" + className + "` implements the Manager interface " + 
            	   "(what it implements isn't the Manager interface)!", Manager.class, superinterfaces[0]);
        }

        @Test(timeout = 1000)
        public void checkLearningManagementSystem() {
            String className = "LearningManagementSystem";
            Class<?> clazz = LearningManagementSystem.class;
            checkGenericClass(className, clazz);
        }

        @Test(timeout = 1000)
        public void checkManagers() {

            String className = "UIManager";
            Class<?> clazz = UIManager.class;
            checkManagerClass(className, clazz);

            className = "QuizManager";
            clazz = QuizManager.class;
            checkManagerClass(className, clazz);

            className = "QuizFileManager";
            clazz = QuizFileManager.class;
            checkManagerClass(className, clazz);

            className = "UserManager";
            clazz = UserManager.class;
            checkManagerClass(className, clazz);

            className = "UserFileManager";
            clazz = UserFileManager.class;
            checkManagerClass(className, clazz);

            className = "GradedQuizManager";
            clazz = GradedQuizManager.class;
            checkManagerClass(className, clazz);

            className = "GradedQuizFileManager";
            clazz = GradedQuizFileManager.class;
            checkManagerClass(className, clazz);

        }

        public void receiveInput(String input) {
        	System.setIn(new ByteArrayInputStream(input.getBytes()));
        }
        
        public void tryTestInputRegex(String[] inputArr, String[] expectedArr, String errorMsg) {
        	// Converts the input and output arrays
            // into strings seperated by new lines.
            String input = "";
            for (int i = 0; i < inputArr.length; i++) {
            	input += inputArr[i] + "\n";
            }
            String expected = "";
            for (int i = 0; i < expectedArr.length; i++) {
            	expected += expectedArr[i] + "\n";
            }
            
            // Reset output
            outputStart();

            // Runs the program with the input values
            // Replace TestProgram with the name of the class with the main method
            receiveInput(input);
            try {
            	LearningManagementSystem.main(new String[0]);
            } catch (Exception e) {
            	e.printStackTrace();
            }
            // Retrieves the output from the program
            String stuOut = ANSICodes.stripCodes(testOut.toString());

            // Trims the output and verifies it is correct.
            stuOut = stuOut.replace("\r\n", "\n");
            
            Assert.assertTrue(errorMsg, stuOut.trim().matches(expected.trim()));
        }

        public void tryTestInput(String[] inputArr, String[] expectedArr, String errorMsg) {
        	// Converts the input and output arrays
            // into strings seperated by new lines.
            String input = "";
            for (int i = 0; i < inputArr.length; i++) {
            	input += inputArr[i] + "\n";
            }
            String expected = "";
            for (int i = 0; i < expectedArr.length; i++) {
            	expected += expectedArr[i] + "\n";
            }
            
            // Reset output
            outputStart();

            // Runs the program with the input values
            // Replace TestProgram with the name of the class with the main method
            receiveInput(input);
            try {
            	LearningManagementSystem.main(new String[0]);
            } catch (Exception e) {
            	e.printStackTrace();
            }
            // Retrieves the output from the program
            String stuOut = ANSICodes.stripCodes(testOut.toString());

            // Trims the output and verifies it is correct.
            stuOut = stuOut.replace("\r\n", "\n");

            Assert.assertEquals(errorMsg, expected.trim(), stuOut.trim());
        }

        public void printOutput(String[] inputArr) {
        	// Converts the input and output arrays
            // into strings seperated by new lines.
            String input = "";
            for (int i = 0; i < inputArr.length; i++) {
            	input += inputArr[i] + "\n";
            }
            
            // Reset output
            outputStart();
            
            // Runs the program with the input values
            // Replace TestProgram with the name of the class with the main method
            receiveInput(input);
            try {
            	LearningManagementSystem.main(new String[0]);
            } catch (Exception e) {
            	e.printStackTrace();
            }
            String stuOut = ANSICodes.stripCodes(testOut.toString());

            // Trims the output and verifies it is correct.
            stuOut = stuOut.replace("\r\n", "\n");
            
            restoreInputAndOutput();
            
            System.out.println("String[] expectedArr = new String[] {");
            for (String str: stuOut.split("\n")) {
                System.out.println("    \"" + str + "\",");
            }
            System.out.println("};");
        }
        
        public void clearFiles() {
        	boolean success1 = FileWrapper.writeFile("./data/gradedQuizzes.txt", new ArrayList<String>());
        	boolean success2 = FileWrapper.writeFile("./data/quizzes.txt", new ArrayList<String>());
        	boolean success3 = FileWrapper.writeFile("./data/users.txt", new ArrayList<String>());
        	Assert.assertTrue("Unable to write to files", success1);
        	Assert.assertTrue(success2);
        	Assert.assertTrue(success3);
        }
        
        @Test(timeout = 1000)
        public void testBasicExit() {
            // Set the input.
        	// Each element is a new line.
            String[] inputArr = new String[] {
        		"3",
            };
            // Pair the input with the expected result
            String[] expectedArr = new String[] {
        		"Welcome to the Learning Management System!",
        		"Please select one of the following options:",
        		"1: Login",
        		"2: Create User",
        		"3: Exit",
        		"Okay! Bye!",
            };
            String errorMsg = "The output was not expected!";

            tryTestInput(inputArr, expectedArr, errorMsg);
        }

        // Make more test cases
        @Test(timeout = 1000)
        public void testCopyPastable() {

            String[] inputArr = new String[] {
        		"3",
            };

            String[] expectedArr = new String[] {
        		"Welcome to the Learning Management System!",
        		"Please select one of the following options:",
        		"1: Login",
        		"2: Create User",
        		"3: Exit",
        		"Okay! Bye!",
            };
            String errorMsg = "The output was not expected!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }

        @Test(timeout = 1000)
        public void testInvalidUser() {

        	clearFiles();

            String[] inputArr = new String[] {
                "1", "test", "test", "2", "3"
            };

            String[] expectedArr = new String[] {
                "Welcome to the Learning Management System!",
                "Please select one of the following options:",
                "1: Login",
                "2: Create User",
                "3: Exit",
                "",
                "Logging into the Learning Management System.",
                "Please enter your login details.",
                "Username: ",
                "Password: ",
                "",
                "Invalid login credentials.",
                "Would you like to try again?",
                "1: Yes",
                "2: No",
                "Okay. Going back to the main menu.",
                "",
                "Welcome to the Learning Management System!",
                "Please select one of the following options:",
                "1: Login",
                "2: Create User",
                "3: Exit",
                "Okay! Bye!"
            };
            String errorMsg = "The output was not expected!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }
        


        /**
         * Because all menus use InputMenu and OptionMenu
         * (or a derivative), if these menus safely handle invalid input,
         * all menus safely handle invalid input
         * Because the base error checking is derived from these classes.
         */
        @Test(timeout = 1000)
        public void testInvalidInput() {

        	clearFiles();

            String[] inputArr = new String[] {
                "", // Invalid input for OptionMenu
                "haha", // Invalid input for OptionMenu
                "10", // Invalid input for OptionMenu
                "-102", // Invalid input for OptionMenu
                "1", // Valid input for OptionMenu
                "", // Invalid Input for InputMenu
                "test", // Valid Input for InputMenu
                "test", // Valid Input for InputMenu
                "2", // 
                "3" // 
            };

            String[] expectedArr = new String[] {
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "Please use a valid integer when selecting an option.", // Errors
        	    "Please use a valid integer when selecting an option.",
        	    "Please select a valid option.",
        	    "Please select a valid option.",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Please try again and enter a valid input.", // Error
        	    "Password: ",
        	    "",
        	    "Invalid login credentials.",
        	    "Would you like to try again?",
        	    "1: Yes",
        	    "2: No",
        	    "Okay. Going back to the main menu.",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "Okay! Bye!",
        	};
            
            String errorMsg = "The output was not expected!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }

        @Test(timeout = 1000)
        public void testCreateQuiz() {

        	clearFiles();
        	
            String[] inputArr = new String[] {
                "2", // Create User
                "Test User", // Name
                "testUsername", // Username
                "testPassword", // Password
                "1", // Teacher
                "1", // Yes, Make it
                "1", // Login 
                "testUsername", // Username
                "testPassword", // Password
                "3", // Add new Quiz
                "Test Quiz", 
                "Test Course", 
                "2", // Dont import
                "2", // Add Question
                "1", // Multiple Choice
                "Test Question?", 
                "1", // Add answer
                "Correct Answer", 
                "1", // Points Value
                "1", // Add Answer
                "Incorrect Answer", 
                "0", // points 
                "6", // exit
                "8", // save quiz
                "1", // list quizzes
                "1", // select test course
                "1", // select test quiz
                "", // Enter to go back
                "2", // Go back
                "6", // Logout
                "3" // Exit
            };
            
            String[] expectedArr = new String[] {
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Creating a new user",
        	    "Please answer the following questions",
        	    "What is your name?",
        	    "What username do you want?",
        	    "What password do you want?",
        	    "Will you be a teacher or a student?",
        	    "1: Teacher",
        	    "2: Student",
        	    "",
        	    "The following are the values you gave:",
        	    "Username - testUsername",
        	    "User Type - Teacher",
        	    "Name - Test User",
        	    "Password - testPassword",
        	    "",
        	    "Are you satisfied with these values?",
        	    "1: Yes",
        	    "2: No",
        	    "Successfully created the user.",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Password: ",
        	    "You have successfully logged in!",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Creating a new quiz.",
        	    "What would you like the name of this quiz to be?",
        	    "What is the name of the course this quiz will be in?",
        	    "Would you like to import this quiz from a file?",
        	    "1: Yes",
        	    "2: No",
        	    "Successfully created the quiz.",
        	    "",
        	    "Modifying Quiz: 'Test Quiz'",
        	    "Current Amount of Questions: 0",
        	    "1: View Questions",
        	    "2: Add Question",
        	    "3: Edit Questions",
        	    "4: Remove Question",
        	    "5: Change Name",
        	    "6: Change Course",
        	    "7: Set Scrambled",
        	    "8: Save Quiz",
        	    "9: Delete Quiz",
        	    "What type of question do you want to add?",
        	    "1: Multiple Choice",
        	    "2: True or False",
        	    "3: Dropdown",
        	    "What is the question?",
        	    "",
        	    "Modifying Question: Test Question?",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "What is the answer?",
        	    "How many points is this answer worth?",
        	    "",
        	    "Modifying Question: Test Question?",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "What is the answer?",
        	    "How many points is this answer worth?",
        	    "",
        	    "Modifying Question: Test Question?",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "",
        	    "Modifying Quiz: 'Test Quiz'",
        	    "Current Amount of Questions: 1",
        	    "1: View Questions",
        	    "2: Add Question",
        	    "3: Edit Questions",
        	    "4: Remove Question",
        	    "5: Change Name",
        	    "6: Change Course",
        	    "7: Set Scrambled",
        	    "8: Save Quiz",
        	    "9: Delete Quiz",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Please select a course.",
        	    "1: Test Course",
        	    "2: Exit",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Exit",
        	    "",
        	    "Quiz Info",
        	    "Quiz name: Test Quiz",
        	    "Author: Test User",
        	    "Course: Test Course",
        	    "Please press Enter to continue.",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Exit",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "Okay! Bye!",
        	};
        	
            String errorMsg = "Unable to properly create a quiz and view it!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }
        
        @Test(timeout = 1000)
        public void testPersistance() {

        	// Create Basic Quiz
        	this.testCreateQuiz();
        	
        	// Then try access the user and quiz
        	// again (after they logged out)
            String[] inputArr = new String[] {
                "1", // Login
                "testUsername", // Username
                "testPassword", // Password
                "1", // list quizzes
                "1", // select test course
                "1", // select test quiz
                "", // Enter to go back
                "2", // Go back
                "6", // Logout
                "3" // Exit
            };
            
            String[] expectedArr = new String[] {
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Password: ",
        	    "You have successfully logged in!",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Please select a course.",
        	    "1: Test Course",
        	    "2: Exit",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Exit",
        	    "",
        	    "Quiz Info",
        	    "Quiz name: Test Quiz",
        	    "Author: Test User",
        	    "Course: Test Course",
        	    "Please press Enter to continue.",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Exit",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "Okay! Bye!",
        	};
        	
            String errorMsg = "The data is not persistent! Unable to login, or " + 
                "unable to view quiz from previous session!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }
        
        @Test(timeout = 1000)
        public void testUserStuff() {

        	// Create Basic User and Quiz
        	this.testCreateQuiz();
        	
        	// Then try to change the user data and delete the account.
            String[] inputArr = new String[] {
                "1", // Login
                "testUsername", // Username
                "testPassword", // Password
                "5", // User settings (in teacher menu)
                "1", // Edit User
                "1", // Change username
                "UsernameTest", // new username
                "2", // new password
                "PasswordTest", // Password
                "3", // new name
                "Name", // name
                "4", // exit edit user
                "3", // exit user settings
                "6", // logout // Then try to login with new stuff
                "1", // Login
                "UsernameTest", // Username
                "PasswordTest", // Password
                "5", // User settings (in teacher menu)
                "2", // Delete account
                "1", // Yes
                "3" // Exit
            };

            String[] expectedArr = new String[] {
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Password: ",
        	    "You have successfully logged in!",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "User Settings Menu",
        	    "Select one of the following options:",
        	    "1: Edit User",
        	    "2: Delete User",
        	    "3: Exit",
        	    "",
        	    "Edit User Menu",
        	    "1: Username",
        	    "2: Password",
        	    "3: Name",
        	    "4: Save Changes",
        	    "Type in your new username",
        	    "Successfully changed your username",
        	    "",
        	    "Edit User Menu",
        	    "1: Username",
        	    "2: Password",
        	    "3: Name",
        	    "4: Save Changes",
        	    "Type in your new password",
        	    "Successfully changed your password",
        	    "",
        	    "Edit User Menu",
        	    "1: Username",
        	    "2: Password",
        	    "3: Name",
        	    "4: Save Changes",
        	    "Type in your new name",
        	    "Successfully changed your name",
        	    "",
        	    "Edit User Menu",
        	    "1: Username",
        	    "2: Password",
        	    "3: Name",
        	    "4: Save Changes",
        	    "All changes were successfully saved",
        	    "",
        	    "User Settings Menu",
        	    "Select one of the following options:",
        	    "1: Edit User",
        	    "2: Delete User",
        	    "3: Exit",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Password: ",
        	    "You have successfully logged in!",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "User Settings Menu",
        	    "Select one of the following options:",
        	    "1: Edit User",
        	    "2: Delete User",
        	    "3: Exit",
        	    "",
        	    "Are you sure you want to delete your account?",
        	    "1: Yes",
        	    "2: No",
        	    "Your account has been successfully deleted.",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "Okay! Bye!",
        	};

            String errorMsg = "The user can't be changed! Or they aren't successfully deleted!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }
        
        @Test(timeout = 1000)
        public void testQuizzes() {

        	// Clear files
        	this.clearFiles();
        	
        	// Create user and add new quizzes
            String[] inputArr = new String[] {
                "2", // Create User
                "Test User", // Name
                "testUsername", // Username
                "testPassword", // Password
                "1", // Teacher
                "1", // Yes, Make it
                "1", // Login 
                "testUsername", // Username
                "testPassword", // Password
                "3", // Add new Quiz
                "Test Quiz", 
                "Test Course", 
                "2", // Dont import
                "2", // Add Question
                "1", // Multiple Choice
                "Test Question?", 
                "1", // Add answer
                "Correct Answer", 
                "1", // Points Value
                "1", // Add Answer
                "Incorrect Answer", 
                "0", // points 
                "6", // exit
                "2", // Add Question
                "1", // Multiple Choice
                "Question 2!", 
                "1", // Add answer
                "Totally not correct", 
                "1", // Points Value
                "1", // Add Answer
                "Totally not incorrect", 
                "0", // points 
                "6", // exit
                "1", // View questions
                "1", // View question 1
                "", // Enter
                "2", // View question 2
                "", // Enter
                "3", // Exit
                "8", // save quiz
                "3", // Add new Quiz
                "Test Quiz 2", 
                "Test Course", 
                "2", // Dont import
                "2", // Add Question
                "1", // Multiple Choice
                "Test Question?", 
                "1", // Add answer
                "Correct Answer", 
                "1", // Points Value
                "1", // Add Answer
                "Incorrect Answer", 
                "0", // points 
                "6", // exit
                "8", // save quiz
                "1", // list quizzes
                "1", // select test course
                "2", // select test quiz 2
                "", // Enter to go back
                "3", // Go back
                "6", // Logout
                "3" // Exit
            };
            
            String[] expectedArr = new String[] {
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Creating a new user",
        	    "Please answer the following questions",
        	    "What is your name?",
        	    "What username do you want?",
        	    "What password do you want?",
        	    "Will you be a teacher or a student?",
        	    "1: Teacher",
        	    "2: Student",
        	    "",
        	    "The following are the values you gave:",
        	    "Username - testUsername",
        	    "User Type - Teacher",
        	    "Name - Test User",
        	    "Password - testPassword",
        	    "",
        	    "Are you satisfied with these values?",
        	    "1: Yes",
        	    "2: No",
        	    "Successfully created the user.",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Password: ",
        	    "You have successfully logged in!",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Creating a new quiz.",
        	    "What would you like the name of this quiz to be?",
        	    "What is the name of the course this quiz will be in?",
        	    "Would you like to import this quiz from a file?",
        	    "1: Yes",
        	    "2: No",
        	    "Successfully created the quiz.",
        	    "",
        	    "Modifying Quiz: 'Test Quiz'",
        	    "Current Amount of Questions: 0",
        	    "1: View Questions",
        	    "2: Add Question",
        	    "3: Edit Questions",
        	    "4: Remove Question",
        	    "5: Change Name",
        	    "6: Change Course",
        	    "7: Set Scrambled",
        	    "8: Save Quiz",
        	    "9: Delete Quiz",
        	    "What type of question do you want to add?",
        	    "1: Multiple Choice",
        	    "2: True or False",
        	    "3: Dropdown",
        	    "What is the question?",
        	    "",
        	    "Modifying Question: Test Question?",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "What is the answer?",
        	    "How many points is this answer worth?",
        	    "",
        	    "Modifying Question: Test Question?",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "What is the answer?",
        	    "How many points is this answer worth?",
        	    "",
        	    "Modifying Question: Test Question?",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "",
        	    "Modifying Quiz: 'Test Quiz'",
        	    "Current Amount of Questions: 1",
        	    "1: View Questions",
        	    "2: Add Question",
        	    "3: Edit Questions",
        	    "4: Remove Question",
        	    "5: Change Name",
        	    "6: Change Course",
        	    "7: Set Scrambled",
        	    "8: Save Quiz",
        	    "9: Delete Quiz",
        	    "What type of question do you want to add?",
        	    "1: Multiple Choice",
        	    "2: True or False",
        	    "3: Dropdown",
        	    "What is the question?",
        	    "",
        	    "Modifying Question: Question 2!",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "What is the answer?",
        	    "How many points is this answer worth?",
        	    "",
        	    "Modifying Question: Question 2!",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "What is the answer?",
        	    "How many points is this answer worth?",
        	    "",
        	    "Modifying Question: Question 2!",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "",
        	    "Modifying Quiz: 'Test Quiz'",
        	    "Current Amount of Questions: 2",
        	    "1: View Questions",
        	    "2: Add Question",
        	    "3: Edit Questions",
        	    "4: Remove Question",
        	    "5: Change Name",
        	    "6: Change Course",
        	    "7: Set Scrambled",
        	    "8: Save Quiz",
        	    "9: Delete Quiz",
        	    "",
        	    "Select a question to view it.",
        	    "1: Test Question?",
        	    "2: Question 2!",
        	    "3: Exit",
        	    "",
        	    "Question Info",
        	    "Test Question?",
        	    "1: Correct Answer",
        	    "2: Incorrect Answer",
        	    "",
        	    "Please press Enter to continue.",
        	    "",
        	    "Select a question to view it.",
        	    "1: Test Question?",
        	    "2: Question 2!",
        	    "3: Exit",
        	    "",
        	    "Question Info",
        	    "Question 2!",
        	    "1: Totally not correct",
        	    "2: Totally not incorrect",
        	    "",
        	    "Please press Enter to continue.",
        	    "",
        	    "Select a question to view it.",
        	    "1: Test Question?",
        	    "2: Question 2!",
        	    "3: Exit",
        	    "",
        	    "Modifying Quiz: 'Test Quiz'",
        	    "Current Amount of Questions: 2",
        	    "1: View Questions",
        	    "2: Add Question",
        	    "3: Edit Questions",
        	    "4: Remove Question",
        	    "5: Change Name",
        	    "6: Change Course",
        	    "7: Set Scrambled",
        	    "8: Save Quiz",
        	    "9: Delete Quiz",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Creating a new quiz.",
        	    "What would you like the name of this quiz to be?",
        	    "What is the name of the course this quiz will be in?",
        	    "Would you like to import this quiz from a file?",
        	    "1: Yes",
        	    "2: No",
        	    "Successfully created the quiz.",
        	    "",
        	    "Modifying Quiz: 'Test Quiz 2'",
        	    "Current Amount of Questions: 0",
        	    "1: View Questions",
        	    "2: Add Question",
        	    "3: Edit Questions",
        	    "4: Remove Question",
        	    "5: Change Name",
        	    "6: Change Course",
        	    "7: Set Scrambled",
        	    "8: Save Quiz",
        	    "9: Delete Quiz",
        	    "What type of question do you want to add?",
        	    "1: Multiple Choice",
        	    "2: True or False",
        	    "3: Dropdown",
        	    "What is the question?",
        	    "",
        	    "Modifying Question: Test Question?",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "What is the answer?",
        	    "How many points is this answer worth?",
        	    "",
        	    "Modifying Question: Test Question?",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "What is the answer?",
        	    "How many points is this answer worth?",
        	    "",
        	    "Modifying Question: Test Question?",
        	    "1: Add Answer",
        	    "2: Remove Answer",
        	    "3: Modify Answer",
        	    "4: View Question",
        	    "5: View Point Values",
        	    "6: Save Question",
        	    "",
        	    "Modifying Quiz: 'Test Quiz 2'",
        	    "Current Amount of Questions: 1",
        	    "1: View Questions",
        	    "2: Add Question",
        	    "3: Edit Questions",
        	    "4: Remove Question",
        	    "5: Change Name",
        	    "6: Change Course",
        	    "7: Set Scrambled",
        	    "8: Save Quiz",
        	    "9: Delete Quiz",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Please select a course.",
        	    "1: Test Course",
        	    "2: Exit",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Test Quiz 2",
        	    "3: Exit",
        	    "",
        	    "Quiz Info",
        	    "Quiz name: Test Quiz 2",
        	    "Author: Test User",
        	    "Course: Test Course",
        	    "Please press Enter to continue.",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Test Quiz 2",
        	    "3: Exit",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "Okay! Bye!",
        	};

            String errorMsg = "An error occurred when checking if multiple quizzes " + 
                "and questions can exist!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }
        
        @Test(timeout = 1000)
        public void testSubmit() {

        	// Create a 2 question quiz and second quiz
        	this.testQuizzes();
        	
        	// Then try take the quiz as a new student
            String[] inputArr = new String[] {
                "2", // Create User
                "Student Name", // Name
                "studentUsername", // Username
                "studentPassword", // Password
                "2", // Student
                "1", // Yes, Make it
                "1", // Login 
                "studentUsername", // Username
                "studentPassword", // Password
                "1", // View quizzes
                "1", // Course
                "1", // Quiz
                "", // Enter
                "3", // Back
                "2", // Take Quiz
                "1", // Select Course
                "1", // Select Quiz
                "1", // Yes, take it
                "1", // Question 1
                "2", // Question 2
                "1", // Submit
                "1", // See Score
                "1", // View Questions
                "1", // View question 1
                "", // Enter
                "3", // Exit
                "3", // Exit
                "5", // logout
                "3" // Exit
            };
            
            String[] expectedArr = new String[] {
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Creating a new user",
        	    "Please answer the following questions",
        	    "What is your name?",
        	    "What username do you want?",
        	    "What password do you want?",
        	    "Will you be a teacher or a student?",
        	    "1: Teacher",
        	    "2: Student",
        	    "",
        	    "The following are the values you gave:",
        	    "Username - studentUsername",
        	    "User Type - Student",
        	    "Name - Student Name",
        	    "Password - studentPassword",
        	    "",
        	    "Are you satisfied with these values?",
        	    "1: Yes",
        	    "2: No",
        	    "Successfully created the user.",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Password: ",
        	    "You have successfully logged in!",
        	    "",
        	    "Main Menu",
        	    "Please select one of the following options:",
        	    "1: View Quizzes",
        	    "2: Take Quiz",
        	    "3: View Your Quiz Submissions",
        	    "4: User Settings",
        	    "5: Logout",
        	    "",
        	    "Please select a course.",
        	    "1: Test Course",
        	    "2: Exit",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Test Quiz 2",
        	    "3: Exit",
        	    "",
        	    "Quiz Info",
        	    "Quiz name: Test Quiz",
        	    "Author: Test User",
        	    "Course: Test Course",
        	    "Please press Enter to continue.",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Test Quiz 2",
        	    "3: Exit",
        	    "",
        	    "Main Menu",
        	    "Please select one of the following options:",
        	    "1: View Quizzes",
        	    "2: Take Quiz",
        	    "3: View Your Quiz Submissions",
        	    "4: User Settings",
        	    "5: Logout",
        	    "",
        	    "Please select a course.",
        	    "1: Test Course",
        	    "2: Exit",
        	    "",
        	    "Please select a quiz to take.",
        	    "1: Test Quiz",
        	    "2: Test Quiz 2",
        	    "3: Exit",
        	    "",
        	    "Quiz Info",
        	    "Quiz name: Test Quiz",
        	    "Author: Test User",
        	    "Course: Test Course",
        	    "",
        	    "Would you like to take this quiz",
        	    "1: Yes",
        	    "2: No",
        	    "Okay. Beginning the quiz.",
        	    "",
        	    "Question 1",
        	    "",
        	    "Test Question?",
        	    "1: Correct Answer",
        	    "2: Incorrect Answer",
        	    "3: Import Response From File",
        	    "",
        	    "Question 2",
        	    "",
        	    "Question 2!",
        	    "1: Totally not correct",
        	    "2: Totally not incorrect",
        	    "3: Import Response From File",
        	    "",
        	    "You have finished the quiz.",
        	    "Would you like to submit it?",
        	    "1: Submit",
        	    "2: Cancel",
        	    "Successfully submitted the quiz.",
        	    "",
        	    "Would you like to see your score?",
        	    "1: Yes",
        	    "2: No",
        	    "",
        	    "Viewing your submission of Quiz: Test Quiz in course: Test Course",
        	    "Total Score: 1/2",
        	    "1: View All Questions",
        	    "2: View Incorrect Answers",
        	    "3: Exit",
        	    "",
        	    "Select a question to view it.",
        	    "1: Test Question?",
        	    "2: Question 2!",
        	    "3: Exit",
        	    "",
        	    "Question Info",
        	    "Test Question?",
        	    "1: Correct Answer",
        	    "2: Incorrect Answer",
        	    "The given answer was: 1: Correct Answer",
        	    "The best answer was: 1: Correct Answer",
        	    "Points earned: 1/1",
        	    "Please press Enter to continue.",
        	    "",
        	    "Select a question to view it.",
        	    "1: Test Question?",
        	    "2: Question 2!",
        	    "3: Exit",
        	    "",
        	    "Viewing your submission of Quiz: Test Quiz in course: Test Course",
        	    "Total Score: 1/2",
        	    "1: View All Questions",
        	    "2: View Incorrect Answers",
        	    "3: Exit",
        	    "",
        	    "Main Menu",
        	    "Please select one of the following options:",
        	    "1: View Quizzes",
        	    "2: Take Quiz",
        	    "3: View Your Quiz Submissions",
        	    "4: User Settings",
        	    "5: Logout",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "Okay! Bye!",
        	};

            String errorMsg = "Unable to take a quiz, and view the submission!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }
        
        @Test(timeout = 1000)
        public void testSubmissionTimestamp() {

        	// Take a quiz and submit it
        	this.testSubmit();
        	
        	// Then try view the submission later.
            String[] inputArr = new String[] {
                "1", // Login 
                "studentUsername", // Username
                "studentPassword", // Password
                "3", // View quiz submissions
                "1", // Quiz submission 1
                "2", // View incorrect answers
                "1", // Question 2 (choice 1 because got #1 correct)
                "", // Enter
                "2", // Exit
                "3", // Exit
                "5", // Logout
                "1", // Login 
                "testUsername", // Username
                "testPassword", // Password
                "2", // View quiz submissions
                "1", // Quiz submission 1
                "2", // View incorrect answers
                "1", // Question 2 (choice 1 because got #1 correct)
                "", // Enter
                "2", // Exit
                "3", // Exit
                "6", // Logout
                "3" // Exit
            };
            
            String[] expectedArr = new String[] {
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Password: ",
        	    "You have successfully logged in!",
        	    "",
        	    "Main Menu",
        	    "Please select one of the following options:",
        	    "1: View Quizzes",
        	    "2: Take Quiz",
        	    "3: View Your Quiz Submissions",
        	    "4: User Settings",
        	    "5: Logout",
        	    "",
        	    "Please Select one of the following quiz submissions.",
        	    "1: Student Name - Test Quiz - .*", // RegEx for any date
        	    "2: Exit",
        	    "",
        	    "Viewing your submission of Quiz: Test Quiz in course: Test Course",
        	    "Total Score: 1\\/2",
        	    "1: View All Questions",
        	    "2: View Incorrect Answers",
        	    "3: Exit",
        	    "",
        	    "Select a question to view it.",
        	    "1: Question 2!",
        	    "2: Exit",
        	    "",
        	    "Question Info",
        	    "Question 2!",
        	    "1: Totally not correct",
        	    "2: Totally not incorrect",
        	    "The given answer was: 2: Totally not incorrect",
        	    "The best answer was: 1: Totally not correct",
        	    "Points earned: 0\\/1",
        	    "Please press Enter to continue.",
        	    "",
        	    "Select a question to view it.",
        	    "1: Question 2!",
        	    "2: Exit",
        	    "",
        	    "Viewing your submission of Quiz: Test Quiz in course: Test Course",
        	    "Total Score: 1\\/2",
        	    "1: View All Questions",
        	    "2: View Incorrect Answers",
        	    "3: Exit",
        	    "",
        	    "Main Menu",
        	    "Please select one of the following options:",
        	    "1: View Quizzes",
        	    "2: Take Quiz",
        	    "3: View Your Quiz Submissions",
        	    "4: User Settings",
        	    "5: Logout",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Password: ",
        	    "You have successfully logged in!",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "1: Student Name - Test Quiz - .*", // RegEx for any date
        	    "2: Exit",
        	    "",
        	    "Viewing Student Name's submission of Quiz: 'Test Quiz' in course: Test Course",
        	    "Total Score: 1\\/2",
        	    "1: View All Questions",
        	    "2: View Incorrect Answers",
        	    "3: Exit",
        	    "",
        	    "Select a question to view it.",
        	    "1: Question 2!",
        	    "2: Exit",
        	    "",
        	    "Question Info",
        	    "Question 2!",
        	    "1: Totally not correct",
        	    "2: Totally not incorrect",
        	    "The given answer was: 2: Totally not incorrect",
        	    "The best answer was: 1: Totally not correct",
        	    "Points earned: 0\\/1",
        	    "Please press Enter to continue.",
        	    "",
        	    "Select a question to view it.",
        	    "1: Question 2!",
        	    "2: Exit",
        	    "",
        	    "Viewing Student Name's submission of Quiz: 'Test Quiz' in course: Test Course",
        	    "Total Score: 1\\/2",
        	    "1: View All Questions",
        	    "2: View Incorrect Answers",
        	    "3: Exit",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "Okay! Bye!",
        	};

            String errorMsg = "Unable to view quiz submissions!";

            tryTestInputRegex(inputArr, expectedArr, errorMsg);

        }
        
        @Test(timeout = 1000)
        public void testEditQuiz() {

        	// Create a few quizzes
        	this.testQuizzes();
        	
        	String[] inputArr = new String[] {
                "1", // Login 
                "testUsername", // Username
                "testPassword", // Password
                "4", // Modify quizzes
                "1", // Course 1
                "1", // Quiz 1
                "1", // View questions
                "3", // Exit
                "3", // Edit Questions
                "1", // Question 1 
                "1", // Add answer
                "New Answer", 
                "0", // Points Value
                "3", // Modify Answer
                "2", // Select second one 
                "Bad Answer", // new name
                "0", // new value
                "6", // Save question
                "2", // Add Question
                "1", // Multiple Choice
                "New Question!", 
                "1", // Add answer
                "Totally not copied", 
                "1", // Points Value
                "1", // Add Answer
                "Totally not wrong", 
                "0", // points 
                "6", // exit
                "4", // remove question
                "2", // Question 2
                "1", // Yes, want to delete
                "7", // Set scrambled
                "1", // Yes
                "1", // View questions
                "1", // View question 1
                "", // Enter
                "2", // View question 2
                "", // Enter
                "3", // Exit
                "8", // save quiz
                "1", // list quizzes
                "1", // select test course
                "1", // select first quiz
                "", // Enter to go back
                "3", // Go back
                "6", // Logout
                "3" // Exit
            };
        	
        	String[] expectedArr = new String[] {
    		    "",
    		    "Welcome to the Learning Management System!",
    		    "Please select one of the following options:",
    		    "1: Login",
    		    "2: Create User",
    		    "3: Exit",
    		    "",
    		    "Logging into the Learning Management System.",
    		    "Please enter your login details.",
    		    "Username: ",
    		    "Password: ",
    		    "You have successfully logged in!",
    		    "",
    		    "Teacher Menu",
    		    "1: List Quizzes",
    		    "2: List All Quiz Submissions",
    		    "3: Add New Quiz",
    		    "4: Modify Quizzes",
    		    "5: User Settings",
    		    "6: Logout",
    		    "",
    		    "Please select a course.",
    		    "1: Test Course",
    		    "2: Exit",
    		    "",
    		    "Please select a quiz to modify.",
    		    "1: Test Quiz",
    		    "2: Test Quiz 2",
    		    "3: Exit",
    		    "",
    		    "Modifying Quiz: 'Test Quiz'",
    		    "Current Amount of Questions: 2",
    		    "1: View Questions",
    		    "2: Add Question",
    		    "3: Edit Questions",
    		    "4: Remove Question",
    		    "5: Change Name",
    		    "6: Change Course",
    		    "7: Set Scrambled",
    		    "8: Save Quiz",
    		    "9: Delete Quiz",
    		    "",
    		    "Select a question to view it.",
    		    "1: Test Question?",
    		    "2: Question 2!",
    		    "3: Exit",
    		    "",
    		    "Modifying Quiz: 'Test Quiz'",
    		    "Current Amount of Questions: 2",
    		    "1: View Questions",
    		    "2: Add Question",
    		    "3: Edit Questions",
    		    "4: Remove Question",
    		    "5: Change Name",
    		    "6: Change Course",
    		    "7: Set Scrambled",
    		    "8: Save Quiz",
    		    "9: Delete Quiz",
    		    "",
    		    "Select a question to view it.",
    		    "1: Test Question?",
    		    "2: Question 2!",
    		    "3: Exit",
    		    "",
    		    "Modifying Question: Test Question?",
    		    "1: Add Answer",
    		    "2: Remove Answer",
    		    "3: Modify Answer",
    		    "4: View Question",
    		    "5: View Point Values",
    		    "6: Save Question",
    		    "What is the answer?",
    		    "How many points is this answer worth?",
    		    "",
    		    "Modifying Question: Test Question?",
    		    "1: Add Answer",
    		    "2: Remove Answer",
    		    "3: Modify Answer",
    		    "4: View Question",
    		    "5: View Point Values",
    		    "6: Save Question",
    		    "",
    		    "Select an answer to modify.",
    		    "1: Correct Answer",
    		    "2: Incorrect Answer",
    		    "3: New Answer",
    		    "4: Cancel",
    		    "",
    		    "Modifying the selected answer.",
    		    "What should the answer be?",
    		    "How many points should this answer be worth?",
    		    "",
    		    "Modifying Question: Test Question?",
    		    "1: Add Answer",
    		    "2: Remove Answer",
    		    "3: Modify Answer",
    		    "4: View Question",
    		    "5: View Point Values",
    		    "6: Save Question",
    		    "",
    		    "Modifying Quiz: 'Test Quiz'",
    		    "Current Amount of Questions: 2",
    		    "1: View Questions",
    		    "2: Add Question",
    		    "3: Edit Questions",
    		    "4: Remove Question",
    		    "5: Change Name",
    		    "6: Change Course",
    		    "7: Set Scrambled",
    		    "8: Save Quiz",
    		    "9: Delete Quiz",
    		    "What type of question do you want to add?",
    		    "1: Multiple Choice",
    		    "2: True or False",
    		    "3: Dropdown",
    		    "What is the question?",
    		    "",
    		    "Modifying Question: New Question!",
    		    "1: Add Answer",
    		    "2: Remove Answer",
    		    "3: Modify Answer",
    		    "4: View Question",
    		    "5: View Point Values",
    		    "6: Save Question",
    		    "What is the answer?",
    		    "How many points is this answer worth?",
    		    "",
    		    "Modifying Question: New Question!",
    		    "1: Add Answer",
    		    "2: Remove Answer",
    		    "3: Modify Answer",
    		    "4: View Question",
    		    "5: View Point Values",
    		    "6: Save Question",
    		    "What is the answer?",
    		    "How many points is this answer worth?",
    		    "",
    		    "Modifying Question: New Question!",
    		    "1: Add Answer",
    		    "2: Remove Answer",
    		    "3: Modify Answer",
    		    "4: View Question",
    		    "5: View Point Values",
    		    "6: Save Question",
    		    "",
    		    "Modifying Quiz: 'Test Quiz'",
    		    "Current Amount of Questions: 3",
    		    "1: View Questions",
    		    "2: Add Question",
    		    "3: Edit Questions",
    		    "4: Remove Question",
    		    "5: Change Name",
    		    "6: Change Course",
    		    "7: Set Scrambled",
    		    "8: Save Quiz",
    		    "9: Delete Quiz",
    		    "",
    		    "Select a question to delete it.",
    		    "1: Test Question?",
    		    "2: Question 2!",
    		    "3: New Question!",
    		    "4: Exit",
    		    "",
    		    "Question Info",
    		    "Question 2!",
    		    "1: Totally not correct",
    		    "2: Totally not incorrect",
    		    "",
    		    "",
    		    "Are you sure you want to delete this question?",
    		    "1: Yes",
    		    "2: No",
    		    "The question was succesfully deleted.",
    		    "",
    		    "Modifying Quiz: 'Test Quiz'",
    		    "Current Amount of Questions: 2",
    		    "1: View Questions",
    		    "2: Add Question",
    		    "3: Edit Questions",
    		    "4: Remove Question",
    		    "5: Change Name",
    		    "6: Change Course",
    		    "7: Set Scrambled",
    		    "8: Save Quiz",
    		    "9: Delete Quiz",
    		    "",
    		    "Should the quiz be scrambled?",
    		    "1: Yes",
    		    "2: No",
    		    "",
    		    "Modifying Quiz: 'Test Quiz'",
    		    "Current Amount of Questions: 2",
    		    "1: View Questions",
    		    "2: Add Question",
    		    "3: Edit Questions",
    		    "4: Remove Question",
    		    "5: Change Name",
    		    "6: Change Course",
    		    "7: Set Scrambled",
    		    "8: Save Quiz",
    		    "9: Delete Quiz",
    		    "",
    		    "Select a question to view it.",
    		    "1: Test Question?",
    		    "2: New Question!",
    		    "3: Exit",
    		    "",
    		    "Question Info",
    		    "Test Question?",
    		    "1: Correct Answer",
    		    "2: Bad Answer",
    		    "3: New Answer",
    		    "",
    		    "Please press Enter to continue.",
    		    "",
    		    "Select a question to view it.",
    		    "1: Test Question?",
    		    "2: New Question!",
    		    "3: Exit",
    		    "",
    		    "Question Info",
    		    "New Question!",
    		    "1: Totally not copied",
    		    "2: Totally not wrong",
    		    "",
    		    "Please press Enter to continue.",
    		    "",
    		    "Select a question to view it.",
    		    "1: Test Question?",
    		    "2: New Question!",
    		    "3: Exit",
    		    "",
    		    "Modifying Quiz: 'Test Quiz'",
    		    "Current Amount of Questions: 2",
    		    "1: View Questions",
    		    "2: Add Question",
    		    "3: Edit Questions",
    		    "4: Remove Question",
    		    "5: Change Name",
    		    "6: Change Course",
    		    "7: Set Scrambled",
    		    "8: Save Quiz",
    		    "9: Delete Quiz",
    		    "",
    		    "Teacher Menu",
    		    "1: List Quizzes",
    		    "2: List All Quiz Submissions",
    		    "3: Add New Quiz",
    		    "4: Modify Quizzes",
    		    "5: User Settings",
    		    "6: Logout",
    		    "",
    		    "Please select a course.",
    		    "1: Test Course",
    		    "2: Exit",
    		    "",
    		    "Please select a quiz to view.",
    		    "1: Test Quiz",
    		    "2: Test Quiz 2",
    		    "3: Exit",
    		    "",
    		    "Quiz Info",
    		    "Quiz name: Test Quiz",
    		    "Author: Test User",
    		    "Course: Test Course",
    		    "Please press Enter to continue.",
    		    "",
    		    "Please select a quiz to view.",
    		    "1: Test Quiz",
    		    "2: Test Quiz 2",
    		    "3: Exit",
    		    "",
    		    "Teacher Menu",
    		    "1: List Quizzes",
    		    "2: List All Quiz Submissions",
    		    "3: Add New Quiz",
    		    "4: Modify Quizzes",
    		    "5: User Settings",
    		    "6: Logout",
    		    "",
    		    "Welcome to the Learning Management System!",
    		    "Please select one of the following options:",
    		    "1: Login",
    		    "2: Create User",
    		    "3: Exit",
    		    "Okay! Bye!",
    		};
        	
            String errorMsg = "Unable to modify a quiz!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }

        @Test(timeout = 1000)
        public void testImportQuiz() {

        	clearFiles();
        	
            String[] inputArr = new String[] {
                "2", // Create User
                "Test User", // Name
                "testUsername", // Username
                "testPassword", // Password
                "1", // Teacher
                "1", // Yes, Make it
                "1", // Login 
                "testUsername", // Username
                "testPassword", // Password
                "3", // Add new Quiz
                "Test Quiz", 
                "Test Course", 
                "1", // Dont import
                "./TestImport.txt", // File path
                "1", // View qustions
                "4", // Exit
                "8", // save quiz
                "1", // list quizzes
                "1", // select test course
                "1", // select test quiz
                "", // Enter to go back
                "2", // Go back
                "6", // Logout
                "3" // Exit
            };
            
            String[] expectedArr = new String[] {
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Creating a new user",
        	    "Please answer the following questions",
        	    "What is your name?",
        	    "What username do you want?",
        	    "What password do you want?",
        	    "Will you be a teacher or a student?",
        	    "1: Teacher",
        	    "2: Student",
        	    "",
        	    "The following are the values you gave:",
        	    "Username - testUsername",
        	    "User Type - Teacher",
        	    "Name - Test User",
        	    "Password - testPassword",
        	    "",
        	    "Are you satisfied with these values?",
        	    "1: Yes",
        	    "2: No",
        	    "Successfully created the user.",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Password: ",
        	    "You have successfully logged in!",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Creating a new quiz.",
        	    "What would you like the name of this quiz to be?",
        	    "What is the name of the course this quiz will be in?",
        	    "Would you like to import this quiz from a file?",
        	    "1: Yes",
        	    "2: No",
        	    "",
        	    "Importing the quiz from a file.",
        	    "What is the file path?",
        	    "Successfully imported the quiz.",
        	    "",
        	    "Modifying Quiz: 'Test Quiz'",
        	    "Current Amount of Questions: 3",
        	    "1: View Questions",
        	    "2: Add Question",
        	    "3: Edit Questions",
        	    "4: Remove Question",
        	    "5: Change Name",
        	    "6: Change Course",
        	    "7: Set Scrambled",
        	    "8: Save Quiz",
        	    "9: Delete Quiz",
        	    "",
        	    "Select a question to view it.",
        	    "1: What is my name?",
        	    "2: This is true.",
        	    "3: What is the correct answer?",
        	    "4: Exit",
        	    "",
        	    "Modifying Quiz: 'Test Quiz'",
        	    "Current Amount of Questions: 3",
        	    "1: View Questions",
        	    "2: Add Question",
        	    "3: Edit Questions",
        	    "4: Remove Question",
        	    "5: Change Name",
        	    "6: Change Course",
        	    "7: Set Scrambled",
        	    "8: Save Quiz",
        	    "9: Delete Quiz",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Please select a course.",
        	    "1: Test Course",
        	    "2: Exit",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Exit",
        	    "",
        	    "Quiz Info",
        	    "Quiz name: Test Quiz",
        	    "Author: Test User",
        	    "Course: Test Course",
        	    "Please press Enter to continue.",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Exit",
        	    "",
        	    "Teacher Menu",
        	    "1: List Quizzes",
        	    "2: List All Quiz Submissions",
        	    "3: Add New Quiz",
        	    "4: Modify Quizzes",
        	    "5: User Settings",
        	    "6: Logout",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "Okay! Bye!",
        	};
        	
            String errorMsg = "Unable to import a quiz!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }
        
        @Test(timeout = 1000)
        public void testImportResponse() {

        	// Create a 2 question quiz and second quiz
        	this.testQuizzes();
        	
        	// Then try take the quiz as a new student
        	// With a file response
            String[] inputArr = new String[] {
                "2", // Create User
                "Student Name", // Name
                "studentUsername", // Username
                "studentPassword", // Password
                "2", // Student
                "1", // Yes, Make it
                "1", // Login 
                "studentUsername", // Username
                "studentPassword", // Password
                "1", // View quizzes
                "1", // Course
                "1", // Quiz
                "", // Enter
                "3", // Back
                "2", // Take Quiz
                "1", // Select Course
                "1", // Select Quiz
                "1", // Yes, take it
                "3", // Question 1
                "1", // Yes I want to import a response as a file
                "./QuestionResponse.txt", // File path
                "2", // Question 2 answer
                "1", // Submit
                "1", // See Score
                "1", // View Questions
                "1", // View question 1
                "", // Enter
                "3", // Exit
                "3", // Exit
                "5", // logout
                "3" // Exit
            };
            
            String[] expectedArr = new String[] {
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Creating a new user",
        	    "Please answer the following questions",
        	    "What is your name?",
        	    "What username do you want?",
        	    "What password do you want?",
        	    "Will you be a teacher or a student?",
        	    "1: Teacher",
        	    "2: Student",
        	    "",
        	    "The following are the values you gave:",
        	    "Username - studentUsername",
        	    "User Type - Student",
        	    "Name - Student Name",
        	    "Password - studentPassword",
        	    "",
        	    "Are you satisfied with these values?",
        	    "1: Yes",
        	    "2: No",
        	    "Successfully created the user.",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "",
        	    "Logging into the Learning Management System.",
        	    "Please enter your login details.",
        	    "Username: ",
        	    "Password: ",
        	    "You have successfully logged in!",
        	    "",
        	    "Main Menu",
        	    "Please select one of the following options:",
        	    "1: View Quizzes",
        	    "2: Take Quiz",
        	    "3: View Your Quiz Submissions",
        	    "4: User Settings",
        	    "5: Logout",
        	    "",
        	    "Please select a course.",
        	    "1: Test Course",
        	    "2: Exit",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Test Quiz 2",
        	    "3: Exit",
        	    "",
        	    "Quiz Info",
        	    "Quiz name: Test Quiz",
        	    "Author: Test User",
        	    "Course: Test Course",
        	    "Please press Enter to continue.",
        	    "",
        	    "Please select a quiz to view.",
        	    "1: Test Quiz",
        	    "2: Test Quiz 2",
        	    "3: Exit",
        	    "",
        	    "Main Menu",
        	    "Please select one of the following options:",
        	    "1: View Quizzes",
        	    "2: Take Quiz",
        	    "3: View Your Quiz Submissions",
        	    "4: User Settings",
        	    "5: Logout",
        	    "",
        	    "Please select a course.",
        	    "1: Test Course",
        	    "2: Exit",
        	    "",
        	    "Please select a quiz to take.",
        	    "1: Test Quiz",
        	    "2: Test Quiz 2",
        	    "3: Exit",
        	    "",
        	    "Quiz Info",
        	    "Quiz name: Test Quiz",
        	    "Author: Test User",
        	    "Course: Test Course",
        	    "",
        	    "Would you like to take this quiz",
        	    "1: Yes",
        	    "2: No",
        	    "Okay. Beginning the quiz.",
        	    "",
        	    "Question 1",
        	    "",
        	    "Test Question?",
        	    "1: Correct Answer",
        	    "2: Incorrect Answer",
        	    "3: Import Response From File",
        	    "",
        	    "Are you sure you want to import your response from a file?",
        	    "The format of the file should be the response you wish to give.",
        	    "Ex. \"1\" to select Answer #1",
        	    "1: Yes",
        	    "2: No",
        	    "",
        	    "Importing the answer from a file.",
        	    "What is the file path?",
        	    "",
        	    "Question 2",
        	    "",
        	    "Question 2!",
        	    "1: Totally not correct",
        	    "2: Totally not incorrect",
        	    "3: Import Response From File",
        	    "",
        	    "You have finished the quiz.",
        	    "Would you like to submit it?",
        	    "1: Submit",
        	    "2: Cancel",
        	    "Successfully submitted the quiz.",
        	    "",
        	    "Would you like to see your score?",
        	    "1: Yes",
        	    "2: No",
        	    "",
        	    "Viewing your submission of Quiz: Test Quiz in course: Test Course",
        	    "Total Score: 1/2",
        	    "1: View All Questions",
        	    "2: View Incorrect Answers",
        	    "3: Exit",
        	    "",
        	    "Select a question to view it.",
        	    "1: Test Question?",
        	    "2: Question 2!",
        	    "3: Exit",
        	    "",
        	    "Question Info",
        	    "Test Question?",
        	    "1: Correct Answer",
        	    "2: Incorrect Answer",
        	    "The given answer was: 1: Correct Answer",
        	    "The best answer was: 1: Correct Answer",
        	    "Points earned: 1/1",
        	    "Please press Enter to continue.",
        	    "",
        	    "Select a question to view it.",
        	    "1: Test Question?",
        	    "2: Question 2!",
        	    "3: Exit",
        	    "",
        	    "Viewing your submission of Quiz: Test Quiz in course: Test Course",
        	    "Total Score: 1/2",
        	    "1: View All Questions",
        	    "2: View Incorrect Answers",
        	    "3: Exit",
        	    "",
        	    "Main Menu",
        	    "Please select one of the following options:",
        	    "1: View Quizzes",
        	    "2: Take Quiz",
        	    "3: View Your Quiz Submissions",
        	    "4: User Settings",
        	    "5: Logout",
        	    "",
        	    "Welcome to the Learning Management System!",
        	    "Please select one of the following options:",
        	    "1: Login",
        	    "2: Create User",
        	    "3: Exit",
        	    "Okay! Bye!",
        	};

            String errorMsg = "Unable to import a response as a file!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }

    }

}