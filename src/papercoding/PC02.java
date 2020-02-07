package papercoding;

public class PC02 {
	public static void sawBlade(int k) {
		for (int i = 0; i < k; ++i) {
			for (int j = k; j > 0; j--) {
				for (int n = 0; n < j; ++n) {
					System.out.print(n <= i + j - k ? "*" : " ");
				}
			}
			System.out.println();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("PC02");
		java.util.Scanner scan = new java.util.Scanner(System.in);
		
		while (true) {
			System.out.print("\tENTER K > ");
			int c = scan.nextInt();
			if (c <= 0) break;
			
			sawBlade(c);
		}
	}
}