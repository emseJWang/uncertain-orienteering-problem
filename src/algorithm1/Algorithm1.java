package algorithm1;

import java.io.*;

public class Algorithm1 {
	static double Lamda;
	static double Except;
	static double Std;
	static double obj;
//	static double[] T = {15, 20, 23, 25, 27, 30, 32, 35, 38, 40, 45};
//	static double[][] KZ = {{40, 60, 80},{50,100,120},{50,100,120},{60,110,140},{60,110,140},{100,150,180},{100,150,200},{100,150,200},{120,200,260},{120,200,260},{120,200,260}};
	
//	static double[] T = {38, 40, 45};
//	static double[][] KZ = {{100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,260},{100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,260},{100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,260}};	

	static double[] T = {15, 20, 23, 25, 27, 30, 32, 35, 38, 40, 45};
	static double[] BS = {120, 200, 210, 230, 230, 265, 300, 320, 360, 395, 450};
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String file = "./data/uncertain/P21A.txt";//
		Readdata.read(file);//
		
		//
		Orienteerproblem test1 = new Orienteerproblem();
		for (int i=7; i<8; i++){
			String head = "./results";
	        String tail = ".txt";
	        String path = head+T[i]+'A'+1+tail;
	        File results = new File(path);
	        FileWriter out = new FileWriter(results);
			BufferedWriter bufw = new BufferedWriter(out);
			for (int j=0; j<9; j++){
				test1.Lamda = 0;
				test1.Tmax = T[i];
//				double K = KZ[i][j];
//				test1.K = KZ[i][j];
				double K = BS[i]*(0.1*(j+1));
				test1.K = BS[i]*(0.1*(j+1));
				test1.calculate();
		
				test1.Lamda = Lamda;
				Except = test1.objective1;
				Std = test1.objective2;
				obj = test1.objective;
				System.out.println("obj="+obj);
				System.out.println("Except="+Except);
				System.out.println("Std="+Std);
		//
		//System.out.println(Math.abs(obj)>0.001);
				while (Math.abs(obj)>0.0000001){
					Lamda = (Except-K)/Std;
					test1.Lamda = Lamda;
					test1.calculate();
					Except = test1.objective1;
					Std = test1.objective2;
					obj = test1.objective;
					System.out.println("Lamda="+Lamda);
					System.out.println("obj="+obj);
					System.out.println("Except="+Except);
					System.out.println("Std="+Std);
				}
			
				bufw.write(String.valueOf(Except)+" ");
				bufw.write(String.valueOf(Std)+" ");
				bufw.write(String.valueOf(obj));
				bufw.newLine();
//				bufw.write(test1.path);
				bufw.newLine();
				}
			bufw.close();
			out.close();
		}
	}


}
