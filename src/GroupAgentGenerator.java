import java.io.Externalizable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GroupAgentGenerator implements AgentGenerator{

	private ArrayList<Agent> agentList;
	private final double extremeMaxValue = 0.5;
	private final double extremeMinValue = 0.0;
	private final double moderateValue = 0.25;
	
	
	

	public GroupAgentGenerator(int numAgents, int extremistsPos, int moderates, int extremistsNeg, int alphaRandSeed) {
		
		Random alphaRandom = new Random(alphaRandSeed);
		agentList=new ArrayList<Agent>();
		//copute each group
		int total = extremistsPos + moderates+ extremistsNeg;
		int numExposAgents = extremistsPos*numAgents/total;
		int numExnegAgents = extremistsNeg*numAgents/total;
		
		int numModerAgents = numAgents - numExposAgents - numExnegAgents;
		//First generate exPosAgents
		for (int i = 0; i < numExposAgents; ++i){
			//in order C+/R-/C-/R+ 
			
			double val1 = extremeMaxValue, val2 = extremeMinValue;
			
			double[] actionProb = {val1,val1,val2,val2};
			Simulation.checkForZeroProbability(actionProb);
			agentList.add(new Agent(actionProb[0],actionProb[1],actionProb[2],actionProb[3],alphaRandom.nextDouble()));
		}

		for (int i = 0; i < numExnegAgents; ++i){
 
			
			double val1 = extremeMaxValue, val2 = extremeMinValue;
			//in order C+/R-/C-/R+			
			double[] actionProb = {val2,val2,val1,val1};
			Simulation.checkForZeroProbability(actionProb);
			agentList.add(new Agent(actionProb[0],actionProb[1],actionProb[2],actionProb[3],alphaRandom.nextDouble()));
		}


		for (int i = 0; i < numModerAgents; ++i){			
			double val1 = extremeMaxValue;
			double val2 = extremeMinValue;
			//in order C+/R-/C-/R+			
			double[] actionProb = {val1,val2,val1,val2};
			
			agentList.add(new Agent(actionProb[0],actionProb[1],actionProb[2],actionProb[3],alphaRandom.nextDouble()));
		}

		
	}

	@Override
	public List<Agent> getAgentList() {
		return agentList;
	}

}
