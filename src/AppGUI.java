import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.toedter.calendar.JDateChooser;

public class AppGUI {
	
	String nameValue;
	String DOBValue;
	String genderValue;
	String mobileNumberValue;
	String departmentValue;
	String emailValue;
	
	InnerContainer nameContainer;
	InnerContainer mobileNumContainer;
	InnerContainer emailContainer;
	JDateChooser dateChooser;
	JComboBox<String> departmentList;
	
	public JFrame appMain() {
		
		ImageIcon imgIcon = new ImageIcon("images\\icon.png");
		JPanel outterContainer = new JPanel();
		
		nameContainer = new InnerContainer("Student Name");
		mobileNumContainer = new InnerContainer("Mobile Number");
		emailContainer = new InnerContainer("Email");
		
		// --------------------Date of Birth Field----------------
		JLabel dateChooserLabel = creatLabel("Date of birth");
		dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(100,30));
		JPanel dateChooserPanel = new JPanel();
		dateChooserPanel.setPreferredSize(new Dimension(450,30));
		dateChooserPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		dateChooserPanel.add(dateChooserLabel);
		dateChooserPanel.add(dateChooser);
		
		// --------------------Gender Field----------------
		JLabel labelGender = creatLabel("Gender");
		
		JRadioButton radioButtonMale = new JRadioButton("Male");
		JRadioButton radioButtonFemale = new JRadioButton("Female");
		
		radioButtonMale.addActionListener(e->genderValue="Male");
		radioButtonFemale.addActionListener(e->genderValue="Female");
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(radioButtonMale);
		buttonGroup.add(radioButtonFemale);
		
		JPanel pannelbutton = new JPanel();
		pannelbutton.setLayout(new FlowLayout(FlowLayout.CENTER,50,3));
		pannelbutton.setPreferredSize(new Dimension(300,30));
		pannelbutton.add(radioButtonMale);
		pannelbutton.add(radioButtonFemale);
		
		JPanel outterButtonPanel = new JPanel();
		outterButtonPanel.setPreferredSize(new Dimension(450,30));
		outterButtonPanel.setLayout(new BorderLayout());
		outterButtonPanel.add(labelGender,BorderLayout.WEST);
		outterButtonPanel.add(pannelbutton,BorderLayout.EAST);
		
		// --------------------Department Field----------------
		JLabel departmentLabel = creatLabel("Department");
		String[] departments = {"","Electronic & Telecommunication","Computer Science",
				"Electrical","Civil","Mechanical","Chemical & Process","Material Scinece"};
		departmentList = new JComboBox<>(departments);
		departmentList.setBackground(Color.white);
		departmentList.setPreferredSize(new Dimension(220,30));
		JPanel panelDepartment = new JPanel();
		panelDepartment.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		panelDepartment.setPreferredSize(new Dimension(450,30));
		panelDepartment.add(departmentLabel);
		panelDepartment.add(departmentList);
		
		
		// --------------------Buttons field----------------
		JPanel panelBtn = new JPanel();
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
		panelBtn.setPreferredSize(new Dimension(400,30));
				
		JButton submitBtn = new JButton("Submit");
		submitBtn.setPreferredSize(new Dimension(100, 30));
		submitBtn.setFocusable(false);

		JButton resetBtn = new JButton("Reset");
		resetBtn.setFocusable(false);
		resetBtn.setPreferredSize(new Dimension(100, 30));

		panelBtn.add(submitBtn);
		panelBtn.add(resetBtn);
		
		// --------------------Add Components to the main container----------------
		outterContainer.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
		outterContainer.setPreferredSize(new Dimension(500, 290));
		outterContainer.add(nameContainer.panel);
		outterContainer.add(dateChooserPanel);
		outterContainer.add(outterButtonPanel);
		outterContainer.add(panelDepartment);
		outterContainer.add(mobileNumContainer.panel);
		outterContainer.add(emailContainer.panel);
		outterContainer.add(panelBtn);
		
		// --------------------Add action listeners to buttons----------------
		submitBtn.addActionListener(e -> {
			try {
				addToDatabase();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		resetBtn.addActionListener(e->{
			buttonGroup.clearSelection();
			genderValue="";
			nameContainer.textField.setText("");
			mobileNumContainer.textField.setText("");
			emailContainer.textField.setText("");
			dateChooser.setCalendar(null);
			departmentList.setSelectedIndex(0);
		});
		
		// --------------------Create JFrame----------------
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(600,440);
		frame.setResizable(false);
		frame.setTitle("Data Entry Application");
		frame.setIconImage(imgIcon.getImage());
		frame.getContentPane().setBackground(new Color(79,91,102));
		frame.add(outterContainer);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER,0,50));
		return frame;
	}
	
	public JLabel creatLabel(String labelTitle) {
		JLabel label = new JLabel();
		label.setText(labelTitle);
		label.setPreferredSize(new Dimension(150,14));
		return label;
	}

	public void addToDatabase() throws Exception {

		nameValue = nameContainer.textField.getText();
		mobileNumberValue = mobileNumContainer.textField.getText();
		emailValue = emailContainer.textField.getText();
		departmentValue = (String) departmentList.getSelectedItem();
		
		if( !nameValue.equals("") && dateChooser.getDate()!=null && !mobileNumberValue.equals("") 
				&& !emailValue.equals("") && !genderValue.equals("") && !departmentValue.equals("")) {
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			DOBValue = df.format(dateChooser.getDate());
			
			String url = "jdbc:mysql://localhost:3306/students_data";
			String userName= "root";
			String pw = "Enter DB password here"; //Add your Database password
			String query = "INSERT INTO student(name,date_of_birth,gender,department,mobile_number,email) VALUES(?,?,?,?,?,?)";
			Class.forName("com.mysql.cj.jdbc.Driver"); //Load driver
			Connection con = DriverManager.getConnection(url,userName,pw); //set connection to the DB
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1,nameValue);
			st.setString(2,DOBValue);
			st.setString(3,genderValue);
			st.setString(4,departmentValue);
			st.setString(5,mobileNumberValue);
			st.setString(6,emailValue);
			st.executeUpdate();
			st.close();
			con.close();
			JOptionPane.showMessageDialog(new JFrame(), "Successfully updated the database!");
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "Please fill all the fields","ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void main(String[] args) {
		AppGUI app = new AppGUI();
		app.appMain();
	}
}
