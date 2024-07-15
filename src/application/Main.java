package application;
	
import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Main extends Application {
	
	String curr_ta="";
	
	@Override
	public void start(Stage primaryStage) {
		FileChooser fileChooser = new FileChooser();
		
		BorderPane bp=new BorderPane();
		bp.setStyle("-fx-Border-color: #ad7d5c;-fx-Border-width:2;");
		Scene s=new Scene(bp,900,500);
		primaryStage.setScene(s);
		primaryStage.setTitle("Project2");
		primaryStage.show();
		
		// top
		HBox hb_top=new HBox();
		Text t=new Text("File: ");
		t.setStyle("-fx-font-size:25;-fx-font-weight:bold;");
		
		Label l_path=new Label();
		l_path.setPrefSize(600, 35);
		l_path.setStyle("-fx-Border-color: Black;-fx-Border-width:2;-fx-font-size:15;-fx-font-weight:bold;-fx-text-fill: #d44350");
		
		Button b_load=new Button("Load");
		b_load.setPrefSize(150, 36);
		b_load.setStyle("-fx-Border-color: #39a265;-fx-Background-color: white;-fx-Border-width:2;-fx-font-size:15;-fx-font-weight:bold");
		
		hb_top.getChildren().addAll(t,l_path,b_load);
		hb_top.setSpacing(10);
		hb_top.setPadding(new Insets(20, 0, 5, 25)); // top, right, bottom, left
		hb_top.setStyle("-fx-Background-color: white;");
		
		
		VBox vb=new VBox();
		
		//center
		HBox hb_center=new HBox();
		TextArea ta=new TextArea();
		ta.setPrefWidth(850);
		ta.setPrefHeight(350);
		ta.setEditable(false);
		ta.setStyle("-fx-text-fill: #681729;-fx-font-size:15;-fx-font-weight:bold;-fx-Border-color: #f5cd37;-fx-Border-width:2;");
		hb_center.getChildren().add(ta);
		hb_center.setStyle("-fx-Background-color: white;");
		
		hb_center.setPadding(new Insets(20,0,10,23.4)); // top, right, bottom, left
		
		// bottom
		HBox hb_bottom=new HBox();
		Button b_prev=new Button("Prev");
		b_prev.setDisable(true);
		b_prev.setPrefSize(150, 36);
		b_prev.setStyle("-fx-Border-color: #39a265;-fx-Background-color: white;-fx-Border-width:2;-fx-font-size:15;-fx-font-weight:bold");
		Button b_next=new Button("Next");
		b_next.setDisable(true);
		b_next.setPrefSize(150, 36);
		b_next.setStyle("-fx-Border-color: #39a265;-fx-Background-color: white;-fx-Border-width:2;-fx-font-size:15;-fx-font-weight:bold");
		hb_bottom.getChildren().addAll(b_prev,b_next);
		hb_bottom.setSpacing(10);
		hb_bottom.setPadding(new Insets(0, 0, 0, 280)); // top, right, bottom, left
		
		
		vb.getChildren().addAll(hb_center,hb_bottom);
		vb.setStyle("-fx-Background-color: white;");
		bp.setTop(hb_top);
		bp.setCenter(vb);
		//bp.setBottom(hb_bottom);
		
		///////////////////////////////////////////////////////////////////////////////
		
		CursorArray<String> ca_data = new CursorArray<>(1000);
		int passlist = ca_data.createList();
		
		// button action:
		
		b_load.setOnAction(e1->{

			try {
				// to select a file.
	   		 	File selectedFile = fileChooser.showOpenDialog(primaryStage);
	   		 	// get data thats inside the file.
	   		 	selectedFile = new File(selectedFile.getAbsolutePath());
	   		 	l_path.setText(selectedFile.getAbsolutePath());
	   		 	Scanner in=new Scanner(selectedFile);
	   		 	
	   		 	String line="";
	   		 	
	   		 	String all_line="";
	   		 	
	   		 	String equation_section="";
	   		 	
	   		 	String equation_infix="";
	   		 	
	   		 	String equation_postfix="";

	   		 	
	   		 	//////////////////////////////
	   		 	boolean readInfix=false;
	   		 	boolean readPostfix=false;
	   		 	
	   		 	while(in.hasNextLine()) {
	   		 		line=in.nextLine();
	   		 		line=line.trim();
	   		 		all_line=all_line+line;
	   		 		// if the section of data end start new section
	   		 		if(line.equals("</section>")) {
	   		 			equation_section="Infix:"+"\n"+equation_infix+"\n"+"Postfix:"+"\n"+equation_postfix;
	   		 			
	   		 			
	   		 			ca_data.insertAtHead(equation_section, passlist);
	   		 			
	   		 			equation_infix="";
	   		 			readInfix=false;
	   		 			equation_postfix="";
	   		 			readPostfix=false;
	   		 			
	   		 			System.out.println("==================================================="+"\n");
	   		 			System.out.println("New Section: "+"\n\n");
	   		 		}
	   		 		
	   		 		// check to read infix or postfix
	   		 		if(line.equals("<infix>")) {
	   		 			readInfix=true;
	   		 			readPostfix=false;
	   		 		}
	   		 		
		   		 	if(line.equals("<postfix>")) {
	   		 			readInfix=false;
	   		 			readPostfix=true;
	   		 		}
	   		 		
	   		 		if(line.length()>=21 && readInfix==true) {
		   		 		String firstWord=line.substring(0,10);
	             		String finalWord=line.substring(line.length()-11,line.length());
	             		
	             		if(firstWord.equals("<equation>") && finalWord.equals("</equation>")) {
	             			String eq=line.substring(10, line.length()-11);
	             			eq=eq.trim();
	             			System.out.println("Infix: "+"\n");
	             			if(checkValidInfixEquation(eq)!=true) {
	             				System.out.println();
							}
							else if(checkInfixBalance(eq)!=true) {
								System.out.println("Unbalanced equation: "+eq);
							}
							else {
								String postfix=convert_infix_to_postfix(eq); // convert to postfix
								double calcPostfix= calculatePostFix(postfix); // calculate postfix
								
								equation_infix=equation_infix+( "* "+eq+" ==> "+postfix+" ==> "+calcPostfix+"\n");
								
							}
	             			
	             		}
             
	   		 		}
	   		 		
	   		 		
	   		 	if(line.length()>=21 && readPostfix==true) {
	   		 		String firstWord=line.substring(0,10);
             		String finalWord=line.substring(line.length()-11,line.length());
             		
             		if(firstWord.equals("<equation>") && finalWord.equals("</equation>")) {
             			String eq=line.substring(10, line.length()-11);
             			eq=eq.trim();
             			System.out.println("Postfix: "+"\n");
             			if(checkValidPostfixEquation(eq)!=true) {
             				System.out.println();
						}
						else {
							String prefix=convert_postfix_to_prefix(eq); // convert to postfix
							double calcPrefix= calculatePrefix(prefix); // calculate postfix
							
							equation_postfix=equation_postfix+( "* "+eq+" ==> "+prefix+" ==> "+calcPrefix+"\n");
							
						}
             			
             		}
         
   		 		}
	   		 		
	   		 		
	   		 	}
	   		 	
	   		 	if(!checkValidFile(all_line)) {
	   		 		ta.setText("Error: Invalid file");
	   		 		ta.setStyle("-fx-text-fill: #FF0000;-fx-font-size:15;-fx-font-weight:bold;-fx-Border-color: #f5cd37;-fx-Border-width:2;");
	   		 	}
	   		 	else {
	   		 		ta.setStyle("-fx-text-fill: #681729;-fx-font-size:15;-fx-font-weight:bold;-fx-Border-color: #f5cd37;-fx-Border-width:2;");
	   		 		ta.setText(ca_data.getEnd(passlist));
	   		 		curr_ta=ca_data.getEnd(passlist);

	   		 		b_next.setDisable(false);
	   		 	}
	   		 	
	   		 	
			}
			catch(Exception e_error) {
        		//e_error.printStackTrace();
        	}
			
   		 
		});
		
		b_next.setOnAction(e->{
			curr_ta=ca_data.getNext(curr_ta, passlist);
			ta.setText(curr_ta);
			if(ca_data.getNext(curr_ta, passlist).equals("dummy")) {
				b_next.setDisable(true);
			}
			b_prev.setDisable(false);
			
		});
		
		b_prev.setOnAction(e->{
			curr_ta=ca_data.getPrev(curr_ta, passlist);
			ta.setText(curr_ta);
			if(ca_data.getPrev(curr_ta, passlist)==null) {
				b_prev.setDisable(true);
			}
			b_next.setDisable(false);
		});
		
		
	}
	
	
	
	// checker method 
	public static boolean checkValidFile(String s) {
		CursorStack<String> stack = new CursorStack<>(1000);
		boolean res=true;
		
		///////////////////
		if(s.length()>0) {
		for(int i=0;i<s.length();i++) {
			
			int firstIndex = s.indexOf('<', i);
	        if (firstIndex == -1) {
	            break;
	        }

	        int finalIndex = s.indexOf('>', firstIndex + 1);
	        if (finalIndex == -1) {
	            res = false;
	        }

	        String tag = s.substring(firstIndex + 1, finalIndex);
	        if (tag.startsWith("/")) {
	            if (stack.isEmpty()) {
	                res = false;
	            }
	            String startTag = stack.pop();
	            if (!tag.substring(1).equals(startTag)) {
	                res = false;
	            }
	        } else {
	            stack.push(tag);
	        }
	        i = finalIndex;
		 }
		}
		else {
			res=false;
		}
		///////////////
		
		return res;
	}

	public static boolean checkInfixBalance(String s) {
		CursorStack<String> balancedStack = new CursorStack<>(1000);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c + "") {
			case "{":
			case "[":
			case "(":
			case "<":
			
				balancedStack.push(c + "");
				break;
			case "}":
			case "]":
			case ")":
			case ">":

				if (balancedStack.isEmpty())
					return false;

				char o = balancedStack.pop().charAt(0);

				if (!((c == '}' && o == '{') || (c == ']' && o == '[') || (c == ')' && o == '(')
						|| (c == '>' && o == '<')))
					return false;
			}
		}
		return (balancedStack.isEmpty()) ? true : false;
	}
	
	public static boolean checkValidInfixEquation(String equation) {
	    CursorStack<Character> stack = new CursorStack<>(1000);
	    boolean res = true;
	    equation = equation.replaceAll("\\s", ""); // to remove all spaces, not trim (ex: of trim: " 1 2 " --> "1 2").
	    
	    //System.out.println("########## Checker Of Valid Equation ##########\n");
	    for(int i=0 ;i<equation.length() ;i++) {
	    	
	    	char c = equation.charAt(i);
	    	//////////////////////////////////////////////////////////////////////////////////////////
	    	//1) if in the first of equation or in the end of it there is operator --> false.
	    	// ex: +12 or 12-
	        if ((i == 0 || i == (equation.length()-1) ) && (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^')) {
	        	if(i==0) {
	        		System.out.println("Invalid Infix Equation: "+equation+"\n"
	        	                      +"Error: in the first of equation there is "+c);
	        	}
	        	else {
	        		System.out.println("Invalid Infix Equation:"+equation+"\n"
	        						  +"Error: At the end of equation there is "+c);
	        	}
	        	System.out.println("--------------------------------------------------------------------");
	        	////////////////////
	        	
	            res = false;
	            break;
	        }
	        
		    //////////////////////////////////////////////////////////////////////////////////////////
	        //2) if in the first of equation or in the end of it there is incorrect parentheses --> false.
	        // ex: )1+2 or 1+2(
	        else if( (i==0 && c==')') || (i==(equation.length()-1) && c=='(')) {
	        	if(i==0) {
	        		System.out.println("Invalid Infix Equation: "+equation+"\n"
	        	                      +"Error: in the first of equation there is incorrect parentheses '"+c+"'");
	        	}
	        	else {
	        		System.out.println("Invalid Infix Equation:"+equation+"\n"
	        						  +"Error: At the end of equation there is incorrect parentheses '"+c+"'");
	        	}
	        	System.out.println("--------------------------------------------------------------------");
				////////////////////
	        	res = false;
	            break;
	        }
	        
	      //////////////////////////////////////////////////////////////////////////////////////////
	      //3) if there is an multiple operator respectively --> false.
          // ex: 2++2
	      else if((c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^') && !Character.isDigit(equation.charAt(i + 1))&&
	        		 equation.charAt(i + 1)!='(' && equation.charAt(i + 1)!=')' ) {
	        	
	        	System.out.println("Invalid Infix Equation: "+equation+"\n"
	                      +"Error: there is multiple operator respectively ("+c+" and "+equation.charAt(i + 1)+")");
	        	System.out.println("--------------------------------------------------------------------");
				////////////////////
		        res = false;
		        break; 
	        }
	        
		   //////////////////////////////////////////////////////////////////////////////////////////
	       //4) if there is an parentheses'(' before the operator --> false.
	       // ex: (+3+4
	       else if( (c=='(') && ( (equation.charAt(i + 1)=='+')||(equation.charAt(i + 1)=='-')||(equation.charAt(i + 1)=='*')||
	        		                 (equation.charAt(i + 1)=='/')||(equation.charAt(i + 1)=='%')||(equation.charAt(i + 1)=='^') )){
	        	   System.out.println("Invalid Infix Equation: "+equation+"\n"
        				             +"Error: there is parentheses '"+c+"' "+" before the operator("+equation.charAt(i + 1)+")");
		    	   System.out.println("--------------------------------------------------------------------");
					////////////////////
			        res = false;
			        break; 	
	        }
	        
		   //////////////////////////////////////////////////////////////////////////////////////////
	       //5) if there is an parentheses'(' after the operand --> false.
		   // ex: 1(2+3
	        else if( (i>1) && (c=='(') && (Character.isDigit(equation.charAt(i - 1)) ) ) {
	        	
	        		System.out.println("Invalid Infix Equation: "+equation+"\n"
				                      +"Error: there is parentheses '"+c+"' "+" after the operand("+equation.charAt(i + 1)+")");
	        	
	        	 System.out.println("--------------------------------------------------------------------");
					////////////////////
			        res = false;
			        break; 
	        }
		   //////////////////////////////////////////////////////////////////////////////////////////
	       //6) if there is an parentheses')' after the operator --> false.
		   // ex: 1+2+) //
	        else if(  (c==')') && ( (equation.charAt(i - 1)=='+')||(equation.charAt(i - 1)=='-')||(equation.charAt(i - 1)=='*')||
	                 (equation.charAt(i - 1)=='/')||(equation.charAt(i - 1)=='%')||(equation.charAt(i - 1)=='^') )){
	        			System.out.println("Invalid Infix Equation: "+equation+"\n"
	        							  +"Error: there is parentheses '"+c+"' "+" after the operator("+equation.charAt(i - 1)+")");
	        			System.out.println("--------------------------------------------------------------------");
	        			////////////////////
	        			res = false;
	        			break; 	
	        }
		   //////////////////////////////////////////////////////////////////////////////////////////
	       //7) if there is an parentheses')' before the operand --> false.
		   // ex: 1+2)6
	        else if((i<equation.length()-1) && (c==')') && (Character.isDigit(equation.charAt(i + 1)) ) ) {
	        		System.out.println("Invalid Infix Equation: "+equation+"\n"
				                      +"Error: there is parentheses '"+c+"' "+" before the operand("+equation.charAt(i + 1)+")");
	        	
	        	 System.out.println("--------------------------------------------------------------------");
					////////////////////
			        res = false;
			        break; 
	        }
		   //////////////////////////////////////////////////////////////////////////////////////////
	       //8) if it's just a number !!!!!!!!!!!!!!!!!!!!!!!!
	        else if(!(c == '+' && c == '-' && c == '*' && c == '/' && c == '%' && c == '^')) {
	
	        	int no_operator=0; // if there is no operator --> increase no_operator 1.
	        	no_operator++;
	        	
	        	 if(no_operator==equation.length()-1) { // if no_operator equal to the length-1 --> there is no operator.
		        	System.out.println("Invalid Infix Equation: "+equation+"\n"
		                      +"Error: it's not equation");
		        	System.out.println("--------------------------------------------------------------------");
					////////////////////
		        	res = false;
		        	break;
	        }
	        }
	        
	        
	      //////////////////////////////////////////////////////////////////////////////////////////
	        else {
	        	stack.push(c);
	        }

	    }
	    // if it's valid equation
		if (res == true) {
			//System.out.println("");
			System.out.println("Valid Infix Equation: "+equation);
			System.out.println("--------------------------------------------------------------------");
		}
	    
	    return res;
	}
	
	public static boolean checkValidPostfixEquation(String equation) {
	   if(convert_postfix_to_prefix(equation).equals(" ")) {
		   System.out.println("Invalid Infix Equation: "+equation);
		   System.out.println("--------------------------------------------------------------------");
		   return false;
	   }
	   else {
		   System.out.println("Valid Postfix Equation: "+equation);
		   System.out.println("--------------------------------------------------------------------");
		   return true;
	   }
	}


	
	
	
	// convert and calculate PostFix from (Infix)
	public static String convert_infix_to_postfix(String infix) {
	    CursorStack<String> stack = new CursorStack<>(1000);
	    String postfix = "";
	    
	    infix=infix.replaceAll(" ", "");
	    for (int i = 0; i < infix.length(); i++) {
	        char c = infix.charAt(i);
	        if (Character.isDigit(c) || c=='.') {
	            postfix += c;
	        } 
	        else if(Character.isLetter(c)) {
	        	postfix += c;
	        }
	        else if (c == '(') {
	            stack.push(c + "");
	        } 
	        else if (c == ')') {
	            while (!stack.isEmpty() && !stack.peek().equals("(")) {
	                postfix += " " + stack.pop();
	            }
	            if (!stack.isEmpty() && stack.peek().equals("(")) {
	                stack.pop();
	            }
	        } 
	        else {
	            while (!stack.isEmpty() && Precedence(c) <= Precedence(stack.peek().charAt(0))) {
	                postfix += " " + stack.pop();
	            }
	            postfix += " ";
	            stack.push(c + "");
	        }
	    }
	    
	    while (!stack.isEmpty()) {
	        postfix += " " + stack.pop();
	    }
	    return postfix.trim();
	}

	private static int Precedence(char operator) {
	    switch (operator) {
	        case '+':
	        case '-':
	            return 1;
	        case '*':
	        case '/':
	        case '%':
	            return 2;
	        case '^':
	            return 3;
	        default:
	            return 0;
	    }
	}

	public static double calculatePostFix(String s) {
		CursorStack<Double> stack = new CursorStack<>(1000);
		
		// to get the two operand
		double x=0;
		double y=0;
		
		String c[]=s.split(" ");
		///////////////////
		for(int i=0;i<c.length;i++) {
			
		///////////////////////////////////////////////////////////////
		/* if the char is operator --> pop two element(get two element)
		   from stack and make the operator on them */
			 
			if((c[i].equals("+") || c[i].equals("-") || c[i].equals("*") || c[i].equals("/") || c[i].equals("%") || c[i].equals("^")))  {
				
				  y = stack.pop(); // r u forget it's LIFO (last in first out)
	              x = stack.pop();
	              
	              char c1 = c[i].charAt(0);
	              
					switch (c1) {
						case '+': {
							stack.push(x+y);
							break;
						}
						case '-':{
							stack.push(x-y);
							break;
						}
						case '*':{
							stack.push(x*y);
							break;
						}
						case '/':{
							if (y == 0) {
		                        System.out.println("Error: Cannot divide by zero");   
		                    }
							else {
								stack.push(x/y);
							}
							break;
						}
						case '%':{
							stack.push(x%y);
							break;
						}
						case '^':{
							stack.push(Math.pow(y, x));
							break;
						}
						default:{
							System.out.println("there is no operator");
						}
					}
			 }
			 ///////////
			// if it's operand
			 else if(Character.isDigit(c[i].charAt(0))) {
				 stack.push(Double.parseDouble(c[i])); 
			 }
		}
		
		double res=stack.pop();
		
		return res;
	}

	
	
	
	// convert and calculate PreFix from (PostFix)
	public static String convert_postfix_to_prefix(String postfix) {
	    CursorStack<String> stack = new CursorStack<>(1000);
	    String[] tokens = postfix.split("\\s+");
	
	    for (String token : tokens) {
	        if (isOperand(token)) {
	            // Operand, push onto the stack
	            stack.push(token);
	        } else if (isOperator(token)) {
	            // Operator, pop two operands from the stack
	            String op1 = stack.pop();
	            String op2 = stack.pop();
	
	            // Concatenate the operands and operator
	            String temp = token + " " + op2 + " " + op1;
	
	            // Push the result back to the stack
	            stack.push(temp);
	        } else {
	            System.out.println("Invalid token in postfix expression: " + token);
	            return " ";
	        }
	    }
	    return stack.pop();
	}

	public static double calculatePrefix(String s) {
	    CursorStack<Double> stack = new CursorStack<>(1000);
	
	    String[] tokens = s.split("\\s+");
	    int length = tokens.length;
	
	    for (int i = length - 1; i >= 0; i--) {
	        String token = tokens[i];
	
	        if (isOperand(token)) {
	            // Operand, push onto the stack
	            stack.push(Double.parseDouble(token));
	        } else if (isOperator(token)) {
	            // Operator, pop two operands from the stack
	            double x = stack.pop();
	            double y = stack.pop();
	
	            // Perform the operation and push the result back to the stack
	            switch (token) {
	                case "+":
	                    stack.push(x + y);
	                    break;
	                case "-":
	                    stack.push(x - y);
	                    break;
	                case "*":
	                    stack.push(x * y);
	                    break;
	                case "/":
	                    if (x == 0) {
	                        System.out.println("Error: Cannot divide by zero");
	                        return Double.NaN;
	                    } else {
	                        stack.push(x / y);
	                    }
	                    break;
	                // Add other operators as needed
	                default:
	                    System.out.println("Unsupported operator: " + token);
	                    return Double.NaN;
	            }
	        } else {
	            // Invalid token
	            System.out.println("Invalid token in prefix expression: " + token);
	            return Double.NaN;
	        }
	    }
	
	    // The final result is on the top of the stack
	    return stack.pop();
	}

	public static boolean isOperand(String token) {
	    // Check if the token is an operand (number or variable)
	    return Character.isLetterOrDigit(token.charAt(0));
	}

	public static boolean isOperator(String token) {
	    // Check if the token is an operator
	    return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
	}
	
	//////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		launch(args);
	}
}
