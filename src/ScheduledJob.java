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


    public ScheduledJob(InGameScreen screen, Halligalli obj, int uid) {
        try {
            if (uid < 0)
                throw new IllegalArgumentException();
            this.screen = screen;
            job = obj;
            this.uid = uid;
            this.play = false;
            curStatus = new StatusRes(false);
            curStatus.turn = -1;
            users = new int[4];
            print = false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            prevStatus = curStatus;
            curStatus = job.getStatus(play, uid);
            if (curStatus.remainingCards != null) {
                for (int j = 0; j < 4; j++)
                    if (curStatus.users.get(j) == HalligalliClient.id) {
                        if (curStatus.remainingCards.get(j) == 0) {
                            screen.endScreen();
                            JOptionPane.showMessageDialog(null, "Lose", "Lose", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                int zero_cnt = 0;
                for (int j = 0; j < 4; j++) {
                    if (curStatus.remainingCards.get(j) == 0)
                        zero_cnt++;
                }
                if (zero_cnt == 3) {
                    screen.endScreen();
                    JOptionPane.showMessageDialog(null, "Win", "Win", JOptionPane.PLAIN_MESSAGE);
                    return;
                }

            }

            if (curStatus.waiting == false) {
                this.play = true;
                if (prevStatus.bellUser != curStatus.bellUser && curStatus.bellUser / (-10) == HalligalliClient.id) {
                    JOptionPane.showMessageDialog(null, "You Can't Hit Bell Now", "Don't Hit Bell", JOptionPane.WARNING_MESSAGE);
                    print = true;
                } else if (prevStatus.bellUser != curStatus.bellUser && curStatus.bellUser == HalligalliClient.id) {
                    JOptionPane.showMessageDialog(null,  curStatus.bellUser+"Hit Bell", curStatus.bellUser+"Hit Bell", JOptionPane.WARNING_MESSAGE);
                    print = true;
                }
                /*
                else if (prevStatus.bellUser != curStatus.bellUser && curStatus.bellUser > 0) {
                    JOptionPane.showMessageDialog(null, " Hit Bell", "Other Hit Bell", JOptionPane.WARNING_MESSAGE);
                    print = true;
                }*/
                /*
                if (!userSet) {
                    for (int i = 0; i < 4; i++)
                        users[i] = curStatus.users.get(i);
                    userSet = true;
                }
                */
                for (int i = 0; i < 4; i++) {
                    if (HalligalliClient.id != curStatus.users.get(i))
                        screen.butGetCards[i].setEnabled(false);
                    else if (i != curStatus.turn)
                        screen.butGetCards[i].setEnabled(false);
                    else
                        screen.butGetCards[i].setEnabled(true);
                }

                curStatus.turn -= 1;
                if (curStatus.turn < 0)
                    curStatus.turn = curStatus.users.size() - 1;


                screen.updateScreenWhenHitBell(curStatus);
                if (curStatus.openCards != null && curStatus.openCards[curStatus.turn] != null) {
                {
                	screen.updatePlayerName(curStatus);
                	screen.updateScreen(curStatus);
                }
                }
            }
           if(curStatus.users != null) {
            	screen.updatePlayerName(curStatus);
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
