# Copy all java files to root directory, so that voccheck can check them
cp ./src/main/*.java .
cp ./src/ui/*.java .

# Run voccheck, and pipe the result to voccheck_log
voccheck 2>&1 | tee voccheck_log

# Remove all .java files, except Fake.java
ls | grep -v Fake.java | grep ".java" | xargs rm