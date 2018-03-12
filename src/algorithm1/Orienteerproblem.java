package algorithm1;

import java.io.IOException;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class Orienteerproblem {
	double Tmax;
	int n = Readdata.mNumRequests;//
	double b[][] = Readdata.b;//
	double[][] distance = Readdata.distance;//
	double Lamda;
	double objective;
	double objective1;
	double objective2;
	double K;
//	String path;
	void calculate(){
		
		try {
			IloCplex cplex = new IloCplex();
			
			IloNumVar[][] y = new IloNumVar[n][n];//IloIntVar IloNumVar
			for(int i=0; i<n; i++) {
				y[i] = cplex.boolVarArray(n);
			}
						
			IloNumVar[] u = cplex.numVarArray(n-1, 2, n);//(n-1, 1, n-1)
			
			//objective
			IloNumExpr obj = cplex.linearNumExpr();
			IloLinearNumExpr obj1 = cplex.linearNumExpr();
		
			for(int i=1; i<n-1; i++) {
				//IloLinearNumExpr sumX = cplex.linearNumExpr();
				for(int j=1; j<n; j++)
					if(j!=i)
						obj1.addTerm(b[i][0],y[i][j]);
						//obj1.addTerm(b1[i],y[i][j]);
				//obj.addTerm(1, (IloNumVar) sumX);
			}
			
			IloLinearNumExpr obj2 = cplex.linearNumExpr();
			for(int i=1; i<n-1; i++) {
				//IloLinearNumExpr sumX = cplex.linearNumExpr();
				for(int j=1; j<n; j++)
					if(j!=i)
						obj2.addTerm(b[i][1],y[i][j]);
				//obj.addTerm(1, (IloNumVar) sumX);
			}
			obj=cplex.sum(cplex.sum(-K, obj1), cplex.prod(-Lamda, obj2));
			cplex.addMaximize(obj);

			//constraints
			//1
			IloLinearNumExpr constraint, constraint1;

			constraint = cplex.linearNumExpr();

			for(int i=0; i<n; i++)//0 n-1
			{
				for(int j=0; j<n; j++)//1 n
				{
					//if(j!=i)//
					constraint.addTerm(distance[i][j], y[i][j]);
				}
			}
			cplex.addLe(constraint, Tmax);

			//2
			constraint = cplex.linearNumExpr();
			for(int i=0; i<n-1; i++)
				constraint.addTerm(1, y[i][n-1]);//¸ÄÎªn		
			cplex.addEq(constraint, 1);
			
			constraint = cplex.linearNumExpr();
			for(int j=1; j<n; j++)
				constraint.addTerm(1, y[0][j]);
			
			cplex.addEq(constraint, 1);


			//3
			for(int k=1; k<n-1; k++)
			{
				constraint = cplex.linearNumExpr();
				constraint1 = cplex.linearNumExpr();
				for(int i=0; i<n-1; i++)
				{
					if(i!=k)
					{
						constraint.addTerm(1, y[i][k]);
					}
				}
				for(int j=1; j<n; j++)
				{
					if(j!=k)
					{
						constraint1.addTerm(1, y[k][j]);
					}
				}
				//constraint.addTerm(-2,x[j]);
				cplex.addEq(constraint, constraint1);
				cplex.addLe(constraint,1);
				cplex.addLe(constraint1, 1);
			}

			//4
			for(int i=1; i<n; i++)
			{
				for(int j=1; j<n; j++)
				{
					//if(i!=j)

						constraint = cplex.linearNumExpr();
						constraint.addTerm(1, u[i-1]);//i-1
						constraint.addTerm(-1, u[j-1]);//j-1

						constraint.addTerm(n-1,y[i][j]);

						cplex.addLe(constraint, n-2);

				}
			}
			cplex.solve();
			System.out.println();
		    System.out.println("Solution status: " + cplex.getStatus());
		    System.out.println("Objective value: " + cplex.getObjValue());
		    objective = cplex.getObjValue();
		    objective1 = cplex.getValue(obj1);
		    objective2 = cplex.getValue(obj2);

/*		    if ( cplex.getStatus().equals(IloCplex.Status.Optimal) ) {

	            // Write out the optimal tour
		    	int i, j;
	            double[][] sol = new double[n][]; 
	            int[] succ = new int[n];
	            for (j = 0; j < n; ++j)
	               succ[j] = -1;
	            
	            for (i = 0; i < n; ++i) {
	              sol[i] = cplex.getValues(y[i]);
	               for(j = 0; j < n; ++j) {
	                  if ( sol[i][j] != 0 ) succ[i] = j;
	               }
	            }
	  
	            System.out.println("Optimal tour:");
	            
	            i = 0;
	            path = null;
	            while ( succ[i] != 0 ) {
	               System.out.print(i+1 +", ");
	               path = path +", "+String.valueOf(i+1);
	               if (i == n-1) { break;}
	               i = succ[i];
	               
	           }
	            System.out.println();

	               System.out.println("End!!!");
		    }*/

		    cplex.end();
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
