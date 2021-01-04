This is the source code of a test project for JarSoft by Mikhail Nikulin.
The project is a REST application with a Spring Boot (Maven) backend and Angular frontend, developed in Intellij IDEA environment.
Application is used to manage advertising banners and categories.

This is Spring Boot backend, Angular frontend you can get from this link https://github.com/mw2g/MNikulinJarSoftTestAngular

The application uses a mysql database. Before starting, you need to create a new database. 
Default parameters, base name: nikulintest, username test, password test. You can change them in your application.properties file. 
Flyway is used to create tables and enter test data. You can also disable it in application.properties if you don't need it.

Standard port of backend part is 8080, if needed you can change it in application.properties file. 
It will also need to be changed in the frontend (environment.ts file).

To get the banner text, use a URL like http://localhost:8080/bid?category=book
In response, the application returns the banner text from the category specified in the request. 
If there are multiple banners with this category, the banner with the highest price will be received. 
If multiple banners with the highest price are present, a random one will be returned. 
If a user with the same ip-address and user-agent is called again within one day, they will not see the advertisement shown earlier.
If no banners were found, then the server will return HTTP status 204.
