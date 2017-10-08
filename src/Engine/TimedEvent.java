package Engine;

public class TimedEvent extends Thread{
	int time;
	public TimedEvent(int miliseconds){
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
