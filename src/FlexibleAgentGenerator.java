import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FlexibleAgentGenerator implements AgentGenerator{

	private ArrayList<Agent> agentList;
	static int seed = 1;
	public FlexibleAgentGenerator(int numAgents, NormalGenerator normRandom, Random uniformRandom, boolean isAlpha) {
		
		
		agentList=new ArrayList<Agent>();
		for (int i = 0; i < numAgents; i++) {
			double commitProbability = 0;
			if (isAlpha){
				commitProbability = uniformRandom.nextDouble();
				//NormalGenerator nr = new NormalGenerator(seed++, 0.5, 0.05);
				//commitProbability = nr.getNext();
			} else {
				commitProbability = normRandom.getNext();	
			}
			
			double[] actionProb={0,0,0,0};
			if (uniformRandom.nextDouble()<0.5){
				actionProb[Action.POSITIVE_COMMIT_ACTION] = commitProbability;
				actionProb[Action.NEGATIVE_REVERT_ACTION] = 1-commitProbability;

			} else {
				actionProb[Action.NEGATIVE_COMMIT_ACTION] = commitProbability;
				actionProb[Action.POSITIVE_REVERT_ACTION] = 1-commitProbability;				
			}
			Simulation.checkForZeroProbability(actionProb);

			agentList.add(new Agent(actionProb[0],actionProb[1],actionProb[2],actionProb[3], (isAlpha)?normRandom.getNext():uniformRandom.nextDouble()));
			
		}

	}

	@Override
	public List<Agent> getAgentList() {
		return agentList;
	}

}
