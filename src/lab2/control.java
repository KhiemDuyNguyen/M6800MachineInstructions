package lab2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Controller class for the main view.
 * @author Khiem Nguyen
 * @version 0.1
 */

public class control extends view implements ActionListener
{
	model m = new model(this);
	
/**
 * Constructor with no parameter, add Actionlisterner to buttons
 */
public control()
{
	encode.addActionListener(this);
	decodeBin.addActionListener(this);
	decodeHex.addActionListener(this);
}

/**
 * Perform the action when each button is clicked
 * @param ActionEvent e (e is status when the button is clicked)
 */
public void actionPerformed(ActionEvent e)
{
	Object wb =  e.getSource(); 
	System.out.println("widget selected ");	
	if		(wb == encode ) 		m.encode();
	else if	(wb == decodeBin ) 		m.decodeBin();
	else if	(wb == decodeHex ) 		m.decodeHex();
}	

/**
 * Main method for control class, used to run the whole program
 * @param args
 */
public static void main(String[ ] args)
{    new control ();  }

}
