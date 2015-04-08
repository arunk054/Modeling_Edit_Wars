import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Experimenter {



	private final double delta=0.001;
	ArrayList<SimOutput> listOfRuns ;

	private int maxIterations;
	public Experimenter(int maxIterations) {
		listOfRuns = new ArrayList<SimOutput>();
		this.maxIterations = maxIterations;
	}
	
	public static  double roundTwo(double d){
		double val =Math.floor(d*100);
		d = val/100;
		return d;
	}
	
	public static double roundThree(double d){
		double val =Math.floor(d*1000);
		d = val/1000;
		return d;
	}


	public void runExperiment(Simulation s){

		double consensusVal=0;
		double indKLDivergence=0;
		double prev1KLD=0, prev2KLD=0;
		int i =0;
		for (i = 0; i < maxIterations; i++) {
			s.iterateOnce();

			consensusVal = s.getConsensusKLDivergence();
			
			if (i>1) {
				if (Math.abs(prev1KLD-prev2KLD)<=delta && Math.abs(prev1KLD-consensusVal)<=delta){
					indKLDivergence = roundThree(s.getNormalizedIndividualKLDivergence());
					//System.out.println("breaking "+i + " "+ indKLDivergence);
					break;
				}
			}
			prev2KLD=prev1KLD;
			prev1KLD=consensusVal;
		}
		listOfRuns.add(new SimOutput(i, roundThree(consensusVal), s.getQuality(),indKLDivergence));

	}
	
	public void printOutput(OutputType ot){
		double iter=0;
		double consensus=0, efficiency=0, indDiv=0;
		//Compute average
		for (Iterator iterator = listOfRuns.iterator(); iterator.hasNext();) {
			SimOutput so = (SimOutput) iterator.next();
			iter+=so.iterations;
			consensus+=so.consensus;
			efficiency+=so.efficiency;
			indDiv+=so.indDivergence;
			
		}
		iter = iter/listOfRuns.size();
		
		long iterInt = Math.round(iter);
		consensus = roundThree(consensus/listOfRuns.size());
		efficiency = roundThree(efficiency / listOfRuns.size());
		indDiv/=listOfRuns.size();
		indDiv = roundThree(indDiv);
		if (ot==OutputType.ALL)
			System.out.print(""+iterInt+","+consensus+","+efficiency+","+roundThree(indDiv));
		else if (ot==OutputType.EFFICIENCY){
			System.out.print(efficiency);
		}else if (ot==OutputType.INFLUENCE){
			System.out.print(indDiv);
		}else if (ot==OutputType.ITERATIONS){
			System.out.print(iterInt);
		}
		else if (ot==OutputType.CONSENSUS){
			System.out.print(consensus);
		}
	}

	public void writeAllToFile(String file) {

			BufferedWriter bw;
			
			try {
				bw = new BufferedWriter(new FileWriter(new File(file)));	
				//Write Header;
				bw.write(SimOutput.getHeader()+"\n");
				for (Iterator iterator = listOfRuns.iterator(); iterator
						.hasNext();) {
					SimOutput output = (SimOutput) iterator.next();
					bw.write(output.toString()+"\n");
				}
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}

	public void writeSummaryToFile(String file) {
		BufferedWriter bw;
		
		try {
			bw = new BufferedWriter(new FileWriter(new File(file)));	
			//Write Header;
			bw.write(SimOutput.getHeader()+"\n");

			double iter=0;
			double consensus=0, efficiency=0, indDiv=0;
			//Compute average
			for (Iterator iterator = listOfRuns.iterator(); iterator.hasNext();) {
				SimOutput so = (SimOutput) iterator.next();
				iter+=so.iterations;
				consensus+=so.consensus;
				efficiency+=so.efficiency;
				indDiv+=so.indDivergence;
				
			}
			iter = iter/listOfRuns.size();
			
			long iterInt = Math.round(iter);
			consensus = roundThree(consensus/listOfRuns.size());
			efficiency = roundThree(efficiency / listOfRuns.size());
			indDiv/=listOfRuns.size();
			indDiv = roundThree(indDiv);

			bw.write(""+iterInt+","+consensus+","+efficiency+","+roundThree(indDiv));
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	

	}
		

}
