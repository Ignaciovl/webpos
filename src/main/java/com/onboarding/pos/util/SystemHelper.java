package com.onboarding.pos.util;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.lang3.Validate;

public class SystemHelper {

	private Properties appProperties;
	private Locale locale;
	private Scanner scanner;
	private BufferedReader reader;
	private Console console;

	public SystemHelper() {
		loadProperties();
		loadLocale();
		scanner = new Scanner(System.in);
		scanner.useLocale(getLocale());
		reader = new BufferedReader(new InputStreamReader(System.in));
		console = System.console();
	}

	public Properties getAppProperties() {
		if (appProperties == null)
			loadProperties();
		return appProperties;
	}

	public void setAppProperties(Properties appProperties) {
		this.appProperties = appProperties;
	}
	
	public Locale getLocale() {
		if (locale == null)
			loadLocale();
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getAppProperty(String key) {
		Validate.isTrue(key != null && !key.isEmpty(), "The key cannot be NULL or empty");
		return getAppProperties().getProperty(key);
	}
	
	public void exit(int status) {
		System.exit(0);
	}

	public void printf(final String format, final Object... args) {
		System.out.printf(format, args);
	}

	public void print(final String string) {
		System.out.print(string);
	}

	public void print(final Object object) {
		System.out.print(object);
	}

	public void println(final String string) {
		System.out.println(string);
	}

	public void println(final Object object) {
		System.out.println(object);
	}

	public void printErr(final String string) {
		System.err.print(string);
	}

	public void printErr(final Object object) {
		System.err.print(object);
	}

	public void printlnErr(final String string) {
		System.err.println(string);
	}

	public void printlnErr(final Object object) {
		System.err.println(object);
	}

	public int askOption(String message, int[] validOptions, String invalidMessage) {
		int option;
		loop: while (true) {
			print(message);
			try {
				option = scanner.nextInt();
				for (int validOption : validOptions) {
					if (option == validOption) {
						break loop;
					}
				}
				println(invalidMessage);
			} catch (InputMismatchException e) {
				println(invalidMessage);
				scanner.nextLine();
			}
		}

		return option;
	}

	public String askOption(String message, String[] validOptions, String invalidMessage) {
		String option;
		loop: while (true) {
			print(message);
			try {
				option = reader.readLine();
				for (String validOption : validOptions) {
					if (validOption.equals(option)) {
						break loop;
					}
				}
				println(invalidMessage);
			} catch (Exception e) {
				println(invalidMessage);
				scanner.nextLine();
			}
		}

		return option;
	}

	public void askPressEnter(String message) {
		print(message);
		try {
			System.in.read();
			reader.readLine();
		} catch (Exception e) {
		}
	}

	public void askPressEnterToContinue() {
		askPressEnter("\n\nPress ENTER to continue...");
	}

	public String askString(String message) {
		while (true) {
			print(message);
			try {
				return reader.readLine();
			} catch (Exception e) {
			}
		}
	}

	public int askInt(String message) {
		int input;
		while (true) {
			print(message);
			try {
				input = scanner.nextInt();
				break;
			} catch (InputMismatchException e) {
				println("Invalid value");
				scanner.nextLine();
			}
		}
		return input;
	}
	
	public long askLong(String message) {
		long input;
		while (true) {
			print(message);
			try {
				input = scanner.nextInt();
				break;
			} catch (InputMismatchException e) {
				println("Invalid value");
				scanner.nextLine();
			}
		}
		return input;
	}

	public BigDecimal askBigDecimal(String message) {
		BigDecimal input;
		while (true) {
			print(message);
			try {
				input = scanner.nextBigDecimal();
				break;
			} catch (InputMismatchException e) {
				println("Invalid value");
				scanner.nextLine();
			}
		}
		return input;
	}

	public boolean askYesOrNo(String message) {
		while (true) {
			print(message);
			try {
				String input = reader.readLine();
				if ("Y".equalsIgnoreCase(input))
					return true;
				else if ("N".equalsIgnoreCase(input)) {
					return false;
				}
				println("Invalid value");
			} catch (Exception e) {
			}
		}
	}

	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			System.out.println("Error en la lectura");
		}
		return null;
	}

	public String read() {
		return scanner.next();
	}

	public byte readByte() {
		byte b = scanner.nextByte();
		return b;
	}

	public boolean readBoolean() {
		return scanner.nextBoolean();
	}

	public short readShort() {
		return scanner.nextShort();
	}

	public int readInt() {
		return scanner.nextInt();
	}

	public long readLong() {
		return scanner.nextLong();
	}

	public BigInteger readBigInteger() {
		return scanner.nextBigInteger();
	}

	public float readFloat() {
		return scanner.nextFloat();
	}

	public double readDouble() {
		return scanner.nextDouble();
	}

	public BigDecimal readBigDecimal() {
		return scanner.nextBigDecimal();
	}
	
	public String readPassword(String message) {
		if(console != null) {
			char[] readPassword = console.readPassword(message);
			String password = new String(readPassword);
			return password;
		}
		return null;
	}
	
	private void loadProperties() {
		InputStream inStream = null;
		try {
			inStream = ClassLoader.getSystemResourceAsStream("app.properties");
			appProperties = new Properties();
			appProperties.load(inStream);
		} catch (IOException e) {
			throw new RuntimeException("ERROR: could not load app properties.", e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private void loadLocale() {
		String language = getAppProperty("locale.language");
		String country = getAppProperty("locale.country");

		if (language != null && country != null)
			setLocale(new Locale(language, country));

		if (language != null)
			setLocale(new Locale(language));

		setLocale(Locale.getDefault());
	}

}
