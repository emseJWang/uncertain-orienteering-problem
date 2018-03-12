package algorithm1;

import java.io.*;

public class Readdata {
	public static int mNumRequests;
    public static double xPos[];
    public static double yPos[];
    public static double [][] b;
    public static double[][] distance;


    public static void read(String input) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(new File(input)));

        int lineNumber = 0;
        String line = reader.readLine();
        while (line != null) {
            if (line.startsWith(" "))
                line = line.replaceFirst(" +", "");
            parseLine(line, lineNumber);

            line = reader.readLine();
            lineNumber++;
        }

        reader.close();

        for(int i=0; i<mNumRequests; i++)
            for(int j=0; j<mNumRequests; j++) {
                distance[i][j] = Math.sqrt(Math.pow(xPos[i] - xPos[j], 2) + Math.pow(yPos[i] - yPos[j], 2));
            }
    }



    private static void parseLine(String line, int lineNumber) {
        if (lineNumber == 0) {
            mNumRequests = Integer.parseInt(line);
            xPos = new double[mNumRequests];
            yPos = new double[mNumRequests];
            b = new double[mNumRequests][2];
            /*b[][1] = new double[mNumRequests];
            b2 = new double[mNumRequests];*/
            distance = new double[mNumRequests][mNumRequests];
        } else if (lineNumber <= mNumRequests) {
            String[] values = line.split("\\s+");
            xPos[lineNumber-1] = Double.parseDouble(values[0]);
            yPos[lineNumber-1] = Double.parseDouble(values[1]);
            b[lineNumber-1][0] = Double.parseDouble(values[2]);
            b[lineNumber-1][1] = Double.parseDouble(values[3]);

        }
    }
}
