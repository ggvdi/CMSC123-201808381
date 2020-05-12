package sandbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

public class KaratsubaMultiplier extends JFrame {
	public String add(String num1, String num2) {
		int n1 = num1.length();
		int n2 = num2.length();
		int n = Math.max(n1, n2);
		
		String res = "";
		
		int carry = 0;
		for (int i = 1; i <= n; ++i) {
			int a, b;
			if (n1 - i < 0) a = 0;
			else a = num1.charAt(n1 - i) - '0';
			
			if (n2 - i < 0) b = 0;
			else b = num2.charAt(n2 - i) - '0';
			
			int sum = a + b + carry;
			int u = sum % 10;
			int t = sum / 10;
			
			carry = t;
			res = (char)(u + '0') + res;
		}
		
		if (carry > 0)
			res = (char)(carry + '0') + res;
		
		return res;
	}
	
	public String karatsuba(String a, String b) {
		//cases for negative numbers
		if (a.charAt(0) == '-' && b.charAt(0) == '-') return karatsuba(a.substring(1), b.substring(1));
		if (a.charAt(0) == '-') return " - " + karatsuba(a.substring(1), b);
		if (b.charAt(0) == '-') return " - " + karatsuba(a, b.substring(1));
		
		if (a.length() < 2 && b.length() < 2) {
			int prod = (int)new Integer(a) * (int)new Integer(b);
			return String.valueOf(prod);
		}
		int n = Math.max(a.length(), b.length());
		int n2 = n / 2;
		
		String aH, aL, bH, bL;
		if (a.length() <= n2) {
			aH = "0";
			aL = a;
		}
		else {
			// substring function is [startIndex, endIndex)
			aH = a.substring(0, a.length() - n2);
			aL = a.substring(a.length() - n2);
		}
		
		if (b.length() <= n2) {
			bH = "0";
			bL = b;
		}
		else {
			bH = b.substring(0, b.length() - n2);
			bL = b.substring(b.length() - n2);
		}
		
		long base = 1;
		for (int i = 0; i < n2; ++i) {
			base *= 10;
		}
		
		String r0 = karatsuba(aH, bH);
		String r1 = karatsuba(add(aH, aL), add(bH, bL));
		String r2 = karatsuba(aL, bL);

		
		long r0l = (long)new Long(r0);
		long r1l = (long)new Long(r1);
		long r2l = (long)new Long(r2);
		
		
		long prod = r0l * base * base + (r1l - r0l - r2l) * base + r2l;
		
		return String.valueOf(prod);
	}
	
	
	private JTextField text1 = new JTextField();
	private JTextField text2 = new JTextField();
	private JLabel result = new JLabel("");
	private JButton b = new JButton("MULTIPLY");
	
	public KaratsubaMultiplier() {
		super("Karatsuba Algorithm for Multiplying Numbers");
		setLayout(null);
		add(text1).setBounds(10, 10, 790, 20);
		add(text2).setBounds(10, 30, 790, 20);
		add(b).setBounds(10, 60, 100, 20);
		add(result).setBounds(120, 60, 700, 20);
		
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (text1.getText().matches("^-?[0-9]+$") && text2.getText().matches("^-?[0-9]+$")) {// regex for starts with zero or one '-' and ends with one or more [0-9]
					result.setText(karatsuba(text1.getText(), text2.getText()));
				}
				else {
					result.setText("Invalid Input");
				}
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(820,140);
	}
	
	public static void main(String[] args) {
		new KaratsubaMultiplier();
	}
}