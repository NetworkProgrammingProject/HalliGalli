import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.TimerTask;

public class ScheduledJob extends TimerTask implements Serializable {

	Halligalli job;
	StatusRes prevStatus;
	StatusRes curStatus;
	boolean play = false;
	int uid;
	
	
	public ScheduledJob(Halligalli obj, int uid) {
		try {
		if(uid < 0)
			throw new IllegalArgumentException();
		job = obj;
		this.uid = uid;
		this.play = false;
		curStatus = new StatusRes(true);
		//curStatus = prevStatus = null;
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run()  {
		try {	
			//prevStatus = curStatus;
			int id = job.joinGame();
			curStatus = job.getStatus(play,uid);
			if(curStatus.waiting==false)
				this.play=true;
			System.out.println(curStatus.waiting);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
