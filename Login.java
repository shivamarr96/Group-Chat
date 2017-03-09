import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;

class Login implements ActionListener
{
	JTextField namearea;
	JFrame loginframe;
	JButton loginbutton;
	Border blackline;
	String name;

	public Login()
	{
		loginframe=new JFrame("Enter name to login");
        loginframe.setSize(400,400);
		namearea=new JTextField(20);
        loginbutton=new JButton("Login");
        loginbutton.setBounds(90,250,175,75);
		namearea.setBounds(50,50,250,100);
        loginbutton.addActionListener(this);
        loginframe.setLayout(null);
        loginframe.add(namearea);
        loginframe.add(loginbutton);
        blackline = BorderFactory.createLineBorder(Color.green);
		namearea.setBorder(blackline);
		loginbutton.setBorder(blackline);
		loginframe.setVisible(true);
		loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public void actionPerformed(ActionEvent e)
{
	
	name=namearea.getText();
	loginframe.dispose();
	new Chat("MYCHAT:"+name,name);
}
public static void main(String[] args)
 {
	new Login();
}
}	