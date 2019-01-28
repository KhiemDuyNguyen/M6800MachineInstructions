package lab2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InvalidObjectException;
import java.util.StringTokenizer;

/**
 * model class contains methods to solve the problems
 * @author Khiem Nguyen
 * @version 1.1
 */

public class model 
{
	
//Control
private control c;
public model(control fromC) 
	{ c= fromC; }

IllegalAccessError instructionE = new IllegalAccessError("Instruction error");
IllegalAccessError operatorE = new IllegalAccessError("Operator error");
IllegalAccessError typeE = new IllegalAccessError("Type error");
IllegalAccessError varE = new IllegalAccessError("Var error");
InvalidObjectException registerE = new InvalidObjectException("Wrong register name");
IllegalAccessError registerNumbE = new IllegalAccessError("Wrong number of register");

/**
 * Convert short type to hex type string
 * @param number x needed to be converter
 * @return a string contains hex value of converted number
 */
String shortToHex(short x) {
	String ans="";
	for (int i=0; i<4; i++) {
		int hex = x & 15;
		char hexChar = "0123456789ABCDEF".charAt(hex);
		ans = hexChar + ans;
		x = (short)(x >> 4);
	}
	return ans;
}

/**
 * Convert short type to binary type string
 * @param number x needed to be converter
 * @return a string contains binary value of converted number
 */
String shortToBinary(short x) {
	String ans="";
	for(int i=0; i<16; i++) {
		ans = (x & 1) + ans;
		x = (short)(x >> 1);
	}
	return ans;
}

/**
 * Encode the human instruction to Assembly language with binary and hex instruction
 * @throws instructionE, operatorE, typeE, varE, registerE, registerNumbE
 */
void encode() {
	c.errorLabel.setText("");
	c.tf2.setText("");
	c.tf3.setText("");
	
	//Get text from instruction and token-ed it
	String instruction = c.tf1.getText().trim().toUpperCase();
	StringTokenizer tokenInstruction = new StringTokenizer(instruction);
	
	int binary = 0;
	String op = tokenInstruction.nextToken();
	StringTokenizer tokenOp = new StringTokenizer(op, ".", false);
	String operator = tokenOp.nextToken();
	
	//List of exceptions
	
	//Check length of instruction
	try {
	if (tokenInstruction.countTokens() != 1) {
		throw instructionE;
	}
	} catch (IllegalAccessError e) {
		c.errorLabel.setText("Illegal format for assembly instructionz");
		return;
	}
	
	//Check and convert operators
	try {
	if(operator.equals("ADD")) {
		binary = 0xD000;
	} else if(operator.equals("SUB")) {
		binary = 0x9000;
	} else if(operator.equals("MULS")) {
		binary = 0xC000;
	} else if(operator.equals("DIVS")) {
		binary = 0x8000;
	} else 
		throw operatorE;
	} catch (IllegalAccessError e) {
		c.errorLabel.setText("Illegal Operation for assembly instruction");
		return;
	}

	//Check and convert data type
	try {
	String type = tokenOp.nextToken();
	if(type.equals("B")) 
		binary = binary | 0x0000;
	else if(type.equals("W")) 
		binary = binary | 0x0040;
	else if(type.equals("L"))
		binary = binary | 0x0080;
	else 
		throw typeE;
	} catch (IllegalAccessError e) {
		c.errorLabel.setText("Illegal Data size for assembly instruction");
		return;
	}
	
	String variables = tokenInstruction.nextToken();
	StringTokenizer tokenVar = new StringTokenizer(variables, ",", false);
	
	//Check register length
	try {
	if (tokenVar.countTokens() != 2 || variables.endsWith(",")) 
		throw varE;
	} catch (IllegalAccessError e) {
		c.errorLabel.setText("Illegal format for assembly Registers");
		return;
	}
	
	//Check registers
	try {
	String begin = tokenVar.nextToken();
	String end = tokenVar.nextToken();
	if (begin.charAt(0) != 'D' || begin.length() != 2 || end.charAt(0) != 'D' || end.length() != 2)
		throw registerE;
	
	char char_var1 = begin.charAt(1);
	int num_var1 = Character.getNumericValue(char_var1);	
	char char_var2 = end.charAt(1);
	int num_var2 = Character.getNumericValue(char_var2);
	if (num_var1 > 7 || num_var2 > 7)
		throw registerNumbE;
	
	binary = binary | num_var1;
	binary = binary | (num_var2 << 9);
	
	if (c.errorLabel.getText().equals("")) {
		c.tf2.setText(shortToBinary((short)binary));
		c.tf3.setText(shortToHex((short)binary));
	}
	} catch(InvalidObjectException e) {
		c.errorLabel.setText("Illegal register name");
	}
	catch (IllegalAccessError e) {
		c.errorLabel.setText("Illegal register number, (0-7) only");
	}

}

/**
 * Decode binary instruction to hex instruction and human instruction
 * @throws Exception
 * @see decode
 */
void decodeBin() {
	c.errorLabel.setText("");
	c.tf1.setText("");
	c.tf3.setText("");
	
	String bin = c.tf2.getText();
	String s = c.tf2.getText().trim();
	if (bin.length()!=16) {
		c.errorLabel.setText("Binary number must be 16 bits");
		return;
	}
	int binary;
	try {
		binary = Integer.parseInt(s,2);
	} catch (Exception e) {
		c.errorLabel.setText("Illegal binary number");
		return;
	}
	c.tf3.setText(shortToHex((short)binary));
	decode(binary);
}

/**
 * Decode hex instruction to binary instruction and human instruction
 * @throws Exception
 * @see decode
 */
void decodeHex() {
	c.errorLabel.setText("");
	c.tf1.setText("");
	c.tf2.setText("");
	
	String hex = c.tf3.getText();
	String s = c.tf3.getText().trim();
	if (hex.length()!=4) {
		c.errorLabel.setText("Hex number must be 4 digits");
		return;
	}
	int binary;
	try {
		binary = Integer.parseInt(s,16);
	} catch (Exception e) {
		c.errorLabel.setText("Illegal hex number");
		return;
	}
	c.tf2.setText(shortToBinary((short)binary));
	decode(binary);
}

/**
 * Convert the number values into human instruction and display it
 * @param number values contains the human code
 * @throws operatorE, typeE, instructionE
 */
void decode(int binary) {
	String assem = "";
	
	//Check and display the operators
	try {
	if ((binary & 0xF000) == 0xD000)
		assem = "ADD.";
	else if ((binary & 0xF000) == 0x9000)
		assem = "SUB.";
	else if ((binary & 0xF000) == 0xC000) {
		assem = "MULS.";
	}
	else if ((binary & 0xF000) == 0x8000) {
		assem = "DIVS.";
	}
	else 
		throw operatorE;
	} catch (IllegalAccessError e) {
		c.errorLabel.setText("Illegal Operator");
		return;
	}
	
	//Check and display data type
	try {
	if (((binary >> 6) & 0x0007) == 0x0000)
		assem += "B ";
	else if (((binary >> 6)  & 0x0007) == 0x0001)
		assem += "W ";
	else if (((binary >> 6)  & 0x0007) == 0x0002)
		assem += "L ";
	else
		throw typeE;
	} catch (IllegalAccessError e) {
		c.errorLabel.setText("Illegal data size");
		return;	
	}
	
	//Check and display registers
	try {
	if (((binary >> 3) & 0x0007) != 0x0000)
		throw instructionE;
	int begin = binary & 0x0007;
	int end = (binary >> 9) & 0x0007;
	String beginStr = Integer.toString(begin);
	String endStr = Integer.toString(end);
	assem += "D" + beginStr + "," + "D" + endStr;
	c.tf1.setText(assem);
	} catch (IllegalAccessError e) {
		c.errorLabel.setText("Illegal Instruction, review instruction.");
		return;	
	}


	}
}











