
import java.util.*;   

public class MergeSort {
	public static Random random = new Random();

	public static void main(String[] args) throws Throwable {
		int LENGTH = 1500;   // numero de elementos base del array
		int RUNS   =  16;   // veces que crecera el array
		int CONST = 2;    // constante de crecimiento 
		
		//secuencial
		System.out.printf("\tSecuencial\n\n");
		for (int i = 1; i <= RUNS; i++) {
			float[] a = createRandomArray(LENGTH);

			// medicion del tiempo de ejecucion
			long start = System.currentTimeMillis();
			mergeSort(a);
			long end = System.currentTimeMillis();
			
			System.out.printf("%d) %10d elementos  :  %6d ms \n", i, LENGTH, end - start);
			LENGTH *= CONST;   // crecimiento del array
		}
		LENGTH = 1500;   // numero de elementos base del array
		RUNS   =  16;   // veces que crecera el array
		CONST = 2;    // constante de crecimiento 
		
		//paralelo
		System.out.printf("\tParalelo\n\n");
		for (int i = 1; i <= RUNS; i++) {
			float[] a = createRandomArray(LENGTH);

			// medicion del tiempo de ejecucion
			long start = System.currentTimeMillis();
			parallelMergeSort(a);
			long end = System.currentTimeMillis();;
			
			System.out.printf("%d) %10d elementos  :  %6d ms \n", i, LENGTH, end - start);
			LENGTH *= CONST;   // crecimiento del array
		}
	}
	
	//sobrecarga de metodos	
	public static void parallelMergeSort(float[] a) {
		int threads = 4;
		parallelMergeSort(a, threads);
	}
	
	public static void parallelMergeSort(float[] a, int threadCount) {
		//Un solo thread
		if (threadCount <= 1) {
			mergeSort(a);
		//Mas de dos threads
		} else if (a.length >= 2) {
			
			float[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
			float[] right = Arrays.copyOfRange(a, a.length / 2, a.length);
			

			Thread lThread = new Thread(new Sorter(left,  threadCount / 2));
			Thread rThread = new Thread(new Sorter(right, threadCount / 2));
			
			
			lThread.start();
			rThread.start();
			//rThread espera que el hilo lthread termine para comenzar su ejecucion
			try {
		
				lThread.join();
				rThread.join();
			} catch (InterruptedException ie) {}
		
			
			merge(left, right, a);
		}
	}
	
	// merge sort sin paralelizar(para un solo hilo)
	public static void mergeSort(float[] a) {
		if (a.length >= 2) {

			float[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
			float[] right = Arrays.copyOfRange(a, a.length / 2, a.length);
			
			mergeSort(left);
			mergeSort(right);
			//ordena
			merge(left, right, a);
		}
	}
	
	// Combinar y terminar de ordenar los dos arrays resultantes
	public static void merge(float[] left, float[] right, float[] a) {
		int i1 = 0;
		int i2 = 0;
		for (int i = 0; i < a.length; i++) {
			
			if (i2 >= right.length || (i1 < left.length && left[i1] < right[i2])) {
				a[i] = left[i1];
				i1++;
			} else {
				a[i] = right[i2];
				i2++;
			}
		}
	}
		
	// Array de floats aleatorios
	public static float[] createRandomArray(int length) {
		float[] a = new float[length];
		for (int i = 0; i < a.length; i++) {
			a[i] = random.nextFloat() * 1500000;
		}
		return a;
	}
}
