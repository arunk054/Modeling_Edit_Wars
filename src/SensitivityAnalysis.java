
public class SensitivityAnalysis {

	public static void main(String[] args) {
		//Measure efficiency, and #turns for different agent size
		
		int[] agentSize = {100,300,500};
		
		//Difference between turns and efficiency, LC = 0.2 and 0.8, alpha = uniform random 
			//for different values of agentsize
		double[] LC = {0.2,0.8};
		for (int i = 0; i < LC.length; i++) {
			for (int j = 0; j < agentSize.length; j++) {
				LaunchPCommit.launch(agentSize[j], 0.5,LC[i]);	
			}			
		}
		double[] alpha = {0.2,0.8};
		//Difference between turns and efficiency, alpha = 0.2 and 0.8, LC = uniform random 
		//for different values of agentsize
		for (int i = 0; i < alpha.length; i++) {
			for (int j = 0; j < agentSize.length; j++) {
				LaunchAlpha.launch(agentSize[j], 0.5, alpha[i]);	
			}			
		}
		//Sama for lambda
		double[] lambdaArr = {0.2,0.5,0.8};
		
		//Difference between turns and efficiency, LC = 0.2 and 0.8, alpha = uniform random 
		//for different values of agentsize
		
		for (int i = 0; i < LC.length; i++) {
			for (int j = 0; j < lambdaArr.length; j++) {
				LaunchPCommit.launch(100, lambdaArr[j],LC[i]);	
			}			
		}
		
		//Difference between turns and efficiency, alpha = 0.2 and 0.8, LC = uniform random 
		//for different values of agentsize
		for (int i = 0; i < alpha.length; i++) {
			for (int j = 0; j < lambdaArr.length; j++) {
				LaunchAlpha.launch(100, lambdaArr[j],alpha[i]);	
			}			
		}

	}
}
