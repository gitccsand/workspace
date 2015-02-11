package alg.dastru;

public class Coder {
	
	private static long[] projs={100,200,260,280,350,420,580,670,700};
	
	private static long DI=100;
	
	private static float AI=7f;
	

	public static void main(String[] args) {
		long cur_time = 0;
		long total_spend = 0;
		long x=0;
		long remain_time=0;
		long use_time = 0;
		
		int i=0;
		while(i<projs.length){
			
			remain_time = projs[i] - cur_time;
			
			if(remain_time>=DI){
				
				x=0;
				
			}else{
				
				x = (long) Math.ceil((DI-remain_time)/AI);
			}			
			
			use_time= (long) (DI - AI * x);
			System.out.println(projs[i]+" cost $"+ x + " and last " + use_time +" days");
			
			cur_time += use_time;
			total_spend += x;			
			i++;
		}
		
		System.out.println("all puojects are completed !");
		System.out.println("current time is "+ cur_time + " days and total spend is $"+ total_spend);

	}

}
