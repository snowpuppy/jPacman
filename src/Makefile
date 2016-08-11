build:
	javac PacmanGame.java
	jar -cmvf META-INF/MANIFEST.MF PacmanGame.jar  ../images *.class
DrawImage.class: build
run: PacmanGame.class
	java -jar PacmanGame.jar
clean:
	rm *.class
