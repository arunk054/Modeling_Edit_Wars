import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Simulation {
	
	//List of agents
	ArrayList<Agent> agentList;

	double[] aspiredPayoff=new double[Action.TOTAL_ACTIONS];
	double[] maxPayoff = new double[Action.TOTAL_ACTIONS];
	private static final double MAX_PAYOFF = 2;
	private static final double MIN_PAYOFF = 0;
	
	private Random agentSelectionRandom, actionSelectionRandom;
	
	
	ConfigurationParameters cp;
	
	
	public Simulation(double lambda, AgentGenerator ag, int agentSelSeed, int actionSelSeed) {
		//initialize variables
		cp=new ConfigurationParameters(lambda);
		
		
		this.agentList=(ArrayList<Agent>) ag.getAgentList();
		agentSelectionRandom = new Random(agentSelSeed);
		actionSelectionRandom = new Random(actionSelSeed);
		
	}
	
	public void runOnce(){
		//randomly select an agent
		List<Agent> ll = new ArrayList<Agent>(agentList);
		for(Agent agent: agentList){
			agent.setPayoff(0);
			agent.aspiredPayoff = 0;
		}
		//Create dummy agent and dummy action index for the first element in the sequence
		int prevActionIndex=Action.NEGATIVE_COMMIT_ACTION;
		if (actionSelectionRandom.nextDouble()<0.5){
			prevActionIndex=Action.POSITIVE_COMMIT_ACTION;
		}
		
		Agent prevAgent=new Agent(0,0,0,0,ll.get(0).alpha);
		
		while (ll.size()>0){
			//Generate a random number between 0 to size of LL
			int selectedAgentPos = agentSelectionRandom.nextInt(ll.size());

			Agent curAgent = ll.get(selectedAgentPos);
			
			int curActionIndex = curAgent.getRandomAction(actionSelectionRandom);
			curAgent.setSelectedAction(curActionIndex);
			
			//Compute Payoff
			Action.updatePayoff(prevAgent, curAgent, cp);
			//This will compute the average of all payoff
			//For now lets just use alpha
			//Action.updateAspiredPayoff(prevAgent, curAgent);
			Action.updateAspiredPayoffSimple(prevAgent, curAgent);
			
			prevAgent=curAgent;
			
			ll.remove(selectedAgentPos);
		}
	}
	
	
	public double getNormalizedIndividualKLDivergence(){
		//Get sum of all kL divergence

		double d=0;
		int n = agentList.size();
		double maxKL = 0;
		int neglected=0;
		ArrayList values = new ArrayList<Double>();
		for (int i = 0; i < n;i++) {
			double kl =getKLDivergence(agentList.get(i));

			//Only consider those ones which have a higher divergence
			if (kl < 0){
				neglected++;
				continue;
			}
			if (kl>maxKL){
				maxKL=kl;
			}
			//d+=kl;
			values.add(kl);

		}
		//Denominator
		double denominator = 0;
		
		if (maxKL <= 0){
			//System.out.println("Error in max KL: "+maxKL);
			//System.exit(-1);
			return 0;
		}
		//denominator *=maxKL;
		denominator = n;
		values = getNormalized(values);
		double mean = 0;
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			d = (Double) iterator.next();
			mean+=d;
		}
		mean = mean/values.size();
		
		return mean;
		
	}

	private ArrayList getNormalized(ArrayList<Double> values){
		//FInd max and min
		double max = values.get(0);
		double min = values.get(0);
		
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			Double d = (Double) iterator.next();
			
			if (d < min) {
				min = d;
			} 
			if (d > max){
				max = d;
			}
		}
		if (max==min){
			min = 0;
		}
		if (max==0){
			max=0.00001;
			
		}
		ArrayList newValues = new ArrayList<Double>();
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			Double d2 = (Double) iterator.next();
			newValues.add((d2)/(max));
		}
		return newValues;
	}
	
	public double getNormalizedQuality(){

		int totalP=0, totalN=0;
		ArrayList<Double> values = new ArrayList<Double>();
		for (Iterator iterator = agentList.iterator(); iterator.hasNext();) {
			Agent curAgent = (Agent) iterator.next();
			totalP = curAgent.positiveCommits+curAgent.negativeCommits;
			totalN = curAgent.positiveReverts+curAgent.negativeReverts;
			if (totalN == 0){
				totalN=1;
//					totalP += 1;
			}
			double val =(double)totalP/totalN;
			//System.out.println(totalP + " "+totalN);
			values.add(new Double(val));
		}
		values = getNormalized(values);
		double mean = 0;
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			Double d = (Double) iterator.next();
			mean+=d;
		}
		mean = mean/values.size();
		double val =Math.floor(mean*100);
		mean = val/100;
		return mean;
	}

	
	public double getNormalizedQualityNew(){
		int totalP=0, totalN=0;
		ArrayList<Double> values = new ArrayList<Double>();
		for (Iterator iterator = agentList.iterator(); iterator.hasNext();) {
			Agent curAgent = (Agent) iterator.next();
			totalP = curAgent.positiveCommits+curAgent.negativeCommits;
			totalN = curAgent.positiveReverts+curAgent.negativeReverts;

			double val =(double)totalP/(totalN+totalP);
			//System.out.println(totalP + " "+totalN);
			values.add(new Double(val));
		}
//		values = getNormalized(values);
		double mean = 0;
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			Double d = (Double) iterator.next();
			mean+=d;
		}
		mean = mean/values.size();
		double val =Math.floor(mean*100);
		mean = val/100;
		return mean;
	}

	
	public double getQuality(){

		int totalP=0, totalN=0;
		for (Iterator iterator = agentList.iterator(); iterator.hasNext();) {
			Agent curAgent = (Agent) iterator.next();
			totalP += curAgent.positiveCommits+curAgent.negativeCommits;
			totalN+=curAgent.positiveReverts+curAgent.negativeReverts;
		}
		double mean = (double)totalP/(totalN+totalP);
		double val =Math.floor(mean*100);
		mean = val/100;
		return mean;
	
	}
	
	public double computeEquilibriumCondition(){
		int totalP=0, totalN=0;
		for (Iterator iterator = agentList.iterator(); iterator.hasNext();) {
			Agent curAgent = (Agent) iterator.next();
			totalP += curAgent.positiveCommits;
			totalN+=curAgent.negativeCommits;
		}
		double mean = (double)totalP/totalN;
		double val =Math.floor(mean*100);
		mean = val/100;
		//System.out.println(mean);
		return mean;
	}

//	public void getCommitSD(int iterations){
//		double mean=0;
//		int totalP=0, totalN=0;
//		for (Iterator iterator = agentList.iterator(); iterator.hasNext();) {
//			Agent curAgent = (Agent) iterator.next();
//			double diff = curAgent.positiveCommits-curAgent.NegativeCommits;
//			
//			diff= diff/(double)iterations;
//			mean+=diff;
//			//System.out.println(diff);
//			totalP += curAgent.positiveCommits+curAgent.NegativeCommits;//+curAgent.negativeReverts;
//			totalN+=curAgent.negativeReverts+curAgent.positiveReverts;//+curAgent.positiveReverts;
//		}
//		mean=mean/agentList.size();
//		mean = (double)totalP/totalN;
//		//System.out.println(mean);
//		
//	}
	
	//This is based on Bush mosteller reinforcement learning
	public void updateProbabilities(){
		for (Iterator iterator = agentList.iterator(); iterator.hasNext();) {
			Agent curAgent = (Agent) iterator.next();
			

			double curPayoff = curAgent.payoff;
			double aspired = curAgent.aspiredPayoff;
			double denom1 = MAX_PAYOFF - aspired; //aspired cannot be greater than max payoff in our case
			double denom2 = aspired - MIN_PAYOFF; //Math.abs(Min_payoff - aspired)
			double denom = denom1;
			if (denom2 > denom1){
				denom = denom2;
			}
			
//			if (curPayoff < aspired) {
//				//this should be positive
//				denom = aspired - MIN_PAYOFF;
//			} else if (curPayoff == aspired) {
//				denom = 1; //does not matter since numerator is 0
//			}
			
			if (denom == 0){
				//Should not occur:
				System.err.println("Error : denom is 0 "+ "asp : "+aspired+ " cur "+curPayoff);
			}
			double sj = (curAgent.payoff-curAgent.aspiredPayoff)/(denom);
			
			if (sj < -1 || sj > 1){
				//Should not occur:
				System.err.println("Error : sj out of range "+ "asp : "+aspired+ " cur "+curPayoff);		
			}
			double delta = 0;
			if (sj >= 0){
				delta = cp.lambda*sj*(1-curAgent.actionProbs[curAgent.selectedActionIndex]);
			} else {
				delta = cp.lambda*sj*curAgent.actionProbs[curAgent.selectedActionIndex];
			}
			curAgent.actionProbs[curAgent.selectedActionIndex]+=delta;
			
			double sumOfOtherOptions=0;
			for (int i = 0; i < curAgent.actionProbs.length; i++) {
				if (i!=curAgent.selectedActionIndex){
					sumOfOtherOptions+=curAgent.actionProbs[i];
				}
			}

			//update other probabilities
			for (int i = 0; i < curAgent.actionProbs.length; i++) {
				if (i!=curAgent.selectedActionIndex){
					curAgent.actionProbs[i]-=(double)curAgent.actionProbs[i]*delta/sumOfOtherOptions;
				}
			}
			checkForZeroProbability(curAgent.actionProbs);
			//validateSumOfProbs(curAgent.actionProbs);
		}
	}
	
	private void validateSumOfProbs(double[] actionProbs) {
		
	}

	public static void checkForZeroProbability(double[] actionProbs) {
		double eps = 0.001;
		double sum = 0;
		for (int i = 0; i < actionProbs.length; i++) {
			if (actionProbs[i] <= 0){
				actionProbs[i]=eps;				
			}
			sum+=actionProbs[i];
		}
		for (int j = 0; j < actionProbs.length; j++) {
				actionProbs[j]= actionProbs[j]/sum;
		}
	}

	public void iterateOnce(){
		runOnce();
		updateProbabilities();
	}
	
	public double getDistance(Agent a1, Agent a2){
		double[] P = a1.actionProbs;
		double[] Q = a2.actionProbs;
		double distance=0;
		for (int i = 0; i < 1; i++) {
			
			double d = Math.pow(P[i]-Q[i], 2);
			distance+=d;
		}
		//System.out.println("distance "+distance);
		return Math.sqrt(distance);
	}
	
	public double getGaussianDistance(Agent a1, Agent a2){
		double[] P = a1.actionProbs;
		double[] Q = a2.actionProbs;
		double distance=0;
		for (int i = 0; i < P.length; i++) {
			double d = Math.pow(P[i]-Q[i], 2);
			distance+=d;
		}
		//return Math.sqrt(distance);
		double sigma =1;
		sigma = Math.pow(sigma, 2);
		distance /= (2*sigma);
		distance = Math.exp(-1*distance);
		return distance;
	}
	public double getKLDivergence(Agent a1){
		double[] P = a1.initialActionProbs;
		double[] Q = a1.actionProbs;
		double kl=0;
		for (int i = 0; i < P.length; i++) {
			//Assuming P[i] or Q[i] is never zero
			double d=0;
			d= P[i]*(Math.log(P[i])-Math.log(Q[i]));
			kl+=d;
			
			d= Q[i]*(Math.log(Q[i])-Math.log(P[i]));
			kl+=d;
		}
		
		return kl;
	}
	public double getKLDivergence(Agent a1, Agent a2){
		double[] P = a1.actionProbs;
		double[] Q = a2.actionProbs;
		double kl=0;
		for (int i = 0; i < P.length; i++) {
			//Assuming P[i] or Q[i] is never zero
			double d=0;
			d= P[i]*(Math.log(P[i])-Math.log(Q[i]));
			kl+=d;
			
			d= Q[i]*(Math.log(Q[i])-Math.log(P[i]));
			kl+=d;
			
			
		}
		/*if (kl<0){
			System.out.println(kl);
		}*/
		//System.out.println(kl);
		return kl;
	}
	private double twoDecimalRound(double d){
		double val = Math.floor(d*100);
		d = val/100;
		return d;
	}
	//C+/R- on positive  opinion
	public double getConsensusOnOpinionSD(){
		if (agentList.size()<2){
			return 0;
		}
		double mean=0;
		//for all agents compute the mean of c+/R-
		for (Iterator iterator = agentList.iterator(); iterator.hasNext();) {
			Agent curAgent = (Agent) iterator.next();
			mean+=curAgent.actionProbs[Action.POSITIVE_COMMIT_ACTION];
			//System.out.println(": "+twoDecimalRound(curAgent.actionProbs[Action.POSITIVE_COMMIT_ACTION])+","+twoDecimalRound(curAgent.actionProbs[Action.NEGATIVE_REVERT_ACTION])+","+twoDecimalRound(curAgent.actionProbs[Action.NEGATIVE_COMMIT_ACTION])+","+twoDecimalRound(curAgent.actionProbs[Action.POSITIVE_REVERT_ACTION]));
		}
		mean=mean/agentList.size();

		
		double sd = 0;
		//For each agent compute (xi-mean)squared
		for (Iterator iterator = agentList.iterator(); iterator.hasNext();) {
			Agent curAgent = (Agent) iterator.next();
			double d=curAgent.actionProbs[Action.POSITIVE_COMMIT_ACTION]- mean;
			d= Math.pow(d, 2);
			sd+=d;
		}
		
		sd = sd / (agentList.size() - 1);
		
		sd = Math.sqrt(sd);
		return sd;
	}
	
	
	
	public double getEucledianConsensus(){
		//Get sum of all kL divergence
		
		double d=0;
		int n = agentList.size();
		double maxKL = 0;
		int neglected=0;
		int count=0;
		for (int i = 0; i < n/2;i++) {
			for (int j = n/2;j < n; j++) {
				double kl =getDistance(agentList.get(i), agentList.get(j));
				count++;
				//Only consider those ones which have a higher divergence
				if (kl < -1){
					neglected++;
					
					continue;
				}
				if (kl>maxKL){
					maxKL=kl;
				}
				d+=kl;
			}	
		}
		//Denominator
		//double denominator = n*(n-1)/(double)2 - neglected;
		double denominator = count - neglected;
		
		if (maxKL <= 0){
			System.out.println("Error in max KL: "+maxKL);
			//System.exit(-1);
			return 0;
		}
		denominator *=maxKL;
		
		//return 1- (d/denominator);
		return (d/denominator);
	}
	
	public double getConsensusKLDivergence(){
		//Get sum of all kL divergence
		
		double d=0;
		int n = agentList.size();
		double maxKL = 0;
		int neglected=0;
		for (int i = 0; i < n;i++) {
			for (int j = i+1;j < n; j++) {
				double kl =getKLDivergence(agentList.get(i), agentList.get(j));
				
				//Only consider those ones which have a higher divergence
				if (kl < -1){
					neglected++;
					continue;
				}
				if (kl>maxKL){
					maxKL=kl;
				}
				d+=kl;
			}	
		}
		//Denominator
		double denominator = n*(n-1)/(double)1 - neglected - 1;
		
		if (maxKL <= 0){
			//System.out.println("Error in max KL: "+maxKL);
			//System.exit(-1);
			return 0;
		}
		denominator *=maxKL;
		
		//return 1 - (d/denominator);
		return (d/denominator);
	}
	

	public double getIndividualKLDivergence(){
		//Get sum of all kL divergence

		double d=0;
		int n = agentList.size();
		double maxKL = 0;
		int neglected=0;
		for (int i = 0; i < n;i++) {
			double kl =getKLDivergence(agentList.get(i));

			//Only consider those ones which have a higher divergence
			if (kl < 0){
				neglected++;
				continue;
			}
			if (kl>maxKL){
				maxKL=kl;
			}
			d+=kl;

		}
		//Denominator
		double denominator = n*(n-1)/(double)1 - neglected - 1;
		
		if (maxKL <= 0){
			//System.out.println("Error in max KL: "+maxKL);
			//System.exit(-1);
			return 0;
		}
		denominator *=maxKL;
		denominator = n;
		
		//return 1- (d/denominator);
		return (d/denominator);
	}
	
}
