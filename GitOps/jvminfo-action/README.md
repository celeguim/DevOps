# Prerequisites

#####
- JDK 11
- Maven 3

# Technologies 
- Spring MVC
- Maven
- JSP

# Database
Here, we used Mysql DB 
MySQL DB Installation Steps for Linux ubuntu 14.04:
- $ sudo apt-get update
- $ sudo apt-get install mysql-server

Then look for the file :
- /src/main/resources/db_backup.sql
- db_backup.sql file is a mysql dump file.we have to import this dump to mysql db server
- > mysql -u <user_name> -p accounts < db_backup.sql
