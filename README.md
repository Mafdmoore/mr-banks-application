# mr-banks-application

---RUNNING THE PROJECT---

This project should run properly from Eclipse after cloning the repository.

To do so, simply right click the project folder in the package explorer >> Run As >> Spring Boot App. The project can be accessed from the following URL:
	
	http://localhost:8080/


---TROUBLESHOOTING---

If the project does not run, the issue is most likely one of the following:
	1. The project is being run on an Eclipse installation that doesn't have the following plugins -
		a. Spring Tools (aka Spring IDE and Spring Tool Suite) 3.9.3.RELEASE
	   Installing the above via the Eclipse marketplace should fix this issue.
	2. Git has not downloaded one or more of the following templates -
		a. _main.html
		b. accountsPage.html
		c. createAccountPage.html
		d. createAccountSuccessfulPage.html
		e. editAccountPage.html
		f. editAccountSuccessfulPage.html
		g. welcomePage.html
	   Deleting the project off your local machine and re-cloning the repository from Github should fix this issue.
	3. The project is unable to recognise certain libraries and/or dependencies (such as the @EnableWebMvc tag and the WebMvcConfigurer interface).
	   This can be fixed with the following process -
		a. Locate your .m2 folder (default location is C:\Users\Admin\.m2) and delete everything inside \.m2\repository. You will need to close Eclipse for this.
		b. Reopen Eclipse, right click on the project folder in the package explorer >> Maven >> Update Project...
		c. Accept all defaults and click OK. Eclipse will then spend a few minutes repopulating the .m2\repository folder.
		d. Click on Project at the top of the Eclipse window >> Clean...