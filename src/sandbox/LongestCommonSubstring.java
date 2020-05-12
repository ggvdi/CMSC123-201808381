package sandbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
 
public class LongestCommonSubstring extends JFrame implements ActionListener{
	private JTextField text1 = new JTextField();
	private JTextField text2 = new JTextField();
	private JLabel substring = new JLabel("");
	private JButton b = new JButton("RUN");
	private JButton r = new JButton("RANDOMIZE");
	
	public LongestCommonSubstring() {
		super("Longest Common Substring Demo");
		setSize(800, 800);
		setLayout(null);
		add(text1).setBounds(10, 10, 790, 20);
		add(text2).setBounds(10, 30, 790, 20);
		add(b).setBounds(10, 60, 100, 20);
		add(r).setBounds(10, 90, 200, 20);
		add(substring).setBounds(110, 60, 700, 20);
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		text1.addActionListener(this);
		text2.addActionListener(this);
		b.addActionListener(this);
		r.addActionListener(this);
		setSize(820,170);
	}
	
	private void solve() {
		String a = text1.getText(),
			   b = text2.getText();
		int[][] len = new int[a.length() + 1][b.length() + 1];
		int endIX = a.length(), endLen = 0;
		
		for (int i = 1; i <= a.length(); ++i) {
			for (int j = 1; j <= b.length(); ++j) {
				if (a.charAt(i - 1) == b.charAt(j - 1)) {
					len[i][j] = len[i - 1][j - 1] + 1;
					if (len[i][j] > endLen) {
						endLen = len[i][j];
						endIX = i;
					}
				}
			}
		}
		int sIX = endIX - endLen;
		substring.setText(a.substring(sIX, sIX + endLen));
		if (endLen == 0) 
			substring.setText("!NO COMMON SUBSTRING!");
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == text1 || e.getSource() == text2) {
			substring.setText("");
		}
		if (e.getSource() == b) {
			solve();
		}
		if (e.getSource() == r) {
			int len = (int)(Math.random() * 100);
			String a = "";
			for (int i = 0; i < len; ++i) {
				a += (char)((int)(Math.random() * 90) + 33);
			}
			text1.setText(a);
			
			len = (int)(Math.random() * 100);
			a = "";
			for (int i = 0; i < len; ++i) {
				a += (char)((int)(Math.random() * 90) + 33);
			}
			text2.setText(a);
		}
	}
	
	public static void main(String[] args) {
		new LongestCommonSubstring();
	}
} 