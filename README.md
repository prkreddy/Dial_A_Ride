Dial_A_Ride
===========
International Institute of Information Technology, Bangalore.
CS501 Data Structures and Algorithms.
Mini Project Due on 30, November 2013.
In public intracity transport system (like BMTC in Bangalore), the pick-up and drop-off lo-
cations are determined by the service provider, not by the passenger. On the other hand, a taxi
(like merucabs, easy cabs in Bangalore) is a small vechile with a driver, used by individual (or a
small group of people) to travel between locations of their choice. The taxi service is the most
comfortable, but the costliest mode of transport, where as the public/bus system is the cheapest,
but least comfortable. Dial-a-ride is a form of public transport system which tries to achieve the
comfort level of a taxi service at low cost. It is characterized by flexible routing and scheduling of
small vehicles operating in shared-ride mode (there will be others who will share the vechile with
you during the journey) between pick-up and drop-off locations as requested by passengers. There
are many variants of this problem, we will consider one such (simple) variant for this project.
A typical passenger request in Dial a ride problem will look like,
< ElectronicCity, InternationalAirport, (10 : 40AM, 11 : 15AM ) >
meaning the passenger wants to go from Electronic City to Bangalore International Airport(BIA)
and he would like to be picked up in Electronic city between 10:40 AM and 11:15 AM. Such requests
are generally registered though telephone or Internet. It is not necessary that vechile will take the
shortest route, it might deviate from the shortest route in order to pick some other passenger,
but the passenger will be charged based on the shortest distance between the locations that he is
travelling. The main objective of the project is to schedule the cabs, in such a way as to maximize
the revenue.
You are given n(100) main locations of the city and distances to from each location to some of
its neighboring locations. You can compute the shortest distance from this data (you may assume
that every location is reachable from every other location). The amount of money that a passenger
will pay to go from location A to B is proportional to the shortest distance from A to B. You can
assume that the base rate is Rs.1 per KM.
You are given N (250) vehicles, each of capacity c(5). You can assume that the average speed of
these vehicle is 2 minutes per KM. You are also given the location of these vehicles at midnight.
You are given R(5000) passenger requests. A request is of the form A, B, t1 , t2 implying that the
passenger would like to picked up at location A between time t1 and t2 , and he should be dropped
at location B. For simplicity, you can assume that all the locations are integers between 1 and n
and the time is in minutes between 0 and 1440, with midnight as reference point. 10 : 40AM will
be noted as 640.
You have to write a program to schedule the N vehicles in such a way as to maximize the revenue.
You can decide to reject a passenger request (it may not be possible to meet all the requests).
The input will be given in the following format
nN cR
n ∗ n matrix indicating the distances to nearest locations.
A sequence of N locations - indicating where the vehicle is at midnight.
A sequence of R requests.
Expected output of the program is schedule for each vehicle and the total revenue generated.
For, example the input can be
5 10 5 20
-1 10 -1 -1 -1
10 -1 25 10 -1
-1 25 -1 20 30
-1 10 20 -1 -1
-1 -1 28 -1 -1
1123445521
1 4 600 645
5 1 610 623
2 5 639 672
1 2 619 623
1 5 640 720
2 5 647 723
4 2 625 739
1 4 700 745
4 1 239 839
2 4 739 752
1 3 719 923
2 5 654 729
4 5 704 723
4 2 625 739
2 5 645 725
2 1 647 723
4 3 655 739
1 4 720 745
4 2 239 539
2 4 390 752

