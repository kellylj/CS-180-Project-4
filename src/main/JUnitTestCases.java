package main;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;

import org.junit.After;
import java.io.ByteArrayInputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import utils.ANSICodes;

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

            Assert.assertTrue("Ensure that `"+className+"` is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `"+className+"` is NOT `abstract`!", Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `"+className+"` extends `Object`!", Object.class, superclass);
            Assert.assertEquals("Ensure that `"+className+"` implements no interfaces!", 0, superinterfaces.length);
        }

        public void checkManagerClass(String className, Class<?> clazz) {
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;


            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `"+className+"` is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `"+className+"` is NOT `abstract`!", Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `"+className+"` extends `Object`!", Object.class, superclass);
            Assert.assertEquals("Ensure that `"+className+"` implements the Manager interface (it doesn't implement any)!", 1, superinterfaces.length);
            Assert.assertEquals("Ensure that `"+className+"` implements the Manager interface (what it implements isn't the Manager interface)!", Manager.class, superinterfaces[0]);
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

        }

        public void receiveInput(String input) {
        	System.setIn(new ByteArrayInputStream(input.getBytes()));
        }

        public void tryTestInput(String[] inputArr, String[] expectedArr, String errorMsg) {
        	// Converts the input and output arrays
            // into strings seperated by new lines.
            String input = "";
            for(int i = 0; i < inputArr.length; i++) {
            	input += inputArr[i] + "\n";
            }
            String expected = "";
            for(int i = 0; i < expectedArr.length; i++) {
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
            Assert.assertEquals(errorMsg, expected.trim(), stuOut.trim());
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

        // TODO Make more test cases
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

    }

}