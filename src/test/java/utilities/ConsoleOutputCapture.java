package utilities;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleOutputCapture {
	private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private static PrintStream originalSystemOut = System.out;
	private static PrintStream originalSystemErr = System.err;
	private static PrintStream captureOut = new PrintStream(outputStream);
	private static PrintStream captureErr = new PrintStream(outputStream);

	public static void startCapture() {
		System.setOut(captureOut);
		System.setErr(captureErr);
	}

	public static void stopCapture() {
		System.setOut(originalSystemOut);
		System.setErr(originalSystemErr);
	}

	public static String getCapturedOutput() {
		return outputStream.toString();
	}

	public static void clearCapturedOutput() {
		outputStream.reset();
	}
}