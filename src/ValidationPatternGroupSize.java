import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class ValidationPatternGroupSize {

	public static String getValue(int[] arr){
		String s = "";
		for (int i = 0; i < arr.length; i++) {
			s += arr[i] + "_";
		}
		return s;
	}
	public static void main(String[] args) {


		int arr[][] = {{0,0,1}};
		int maxIterations = 2000;
		int numAgents = 100;
		double lambda = 0.5;
		int repititions = 1000;
		for (int i = 0; i <= 10; ++i){
			Experimenter ex = new Experimenter(maxIterations);
			int actionSelSeed = 0, agentSelectionSeed = 1, agentGenSeed = 2;
			//# repititions
			int[] proportion={i,0,10-i};
			for (int j = 0; j < repititions; ++j){
				Simulation s = new Simulation(lambda, new GroupAgentGenerator(numAgents, proportion[0], proportion[1], 
						proportion[2], agentGenSeed++), 
						agentSelectionSeed, actionSelSeed);
				ex.runExperiment(s);
				
			}
			String allFile = "Profile_"+repititions+"_"+getValue(proportion);
			String summaryFile = allFile+"summary"; 
			ex.writeAllToFile(allFile+".csv");
			//ex.writeSummaryToFile(summaryFile+".csv");
			ex.printOutput( OutputType.ALL);
			System.out.println();
		}

	}	
}
