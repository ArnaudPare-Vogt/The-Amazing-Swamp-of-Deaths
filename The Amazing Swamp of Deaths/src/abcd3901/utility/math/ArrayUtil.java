package abcd3901.utility.math;


public class ArrayUtil {

	/**
	 * Calculates the sum of all the elements of the provided integer array. If the values are negative, they are subtracted.
	 * @param array the integer array of which calculate the sum of the elements
	 * @return the sum of the individual elements of the array
	 */
	public static int intArraySum(int[] array){
		int sum = 0;
		for (int i : array) {
			sum += i;
		}
		return sum;
	}
	
	/**
	 * Calculates the sum of the elements of the provided integer array, until the end index. If the values are negative, they are subtracted.
	 * @param array the integer array of which calculate the sum of the elements
	 * @param end the index where the calculation shall end
	 * @return the sum of the individual elements of the array, until the end parameter.
	 */
	public static int intArraySumUntil(int[] array,int end){
		if(end<array.length){
			int sum = 0;
			for (int i = 0; i < end; i++) {
				sum += array[i];
			}
			return sum;
		}else{
			throw new IllegalArgumentException("The end param cannot exceed the lenght of the array!");
		}
	}
	
	/**
	 * counts how many are true
	 * @param bool
	 * @return
	 */
	public static int count(boolean ... bool){
		int i = 0;
		for (boolean b : bool) {
			if(b)i++;
		}
		return i;
	}
}
