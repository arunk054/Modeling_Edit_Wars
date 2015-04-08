import java.util.Random;


public class Agent {


	//probabilities for actionsC+,R-, C-,R+
	double[] actionProbs = {0.0,0.0,0.0,0.0};
	double[] initialActionProbs = {0.0,0.0,0.0,0.0};
	double payoff,aspiredPayoff;
	
	int selectedActionIndex;
	int positiveCommits, negativeCommits;
	int positiveReverts,negativeReverts;
	int maxReverts;
	int curReverts=0;
	double alpha;
	
	public Agent(double cPlus, double rMinus, double cMinus, double rPlus, double alpha) {
		
		this.actionProbs[Action.POSITIVE_COMMIT_ACTION]=cPlus;
		this.actionProbs[Action.NEGATIVE_REVERT_ACTION]=rMinus;
		this.actionProbs[Action.NEGATIVE_COMMIT_ACTION]=cMinus;
		this.actionProbs[Action.POSITIVE_REVERT_ACTION]=rPlus;
		
		this.initialActionProbs[Action.POSITIVE_COMMIT_ACTION]=cPlus;
		this.initialActionProbs[Action.NEGATIVE_REVERT_ACTION]=rMinus;
		this.initialActionProbs[Action.NEGATIVE_COMMIT_ACTION]=cMinus;
		this.initialActionProbs[Action.POSITIVE_REVERT_ACTION]=rPlus;
		
		
		this.payoff=0;
		this.aspiredPayoff=0;
		positiveCommits=0;
		negativeCommits=0;
		positiveReverts=0;
		negativeReverts=0;
		this.alpha = alpha;
	}

	public int getRandomAction(Random r){
		double cumulativeProb=0;
		double d = r.nextDouble();
		for (int i = 0; i < actionProbs.length; i++) {
			cumulativeProb+=actionProbs[i];
//			if (curReverts>=maxReverts){
//				if (Action.isRevertAction(i)){
//					if (i==actionProbs.length-1){
//						return i-1;
//					}else 
//						continue;
//				}
//			}
			if (d < cumulativeProb){
				return i;
			}	
		}
		
		System.out.println("Error: Action Probabilities does not sum to 1");
		return 0;
	}
	
	public void setSelectedAction(int index){
		this.selectedActionIndex=index;
		if (Action.isRevertAction(index)){
			curReverts++;
		}
		if (index==Action.POSITIVE_COMMIT_ACTION){
			this.positiveCommits++;
		} else if (index == Action.NEGATIVE_COMMIT_ACTION){
			this.negativeCommits++;
		} else if (index == Action.NEGATIVE_REVERT_ACTION){
			this.negativeReverts++;
		}else {
			this.positiveReverts++;
		}
		
	}
	
	public void addPayoff(double val){
		this.payoff+=val;
	}
	
	public void setPayoff(double payoff){
		this.payoff=payoff;
	}
	
	public double getPayoff(){
		return this.payoff;
	}
	
	public void addAspiredPayoff(double aspiredPayoff){
		this.aspiredPayoff+=aspiredPayoff;	
	}
	
	
}
