package abcd3901.graphics;

public class GraphicUtility {

	/**
	 * returns a int argb pixel based on the overlay of argb1 over argb2
	 * @param argb1 the top pixel color
	 * @param argb2 the bottom pixel color
	 * @return the total color
	 */
	public static int alphaBlend(int argb1,int argb2){
		int rgb1 = argb1 & 0x00ffffff;
		int rgb2 = argb2 & 0x00ffffff;
		double a1 = ((argb1 & 0xff000000)>>>24)/(double)(0xff);
		double a2 = ((argb2 & 0xff000000)>>>24)/(double)(0xff);
		
		double af = a1 + a2 * (1-a1);
		
		int b1 = rgb1&0xff;
		int g1 = (rgb1&0x00ff00)>>8;
		int r1 = (rgb1&0xff0000)>>16;
		
		int b2 = rgb2&0xff;
		int g2 = (rgb2&0x00ff00)>>8;
		int r2 = (rgb2&0xff0000)>>16;
		
		int bf = (int)(b1*a1 + b2*a2*(1-a1));
		int gf = (int)(g1*a1 + g2*a2*(1-a1));
		int rf = (int)(r1*a1 + r2*a2*(1-a1));
		
		int afin = (int)(af*0xff);
		
		return (afin<<24)|(rf<<16)|(gf<<8)|(bf);
	}
}
