All:
	rm -r bin
	javac -cp . -sourcepath src/ -d bin/  src/*.java
	java -cp bin/ Main
