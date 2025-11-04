# ConnectionPool Experiment

This project demonstrates the challenges of managing database connections without a connection pool, and how to solve these issues using a custom connection pool implemented with a `BlockingQueue` in Java.

---

## Overview

- **TooManyConnectionsDemo**:  
  Simulates opening many simultaneous connections to a MySQL database without any connection pooling.  
  This can lead to running out of available database connections, resulting in SQL exceptions such as "Too many connections".

- **ConnectionPoolDemo**:  
  Implements a simple connection pool using a fixed-size `ArrayBlockingQueue`.  
  Limits the number of active database connections by reusing the connections efficiently. Threads block if no connections are available, avoiding overload on the database.

---

## Technologies

- Java 8+
- MySQL (database server)
- JDBC (Java Database Connectivity)

---

## Configuration

Update the following fields in both demos according to your MySQL setup:

```java
private static final String URL = "jdbc:mysql://10.65.134.76:3310/sys";
private static final String USER = "root";
private static final String PASSWORD = "your_password_here";


How to Run
TooManyConnectionsDemo (Without Pooling)
This demo launches 5000 threads where each thread tries to open a new connection and hold it for 2 seconds.
This can overwhelm the database server, causing connection limit errors.
Run:
javac TooManyConnectionsDemo.java
java TooManyConnectionsDemo
ConnectionPoolDemo (With Pooling)
This demo creates a ConnectionPool with a fixed size (e.g., 5). Threads acquire a connection from the pool and release it after use, ensuring max simultaneous connections are limited and reused.
Run:
javac ConnectionPoolDemo.java
java ConnectionPoolDemo
Summary
Without pooling, databases can run out of connections under heavy load.
Implementing a connection pool limits concurrent connections and improves resource utilization.
This experiment shows the importance of connection pooling in database-driven applications.
