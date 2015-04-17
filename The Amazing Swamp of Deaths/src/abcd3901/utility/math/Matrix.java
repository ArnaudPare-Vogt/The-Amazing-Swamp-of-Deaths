package abcd3901.utility.math;

public class Matrix {

	private double [][] data;
	private int height,width;
	
	/**
	 * Creates a matrix using the data provided. The length of all arrays should be the same, if not, an IllegalArgumentException gets thrown.
	 * @param data the data from which generate the matrix. It must be a rectangular array.
	 */
	public Matrix(double[][] data){
		int l = data[0].length;
		for (int i = 0; i < data.length; i++) {
			if(l!=data[i].length){
				throw new IllegalArgumentException("The matrix array has to be generated with arrays of the same length. line "+i+" is not of lenght "+l);
			}
		}
		this.data=data;
		width = l;
		height = data.length;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public double getParameter(int line, int position){
		if(line >= height || line < 0){
			throw new IllegalArgumentException("The line has to be between 0 and "+getHeight()+" it, is currently "+line);
		}
		if(position >= width || position < 0){
			throw new IllegalArgumentException("The position has to be between 0 and "+getWidth()+", it is currently "+line);
		}
		return data[line][position];
	}
	
	public void addLine(int targetLine,double targetLineMuliplier, int addingLine, double addingLineMultiplier){
		if(targetLine >= height || targetLine < 0){
			throw new IllegalArgumentException("The line has to be between 0 and "+getHeight()+" it, is currently "+targetLine);
		}
		if(addingLine >= height || addingLine < 0){
			throw new IllegalArgumentException("The line has to be between 0 and "+getHeight()+" it, is currently "+addingLine);
		}
		for (int i = 0; i < data[targetLine].length; i++) {
			data[targetLine][i] = (targetLineMuliplier*data[targetLine][i])+(addingLineMultiplier*data[addingLine][i]);
		}
	}
	
	public void makeTriangularMatrix(){
		for (int i = 0; i < height; i++) {
			for (int j = i+1; j < height; j++) {
				if(data[j][i]!=0){
					addLine(j,data[i][i],i,-data[j][i]);
				}
			}
		}
	}
	
	/**
	 * Can the matrix be solvable by calling the solve method?
	 * @return if the matrix is solvable
	 */
	public boolean solvable(){
		return width==height+1;
	}
	
	
	public double[] solve(){
		if(!solvable()){
			throw new IllegalStateException("The matrix cannot be solved!");
		}
		makeTriangularMatrix();
		double[] solutions = new double[width-1];
		for (int i = solutions.length-1; i >= 0; i--) {
			double sol = data[i][width-1];
			for (int j = i+1; j < height; j++) {
				sol-=data[i][j]*solutions[j];
			}
			solutions[i]=sol/data[i][i];
		}
		return solutions;
	}
	
	@Override
	public String toString(){
		String s = "";
		for (int i = 0; i < data.length; i++) {
			s+="[";
			for (int j = 0; j < data[i].length; j++) {
				s+=data[i][j];
				if(j == data[i].length-2){
					s+=" | ";
				}else if(j == data[i].length-1){
					
				}else{
					s+=" , ";
				}
			}
			s+="]\n";
		}
		return s;
	}
}
