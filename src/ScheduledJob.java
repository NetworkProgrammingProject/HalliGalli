import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class ScheduledJob extends TimerTask implements Serializable {

    InGameScreen screen;
    Halligalli job;
    StatusRes prevStatus;
    StatusRes curStatus;
    boolean play = false;
    boolean userSet = false;
    boolean print;
    int uid;
    int[] users;
    int tmpTurn;
    int contin;

    public ScheduledJob(InGameScreen screen, Halligalli obj, int uid) {
        try {
            if (uid < 0)
                throw new IllegalArgumentException();
            this.screen = screen;
            job = obj;
            this.uid = uid;
            this.play = false;
            curStatus = new StatusRes(false);
            curStatus.turn = 0;
            curStatus.bellUser = 0;
            users = new int[4];
            print = false;
            contin = 0;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            prevStatus = curStatus;
            curStatus = job.getStatus(play, uid);

            if (curStatus.waiting == false) {
                this.play = true;
                if (prevStatus.bellUser != curStatus.bellUser && curStatus.bellUser / (-10) == HalligalliClient.id) {
                    JOptionPane.showMessageDialog(null, "You Can't Hit Bell Now", "You Can't Hit Bell Now", JOptionPane.WARNING_MESSAGE);
                    screen.updatePlayerName(curStatus);
                    screen.updateScreen(curStatus);
                } else if (curStatus.bellUser > 0 && prevStatus.bellUser != curStatus.bellUser) {
                    if (curStatus.bellUser == HalligalliClient.id) {
                        int zeroCount = 0;

                        for (int i = 0; i < 4; i++)
                            if (curStatus.remainingCards.get(i) == 0
                                    && curStatus.users.get(i) != HalligalliClient.id)
                                zeroCount++;

                        //bell을 누른 사람이 자기 인데  게임이 끝난게 아닌 경우 벨을 자기가 눌렀다는 Dialog를 띄워줌
                        if (zeroCount < curStatus.users.size() - 1)
                            JOptionPane.showMessageDialog(null, curStatus.bellUser + " Hit Bell", curStatus.bellUser + " Hit Bell", JOptionPane.WARNING_MESSAGE);
                        else {
                            contin = JOptionPane.showConfirmDialog(null, curStatus.bellUser + " Win. Continue?", curStatus.bellUser + " Win", JOptionPane.WARNING_MESSAGE);
                            screen.endScreen();

                            //프로그램 재시작
                            if (contin == 0)
                                HalligalliClient.resetClient();
                        }
                    }
                    //bell을 눌렀을 때 남아있는 카드가 0장이면 해당 사용자 패배 표시
                    for (int j = 0; j < 4; j++)
                        if (curStatus.users.get(j) == HalligalliClient.id) {
                            if (curStatus.remainingCards.get(j) == 0) {
                                contin = JOptionPane.showConfirmDialog(null, "You Lose. Continue?", "You Lose", JOptionPane.WARNING_MESSAGE);
                                screen.endScreen();
                                if (contin == 0)
                                    HalligalliClient.resetClient();
                                return;
                            }
                        }
                }

                //자기 차례인 경우 자신의 버튼만을 갱신 
                for (int i = 0; i < 4; i++) {
                    if (HalligalliClient.id != curStatus.users.get(i))
                        screen.butGetCards[i].setEnabled(false);
                    else if (i != curStatus.turn)
                        screen.butGetCards[i].setEnabled(false);
                    else
                        screen.butGetCards[i].setEnabled(true);
                }

                screen.updateScreenWhenHitBell(curStatus);
                if (curStatus.openCards != null && curStatus.openCards[prevStatus.turn] != null) {
                    {
                        tmpTurn = curStatus.turn;
                        curStatus.turn = prevStatus.turn;
                        screen.updatePlayerName(curStatus);
                        screen.updateScreen(curStatus);
                        curStatus.turn = tmpTurn;
                    }
                }
            }

            if (curStatus.users != null)
                screen.updatePlayerName(curStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
