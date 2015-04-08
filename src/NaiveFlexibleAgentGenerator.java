import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NaiveFlexibleAgentGenerator implements AgentGenerator{

	private ArrayList<Agent> agentList;

	public NaiveFlexibleAgentGenerator(int numAgents, NormalGenerator commitProb, NormalGenerator alphaProb, Random uniformRandom, boolean isAlpha) {
		
		
		agentList=new ArrayList<Agent>();
		for (int i = 0; i < numAgents; i++) {
			double commitProbability = 0;
			if (isAlpha){
				commitProbability = commitProb.getNextUR();	
			} else {
				commitProbability = commitProb.getNext();	
			}
			
			double commitProbHalf = commitProbability/2;
			double delta = uniformRandom.nextDouble()*commitProbHalf;
			double[] actionProb={0,0,0,0};
			if (uniformRandom.nextDouble()<0.5){
				delta = 0-delta;
			}
			actionProb[Action.POSITIVE_COMMIT_ACTION] = commitProbHalf-delta;
			actionProb[Action.NEGATIVE_COMMIT_ACTION] = commitProbHalf+delta;

			double revertProbHalf = (1-commitProbability)/2;
			delta = uniformRandom.nextDouble()*revertProbHalf;
			if (uniformRandom.nextDouble()<0.5){
				delta = 0-delta;
			}

			actionProb[Action.POSITIVE_REVERT_ACTION] = revertProbHalf-delta;
			actionProb[Action.NEGATIVE_REVERT_ACTION] = 1-actionProb[Action.POSITIVE_COMMIT_ACTION]-actionProb[Action.NEGATIVE_COMMIT_ACTION]-actionProb[Action.POSITIVE_REVERT_ACTION];
			agentList.add(new Agent(actionProb[0],actionProb[1],actionProb[2],actionProb[3], (isAlpha)?alphaProb.getNext():alphaProb.getNextUR()));
			
		}

	}

	@Override
	public List<Agent> getAgentList() {
		return agentList;
	}

}
