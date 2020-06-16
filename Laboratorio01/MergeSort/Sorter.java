public class Sorter implements Runnable {
	private float[] a;
	private int threadCount;
	
	public Sorter(float[] a, int threadCount) {
		this.a = a;
		this.threadCount = threadCount;
	}
	
	public void run() {
		MergeSort.parallelMergeSort(a, threadCount);
	}
}