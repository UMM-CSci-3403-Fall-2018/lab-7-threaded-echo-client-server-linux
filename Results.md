Ran on Tedlar, used README.md as the file to echo.

5, 10, and 20 clients, are less than 1 second

40 clients: 2 seconds

80 clients: 4 seconds

100 clients: 25 seconds the first time, 6 seconds the second time

200 clients: 54 seconds the first time, 17 seconds the second time

##Summary
The multi-threaded server handled lots of clients very quickly, and could handle 200 in about
17 seconds. However it wasn't totally consistent, as the first time we had it run 200 clients 
it took nearly a minute. When we tried to run 400 clients, the computer became unresponsive and
we didn't let it finish, because it seems that this computer isn't capable of handling that many
threads.