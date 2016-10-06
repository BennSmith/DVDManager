/*	Author: 	Ben Smith
 * 	Date: 		May 6, 2016
 * 	
 * 	CSE 274 Final Project
 * 
 * 
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;



@SuppressWarnings("serial")
public class Store extends JPanel {

	private static Font TITLE_FONT = new Font("Sans-Serif", Font.PLAIN, 20);

	// === Private Data ===

	private LinkedList<DVD> dvdList = new LinkedList<DVD>();
	private LinkedList<Customer> customerList = new LinkedList<Customer>();

	// === GUI Data ===

	private Style benStyle = new Style();

	// === Window ===

	private JFrame window = new JFrame();

	// === Save/Load ===

	private JButton btnSave;
	private JButton btnLoad;
	private JPanel btnBlankStrip;

	// === DVD Table ===

	private JTextArea dvdTitle;
	private JScrollPane dvdPane;
	private JTable dvdTable;
	private DefaultTableModel dvdModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private DefaultTableModel dvdSearchModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	// === DVD Table Search ===

	private JTextField txtSearch;
	private JButton btnSearch;
	private JButton btnExitSearch;

	// === DVD List Buttons ===

	private JButton btnAddDVD;
	private JButton btnRemoveDVD;
	private JButton btnAddCopy;
	private JButton btnRemoveCopy;

	// === Customer List Table ===

	private JScrollPane customerPane;
	private JTable customerTable;
	private DefaultTableModel customerModel = new DefaultTableModel(){
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private DefaultTableModel customerSearchModel = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	// === Customer Table Search ===

	private JTextField txtCSearch;
	private JButton btnCSearch;
	private JButton btnExitCSearch;

	// === Customer List Buttons ===

	private JButton btnAddCustomer;
	private JButton btnRemoveCustomer;
	private JButton btnRent;
	private JButton btnReturn;

	// === Constructor ===

	public Store() {

		// === Set Up Window ===
		window.setBounds(50,70,1200,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.setResizable(false);

		window.setTitle("DVD Rental Store");
		window.getContentPane().setLayout(null);

		window.getContentPane().add(this);

		// === Customer List ===

		customerTable = new JTable(customerModel);
		customerPane = new JScrollPane(customerTable);
		customerTable.setFillsViewportHeight(true);
		customerPane.setBounds(25,90,400,400);

		initCustomerTable();

		// === Customer Details ===

		customerTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					getCDetails();
				}
			}
		});

		// === Customer List Search ===

		txtCSearch = new JTextField("");
		txtCSearch.setBounds(25,55,300,30);

		btnCSearch = new JButton("Search");
		btnCSearch.setBounds(325,55,100,30);

		btnCSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtCSearch.getText() != "")
					searchCustomerTable(txtCSearch.getText());
			}

		});

		// === Clear Customer Search ===

		btnExitCSearch = new JButton("Clear Search");
		btnExitCSearch.setBounds(25,495,400,30);

		btnExitCSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				customerTable.setModel(customerModel);

			}

		});

		// === Add Customer Button ===

		btnAddCustomer = new JButton("Add Customer");
		btnAddCustomer.setBounds(430,90,150,30);

		btnAddCustomer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCustomer();
			}



		});

		// === Remove Customer Button ===

		btnRemoveCustomer = new JButton("Remove Customer");
		btnRemoveCustomer.setBounds(430,120,150,30);

		btnRemoveCustomer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String acct = customerTable.getModel().getValueAt(
							customerTable.getSelectedRow(),
							((AbstractTableModel) customerTable.getModel()).findColumn("Account Number")
							).toString();
					Customer key = new Customer("?", acct);

					Collections.sort(customerList);
					int index = Collections.binarySearch(customerList, key);

					Customer found = customerList.get(index);

					for(DVD dRented : found.getRented()) {
						for(DVD dInStore : dvdList) {
							if(dRented.compareTo(dInStore) == 0) {
								dInStore.setCopies(dInStore.getCopies() + 1);
							}
						}
					}

					customerList.remove(index);

					((DefaultTableModel) customerTable.getModel()).removeRow(customerTable.getSelectedRow());
					updateCustomerList();
					updateDVDList();
				} catch(ArrayIndexOutOfBoundsException ex) {}
			}

		});

		// === Rent DVD Button ===

		btnRent = new JButton("Rent DVD");
		btnRent.setBounds(430,150,150,30);

		btnRent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				rentDVDMenu();
			}

		});

		// === Return DVD Button ===

		btnReturn = new JButton("Return DVD");
		btnReturn.setBounds(430,180,150,30);

		btnReturn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				returnDVD();
			}

		});

		// === DVD List Table ===

		dvdTable = new JTable(dvdModel);
		dvdPane = new JScrollPane(dvdTable);
		dvdTable.setFillsViewportHeight(true);
		dvdPane.setBounds(625,90,400,400);

		initDVDTable();

		// === DVD Details ===
		dvdTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
					getDetails();
			}
		});


		// === DVD List Search ===

		txtSearch = new JTextField("");
		txtSearch.setBounds(625,55,300,30);

		btnSearch = new JButton("Search");
		btnSearch.setBounds(925,55,100,30);

		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtSearch.getText() != "");
				searchDVDTable(txtSearch.getText());
			}

		});

		// === Clear DVD Search Button ===

		btnExitSearch = new JButton("Clear Search");
		btnExitSearch.setBounds(625,495,402,30);

		btnExitSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dvdTable.setModel(dvdModel);
			}

		});

		// === Add DVD Button ===

		btnAddDVD = new JButton("Add DVD");
		btnAddDVD.setBounds(1030,90,150,30);

		btnAddDVD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addDVD();
			}

		});

		// === DVD Remove Button ===

		btnRemoveDVD = new JButton("Remove DVD");
		btnRemoveDVD.setBounds(1030,120,150,30);

		btnRemoveDVD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = dvdTable.getModel().getValueAt(
							dvdTable.getSelectedRow(),
							((AbstractTableModel) dvdTable.getModel()).findColumn("Title")
							).toString();
					DVD key = new DVD(name);

					Collections.sort(dvdList);
					int index = Collections.binarySearch(dvdList, key);
					dvdList.remove(index);
					
					for(Customer c : customerList) {
						for(DVD d : c.getRented()) {
							if(d.compareTo(key) == 0)
								c.getRented().remove(d);
						}
					}

					((DefaultTableModel) dvdTable.getModel()).removeRow(dvdTable.getSelectedRow());
					updateDVDList();
				} catch(ArrayIndexOutOfBoundsException ex) {}
			}

		});

		// === Add Copy ===

		btnAddCopy = new JButton("Add Copy");
		btnAddCopy.setBounds(1030,150,150,30);

		btnAddCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addCopy();
				} catch(ArrayIndexOutOfBoundsException ex) {}
			}

		});

		// === Remove Copy ===

		btnRemoveCopy = new JButton("Remove Copy");
		btnRemoveCopy.setBounds(1030,180,150,30);

		btnRemoveCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					removeCopy();
				} catch(ArrayIndexOutOfBoundsException ex) {}
			}

		});

		// === Save Button ===

		btnSave = new JButton("Save");
		btnSave.setBounds(0,0,150,30);

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saveDVD();
					saveCustomer();
				} catch (IOException e1) {
					System.out.print("Failure to Save");
				}
			}

		});

		// === Load Button ===

		btnLoad = new JButton("Load");
		btnLoad.setBounds(150,0,150,30);

		btnLoad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					loadDVD();
					loadCustomer();
				} catch (IOException e1) {
					System.out.println("Failure to Load");
				}
			}

		});

		// === Save/Load Panel ===

		btnBlankStrip = new JPanel();
		btnBlankStrip.setBounds(300,0,900,30);

		// === Add Components ===

		benStyle.styleFrame(window);

		window.getContentPane().add(customerPane);

		benStyle.styleScrollPane(customerPane);

		window.getContentPane().add(btnSave);
		window.getContentPane().add(btnLoad);
		window.getContentPane().add(btnBlankStrip);

		benStyle.styleButton(btnSave);
		benStyle.styleButton(btnLoad);
		benStyle.styleJPanel(btnBlankStrip);

		window.getContentPane().add(txtCSearch);
		window.getContentPane().add(btnCSearch);
		window.getContentPane().add(btnExitCSearch);

		benStyle.styleTextField(txtCSearch);
		benStyle.styleButton(btnCSearch);
		benStyle.styleButton(btnExitCSearch);

		window.getContentPane().add(btnAddCustomer);
		window.getContentPane().add(btnRemoveCustomer);
		window.getContentPane().add(btnRent);
		window.getContentPane().add(btnReturn);

		benStyle.styleButton(btnAddCustomer);
		benStyle.styleButton(btnRemoveCustomer);
		benStyle.styleButton(btnRent);
		benStyle.styleButton(btnReturn);

		window.getContentPane().add(dvdPane);

		benStyle.styleScrollPane(dvdPane);

		window.getContentPane().add(txtSearch);
		window.getContentPane().add(btnSearch);
		window.getContentPane().add(btnExitSearch);

		benStyle.styleTextField(txtSearch);
		benStyle.styleButton(btnSearch);
		benStyle.styleButton(btnExitSearch);

		window.getContentPane().add(btnAddDVD);
		window.getContentPane().add(btnRemoveDVD);
		window.getContentPane().add(btnAddCopy);
		window.getContentPane().add(btnRemoveCopy);

		benStyle.styleButton(btnAddDVD);
		benStyle.styleButton(btnRemoveDVD);
		benStyle.styleButton(btnAddCopy);
		benStyle.styleButton(btnRemoveCopy);

		benStyle.styleFrame(window);

		window.setVisible(true);
	}

	// === Initialize Table ===
	/*
	 * Method which initializes the DVD Database for the Store
	 */
	public void initDVDTable() {
		dvdModel.addColumn("Title");
		dvdModel.addColumn("Copies");

		for(int x = 0; x < dvdList.size(); x ++) {
			String name = dvdList.get(x).getName();
			int copies = dvdList.get(x).getCopies();

			dvdModel.addRow(new Object[]{name, copies});
		}
	}

	// === Initialize Customer List ===
	/*
	 * Method which initializes the Customer Database for the store
	 * 
	 */

	public void initCustomerTable() {
		customerModel.addColumn("Name");
		customerModel.addColumn("Account Number");

		for(int x = 0; x < customerList.size(); x ++) {
			String name = customerList.get(x).getName();
			String acct = customerList.get(x).getAcctNum();

			customerModel.addRow(new Object[]{name, acct});
		}
	}

	// === Update DVD Table ===
	/*
	 * Method which updates the Table containing the DVD Database
	 */
	public void updateDVDList() {
		while(dvdModel.getRowCount() > 0) {
			dvdModel.removeRow(0);
		}

		Collections.sort(dvdList);

		for(int x = 0; x < dvdList.size(); x ++) {
			String name = dvdList.get(x).getName();
			int copies = dvdList.get(x).getCopies();

			dvdModel.addRow(new Object[]{name, copies});
		}

		dvdTable.setModel(dvdModel);
	}

	// === Update Customer Table ===
	/*
	 * Method which updates the Table containing the Customer Database
	 */
	public void updateCustomerList() {
		while(customerModel.getRowCount() > 0) {
			customerModel.removeRow(0);
		}

		Collections.sort(customerList);

		for(int x = 0; x < customerList.size(); x ++) {
			String name = customerList.get(x).getName();
			String acct = customerList.get(x).getAcctNum();

			customerModel.addRow(new Object[]{name, acct});
		}

		customerTable.setModel(customerModel);
	}

	// === Search DVD Table ===
	/*
	 * Method which enables the user to search for a specific DVD
	 */
	public void searchDVDTable(String key) {

		key = key.trim().toUpperCase();

		dvdSearchModel = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		dvdSearchModel.addColumn("Title");
		dvdSearchModel.addColumn("Copies");

		for(int x = 0; x < dvdList.size(); x ++) {
			String name = dvdList.get(x).getName();
			Integer copies = new Integer(dvdList.get(x).getCopies());

			if(name.toUpperCase().contains(key)) {
				dvdSearchModel.addRow(new Object[]{name, copies});
			}
		}

		dvdTable.setModel(dvdSearchModel);

	}

	// === Search Customer Table ===
	/*
	 * Method which enables the user to search for a specific Customer
	 */
	public void searchCustomerTable(String key) {

		key = key.trim().toUpperCase();

		customerSearchModel = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		customerSearchModel.addColumn("Name");
		customerSearchModel.addColumn("Account Number");

		for(int x = 0; x < customerList.size(); x ++) {
			String name = customerList.get(x).getName();
			String acct = customerList.get(x).getAcctNum();

			if(name.toUpperCase().contains(key)) {
				customerSearchModel.addRow(new Object[]{name, acct});
			}
		}

		customerTable.setModel(customerSearchModel);
	}

	// === Add Customer ===
	/*
	 * Method which enables the user to add a new Customer to the database
	 */
	public void addCustomer() {
		final JFrame addFrame = new JFrame();
		addFrame.setBounds(100,100,600,200);
		addFrame.setLayout(null);

		JPanel addPanel = new JPanel();
		addFrame.getContentPane().add(addPanel);

		JLabel lblName = new JLabel("Enter Name");
		lblName.setBounds(25,50,200,30);

		final JTextField txtName = new JTextField("");
		txtName.setBounds(225,50,300,30);

		JButton btnAdd = new JButton("Add Customer");
		btnAdd.setBounds(25,130,500,30);

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String acct = randAcct();

				if(
						!name.equals("") &&
						!acct.equals("")
						) {

					Customer newCustomer = new Customer(name, acct);

					customerList.add(newCustomer);
					updateCustomerList();

				}

				addFrame.setVisible(false);
				addFrame.dispose();
			}

		});

		addFrame.getContentPane().add(lblName);
		addFrame.getContentPane().add(txtName);
		addFrame.getContentPane().add(btnAdd);

		benStyle.styleTextField(txtName);
		benStyle.styleButton(btnAdd);

		addFrame.setVisible(true);
	}

	// === Add DVD ===
	/*
	 * Method which enables the user to add a new DVD to the database
	 */
	public void addDVD() {
		final JFrame addFrame = new JFrame();
		addFrame.setBounds(100,100,600,400);
		addFrame.setLayout(null);

		JPanel addPanel = new JPanel();
		addFrame.getContentPane().add(addPanel);

		JLabel lblTitle = new JLabel("Add DVD");
		lblTitle.setFont(TITLE_FONT);
		lblTitle.setBounds(250,0,100,50);

		JLabel lblDVDTitle = new JLabel("Enter Title");
		lblDVDTitle.setBounds(25,50,200,30);

		final JTextField txtDVDTitle = new JTextField("");
		txtDVDTitle.setBounds(225,50,300,30);

		JLabel lblDVDStars1 = new JLabel("Enter the Names of Stars");
		JLabel lblDVDStars2 = new JLabel("(ex. John Doe, Jane Doe, ...)");
		lblDVDStars1.setBounds(25,90,200,30);
		lblDVDStars2.setBounds(25,120,200,30);

		final JTextField txtDVDStars = new JTextField("");
		txtDVDStars.setBounds(225,90,300,60);

		JLabel lblProducer = new JLabel("Enter the Producer Name");
		lblProducer.setBounds(25,160,200,30);

		final JTextField txtProducer = new JTextField("");
		txtProducer.setBounds(225,160,300,30);

		JLabel lblDirector = new JLabel("Enter the Director Name");
		lblDirector.setBounds(25,200,200,30);

		final JTextField txtDirector = new JTextField("");
		txtDirector.setBounds(225,200,300,30);

		JLabel lblProductionCo = new JLabel("Enter the Production Company");
		lblProductionCo.setBounds(25,240,200,30);

		final JTextField txtProductionCo = new JTextField("");
		txtProductionCo.setBounds(225,240,300,30);

		JLabel lblCopies = new JLabel("Enter the number of copies");
		lblCopies.setBounds(25,280,200,30);

		final JTextField txtCopies = new JTextField();
		txtCopies.setBounds(225,280,300,30);

		JButton btnAdd = new JButton("Add DVD");
		btnAdd.setBounds(25,320,500,30);

		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtDVDTitle.getText();
				String stars = txtDVDStars.getText();
				String producer = txtProducer.getText();
				String director = txtDirector.getText();
				String productionCo = txtProductionCo.getText();
				String c = txtCopies.getText();
				int copies = 0;
				try {
					copies = Integer.parseInt(c);
				} catch(Exception ex) {}

				String[] starring = stars.split(", ");

				if(
						!name.equals("") &&
						!starring.equals("") &&
						!producer.equals("") &&
						!director.equals("") &&
						!productionCo.equals("") && 
						c != ""
						) {

					DVD newDVD = new DVD(name, starring, producer, director, productionCo, copies);

					dvdList.add(newDVD);
					updateDVDList();

				}

				addFrame.setVisible(false);
				addFrame.dispose();
			}

		});

		addFrame.getContentPane().add(lblTitle);
		addFrame.getContentPane().add(lblDVDTitle);
		addFrame.getContentPane().add(txtDVDTitle);
		addFrame.getContentPane().add(lblDVDStars1);
		addFrame.getContentPane().add(lblDVDStars2);
		addFrame.getContentPane().add(txtDVDStars);
		addFrame.getContentPane().add(lblProducer);
		addFrame.getContentPane().add(txtProducer);
		addFrame.getContentPane().add(lblDirector);
		addFrame.getContentPane().add(txtDirector);
		addFrame.getContentPane().add(lblProductionCo);
		addFrame.getContentPane().add(txtProductionCo);
		addFrame.getContentPane().add(lblCopies);
		addFrame.getContentPane().add(txtCopies);
		addFrame.getContentPane().add(btnAdd);

		benStyle.styleFrame(addFrame);
		benStyle.styleTextField(txtDVDTitle);
		benStyle.styleTextField(txtDVDStars);
		benStyle.styleTextField(txtProducer);
		benStyle.styleTextField(txtDirector);
		benStyle.styleTextField(txtProductionCo);
		benStyle.styleTextField(txtCopies);
		benStyle.styleButton(btnAdd);

		addFrame.setVisible(true);
	}

	// === Add Copy ===
	/*
	 * Method which enables the user to add a copy of a dvd to the dvd database
	 */
	public void addCopy() {
		String name = dvdTable.getModel().getValueAt(
				dvdTable.getSelectedRow(),
				((AbstractTableModel) dvdTable.getModel()).findColumn("Title")
				).toString();
		DVD key = new DVD(name);

		Collections.sort(dvdList);
		int index = Collections.binarySearch(dvdList, key);

		dvdList.get(index).addCopy();

		updateDVDList();
	}

	// === Remove Copy ===
	/*
	 * Method wich enables a user to remove a copy of a dvd from the database
	 */
	public void removeCopy() {
		String name = dvdTable.getModel().getValueAt(
				dvdTable.getSelectedRow(),
				((AbstractTableModel) dvdTable.getModel()).findColumn("Title")
				).toString();
		DVD key = new DVD(name);

		Collections.sort(dvdList);
		int index = Collections.binarySearch(dvdList, key);

		dvdList.get(index).setCopies(dvdList.get(index).getCopies() - 1);

		updateDVDList();
	}

	// === Get DVD Details ===
	/*
	 * Method which enables the user to get the details of a specific DVD
	 */
	public void getDetails() {

		String name = dvdTable.getModel().getValueAt(
				dvdTable.getSelectedRow(),
				((AbstractTableModel) dvdTable.getModel()).findColumn("Title")
				).toString();
		DVD key = new DVD(name);

		Collections.sort(dvdList);
		int index = Collections.binarySearch(dvdList, key);

		DVD found = dvdList.get(index);

		JFrame detailsFrame = new JFrame();
		detailsFrame.setBounds(100,100,800,400);
		detailsFrame.setLayout(null);

		JPanel detailsPanel = new JPanel();
		detailsFrame.getContentPane().add(detailsPanel);

		DefaultTableModel detailsModel = new DefaultTableModel();
		JTable detailsTable = new JTable(detailsModel);

		JScrollPane detailsPane = new JScrollPane(detailsTable);
		detailsTable.setFillsViewportHeight(true);
		detailsPane.setBounds(25,25,750,300);

		detailsModel.addColumn("");
		detailsModel.addColumn("");

		detailsModel.addRow(new Object[]{"Name", found.getName()});
		detailsModel.addRow(new Object[]{"",""});

		String[] starring = found.getStarring();
		detailsModel.addRow(new Object[]{"Starring", starring[0]});
		for(int x = 1; x < starring.length; x ++) {
			detailsModel.addRow(new Object[]{"", starring[x]});
		}
		detailsModel.addRow(new Object[]{"",""});

		detailsModel.addRow(new Object[]{"Producer", found.getProducer()});
		detailsModel.addRow(new Object[]{"",""});
		detailsModel.addRow(new Object[]{"Director", found.getDirector()});
		detailsModel.addRow(new Object[]{"",""});
		detailsModel.addRow(new Object[]{"Production Company", found.getProductionCo()});
		detailsModel.addRow(new Object[]{"",""});
		detailsModel.addRow(new Object[]{"Copies", found.getCopies()});
		detailsModel.addRow(new Object[]{"",""});

		detailsFrame.getContentPane().add(detailsPane);

		benStyle.styleScrollPane(detailsPane);

		detailsFrame.setVisible(true);
	}

	// === Get Customer Details ===
	/*
	 * Method which enables the use to get the details of a specific Customer
	 */
	public void getCDetails() {
		String acct = customerTable.getModel().getValueAt(
				customerTable.getSelectedRow(),
				((AbstractTableModel) customerTable.getModel()).findColumn("Account Number")
				).toString();
		Customer key = new Customer("", acct);

		Collections.sort(customerList);
		int index = Collections.binarySearch(customerList, key);

		Customer found = customerList.get(index);

		JFrame detailsFrame = new JFrame();
		detailsFrame.setBounds(100,100,800,400);
		detailsFrame.setLayout(null);

		JPanel detailsPanel = new JPanel();
		detailsFrame.getContentPane().add(detailsPanel);

		DefaultTableModel detailsModel = new DefaultTableModel();
		JTable detailsTable = new JTable(detailsModel);

		JScrollPane detailsPane = new JScrollPane(detailsTable);
		detailsTable.setFillsViewportHeight(true);
		detailsPane.setBounds(25,25,750,300);

		detailsModel.addColumn("");
		detailsModel.addColumn("");

		detailsModel.addRow(new Object[]{"Name", found.getName()});
		detailsModel.addRow(new Object[]{"",""});

		detailsModel.addRow(new Object[]{"Account Number", found.getAcctNum()});
		detailsModel.addRow(new Object[]{"",""});

		LinkedList<DVD> rented = found.getRented();
		if(!rented.isEmpty()) {
			detailsModel.addRow(new Object[]{"Rented Movies", rented.get(0).getName()});
			for(int x = 1; x < rented.size(); x ++) {
				detailsModel.addRow(new Object[]{"", rented.get(x).getName()});
			}
		} else {
			detailsModel.addRow(new Object[]{"Rented Movies",""});
		}
		detailsModel.addRow(new Object[]{"",""});

		detailsFrame.getContentPane().add(detailsPane);

		benStyle.styleScrollPane(detailsPane);

		detailsFrame.setVisible(true);
	}

	// === Rent DVD ===
	/*
	 * Method which creates a pop-up window that enables the user to rent
	 * a DVD for the selected customer
	 */
	public void rentDVDMenu() {
		try {
			String acct =  
					customerTable.getModel().getValueAt(
							customerTable.getSelectedRow(), 
							((AbstractTableModel) customerTable.getModel()).findColumn("Account Number")
							).toString();

			Collections.sort(customerList);
			int index = Collections.binarySearch(customerList, new Customer("",acct));
			final Customer found = customerList.get(index);

			final JFrame rentFrame = new JFrame();
			rentFrame.setBounds(100,100,800,600);
			rentFrame.setLayout(null);

			JPanel rentPanel = new JPanel();
			rentFrame.getContentPane().add(rentPanel);

			final JTable rentTable = new JTable(dvdModel);
			JScrollPane rentPane = new JScrollPane(rentTable);
			rentTable.setFillsViewportHeight(true);

			rentPane.setBounds(25,25,750,300);

			final JTextArea rentError = new JTextArea("");
			rentError.setBounds(25,375,750,60);

			JButton rentButton = new JButton("Rent Movie");
			rentButton.setBounds(25,335,750,30);
			rentButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						String key = rentTable.getValueAt(
								rentTable.getSelectedRow(), 
								((AbstractTableModel) rentTable.getModel()).findColumn("Title")
								).toString();
						Collections.sort(dvdList);
						DVD toRent = dvdList.get(Collections.binarySearch(dvdList, new DVD(key)));

						if(toRent.isAvailable()) {
							benStyle.styleTextAreaGood(rentError);
							rentError.setText("Movie Rented");
							found.rentDVD(toRent);
							toRent.setCopies(toRent.getCopies() - 1);
							updateDVDList();
						} else {
							benStyle.styleTextAreaBad(rentError);
							rentError.setText("Cannot Rent This Movie, There Are No Available Copies");
						}
					} catch(ArrayIndexOutOfBoundsException ex) {
						benStyle.styleTextAreaBad(rentError);
						rentError.setText("No DVD Selected");
					}
				}

			});

			rentFrame.getContentPane().add(rentPane);
			rentFrame.getContentPane().add(rentButton);
			rentFrame.getContentPane().add(rentError);

			benStyle.styleScrollPane(rentPane);
			benStyle.styleButton(rentButton);
			benStyle.styleTextArea(rentError);

			rentFrame.setVisible(true);
		} catch(ArrayIndexOutOfBoundsException e) {}
	}

	// === Return DVD ===
	/*
	 * Method which creates a pop-up window which enables the user to return a DVD for
	 * the selected Customer
	 */
	public void returnDVD() {
		try {
			String acct =  
					customerTable.getModel().getValueAt(
							customerTable.getSelectedRow(), 
							((AbstractTableModel) customerTable.getModel()).findColumn("Account Number")
							).toString();

			Collections.sort(customerList);
			int index = Collections.binarySearch(customerList, new Customer("",acct));
			final Customer found = customerList.get(index);

			final JFrame returnFrame = new JFrame();
			returnFrame.setBounds(100,100,800,500);
			returnFrame.setLayout(null);

			JPanel returnPanel = new JPanel();
			returnFrame.getContentPane().add(returnPanel);

			final DefaultTableModel returnModel = new DefaultTableModel();

			final JTable returnTable = new JTable(returnModel);
			JScrollPane returnPane = new JScrollPane(returnTable);
			returnTable.setFillsViewportHeight(true);
			returnPane.setBounds(25,25,750,300);

			returnModel.addColumn("");
			LinkedList<DVD> rented = found.getRented();
			for(int x = 0; x < rented.size(); x ++) {
				returnModel.addRow(new Object[]{rented.get(x).getName()});
			}

			final JTextArea returnError = new JTextArea();
			returnError.setBounds(25,405,750,60);

			JButton returnButton = new JButton("Return Movie");
			returnButton.setBounds(25,335,750,60);

			returnButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						String key = returnTable.getValueAt(
								returnTable.getSelectedRow(), 
								((AbstractTableModel) returnTable.getModel()).findColumn("")
								).toString();
						Collections.sort(dvdList);
						DVD toReturn = dvdList.get(Collections.binarySearch(dvdList, new DVD(key)));

						toReturn.setCopies(toReturn.getCopies() + 1);


						found.getRented().remove(Collections.binarySearch(found.getRented(), toReturn));
						returnModel.removeRow(returnTable.getSelectedRow());

						benStyle.styleTextAreaGood(returnError);
						returnError.setText("Movie Returned");

						updateDVDList();
						updateCustomerList();
					} catch(ArrayIndexOutOfBoundsException ex) {
						benStyle.styleTextAreaBad(returnError);
						returnError.setText("No DVD Selected");
					}
				}
			});

			returnFrame.add(returnPane);
			returnFrame.add(returnButton);
			returnFrame.add(returnError);

			benStyle.styleFrame(returnFrame);
			benStyle.styleButton(returnButton);
			benStyle.styleTextArea(returnError);

			returnFrame.setVisible(true);

		} catch (ArrayIndexOutOfBoundsException ex) {}
	}

	// === Save CustomerList to File ===
	/*
	 * Method which saves the data from customerList to customers.txt
	 */
	public void saveCustomer() throws IOException {
		archiveFile("customer", new File("customer.txt"));
		
		PrintWriter pw = new PrintWriter(new File("customer.txt"));

		for(Customer c : customerList) {
			c.saveText(pw);
		}

		pw.close();
	}

	// === Load CustomerList from File ===
	/*
	 * Method which loads the data from customer.txt to customerList
	 */
	public void loadCustomer() throws IOException {
		Scanner in = new Scanner(new File("customer.txt"));

		customerList.clear();
		
		while(in.hasNextLine()) {
			Customer c = new Customer();
			c.readText(in);
			customerList.add(c);
		}

		in.close();

		updateCustomerList();
	}

	// === Save DVDList to File ===
	/*
	 * Method which saves the data from dvdList to dvd.txt
	 */
	public void saveDVD() throws IOException {
		archiveFile("dvd", new File("dvd.txt"));
		
		PrintWriter pw = new PrintWriter(new File("dvd.txt"));

		for(DVD d : dvdList) {
			d.saveText(pw);
		}

		pw.close();
	}

	// === Load DVDList from File ===
	/*
	 * Method which reads the data from dvd.txt to dvdList
	 */
	public void loadDVD() throws FileNotFoundException {
		Scanner in = new Scanner(new File("dvd.txt"));

		dvdList.clear();

		while(in.hasNextLine()) {
			DVD d = new DVD();
			d.readText(in);
			dvdList.add(d);
		}

		in.close();

		updateDVDList();
	}
	
	// === Archive File ===
	/*
	 * Archives the old customer.txt or dvd.txt for retrieval purposes
	 * file saved as: <list type> archive <date>
	 * ex. dvd archive Fri 06 23-56-09 EDT 2016.txt
	 */
	
	public void archiveFile(String type, File org) {
		String date = new Date().toString();
		date.replaceAll(" ", "-");
		
		/*org.renameTo(new File(
				"/Users/BenSmith/Documents/workshop/CSE274_FinalProject/" +
				type + "-archive-" + date +".txt"
				));*/
		org.renameTo(new File("Archives/" + 
				type + "-archive-" + date +".txt"
				));
	}

	// === Generate Random Account Number ===
	/*
	 * Method which generates a random 10 character id number
	 */
	public String randAcct() {
		String str = "";
		Random rnd = new Random();
		for(int x = 0; x < 10; x ++) {
			str = str + (char) (rnd.nextInt(10) + 48); 
		}
		
		return str;
	}
}
