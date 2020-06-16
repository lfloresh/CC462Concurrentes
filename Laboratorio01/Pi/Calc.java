
import java.math.*;
public class Calc extends Thread {

	private final BigDecimal start;
    private final BigDecimal end;
    private final BigDecimal width;

    private BigDecimal sum;
    
    public Calc(BigDecimal start, BigDecimal end, BigDecimal width) {
        this.start = start;
        this.end = end;
        this.sum = BigDecimal.ZERO;
        this.width = width;
    }
	
    @Override
	public void run() {
    	BigDecimal mid, height;

        for (BigDecimal i = start; i.compareTo(end) < 0; i = i.add(BigDecimal.ONE)) {
            mid = (i.add(BigDecimal.valueOf(0.5))).multiply(width,MathContext.DECIMAL128);
            height = BigDecimal.valueOf(4.0).divide(BigDecimal.ONE.add(mid.multiply(mid,MathContext.DECIMAL128)),MathContext.DECIMAL128);
            this.sum = this.sum.add(height);
        }
	}
	
    public BigDecimal getSum() {
        return sum;
    }

}
