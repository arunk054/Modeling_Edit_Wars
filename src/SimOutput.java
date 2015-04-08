
public class SimOutput {
	long iterations;
	double consensus, efficiency, indDivergence;
	
	SimOutput(long iter, double con, double eff, double indDiv){
		this.iterations=iter;
		this.consensus=con;
		this.efficiency = eff;
		this.indDivergence = indDiv;
	}

	public static String getHeader() {
		//Hard Code for now
		return "Turns,Consensus,Efficiency,Influence";
	}
	
	@Override
	public String toString() {
		String s = iterations + ","+ Experimenter.roundThree(consensus) + ","+
		Experimenter.roundThree(efficiency) + ","+ Experimenter.roundThree(indDivergence);
		return s;
	}

}
