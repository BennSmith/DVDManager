=== DVD Manager ===

Code by: Ben Smith
Design by : Ben Smith
Copyright: 2016 smithb25@miamioh.edu

I. About
	
	DVD Manager is a program designed to assist managers of DVD rental stores in managing lists
	of their customers and the DVDs in stock.  Specifically the main purpose of the program is to
	allow the rental and return of dvds by customers, while not losing any data in the process.
	The program also contains other features allowing the manager to easily keep a database of all
	customers to the store and dvds that the store owns, with the ability to add and remove customers,
	add and remove dvds, add and remove a copy of a dvd, see the particular details of a dvd or customer,
	and to save and load the databases to .txt files.
	

II. How to Use
	
	1. 	Open the Program as a Project in Eclipse
	
	2. 	Welcome to the main program of the window.  There are three main areas: the menu bar at the top,
		the customer database on the left, and the dvd database on the right.  The following sections address
		what how to use each component.
		
		A.	The menu bar
			
			Save 	This button saves the current databases to dvd.txt and customer.txt, and archives
					the previous state of dvd.txt and customer.txt in the Archives folder in the following
					format:
					
					customer-archive-(date).txt
					dvd-archive-(date).txt
					
					This allows the manager in the event of a mistake to return to an archived states of the
					store, and copy the contents over to customer.txt and dvd.txt.
					
					Warning!  When copying from archived file, make sure to copy from the same date and time for
					customer and dvd, otherwise the data will not match up, resulting in loss or gain in dvds
					
			Load	This button loads to the database the customer and dvd data found in dvd.txt and customer.txt
			
			
		B.	The Customer Database
		
			The table contains a list of all the customers in the store, sorted by their account number.
			To get the specific details for a customer, select their name in the table and then double click.
			
			Search			This button filters the table by name based on the text that is entered in the text field next
							to the button.
					
			Clear Search	This button resets the table to display the entire list of customers.
			
			Add Customer	This button brings up a new window, where the user enters in the name of a new customer
							and the program generates a random account number for that customer, and adds it to the database
							
			Remove Customer	To use, select a customer in the table then press this button and the customer will be removed from
							the database, and all their rented dvds will be returned to the dvd database
							
			Rent DVD		To use, select a customer in the table then press rent dvd.  This will bring up a new window containing
							the list of dvds and a rent button.  Select a dvd and then click the rent button.  
							If no dvd is selected or there are no copies in store, an error message will appear in the box below.
							If the rental is successful, then a success message will appear in the box below.
							
			Return DVD		To use, select a customer in the table then press return dvd.  This will bring up a new window containing
							the list of dvds that the customer is currently renting.  Select the one that the customer is returning,
							and then press the return button, and dvd will be returned to the dvd database.
							If no dvd is selected, then an error message will appear in the box below.
							If the return is successful, then a success message will appear in the box below.
							
		B.	The DVD Database
		
			The table contains a list of all the dvds in the store, sorted by the titles.
			To get the specific details for a dvd, select its title in the table and then double click.
			
			Search			This button filters the table by title based on the text that is entered in the text field next
							to the button.
					
			Clear Search	This button resets the table to display the entire list of dvds.
			
			Add DVD			This button brings up a new window, where the user enters in the data for a new dvd
							and the program adds it to the database
							
			Remove DVD		To use, select a dvd in the table then press this button and the dvd will be removed from
							the database, as well as from the lists of rented dvds for the customers who had rented that movie
							
			Add Copy		To use, select a dvd in the table then press this button, and the number of copies of that movie will
							increase by 1
							
			Remove Copy		To use, select a dvd in the table then press this button, and the number of copies of that movie will
							decrease by 1
