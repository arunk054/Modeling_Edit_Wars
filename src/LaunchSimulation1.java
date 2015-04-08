import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class LaunchSimulation1 {

	private static  long iterations = 3000;
	private static double delta=0.001;

	private static double roundTwo(double d){
		double val =Math.floor(d*100);
		d = val/100;
		return d;
	}
	
	private static double roundThree(double d){
		double val =Math.floor(d*1000);
		d = val/1000;
		return d;
	}


	private static SimOutput runExperiment(Simulation s){


		//Simulation s = new Simulation(0.1, 0.2, 0.5, new RandomAgentGenerator(10, 40), 50, 100);
		double consensusVal=0;
		//System.out.println("=====");
		double indKLDivergence=0;
		double prev1KLD=0, prev2KLD=0;
		int i =0;
		for (i = 0; i < iterations; i++) {
			s.iterateOnce();

			consensusVal = s.getConsensusKLDivergence();
			
//			System.out.println("con " +consensusVal);
			
//			System.out.println("in "+indKLDivergence);
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
		return new SimOutput(i, roundThree(s.getConsensusKLDivergence()), s.getQuality(),indKLDivergence);

	}
		
	
	
	public static void printOutput(ArrayList listOfRuns, OutputType ot){
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
		consensus = roundTwo(consensus/listOfRuns.size());
		efficiency = roundTwo(efficiency / listOfRuns.size());
		indDiv/=listOfRuns.size();
		indDiv = roundTwo(indDiv);
		if (ot==OutputType.ALL)
			System.out.print(""+iterInt+","+consensus+","+efficiency+","+roundTwo(indDiv));
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
	
	
	//Surface
	public static void main(String[] args) {

		
		
		double[] arrAlpha= {0.2,0.5,0.8};
		double[] arrPC= {0.2,0.5,0.8};
		iterations = 2000;
		
		
		for (int i = 0; i < arrPC.length; ++i){
				int actionSelSeed = 0, agentSelectionSeed = 1, normRandomSeed = 2;
				ArrayList<SimOutput> listOfRuns = new ArrayList<SimOutput>();
				for (int j = 0; j < 100; ++j){
					Simulation s = new Simulation(0.5, new FlexibleAgentGenerator(100, 
							new NormalGenerator(normRandomSeed++, arrPC[i], 0.1), 
							new Random(agentSelectionSeed++), false), agentSelectionSeed, actionSelSeed);
					SimOutput so = runExperiment(s);
					listOfRuns.add(so);
				}
					printOutput(listOfRuns, OutputType.ALL);
					System.out.println();
		}

	}	
}
