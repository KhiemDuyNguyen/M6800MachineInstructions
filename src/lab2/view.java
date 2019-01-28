package lab2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
/** The main view for Instruction interface
 *	@author Khiem Nguyen
 *  @version 0.2
 */

public class view extends JFrame
/** List of variables needed
 * @since 0.1
 */
{	
	//Texts panel
	JLabel lb1 = new JLabel("To Assembly Language");
	JLabel lb2 = new JLabel("Binary Instruction                       ");
	JLabel lb3 = new JLabel("                     Hex Instruction");
	JLabel errorLabel = new JLabel("");
	
	//Text field [instruction, binary, hex]
	JTextField tf1 = new JTextField(20);
	JTextField tf2 = new JTextField(20);
	JTextField tf3 = new JTextField(20);

	//Buttons
	JButton encode = new JButton("Encode");
	JButton decodeBin = new JButton("Decode Binary");
	JButton decodeHex = new JButton("Decode Hex");
	
	//panels to create the interface
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	JPanel p6 = new JPanel();
	JPanel dataP = new JPanel(new GridLayout(6,2));
	
	
/** The constructor of the view class
 * @since 0.1
 */
public view()
{   //General outline position
	setBounds(300,300,550,300);
    setTitle("M68000 Encode/Decode");
	setLayout(new BorderLayout());
	add(dataP,BorderLayout.CENTER);
	  
	//Set size of button
	encode.setPreferredSize(new Dimension(225, 30));  
	decodeBin.setPreferredSize(new Dimension(225, 30));  
	decodeHex.setPreferredSize(new Dimension(225, 30));  
	
	 //Add panel, textfield, and button to desired positions
	dataP.add(p1); p1.add(tf1); p1.add(encode);
    dataP.add(p2); p2.add(lb1);
    dataP.add(p3); p3.add(tf2); p3.add(tf3);
    dataP.add(p4); p4.add(lb2); p4.add(lb3);
    dataP.add(p5); p5.add(decodeBin); p5.add(decodeHex);
    dataP.add(p6); p6.add(errorLabel);
	
	setVisible(true);
}
	
/**
* Main method for view, used to check output
* @param args
*/
public static void main(String[ ] args)
{    new view ();  }	

}