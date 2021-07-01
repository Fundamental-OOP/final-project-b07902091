All:
	rm -r bin
	javac -cp . -sourcepath src/ -d bin/  src/*.java
	jar --create --file OOP_Final.jar --manifest manifest.MF --main-class Main -C bin/ .
	java -jar OOP_Final.jar
