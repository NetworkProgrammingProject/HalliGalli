import java.rmi.RemoteException;
import java.util.Date;
import java.util.TimerTask;

public class ScheduledJob extends TimerTask {

	Halligalli job;
	int uid;
	
	
	public ScheduledJob(Halligalli obj, int uid) {
		try {
		if(uid < 0)
			throw new IllegalArgumentException();
		job = obj;
		this.uid = uid;
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run()  {
		try {
			System.out.println(new Date());
			
		//	job.getStatus(true,uid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
