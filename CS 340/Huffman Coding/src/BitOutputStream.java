import java.io.*;

public abstract class BitOutputStream {

	protected DataOutputStream d;

	public BitOutputStream(String filename) {
		try {
			d = new DataOutputStream(new FileOutputStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void writeBit(int bit);

	public abstract void close();
}
