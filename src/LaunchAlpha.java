import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class LaunchAlpha {

	public static void main(String[] args) {
		//double[] arr= {0.2,0.4,0.6,0.8};
		double[] arr= {.8};
		for (int i = 0; i < arr.length; i++) {
			launch(100,0.9, arr[i]);	
		}
		
	}
	
	public static void launch(int numAgents, double lambda, double alpha) {

		
		int maxIterations = 2000;
		
		int repititions = 1000;
			Experimenter ex = new Experimenter(maxIterations);
			int actionSelSeed = 0, agentSelectionSeed = 1, normRandomSeed = 2;
			//# repititions
			for (int j = 0; j < repititions; ++j){
				Simulation s = new Simulation(lambda, new FlexibleAgentGenerator(numAgents, 
						new NormalGenerator(normRandomSeed++, alpha, 0.05), 
						new Random(agentSelectionSeed++), true), agentSelectionSeed, actionSelSeed);
				ex.runExperiment(s);
				
			}
			String allFile = "Alpha_0.05"+repititions+"_"+numAgents+"_"+lambda+"_"+alpha;
			String summaryFile = allFile+"_summary"; 
			ex.writeAllToFile(allFile+".csv");
			//ex.writeSummaryToFile(summaryFile+".csv");
			ex.printOutput( OutputType.ALL);
			System.out.println();
		

	}	
}
