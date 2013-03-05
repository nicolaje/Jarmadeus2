package jog;

public class timer {
	// wait delay milliseconds
	public static void wait(int delay) {
		try {
			Thread.sleep((long) (delay));
		} catch (InterruptedException e) {}
	}

}
