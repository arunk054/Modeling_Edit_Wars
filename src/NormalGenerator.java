import java.util.Random;


public class NormalGenerator {

	double mean, sd;
	Random  random;
	final static double SMALL = 0.0001, LARGE = 0.9999;
	public NormalGenerator(int seed, double mean, double sd) {
		this.random = new Random(seed);
		this.mean = mean;
		this.sd = sd;
	}
	
	public double getNext(){
		double nextVal = random.nextGaussian()*sd + mean;
		if (nextVal <= 0) {
			nextVal = SMALL;
		} else if (nextVal >=1){
			nextVal = LARGE;
		}
		return nextVal;
	}
	public double getNextUR(){
		return random.nextDouble();
	}
}
