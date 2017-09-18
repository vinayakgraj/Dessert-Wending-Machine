package dessertshop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GUI for the DessertItem class
 * @author Frederick Livingston, Mike Romeo
 * limitation:
 * Name: Must be a String
 * Price: Must contain only integers  (ex $1 -> 100)
 * Weight: Must be a double
 * Price/lbs, Price/doz, Number: Must contain only integers
 */
public class CheckoutGUI extends JFrame implements ActionListener 
{
    private CheckOut CheckOut= new CheckOut();
    private final static int INFO_SIZE = 30;
    private JTextField _info = new JTextField("Number of Items: 0",100);

    private String bnames[]={ "Ice Cream", "Candy", "Cookies", "Sundae"};
//    private String lnames[]={"Name", "Price", "Weight", "Price/lbs", "Price/doz", "Number"};
    private String lnames[] = 
	{
		"Name", "Price", "Weight", "Price/lbs", "Price/doz", "Number", "Topping",
		"Topping Cost"
	};
    private String bnames2[]={"Enter", "Total"};
    private String mnames[]={"Reset", "Exit"};

    private JButton buttons[];
    private JLabel labels[];
    private JButton buttons2[];   
    private JTextField tfields[];
    private JMenuItem menuitems[];

    private JMenuBar bar = new JMenuBar();
    private JMenu file = new JMenu("File");
    private int selecteditem=0;

    /**
     * Declares the Labels
     */
    private void setlabels()
	{
        labels = new JLabel[lnames.length];
        for (int i =0; i < lnames.length; i++) 
		{
            labels[i] = new JLabel(lnames[i]);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            labels[i].setEnabled(false);
        }
    }

    /**
     * Declares the Buttons
     */
    private void setbuttons()
	{
        buttons = new JButton[bnames.length];
        for (int i=0; i< bnames.length; i++) 
		{
            buttons[i] = new JButton( bnames[i]);
            buttons[i].addActionListener(this);

        }
    }

    /**
     * Declares the TextField
     */
    private void settextfield()
	{
        tfields = new JTextField[lnames.length];
        for (int i=0; i < lnames.length; i++) 
		{
            tfields[i] = new JTextField(INFO_SIZE);
            tfields[i].setEnabled(false);
        }
    }

    /**
     * Declares the Enter and Total Buttons
     */
    private void setbutton2(){
        buttons2 = new JButton[bnames2.length];
        for (int i =0; i < bnames2.length; i++) 
		{
            buttons2[i] = new JButton(bnames2[i]);
            buttons2[i].addActionListener(this);
        }
    }

    private void setmenubar()
	{
        menuitems = new JMenuItem[mnames.length];
        for (int i=0; i <mnames.length; i++) 
		{
            menuitems[i] = new JMenuItem(mnames[i]);
            menuitems[i].addActionListener(this);
        }

    }

    public CheckoutGUI(CheckOut CheckOut)
	{
        super("CheckOutGUI");
        CheckOut = this.CheckOut;
        setSize(600,300);
        setLocation(200,200);
        setlabels();
        setbuttons();
        settextfield();
        setbutton2();
        setmenubar();
        ContainerSetup();
        show();
    }



    public void actionPerformed( ActionEvent e)
	{
        Object source = e.getSource();

        if (source == menuitems[0])  //Clear
		{
            CheckOut.clear();
            _info.setText("Number of Items: 0");
            resetinfo();
            inablebuttonsAll();
            disableinfoAll();
        }
        else if (source == menuitems[1])  //Exit
		{
            System.exit(1);
        }
        else if (source == buttons[0])  // Ice Cream
		{
            inableinfo(0); //name
            inableinfo(1); //price
            //inableinfo(5); //number
            selecteditem=0;
        }
        else if (source == buttons[1])  //Candy
		{
            inableinfo(0); //name
            inableinfo(3); //price/lbs
            inableinfo(2); //weight
            //inableinfo(5); //number
            selecteditem=1;
        }
        else if (source == buttons[2])  //Cookie
		{
            inableinfo(0); //name
            //inableinfo(1); //price
            inableinfo(4); //price/doz
            inableinfo(5); //number
            selecteditem=2;
        }
        else if (source == buttons[3])  //Sundae
		{
            inableinfo(0); //name
            inableinfo(1); //price
            //inableinfo(4); //price/doz
//            inableinfo(5); //number
			inableinfo(6); // topping
			inableinfo(7); // topping cost
            selecteditem=3;
        }
        else if (source == buttons2[0])  //Enter
		{
            inablebuttonsAll();
            disableinfoAll();

            try 
			{
				switch (selecteditem) 
				{
					case 0: //Ice Cream
						CheckOut.enterItem( new IceCream(
							tfields[0].getText(), 
							Integer.parseInt(tfields[1].getText())
						));
						break;
					case 1:  //Candy
					    CheckOut.enterItem( new Candy(
							tfields[0].getText(),
							Double.parseDouble(tfields[2].getText()), 
							Integer.parseInt(tfields[3].getText())
						));
						break;
					case 2: //Baked Goods
						CheckOut.enterItem( new Cookie(
							tfields[0].getText(), 
							Integer.parseInt(tfields[5].getText()),
							Integer.parseInt(tfields[4].getText())
						));
						break;
					case 3: //Sundae
						CheckOut.enterItem( new Sundae(
							tfields[0].getText(),
//							Integer.parseInt(tfields[5].getText()),
							Integer.parseInt(tfields[1].getText()),
							tfields[6].getText(),
							Integer.parseInt(tfields[7].getText())
						));
						break;
                } // end switch

                _info.setText("Number of items: "+CheckOut.numberOfItems());
            } // end try

            catch (Exception ref) 
			{
                _info.setText("Invalid Entry, Number of Items: "
					+ CheckOut.numberOfItems()
				);
            }

            finally 
			{
                resetinfo();
            }
        }
        else if (source == buttons2[1])  //Total
		{
            ReceiptGUI r = new ReceiptGUI(CheckOut.toString());
            CheckOut.clear();
            _info.setText("Number of Items: 0");
            resetinfo();
            inablebuttonsAll();
            disableinfoAll();
        }

        for (int i=0; i <buttons.length; i++)  //types
		{
            if (source == buttons[i]) 
			{
                disablebuttons(i);
            }
        }
    }

    private void resetinfo()
	{
        for (int i=0; i< lnames.length; i++) 
		{
            tfields[i].setText("");
        }
    }

    private void disablebuttons(int b)
	{
        for (int i=0; i< buttons.length; i++) 
		{
            if (b != i) buttons[i].setEnabled(false);
        }
    }

    private void inablebuttonsAll()
	{
        for (int i=0; i< buttons.length; i++) 
		{
            buttons[i].setEnabled(true);
        }
    }

    private void inableinfo(int b)
	{
        for (int i=0; i< lnames.length; i++) 
		{
            if (b ==i) 
			{
                labels[i].setEnabled(true);
                tfields[i].setEnabled(true);
            }
        } // end for
    }

    private void disableinfoAll()
	{
        for (int i=0; i <lnames.length; i++) 
		{
            labels[i].setEnabled(false);
            tfields[i].setEnabled(false);
        }
    }

    class ReceiptGUI 
	{

        private JTextArea text = new JTextArea();
        private JFrame receipt = new JFrame("Receipt");
        
		public ReceiptGUI(String info)
		{
            Container p = receipt.getContentPane();
            receipt.setSize(235,600);
            p.add(new JScrollPane(text),BorderLayout.CENTER);
            text.setText(info);
            text.setEditable(false);
            text.setFont(new Font("Monospaced",Font.PLAIN,12));
            receipt.show();
        }
    }

    private void ContainerSetup()
	{
        Container c = getContentPane();

        for (int i=0; i< mnames.length; i++) file.add(menuitems[i]);
        bar.add(file);
        setJMenuBar(bar);

        //North Layout
        _info.setEditable(false);
        _info.setBackground(Color.white);
        c.add(_info,BorderLayout.NORTH);

        //South Layout
        JPanel spanel = new JPanel();
        for (int i=0; i < bnames2.length; i++) spanel.add(buttons2[i]);
        c.add(spanel,BorderLayout.SOUTH);

        //Center Layout
        JPanel cpanel = new JPanel();
        cpanel.setBorder(BorderFactory.createLoweredBevelBorder());
        cpanel.setLayout(new GridLayout(lnames.length,2));
        for (int i=0; i < lnames.length; i++) 
		{
            cpanel.add(labels[i]);
            cpanel.add(tfields[i]);
        }
        c.add(cpanel,BorderLayout.CENTER);

        //West Layout
        JPanel wpanel = new JPanel();
        wpanel.setLayout(new GridLayout(4,0));
        for (int i=0; i< bnames.length; i++) wpanel.add(buttons[i]);
        c.add(wpanel,BorderLayout.WEST);
    }

    public static void main (String args[] )
	{
        CheckoutGUI app = new CheckoutGUI(new CheckOut());

        app.addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) { System.exit(0); } 
		});

    }
} // end CheckOutGUI









