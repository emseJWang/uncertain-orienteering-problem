package algorithm1;

import java.io.*;

public class Test {
	static int[] a = {11, 0, 1, 0};
	static int b=11;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("d:/1.txt");
		FileWriter out = new FileWriter(file);
		BufferedWriter bufw = new BufferedWriter(out);
		System.out.println(b);
		bufw.write(String.valueOf(456.4564)+" ");
		bufw.write(String.valueOf(456.4564));
		bufw.newLine();
		bufw.write(String.valueOf(456.4564));
		bufw.close();
		out.close();
	}

}
