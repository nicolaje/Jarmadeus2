package fr.bretagne.ensta.jog.jarmadeus.talkers.pseudofiletalkers;

class PseudoFileMiddleWare {

	private static final String SETUP = "/sys/bus/spi/devices/spi0.0/setup";
	private static final String CONVERSION = "/sys/bus/spi/devices/spi0.0/conversion";
	private static final String AVERAGING = "/sys/bus/spi/devices/spi0.0/averaging";
	private static final String TEMPERATURE = "/sys/bus/spi/devices/spi0.0/temp1_input";
	private final static String CHAN_IFRD_FRONT = "/sys/bus/spi/devices/spi0.0/in6_input";
	private final static String CHAN_IFRD_REAR = "/sys/bus/spi/devices/spi0.0/in2_input";
	private final static String CHAN_IFRD_LEFT = "/sys/bus/spi/devices/spi0.0/in4_input";
	private final static String CHAN_IFRD_RIGHT = "/sys/bus/spi/devices/spi0.0/in5_input";
	private final static String START_ADC_SETUP="0x62";
}
