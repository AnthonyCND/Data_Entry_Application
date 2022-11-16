import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InnerContainer {
	JLabel label;
	JTextField textField;
	JPanel panel;
	public InnerContainer (String s) {
		this.label = new JLabel();
		this.label.setText(s);
		this.label.setPreferredSize(new Dimension(150,14));
		this.textField = new JTextField();
		this.textField.setPreferredSize(new Dimension(300,30));
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.add(label,BorderLayout.WEST);
		this.panel.add(textField, BorderLayout.EAST);
	}
	
	
}
