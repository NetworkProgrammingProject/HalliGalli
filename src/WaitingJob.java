import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.TimerTask;

public class WaitingJob extends TimerTask implements Serializable {

    WaitingScreen waitSc;
    Halligalli halliGalli;
    StatusRes curStatus;
    int uid;
    boolean play;

    public WaitingJob(WaitingScreen waitSc, boolean play) {
        this.waitSc = waitSc;
        this.halliGalli = waitSc.halliGalli;
        this.uid = waitSc.uid;
        this.play = play;
    }

    public void run() {
        try {
            if (uid < 0)
                uid = halliGalli.joinGame();
            else {
                HalligalliClient.id = uid;
                curStatus = halliGalli.getStatus(play, uid);

                if (curStatus.waiting == false) {
                    //InGame 화면 생성
                    new InGameScreen(halliGalli, uid);
                    this.waitSc.destroy();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
