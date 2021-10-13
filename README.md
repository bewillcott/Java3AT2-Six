
# Java3 AT2 Question 4
This is a TAFE assignment for the Diploma in Software Development,
at the South Metropolitan TAFE, Rockingham, Western Australia.

This project contains a two Server CLient program; each use different
IPC mechanisms to communicate.  The programs have a login facility that
uses hashing techniques.

## Implementation

This project consists of four sub-projects within the aggregator project:

- Common
- GUIClient
- RMIServer
- SocketServer

### Common Library

This is a non-executable jar, containing a collection of classes that are in 
'common' use across multiple projects.

### GUI Client

This application allows the user to perform the functions:

- Create a new account
- Login to an existing account
- Chat to the echo server (Socket Server)

### RMI Server

This server application provides the user account functionality as follows:

- Create new accounts
- Validate login requests
- Store user account details in a CSV file
    - Username
    - Password: hashed

### Socket Server

This server application provides an echo chat functionality.

