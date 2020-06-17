

import java.util.*;
import java.math.*;

public class Pi {
	public static Random random = new Random();

	public static void main(String[] args) throws InterruptedException {
		BigDecimal N = BigDecimal.valueOf(1000);
		int RUNS = 20; // veces que crecera la muestra
		BigDecimal CONST = BigDecimal.valueOf(2); // constante de crecimiento
		long start, end;
		for (int i = 1; i <= RUNS; i++) {
			// medicion del tiempo de ejecucion
			start = System.currentTimeMillis();
			BigDecimal c = parallelPi(N);
			end = System.currentTimeMillis();
			// double total = N;

			BigDecimal result = c.divide(N, MathContext.DECIMAL128);

			System.out.printf("%d) " + N + " experimentos :  %6d ms  " + result + "\n", i,
					end - start);
			N = N.multiply(CONST, MathContext.DECIMAL128); // crecimiento de la muestra
		}

	}

	public static BigDecimal parallelPi(BigDecimal N) {
		BigDecimal threads = new BigDecimal("4");
		return parallelPi(N, threads);
	}

	public static BigDecimal parallelPi(BigDecimal N, BigDecimal threadCount) {
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal width = BigDecimal.ONE.divide(N, MathContext.DECIMAL128);

		Calc mythread[] = new Calc[threadCount.intValue()];


		for (BigDecimal i = BigDecimal.ZERO; i.compareTo(threadCount) < 0; i =
				i.add(BigDecimal.ONE)) {
			BigDecimal start = (i.multiply(N, MathContext.DECIMAL128)).divide(threadCount,
					MathContext.DECIMAL128);
			BigDecimal end = ((i.add(BigDecimal.ONE)).multiply(N, MathContext.DECIMAL128))
					.divide(threadCount, MathContext.DECIMAL128);
			mythread[i.intValue()] = new Calc(start, end, width);
			mythread[i.intValue()].start();
		}


		for (int j = 0; j < threadCount.intValue(); j++) {
			try {
				mythread[j].join();
				sum = sum.add(mythread[j].getSum());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return sum;
	}
}
