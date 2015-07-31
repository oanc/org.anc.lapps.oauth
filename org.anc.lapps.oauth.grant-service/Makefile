help:
	@echo
	@echo "GOALS"
	@echo
	@echo "         war : generates the war file."
	@echo "         jar : generates a runnable jar file."
	@echo "       clean : removes build artifacts."
	@echo "      deploy : copies the war to the local tomcat."
	@echo "        help : prints this help screen."
	@echo
	
war:
	mvn package -Pwar
	
jar:
	mvn package -Pjar

clean:
	mvn clean
	
deploy:
	sudo cp target/planner.war /usr/share/tomcat/server-1/webapps
