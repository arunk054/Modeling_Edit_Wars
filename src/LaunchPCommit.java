import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class LaunchPCommit {


	public static void main(String[] args) {
		double[] arr= {0.2,0.4,0.6,0.8};
		for (int i = 0; i < arr.length; i++) {
			launch(100,0.5,arr[i]);	
		}
		
	}
	
	public static void launch(int numAgents, double lambda, double LC) {


		
		int maxIterations = 2000;
		
		int repititions = 1000;
		
			Experimenter ex = new Experimenter(maxIterations);
			int actionSelSeed = 0, agentSelectionSeed = 1, normRandomSeed = 2;
			//# repititions
			for (int j = 0; j < repititions; ++j){
				Simulation s = new Simulation(lambda, new FlexibleAgentGenerator(numAgents, 
						new NormalGenerator(normRandomSeed++, LC, 0.05), 
						new Random(agentSelectionSeed++), false), agentSelectionSeed, actionSelSeed);
				ex.runExperiment(s);
				
			}
			String allFile = "PCommit_0.05_"+repititions+"_"+numAgents+"_"+lambda+"_"+LC;
			String summaryFile = allFile+"_summary"; 
			ex.writeAllToFile(allFile+".csv");
			ex.writeSummaryToFile(summaryFile+".csv");
			ex.printOutput( OutputType.ALL);
			System.out.println();
		

	}	
}
