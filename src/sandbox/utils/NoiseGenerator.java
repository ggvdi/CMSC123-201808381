package sandbox.utils;

public class NoiseGenerator {
	public static final int NOISE_MAP_SIZE = 256;
	
	private static float[] noisemap = null;
	
	public static float noise(float x, float y) {
		if (noisemap == null) generateNoiseMap();
		
		if (x < 0) x = -x;
		if (y < 0) y = -y;
		
		int x0 = (int)x   ;
		int x1 = (x0 + 1) ;
		int y0 = (int)y   ;
		int y1 = (y0 + 1) ;
		
		float w = x - (float)Math.floor(x);
		float h = y - (float)Math.floor(y);
		
		int loc = prand(x0, y0);
		float pass1 = 1.f * noisemap[loc];
		//float pass2 = 0.25f * noisemap[loc + 1] * noisemap[loc-1];
		//float pass3 = 0.125f * noisemap[loc + 1] * noisemap[loc-1] * noisemap[loc + 2] * noisemap[loc-2];
		//float pass4 = 0.125f * noisemap[loc + 1] * noisemap[loc-1] * noisemap[loc + 2] * noisemap[loc-2] *  noisemap[loc + 3] * noisemap[loc-3];
		float total1 = pass1;// + pass2 + pass3 + pass4;
		
		loc = prand(x0, y1);
		pass1 = 1.f * noisemap[loc];
		//pass2 = 0.25f * noisemap[loc + 1] * noisemap[loc-1];
		//pass3 = 0.125f * noisemap[loc + 1] * noisemap[loc-1] * noisemap[loc + 2] * noisemap[loc-2];
		//pass4 = 0.125f * noisemap[loc + 1] * noisemap[loc-1] * noisemap[loc + 2] * noisemap[loc-2] *  noisemap[loc + 3] * noisemap[loc-3];
		float total2 = pass1;// + pass2 + pass3 + pass4;
		
		loc = prand(x1, y0);
		pass1 = 1.f * noisemap[loc];
		//pass2 = 0.25f * noisemap[loc + 1] * noisemap[loc-1];
		//pass3 = 0.125f * noisemap[loc + 1] * noisemap[loc-1] * noisemap[loc + 2] * noisemap[loc-2];
		//pass4 = 0.125f * noisemap[loc + 1] * noisemap[loc-1] * noisemap[loc + 2] * noisemap[loc-2] *  noisemap[loc + 3] * noisemap[loc-3];
		float total3 = pass1;// + pass2 + pass3 + pass4;
		
		loc = prand(x1, y1);
		pass1 = 1.f * noisemap[loc];
		//pass2 = 0.25f * noisemap[loc + 1] * noisemap[loc-1];
		//pass3 = 0.125f * noisemap[loc + 1] * noisemap[loc-1] * noisemap[loc + 2] * noisemap[loc-2];
		//pass4 = 0.125f * noisemap[loc + 1] * noisemap[loc-1] * noisemap[loc + 2] * noisemap[loc-2] *  noisemap[loc + 3] * noisemap[loc-3];
		float total4 = pass1;// + pass2 + pass3 + pass4;
		
		
		float horiz1 = lerp(total3, total1, w);
		float horiz2 = lerp(total4, total2, w);
		return lerp(horiz2, horiz1, h);
	}
	
	private static float lerp(float a, float b, float ratio) {
		return (1.0f - ratio)*b + ratio*a;
	}
	
	private static int prand(float x, float y) {
		double fpart = Math.cos(Math.cos(x * 0.4) + Math.cos(x * y) * 0.35) * 0.5 + 0.5;
		return (int)(fpart * 250) + 3;
	}
	
	public static void generateNoiseMap() {
		noisemap = new float[NOISE_MAP_SIZE];
		for (int i = 0; i < noisemap.length; ++i) {
			noisemap[i] = (float)Math.random();
		}
	}
}