import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class LaunchProfilesModerates {

	
	public static void main(String[] args) {


		int arr[][] = {{1,0,1},{1,1,1},{1,2,1},{1,3,1},{1,4,1},{1,5,1},{1,6,1},{1,8,1},{1,18,1}, {0,1,0}};
		int maxIterations = 2000;
		int numAgents = 100;
		double lambda = 0.5;
		int repititions = 1000;
		for (int i = 0; i < arr.length; ++i){
			Experimenter ex = new Experimenter(maxIterations);
			int actionSelSeed = 0, agentSelectionSeed = 1, agentGenSeed = 2;
			//# repititions
			for (int j = 0; j < repititions; ++j){
				Simulation s = new Simulation(lambda, new GroupAgentGenerator(numAgents, arr[i][0], arr[i][1], 
						arr[i][2], agentGenSeed++), 
						agentSelectionSeed, actionSelSeed);
				ex.runExperiment(s);
				
			}
			String allFile = "Moderates_"+repititions+"_"+getValueModerates(arr[i]);
			String summaryFile = allFile+"summary"; 
			ex.writeAllToFile(allFile+".csv");
			ex.writeSummaryToFile(summaryFile+".csv");
			ex.printOutput( OutputType.ALL);
			System.out.println();
		}

	}
	private static int getValueModerates(int[] arr) {
		String s = "";
		int total = 0;
		for (int i = 0; i < arr.length; i++) {
			total += arr[i];
		}
		double d = (double)arr[1]/total;
		d = d*100;
		int val = (int)d;
		return val;
		
	}
	
	public static String getValue(int[] arr){
		String s = "";
		for (int i = 0; i < arr.length; i++) {
			s += arr[i] + "_";
		}
		return s;
	}
}
