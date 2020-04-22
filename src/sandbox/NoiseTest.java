package sandbox;

import sandbox.utils.DisplayPanel;
import sandbox.utils.NoiseGenerator;
import java.awt.Graphics2D;
import java.awt.Color;

public class NoiseTest extends DisplayPanel {

	public NoiseTest() {
		super("Noise Test", 800, 800);
	}
	
	private boolean doonce = true;
	
	public void init() {
	}
	
	public void render(Graphics2D g) {
		for (int i = 0; i < 800; ++i) {
			for (int j = 0; j < 800; ++j) {
				float noise = (float)NoiseGenerator.noise(i / 40.f, j / 40.f);
				if (noise < 0) noise = 0;
				if (noise > 1) noise = 1;
				g.setColor(new Color(noise, noise, noise, 1.f));
				g.fillRect(i, j, 1, 1);
			}
		}
	}
	
	public void update(float dt) {
		if (doonce) {
			doonce = false;
			startRecord(15);
		}
	}

	public static void main(String[] args) {
		new NoiseTest().generateJFrame();
	}
}