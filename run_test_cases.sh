javac -cp ./junit4.jar ./src/**/*.java
java -cp src:./junit4.jar main.JUnitTestCases
rm -rf ./src/**/*.class
