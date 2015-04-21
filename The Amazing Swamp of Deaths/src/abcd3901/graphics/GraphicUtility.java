package abcd3901.graphics;

public class GraphicUtility {

	/** WARNING, NOT COMPLETE
	 * returns a int argb pixel based on the overlay of argb1 over argb2
	 * @param argb1 the top pixel color
	 * @param argb2 the bottom pixel color
	 * @return the total color
	 */
	public static int alphaBlend(int argb1,int argb2){
		int rgb1 = argb1 & 0x00ffffff;
		int rgb2 = argb1 & 0x00ffffff;
		double a1 = (argb1 & 0xff000000)/(double)(0xff000000);
		double a2 = (argb2 & 0xff000000)/(double)(0xff000000);
		
		double af = a1 + a2 * (1-a1);
		int rgbf = 0;
		
		return 0;
	}
}
