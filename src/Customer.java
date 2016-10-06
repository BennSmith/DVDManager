import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/*	Author: 	Ben Smith
 * 	Date: 		April 24, 2016
 * 	
 * 	CSE 274 Final Project
 * 
 */

public class Customer implements Comparable<Customer> {
	
	// === Private Data ===
	
	private String name;
	private String acctNum;
	private LinkedList<DVD> rented;
	
	// === Constructors ===
	
	public Customer(String name, String acctNum, LinkedList<DVD> rented) {
		this.name = name;
		this.acctNum = acctNum;
		this.rented = rented;
	}
	
	public Customer(String name, String acctNum) {
		this.name = name;
		this.acctNum = acctNum;
		this.rented = new LinkedList<DVD>();
	}
	
	public Customer() {
		this.name = "---";
		this.acctNum = "------";
		this.rented = new LinkedList<DVD>();
	}
	
	// === Rent ===
	
	public void rentDVD(DVD d) {
		this.rented.add(d);
	}
	
	// === Return ===
	
	public void returnDVD(DVD d) {
		this.rented.remove(
				Collections.binarySearch(rented, d)
				);
	}
	
	// === To String ===
	
	public String toString() {
		
		String dvds = "";
		for(int x = 0; x < rented.size(); x ++) {
			dvds = dvds + rented.get(x).getName() + "\n\t\t";
		}
		
		return	"Name:\t\t" + this.name +
				"\nAccount Num:\t" + this.acctNum +
				"\nRented DVDs:\t" + dvds;
	}
	
	// === Rented to String ===
	
	public String rentedString() {
		String str = "";
		
		for(DVD d : rented) {
			str = str + d.toSingleString() + ":";
		}
		if (str != null && !str.equals(""))
			return str.substring(0,str.length()-1);
		return str;
	}
	
	// === Compare To ===
	
	@Override
	public int compareTo(Customer o) {
		return this.acctNum.compareTo(o.getAcctNum());
	}
	
	// === Save ===
	
	public void saveText(PrintWriter pw) {
		pw.println(name);
		pw.println(acctNum);
		pw.println(rentedString());
	}
	
	// === Read ===
	
	public void readText(Scanner in) {
		try {
			setName(in.nextLine());
			setAcctNum(in.nextLine());
			setRented(new LinkedList<DVD>());
		
			String[] rentStr = in.nextLine().split(":");
			
			for(String rs : rentStr) {
				String[] dvdstr = rs.split("/");
				
				DVD d = new DVD();
			
				d.setName(dvdstr[0]);
				d.setStarring(dvdstr[1].split(","));
				d.setProducer(dvdstr[2]);
				d.setDirector(dvdstr[3]);
				d.setProductionCo(dvdstr[4]);
				d.setCopies(Integer.parseInt(dvdstr[5]));
			
				rented.add(d);
			}
		} catch (Exception e) {}
		
	}
	
	// === Getters and Setters ===
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAcctNum() {
		return this.acctNum;
	}
	
	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}
	
	public LinkedList<DVD> getRented() {
		return this.rented;
	}
	
	public void setRented(LinkedList<DVD> rented) {
		this.rented = rented;
	}
}
