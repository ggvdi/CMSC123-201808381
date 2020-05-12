package sandbox.utils;

public class NoiseGenerator {
	private static float[] noisemap = null;
	private static final int   NUM_OCTAVES = 8;
	private static final float PERSISTENCE = 0.25f;
	
	private static float rand(float n) {
		return ((float)Math.sin(n * 12.9898f + n * 78.233f) * 2.5453f) % 1.f;
	}
	
	private static float rand2D(float x, float y) {
		float n = x + y * 57;
		n = n * n * n;
		return rand(n);
	}
	

	private static float smoothNoise(float x, float y) {
		float corners = ( rand2D(x-1, y-1) + rand2D(x+1, y-1) + rand2D(x-1, y+1) + rand2D(x+1, y+1) ) / 8.f;
		float sides   = ( rand2D(x-1, y)   + rand2D(x+1, y)   + rand2D(x, y-1)   + rand2D(x, y+1) ) /  8.f;
        float center  =   rand2D(x, y) / 4.f;
		return corners + sides + center;
	}
	
	private static float interpolatedNoise(float x, float y) {
		int ix = (int)x;
		float fx = x - ix;
		int iy = (int)y;
		float fy = y - iy;
		
		float v00 = smoothNoise(ix    , iy);
		float v01 = smoothNoise(ix    , iy + 1);
		float v11 = smoothNoise(ix + 1, iy + 1);
		float v10 = smoothNoise(ix + 1, iy);
		
		float w1 = cosineInterpolation(v00, v10, fx);
		float w2 = cosineInterpolation(v01, v11, fx);
		
		return cosineInterpolation(w1, w2, fy);
	}
	
	public static float noise2D(float x, float y) {
		int ix = (int)x;
		float fx = x - ix;
		
		int iy = (int)y;
		float fy = y - iy;
		
		float total = 0;
		float amp = 1;
		float freq = 1;
		
		for (int i = 0; i < NUM_OCTAVES; ++i) {
			total += interpolatedNoise( x * freq, y * freq ) * amp;
			amp *= PERSISTENCE;
			freq *= 2;
		}
		
		return total;
	}
	public static float lerp(float a, float b, float w) {
		return b * w + a * (1 - w);
	}
	
	public static float cosineInterpolation(float a, float b, float w) {
		float rad = w * (float)Math.PI;
		float f = (1 - (float)Math.cos(rad)) * 0.5f;
		return a * (1-f) + b * f;
	}
	
	public static float noise(float x) {
		return 1.f;
	}
}