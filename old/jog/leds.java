package jog;
import jarmadeus.FPGATalker;

public class leds {
	private FPGATalker fpt;
	private final char LEDS_REG=0x0018;

	public leds() {
		fpt = new FPGATalker();
		fpt.setDebugMode(false);
		this.setLeds(0);
	}

	public void setLeds (int ledCode) {
		if ((ledCode < 16) && (ledCode >= 0)) {
			fpt.writeChar(LEDS_REG, (char) ledCode);
		}
	}

	public void ledOn (int ledNum) {
		if ((ledNum >= 1) && (ledNum <= 4)) {
			int ledCode = 1<<(ledNum-1);
			ledCode = ledCode & 0x0F;
			System.out.println ("led code ="+ledCode);
			ledCode = ledCode | (int) fpt.readChar(LEDS_REG);
			System.out.println ("led code ="+ledCode);
			fpt.writeChar(LEDS_REG, (char) ledCode);			
		}
	}

	public void ledOff (int ledNum) {
		if ((ledNum >= 1) && (ledNum <= 4)) {
			int ledCode = ~(1<<(ledNum-1));
			ledCode = ledCode & 0x0F;
			System.out.println ("led code ="+ledCode);
			ledCode = ledCode & (int) fpt.readChar(LEDS_REG);
			System.out.println ("led code ="+ledCode);
			fpt.writeChar(LEDS_REG, (char) ledCode);			
		}
		
	}

	public static void test () {
		leds led = new leds();
		led.setLeds (5);  // leds D1 and D3 on, leds D2 and D4 off
		timer.wait(2000); // wait 2 seconds (2000 ms)
		led.ledOn (2);    // turn on D2
		timer.wait(2000); // wait 2 seconds (2000 ms)
		led.ledOff (2);   // turn off D2
		timer.wait(2000); // wait 2 seconds (2000 ms)
		led.setLeds (0);  // turn off all leds
	}
}
