package cs445.assignment2;

public class Assig2B 
{
	private static int N;
	private static long sbStart, mysbStart, strStart, sbEnd, mysbEnd, strEnd, sbAppend, mysbAppend, strAppend, sbDelete, mysbDelete, strDelete, sbInsert, mysbInsert, strInsert;
	public static void main(String[] args)
	{
		N = Integer.parseInt(args[0]);
		int counter = 0;
		if(counter == 0){
			//StringBuilder
			
			StringBuilder sb = new StringBuilder();
			sbStart = System.nanoTime();
			for(int i = 0; i < N; i++){
				sb.append("A");
			}
			sbEnd = System.nanoTime();
			sbAppend = sbEnd - sbStart;
			sbStart = System.nanoTime();
			for(int i = 0; i < sb.length(); i++){
				sb.delete(0, 1);
			}
			sbEnd = System.nanoTime();
			sbDelete = sbEnd - sbStart;
			sbStart = System.nanoTime();
			for(int i = 0; i < N; i++){
				int index = i/2;
				sb.insert(index, "A");
			}
			sbEnd = System.nanoTime();
			sbInsert = sbEnd - sbStart;
			counter++;
		}
		if(counter == 1){
			//MyStringBuilder
			
			MyStringBuilder mysb = new MyStringBuilder();
			mysbStart = System.nanoTime();
			for(int i = 0; i < N; i++){
				mysb.append("A");
			}
			mysbEnd = System.nanoTime();
			mysbAppend = mysbEnd - mysbStart;
			mysbStart = System.nanoTime();
			for(int i = 0; i < mysb.length(); i++){
				mysb.delete(0, 1);
			}
			mysbEnd = System.nanoTime();
			mysbDelete = mysbEnd - mysbStart;
			mysbStart = System.nanoTime();
			for(int i = 0; i < N; i++){
				int index = i/2;
				mysb.insert(index, "A");
			}
			mysbEnd = System.nanoTime();
			mysbInsert = mysbEnd - mysbStart;
			counter++;
		}
		if(counter == 2){
			//String
			
			String str = new String();
			strStart = System.nanoTime();
			for(int i = 0; i < N; i++){
				str = str + "A";
			}
			strEnd = System.nanoTime();
			strAppend = strEnd - strStart;
			strStart = System.nanoTime();
			for(int i = 0; i < str.length(); i++){
				str = str.substring(1, str.length());
			}
			strEnd = System.nanoTime();
			strDelete = strEnd - strStart;
			strStart = System.nanoTime();
			for(int i = 0; i < N; i++){
				int index = i/2;
				String [] strSplit = new String[2];
				strSplit[0] = str.substring(0, index);
				strSplit[1] = str.substring(index+1, str.length());
				strSplit[0] = strSplit[0] + "A";
				str = strSplit[0] + strSplit[1];
			}
			strEnd = System.nanoTime();
			strInsert = strEnd - strStart;
		}
		System.out.println("SB Append: " + sbAppend + "\nSB Append Avg: " + sbAppend/N + "\nSB Delete: " + sbDelete + "\nSB Delete Avg: " + sbDelete/N + "\nSB Insert: " + sbInsert + "\nSB Insert Avg: " + sbInsert/N);
		System.out.println("\nMYSB Append: " + mysbAppend + "\nMYSB Append Avg: " + mysbAppend/N + "\nMYSB Delete: " + mysbDelete + "\nMYSB Delete Avg: " + mysbDelete/N + "\nMYSB Insert: " + mysbInsert + "\nMYSB Insert Avg: " + mysbInsert/N);
		System.out.println("\nSTR Append: " + strAppend + "\nSTR Append Avg: " + strAppend/N + "\nSTR Delete: " + strDelete + "\nSTR Delete Avg: " + strDelete/N + "\nSTR Insert: " + strInsert + "\nSTR Insert Avg: " + strInsert/N);
	}
}