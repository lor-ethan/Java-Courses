import java.io.*;

public abstract class BitInputStream {

	protected DataInputStream d;

	public BitInputStream(String filename) {
		try {
			d = new DataInputStream(new FileInputStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract int readBit();

	public abstract void close();
}
