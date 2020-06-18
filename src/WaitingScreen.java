import java.awt.BorderLayout;
import java.util.Timer;

import javax.swing.JLabel;

public class WaitingScreen extends  GameScreen {

	public static final String TITLE = "Waiting";

	BorderLayout lay;
    JLabel text;
    Timer m_timer;
    WaitingJob job;
    
    int uid;
	
	public WaitingScreen(int uid) {
		super(TITLE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        
        this.setLayout(new BorderLayout());
        text = new JLabel("Waiting For Matching");
        text.setHorizontalAlignment(JLabel.CENTER);
        this.add(text,BorderLayout.CENTER);
        
        this.getContentPane().add(text);
        this.uid = uid;
        m_timer = new Timer();
        job = new WaitingJob(this,false);
        m_timer.schedule(job, 0, 100);
        setVisible(true);
	}
	
	public void destroy() {
		m_timer.cancel();
		dispose();
	}
}
