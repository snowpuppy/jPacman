build:
	javac PacmanGame.java
	jar -cmvf META-INF/MANIFEST.MF PacmanGame.jar  ../images *.class
	cp pacmangame.sh pacmangame
	chmod u+x pacmangame
DrawImage.class: build
run: PacmanGame.class
	java -jar PacmanGame.jar
clean:
	rm *.class
install:
	install PacmanGame.jar $(DESTDIR)/usr/lib
	echo "java -jar $(DESTDIR)/usr/lib/PacmanGame.jar" > pacmangame
	chmod u+x pacmangame
	install pacmangame $(DESTDIR)/usr/bin
uninstall:
	rm $(DESTDIR)/usr/lib/PacmanGame.jar
	rm $(DESTDIR)/usr/bin/pacmangame
