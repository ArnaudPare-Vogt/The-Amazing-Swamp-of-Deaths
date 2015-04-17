package abcd3901.utility.math;

import java.util.Random;

public class PerlinNoise {
	
	int[] baseValues;
	double[] realValues;
	Spline s;
	double frequency;
	int wavelength,width;
	
	/**
	 * Creates a perlinNoise in 1d
	 * @param wavelength is the space between two points of the base form
	 * @param width the number of times to add the frequency. The actual width is really width*frequency
	 * @param amplitude the max value of the noise
	 * @param rnd the random number generator of the noise
	 * @param persistence the persistence of the noise
	 * @param octaveNum the number of octaves that gets added to the noise
	 */
	public PerlinNoise(int wavelength,int width,int amplitude,Random rnd,double persistence, int octaveNum){
		this.frequency=1;
		this.wavelength=wavelength;
		this.width=width;
		
		//the first and last values are ignored
		baseValues = new int[width+2];
		double[] dbBaseValues = new double[baseValues.length];
		for (int i = 0; i < baseValues.length; i++) {
			baseValues[i] = rnd.nextInt(amplitude);
			dbBaseValues[i] = baseValues[i];
		}
		
		int rep = 0;
		double tempFrequency = 1;
		
		//the actual noise
		realValues = new double[wavelength*width];
		
		for (int i = 0; i < realValues.length; i++) {
			realValues[i]=0;
		}
		
		do{
			
			if(wavelength == 0)break;
			amplitude = (int)(amplitude*persistence);
			tempFrequency = Math.pow(2,rep);
			
			int test = (int)(width*Math.pow(2, rep));
			
			dbBaseValues = new double[test+1];
			
			for (int i = 0; i < dbBaseValues.length; i++) {
				dbBaseValues[i] = rnd.nextInt(amplitude);
			}
			
			s= new Spline(dbBaseValues,1.0/tempFrequency);
			
			for (int i = 0; i < realValues.length; i++) {
				realValues[i] += s.getSplineHeight(i/(double)this.wavelength);
			}
			
			
			
			rep++;
			
		}while(rep<octaveNum);
		
		System.out.println();
	}
	
	public int getWidth(){
		return width*wavelength;
	}
	
	public double getFrequency() {
		return frequency;
	}
	
	public int getWavelength() {
		return wavelength;
	}
	
	public double getValue(double x){
		if(x<0||x>=width){
			return 0;
		}else{
			int i=0;
			while(i/(double)wavelength<x)
				i++;
			return realValues[i];
		}
	}
	
}
