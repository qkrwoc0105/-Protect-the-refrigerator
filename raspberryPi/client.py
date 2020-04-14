# client.py
import socket               # Import socket module
s = socket.socket()         # Create a socket object
host = '192.168.137.1'
port = 12345                # Reserve a port for your service.
s.connect((host, port))
print (s.recv(1024))
s.close                     # Close the socket when done