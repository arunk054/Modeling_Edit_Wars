
public class Action {
	
	public static final int TOTAL_ACTIONS=4;
	public static final int POSITIVE_COMMIT_ACTION=0;
	public static final int NEGATIVE_REVERT_ACTION=1;
	public static final int NEGATIVE_COMMIT_ACTION=2;
	public static final int POSITIVE_REVERT_ACTION=3;
	
	public static boolean isRevertAction(int i){
		if (i== Action.NEGATIVE_REVERT_ACTION || i== Action.POSITIVE_REVERT_ACTION){
			return true;
		}
		else 
			return false;
	}
	//This is based on bucket brigade algorithm
	public static void updatePayoff(Agent prevAgent,Agent curAgent, ConfigurationParameters cp){
		int curActionIndex = curAgent.selectedActionIndex;
		if (curActionIndex==Action.POSITIVE_COMMIT_ACTION){
			updatePayoffForPositiveCommit(prevAgent, curAgent, cp);
		} else if (curActionIndex==Action.NEGATIVE_COMMIT_ACTION){
			updatePayoffForNegativeCommit(prevAgent, curAgent, cp);
		} else if (curActionIndex==Action.POSITIVE_REVERT_ACTION){
			updatePayoffForPositiveRevert(prevAgent, curAgent, cp);
		} else if (curActionIndex==Action.NEGATIVE_REVERT_ACTION){
			updatePayoffForNegativeRevert(prevAgent, curAgent, cp);
		} else {
			//Unknown Action: Error
		}
	}
	
	private static void updatePayoffForPositiveCommit(Agent prevAgent,Agent curAgent, ConfigurationParameters cp){
		int prevIndex = prevAgent.selectedActionIndex;

		Agent agent = curAgent;
		double multiplierI = 0 - (agent.alpha / (1- agent.alpha));
		agent = prevAgent;
		double multiplierJ = 0 - (agent.alpha / (1- agent.alpha));
		
		if (prevIndex==Action.POSITIVE_COMMIT_ACTION){
			agent = prevAgent;
			double falphaj = Math.pow(Math.E, agent.alpha - 1);
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent= curAgent;
			double falphai = Math.pow(Math.E, agent.alpha - 1);
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
			
		} else if (prevIndex==Action.NEGATIVE_COMMIT_ACTION){
			agent = prevAgent;
			double falphaj = Math.pow(Math.E, 0-agent.alpha);
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent = curAgent;
			double falphai = Math.pow(Math.E, 0-agent.alpha);
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
			
			
		} else if (prevIndex==Action.NEGATIVE_REVERT_ACTION){
			agent = prevAgent;
			double falphaj = agent.alpha;
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent = curAgent;
			double falphai = agent.alpha;
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else if (prevIndex==Action.POSITIVE_REVERT_ACTION){
			agent = prevAgent;
			double falphaj = 1-agent.alpha;
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent = curAgent;
			double falphai = 1-agent.alpha;
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else {
			//Unknown Action: Error
		}
	}
	
	private static void updatePayoffForNegativeCommit(Agent prevAgent,Agent curAgent, ConfigurationParameters cp){
		int prevIndex = prevAgent.selectedActionIndex;
		Agent agent = curAgent;
		double multiplierI = 0 - (agent.alpha / (1- agent.alpha));
		agent = prevAgent;
		double multiplierJ = 0 - (agent.alpha / (1- agent.alpha));

		
		if (prevIndex==Action.POSITIVE_COMMIT_ACTION){
			agent = prevAgent;
			double falphaj = Math.pow(Math.E, 0-agent.alpha);
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent = curAgent;
			double falphai = Math.pow(Math.E, 0-agent.alpha);
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
			
		} else if (prevIndex==Action.NEGATIVE_COMMIT_ACTION){
			agent = prevAgent;
			double falphaj = Math.pow(Math.E, agent.alpha - 1);
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent= curAgent;
			double falphai = Math.pow(Math.E, agent.alpha - 1);
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else if (prevIndex==Action.NEGATIVE_REVERT_ACTION){
			agent = prevAgent;
			double falphaj = 1-agent.alpha;
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent = curAgent;
			double falphai = 1-agent.alpha;
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else if (prevIndex==Action.POSITIVE_REVERT_ACTION){
			agent = prevAgent;
			double falphaj = agent.alpha;
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent = curAgent;
			double falphai = agent.alpha;
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else {
			//Unknown Action: Error
		}
	}
	
	private static void updatePayoffForPositiveRevert(Agent prevAgent,Agent curAgent, ConfigurationParameters cp){
		int prevIndex = prevAgent.selectedActionIndex;
		Agent agent = curAgent;
		double multiplierI = 0 - (agent.alpha / (1- agent.alpha));
		agent = prevAgent;
		double multiplierJ = 0 - (agent.alpha / (1- agent.alpha));

		
		if (prevIndex==Action.POSITIVE_COMMIT_ACTION){
			agent = prevAgent;
			double falphaj = 1-agent.alpha;
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent = curAgent;
			double falphai = 1-agent.alpha;
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else if (prevIndex==Action.NEGATIVE_COMMIT_ACTION){
			agent = prevAgent;
			double falphaj = agent.alpha;
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent = curAgent;
			double falphai = agent.alpha;
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else if (prevIndex==Action.NEGATIVE_REVERT_ACTION){
			agent = prevAgent;
			double falphaj = Math.pow(Math.E, (-1)/(1-agent.alpha) );
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent= curAgent;
			double falphai = Math.pow(Math.E, (-1)/(1 - agent.alpha));
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else if (prevIndex==Action.POSITIVE_REVERT_ACTION){
			agent = prevAgent;
			double falphaj = Math.pow(Math.E, (-1)/agent.alpha );
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent= curAgent;
			double falphai = Math.pow(Math.E, (-1)/agent.alpha);
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else {
			//Unknown Action: Error
		}
	}
	
	private static void updatePayoffForNegativeRevert(Agent prevAgent,Agent curAgent, ConfigurationParameters cp){
		int prevIndex = prevAgent.selectedActionIndex;
		Agent agent = curAgent;
		double multiplierI = 0 - (agent.alpha / (1- agent.alpha));
		agent = prevAgent;
		double multiplierJ = 0 - (agent.alpha / (1- agent.alpha));

		
		if (prevIndex==Action.POSITIVE_COMMIT_ACTION){
			agent = prevAgent;
			double falphaj = agent.alpha;
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent = curAgent;
			double falphai = agent.alpha;
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else if (prevIndex==Action.NEGATIVE_COMMIT_ACTION){
			agent = prevAgent;
			double falphaj = 1- agent.alpha;
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent = curAgent;
			double falphai = 1 - agent.alpha;
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else if (prevIndex==Action.NEGATIVE_REVERT_ACTION){
			agent = prevAgent;
			double falphaj = Math.pow(Math.E, (-1)/agent.alpha );
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent= curAgent;
			double falphai = Math.pow(Math.E, (-1)/agent.alpha);
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else if (prevIndex==Action.POSITIVE_REVERT_ACTION){
			agent = prevAgent;
			double falphaj = Math.pow(Math.E, (-1)/(1-agent.alpha) );
			double Pi = 1 - Math.pow(Math.E, falphaj*multiplierI);
			agent= curAgent;
			double falphai = Math.pow(Math.E, (-1)/(1 - agent.alpha));
			double Pj = 1 - Math.pow(Math.E, falphai*multiplierJ);
			prevAgent.addPayoff(Pj);
			curAgent.addPayoff(Pi);
		} else {
			//Unknown Action: Error
		}
	}
	public static void updateAspiredPayoff(Agent prevAgent, Agent curAgent) {

		int curActionIndex = curAgent.selectedActionIndex;
		double val = 0;
		if (curActionIndex==Action.POSITIVE_COMMIT_ACTION){
			val = getAspiredPayoffCommit(prevAgent,curAgent);
		} else if (curActionIndex==Action.NEGATIVE_COMMIT_ACTION){
			val = getAspiredPayoffCommit(prevAgent,curAgent);
		} else if (curActionIndex==Action.POSITIVE_REVERT_ACTION){
			val = getAspiredPayoffRevert(prevAgent,curAgent);
		} else if (curActionIndex==Action.NEGATIVE_REVERT_ACTION){
			val = getAspiredPayoffRevert(prevAgent,curAgent);
		} else {
			//Unknown Action: Error
		}
		curAgent.addAspiredPayoff(val);
		
		curActionIndex = prevAgent.selectedActionIndex;
		if (curActionIndex==Action.POSITIVE_COMMIT_ACTION){
			val = getAspiredPayoffCommit(curAgent,prevAgent);
		} else if (curActionIndex==Action.NEGATIVE_COMMIT_ACTION){
			val = getAspiredPayoffCommit(curAgent,prevAgent);
		} else if (curActionIndex==Action.POSITIVE_REVERT_ACTION){
			val = getAspiredPayoffRevert(curAgent,prevAgent);
		} else if (curActionIndex==Action.NEGATIVE_REVERT_ACTION){
			val = getAspiredPayoffRevert(curAgent,prevAgent);
		} else {
			//Unknown Action: Error
		}
		
		prevAgent.addAspiredPayoff(val);
		
	}
	private static double getAspiredPayoffCommit (Agent otherAgent, Agent curAgent){
		double retVal = 0;
		
		double multiplier =  0 - (curAgent.alpha / (1- curAgent.alpha));
		double fAlphaj  = Math.pow(Math.E, otherAgent.alpha - 1);
		retVal += Math.pow(Math.E, fAlphaj*multiplier);

		fAlphaj  = Math.pow(Math.E, 0 - otherAgent.alpha );
		retVal += Math.pow(Math.E, fAlphaj*multiplier);

		fAlphaj  = otherAgent.alpha ;
		retVal += Math.pow(Math.E, fAlphaj*multiplier);

		fAlphaj  = 1 - otherAgent.alpha ;
		retVal += Math.pow(Math.E, fAlphaj*multiplier);

		retVal = (4 - retVal) / 4;
		return retVal;
	}
	
	
	private static double getAspiredPayoffRevert (Agent otherAgent, Agent curAgent){
		double retVal = 0;
		
		double multiplier =  0 - (curAgent.alpha / (1- curAgent.alpha));
		double fAlphaj  = Math.pow(Math.E, (-1)/ (1-otherAgent.alpha ));
		retVal += Math.pow(Math.E, fAlphaj*multiplier);

		fAlphaj  = Math.pow(Math.E, (-1)/ otherAgent.alpha );
		retVal += Math.pow(Math.E, fAlphaj*multiplier);

		fAlphaj  = otherAgent.alpha ;
		retVal += Math.pow(Math.E, fAlphaj*multiplier);

		fAlphaj  = 1 - otherAgent.alpha ;
		retVal += Math.pow(Math.E, fAlphaj*multiplier);
		
		retVal = (4 - retVal) / 4; 
		return retVal;
	}
	public static void updateAspiredPayoffSimple(Agent prevAgent, Agent curAgent) {
		curAgent.addAspiredPayoff(curAgent.alpha);
		prevAgent.addAspiredPayoff(prevAgent.alpha);
		
	}
	
	public static void updateAspiredPayoffSimple1(Agent prevAgent, Agent curAgent) {
		curAgent.addAspiredPayoff(0.5);
		prevAgent.addAspiredPayoff(0.5);
		
	}
	
}
