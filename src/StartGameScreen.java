import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import java.lang.IllegalArgumentException;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;


public class StartGameScreen extends GameScreen {

	static final String TITLE = "Start Game Screen";
	
	public StartGameScreen(Halligalli halliGalli)
	{
		super(TITLE,halliGalli);
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setContentPane();
	}
	
	
	public StartGameScreen(Halligalli halliGalli,int x, int y){
		super(TITLE,halliGalli);
		makeFrame(x,y,0,0);
		setContentPane();
	}
	
	
	public StartGameScreen(Halligalli halliGalli,int x, int y,int w, int h) {
		super(TITLE,halliGalli);
		makeFrame(x,y,w,h);
		setContentPane();
	}
	
	private void setContentPane()
	{
		try {
			Container contentPane = this.getContentPane();
			JPanel buttonPane = new JPanel();
			JButton buttonStart = new JButton("Start");
			GridBagLayout gridBag = new GridBagLayout();
			GridBagConstraints gridConst = new GridBagConstraints();
	
			ActionListener btnClickListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton clickButton = (JButton) e.getSource();
					if(clickButton.getText().contentEquals("Start"))
					{
						try {
							do {
								HalligalliClient.id = halliGalli.joinGame();
							}while(HalligalliClient.id < 0);
							new InGameScreen(halliGalli);
							dispose();	
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			};
			buttonStart.addActionListener(btnClickListener);
			
			buttonPane.add(buttonStart);
			
			gridConst.weightx = 0.0;
			gridConst.weighty = 0.0;
			gridConst.gridx = 0;
			gridConst.gridy = 0;
			
			contentPane.setLayout(gridBag);
			contentPane.add(buttonPane,gridConst);
			
			
			setVisible(true);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}
