cp ./src/main/*.java .
cp ./src/ui/*.java .
voccheck > voccheck_log
cat voccheck_log
rm ./*.java
