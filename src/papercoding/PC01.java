package papercoding;

public class PC01 {
	public static void stars(int k) {
		for (int i = 0; i < k; ++i) {
			for (int j = 0; j < k; ++j) {
				System.out.print(j < k - 1 - i ? " " : "* ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("PC01");
		java.util.Scanner scan = new java.util.Scanner(System.in);
		
		while (true) {
			System.out.print("\tENTER HEIGHT OF TRIANGLE > ");
			int c = scan.nextInt();
			if (c <= 0) break;
			
			stars(c);
		}
	}
}