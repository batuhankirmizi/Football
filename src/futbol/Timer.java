package futbol;

public class Timer extends Thread{
	int time;
	Timer(int miliseconds){
		super();
		this.time=miliseconds;
	}

	@Override
	public void run(){
		super.run();
		try{
			Thread.sleep(time);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
