package main;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;

import org.hamcrest.BaseMatcher;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import java.io.ByteArrayInputStream;
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

    public static class TestCase {
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

            // Runs the program with the input values
            // Replace TestProgram with the name of the class with the main method
            receiveInput(input);
            try {
            	LearningManagementSystem.main(new String[0]);
            } catch(Exception e) {
            	e.printStackTrace();
            }
            // Retrieves the output from the program
            String stuOut = ANSICodes.stripCodes(testOut.toString());

            // Trims the output and verifies it is correct.
            stuOut = stuOut.replace("\r\n", "\n");
            
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

        @Test(timeout = 1000)
        public void testCreateQuiz() {

            String[] inputArr = new String[] {
                "2", "Test User", "testUsername", "testPassword", "1", "1", "1","testUsername", "testPassword", "3",
                "Test Quiz", "Test Course", "2","2", "1", "Test Question?", "1", "Correct Answer", "1", "1",
                "Incorrect Answer", "0", "6", "7", "1", "1", "1", "2", "2","6","3"
            };

            String[] expectedArr = new String[] {
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
                "4: Change Name",
                "5: Change Course",
                "6: Set Scrambled",
                "7: Save Quiz",
                "8: Delete Quiz",
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
                "4: Change Name",
                "5: Change Course",
                "6: Set Scrambled",
                "7: Save Quiz",
                "8: Delete Quiz",
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
                "Okay! Bye!"

            };
            String errorMsg = "The output was not expected!";

            tryTestInput(inputArr, expectedArr, errorMsg);

        }


    }

}