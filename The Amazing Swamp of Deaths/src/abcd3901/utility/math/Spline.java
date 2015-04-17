package abcd3901.utility.math;

public class Spline {

	double [] dataX,dataY,dataM;
	
	/**
	 * 
	 * @param datax
	 * @param datay
	 * @throws IllegalArgumentException if the two data arrays do not have the same length
	 */
	public Spline (double[] dataX, double[] dataY){
		if(dataX.length != dataY.length){
			throw new IllegalArgumentException("The dataX and dataY lenghts nust be the same.");
		}
		this.dataX = dataX;
		this.dataY=dataY;
		this.dataM=generateSmoothSlopes(dataX,dataY);
	}
	
	public Spline (double[] dataY, double separationArgument){
		this(constructDataX(separationArgument,dataY.length),dataY);
	}
	
	private double[] generateSmoothSlopes(double[] dataX, double[] dataY){
		int n = dataX.length-1;
		double[][] A = new double[n+1][n+2];
		
		for(int i=1; i<n; i++)    // rows
		{
		A[i][i-1] = 1/(dataX[i] - dataX[i-1]);
		
		A[i][i  ] = 2 * (1/(dataX[i] - dataX[i-1]) + 1/(dataX[i+1] - dataX[i])) ;
		
		A[i][i+1] = 1/(dataX[i+1] - dataX[i]);
		
		A[i][n+1] = 3*	( (dataY[i]-dataY[i-1])/ ((dataX[i] - dataX[i-1])*(dataX[i] - dataX[i-1])) 
						+  (dataY[i+1]-dataY[i])/ ((dataX[i+1] - dataX[i])*(dataX[i+1] - dataX[i])) );
		}
		
		A[0][0  ] = 2/(dataX[1] - dataX[0]);
		A[0][1  ] = 1/(dataX[1] - dataX[0]);
		A[0][n+1] = 3 * (dataY[1] - dataY[0]) / ((dataX[1]-dataX[0])*(dataX[1]-dataX[0]));
		A[n][n-1] = 1/(dataX[n] - dataX[n-1]);
		A[n][n  ] = 2/(dataX[n] - dataX[n-1]);
		A[n][n+1] = 3 * (dataY[n] - dataY[n-1]) / ((dataX[n]-dataX[n-1])*(dataX[n]-dataX[n-1]));
		
		Matrix m = new Matrix(A);
		return m.solve();
	}
	
	
	
	private static double[] constructDataX(double separationArgument, int length){
		double[] dataX = new double[length];
		dataX[0]=0;
		for (int i = 1; i < dataX.length; i++) {
			dataX[i] = dataX[i-1]+separationArgument;
		}
		return dataX;
	}
	
	public double getSplineHeight(double x){
		int i = 1;
		
		while(dataX[i]<x) i++;
		
		double t = (x - dataX[i-1]) / (dataX[i] - dataX[i-1]);
		
		double a =  dataM[i-1]*(dataX[i]-dataX[i-1]) - (dataY[i]-dataY[i-1]);
		double b = -dataM[i  ]*(dataX[i]-dataX[i-1]) + (dataY[i]-dataY[i-1]);
		
		double q = (1-t)*dataY[i-1] + t*dataY[i] + t*(1-t)*(a*(1-t)+b*t);
		return q;
	}
	
}
