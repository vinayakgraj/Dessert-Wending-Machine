package dessertshop;

import java.util.Vector;

public class CheckOut {
	private Vector<DessertItem> myDessertItems = new Vector<DessertItem>();
	private int numberOfItems;
	private final int RECEIPT_WIDTH = 30;
	
	public CheckOut() {
		numberOfItems = 0;
	}
	
	public int numberOfItems() {
		return numberOfItems;
	}
	
	public void enterItem(DessertItem item) {
		this.myDessertItems.add(item);
		numberOfItems++;
	}
	
	public void clear() {
		for(int i = 0; i < numberOfItems; i++)
			this.myDessertItems.clear();
		numberOfItems = 0;
	}
	
	public int totalCost() {
		int sum = 0;
		for(int i = 0; i < numberOfItems; i++) 
			sum += myDessertItems.elementAt(i).getCost(); 
		return sum;
	}
	
	public int totalTax() {
		return (int)Math.round(this.totalCost() * DessertShoppe.TAX_RATE / 100.00);
	}
	
	public String toString() {
		String s = "";		// receipt
		
		
		s += "    " + DessertShoppe.STORE_NAME + "\n";
		s += "    " + "--------------------" + "\n";
		
		for(int j = 0; j < numberOfItems; j++){
		
			String l = myDessertItems.elementAt(j).getName();		
			//System.out.println(l);
			
			String p = DessertShoppe.cents2dollarsAndCents(myDessertItems.elementAt(j).getCost());	
			if (p.length() > DessertShoppe.COST_WIDTH)		
				p = p.substring(0, DessertShoppe.COST_WIDTH);
			
			if (myDessertItems.elementAt(j) instanceof Sundae) {		
				
				s += ((Sundae)myDessertItems.elementAt(j)).getTopping() + " Sundae with\n";
				
				while(l.length() < RECEIPT_WIDTH - p.length()){
					l += " ";
				}
				s += l + p + "\n";
			}
			
			else if (myDessertItems.elementAt(j) instanceof IceCream) {		
				
				s += DessertShoppe.cents2dollarsAndCents(((IceCream) myDessertItems.elementAt(j)).getCost()) + "\n";
				
				while(l.length() < RECEIPT_WIDTH - p.length()){
					l += " ";
				}
				s += l + p + "\n";
			}
		
			else if (myDessertItems.elementAt(j) instanceof Candy){		
				s += ((Candy) myDessertItems.elementAt(j)).getWeight() + " lbs @ " + 
						DessertShoppe.cents2dollarsAndCents(((Candy) myDessertItems.elementAt(j)).getPricePerPound()) + " /lb.\n";
				
				while(l.length() < RECEIPT_WIDTH - p.length()){
					l += " ";
				}
				s += l + p + "\n";	
			}
			else {		
				s += ((Cookie)myDessertItems.elementAt(j)).getNumber() + " @ " + 
						DessertShoppe.cents2dollarsAndCents(((Cookie)myDessertItems.elementAt(j)).getPricePerDozen()) + " /dz\n";
				
				while(l.length() < RECEIPT_WIDTH - p.length()){
					l += " ";
				}
				s += l + p + "\n";			
			}	
		}
		
		String line = "\nTax";		
		String tax = DessertShoppe.cents2dollarsAndCents(this.totalTax());	
		while(line.length() <= RECEIPT_WIDTH - tax.length())
			line += " ";
		s += line + tax;
		
		String totalCost = DessertShoppe.cents2dollarsAndCents(this.totalCost() + this.totalTax());	
		line = "\nTotal Cost";
		while(line.length() <= RECEIPT_WIDTH - totalCost.length())
			line += " ";
		s += line + totalCost;
	
		return s;
	}
}
