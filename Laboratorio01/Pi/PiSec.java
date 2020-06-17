import java.math.*;

public class PiSec {
	public static void main(String[] args) {
		BigDecimal N = BigDecimal.valueOf(1000);
		int RUNS = 20;
		BigDecimal CONST = BigDecimal.valueOf(2);
		long start, end;
		for (int i = 1; i <= RUNS; i++) {
			// medicion del tiempo de ejecucion
			start = System.currentTimeMillis();
			BigDecimal c = secuantialPi(N);
			end = System.currentTimeMillis();

			BigDecimal result = c.divide(N, MathContext.DECIMAL128);

			System.out.printf("%d) " + N + " experimentos :  %6d ms  " + result + "\n", i,
					end - start);
			N = N.multiply(CONST, MathContext.DECIMAL128); // crecimiento de la muestra
		}
	}

	public static BigDecimal secuantialPi(BigDecimal N) {
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal width = BigDecimal.ONE.divide(N, MathContext.DECIMAL128);
		BigDecimal mid, height;
		for (BigDecimal i = BigDecimal.ZERO; i.compareTo(N) < 0; i = i.add(BigDecimal.ONE)) {
			mid = (i.add(BigDecimal.valueOf(0.5))).multiply(width, MathContext.DECIMAL128);
			height = BigDecimal.valueOf(4.0).divide(
					BigDecimal.ONE.add(mid.multiply(mid, MathContext.DECIMAL128)),
					MathContext.DECIMAL128);
			sum = sum.add(height);
		}
		return sum;
	}

}
