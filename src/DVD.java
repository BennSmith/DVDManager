import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;

/*	Author: 	Ben Smith
 * 	Date: 		April 24, 2016
 * 	
 * 	CSE 274 Final Project
 * 
 */

public class DVD implements Comparable<DVD> {

	// === Private Data ===

	private String name;
	private String[] starring;
	private String producer;
	private String director;
	private String productionCo;

	private int copies;

	// === Constructors ===

	// Changed starring to a new String 

	public DVD(String name, String[] starring, String producer, String director, String productionCo, int copies) {
		super();
		this.name = name;
		this.starring = starring;
		this.producer = producer;
		this.director = director;
		this.productionCo = productionCo;
		this.copies = copies;
	}

	public DVD(String name) {
		this(name, new String[] {"---"}, "---", "---", "---", 0);
	}

	public DVD() {
		this("---", new String[] {"---"}, "---", "---", "---", 0);
	}

	// === Compare To ===

	@Override
	public int compareTo(DVD o) {
		return this.name.compareTo(o.getName());
	}

	// === To String ===

	@Override
	public String toString() {

		/*String stars = "";
		for(String s : this.starring) {
			stars = stars + s + ", ";
		}*/

		return 	this.name + 
				"\n" + this.getStarringString() +
				"\n" + this.producer +
				"\n" + this.director +
				"\n" + this.productionCo +
				"\n" + this.copies;
	}

	// === To Single String ===

	public String toSingleString() {
		return 
				this.name + "/" + 
				this.getStarringString() + "/" + 
				this.producer + "/" +
				this.director + "/" +
				this.productionCo + "/" +
				this.copies;
	}

	// === Add Copy ===

	public void addCopy() {
		this.copies ++;
	}

	// === Available ===

	public boolean isAvailable() {
		if(this.copies > 0)
			return true;
		return false;
	}

	// === Save ===

	public void saveText(PrintWriter pw) {
		pw.println(name);
		pw.println(getStarringString());
		pw.println(producer);
		pw.println(director);
		pw.println(productionCo);
		pw.println("" + copies + "");
	}

	// === Load ===

	public void readText(Scanner in) {
		try {
			setName(in.nextLine());
			setStarring(in.nextLine().split(","));
			setProducer(in.nextLine());
			setDirector(in.nextLine());
			setProductionCo(in.nextLine());
			setCopies(Integer.parseInt(in.nextLine()));
		} catch(Exception e) {}
	}

	// === Getters and Setters ===

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getStarring() {
		return this.starring;
	}

	public String getStarringString() {
		String str = "";

		if(starring != null && starring.length > 0 && !starring[0].equals("")) {

			for(String s : starring) {
				str = str + s + ",";
			}

			str =  str.substring(0,str.length()-1);

		} else {
			str = "n/a";
		}
		return str;
	}

	public void setStarring(String[] starring) {
		this.starring = starring;
	}

	public String getProducer() {
		return this.producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getProductionCo() {
		return this.productionCo;
	}

	public void setProductionCo(String productionCo) {
		this.productionCo = productionCo;
	}

	public int getCopies() {
		return this.copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}
}
