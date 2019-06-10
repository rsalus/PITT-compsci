package cs401;
import java.util.Scanner;

public class Lab6 
{
	public static double max(double [] data){
		return max_rec(data, data.length-1);
		/*				NON-RECURSIVE METHOD
		double maxVal = 0;
		for(int i = 0; i < data.length; i++)
			if(maxVal < data[i]){maxVal = data[i];}
		return maxVal;
		*/
	}
	
	public static double min(double [] data){
		return min_rec(data, data.length-1);
		/*				NON-RECURSIVE METHOD
		double minVal = data[0];
		for(int i = 0; i < data.length; i++)
			if(minVal > data[i]){minVal = data[i];}
		return minVal;
		*/
	}
	
	public static double sum(double [] data){
		return sum_rec(data, data.length-1);
		/*				NON-RECURSIVE METHOD
		double total = 0;
		for(double val : data)
			total = val + total;
		return total;
		*/
	}
	
	public static double ave(double [] data){
		return ave_rec(data, data.length);
		/*				NON-RECURSIVE METHOD
		double total = sum(data);
		total = total / data.length;
		return total;
		*/
	}
	
	private static double max_rec(double[] data, int loc){
		if(loc > 0){return Math.max(data[loc], max_rec(data, loc-1));}
		else{return data[0];}
	}
	
	private static double min_rec(double[] data, int loc){
		if(loc > 0){return Math.min(data[loc], min_rec(data, loc-1));}
		else{return data[0];}
	}
	
	private static double sum_rec(double[] data, int loc){
		if(loc > 0){return data[loc] + sum_rec(data, loc-1);}
		else{return data[loc];}
	}
	
	private static double ave_rec(double[] data, int loc){
		if(loc == 1){return data[loc-1];}
		else{return ((ave_rec(data, loc-1)*(loc-1) + data[loc-1]) / loc);}
	}

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter size of array:");
		double [] mainData = new double[scan.nextInt()];
		System.out.println("Enter values in array:");
		for(int i = 0; i < mainData.length; i++)
			mainData[i] = scan.nextInt();
		System.out.print("Values: ");
		for (double value : mainData)
			System.out.print(value + ", ");
		System.out.println("\nMax: "+max(mainData)+"\nMin: "+min(mainData)+"\nSum: "+sum(mainData)+"\nAverage: "+ave(mainData));
	}
}