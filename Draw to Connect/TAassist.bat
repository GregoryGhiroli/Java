echo —-Compile all of the code.
javac *.java

echo —-Create the jar files.
jar -cefv TAClient TAClient.jar WhiteBoard*.class TAClient*.class
jar -cefv TAServer TAServer.jar TAServer*.class

echo --Create the Java Docs
javadoc -link http://docs.oracle.com/javase/8/docs/api/ -nohelp -nodeprecatedlist -d doc -author -version -private *.java

echo —-Executes the TAServer and TAClient
start java -jar TAServer.jar
java -jar TAClient.jar


