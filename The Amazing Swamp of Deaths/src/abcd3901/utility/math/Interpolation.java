package abcd3901.utility.math;

public class Interpolation {

	
	public static double cubicInterpolation(double v0,double v1, double v2, double v3, double x){
		double P = (v3 - v2) - (v0 - v1);
		double Q = (v0 - v1) - P;
		double R = v2 - v0;
		double S = v1;

		return P*(Math.pow(x, 3)) + Q*(Math.pow(x, 2)) + R*x + S;
	}
}
