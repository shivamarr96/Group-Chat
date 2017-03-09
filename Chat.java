import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
public class Chat implements ActionListener 
{
	
	JFrame mainframe;
	JPanel panel1,panel2,panel3;
	JTextArea textarea;
	JTextArea contactarea;
	JTextArea chatarea;
	JButton sendbutton,logoutbutton;
	Socket soc;
	DataInputStream din;
	DataOutputStream dout;
	JScrollPane chatscroll,contactscroll;
	InetAddress i;
	Border blackline;
	String name,ss="";
	int flag=-1;

public Chat(String s,String name)
{
try
		{
			soc = new Socket("localhost",1034);
			dout = new DataOutputStream(soc.getOutputStream());
			
		}catch(Exception e)
		{	System.out.println(e);	}
		this.name=name;
		sendbutton=new JButton("Send");
		logoutbutton=new JButton("Logout");
		mainframe=new JFrame(s);
		textarea = new JTextArea("Enter Text here.");
        contactarea = new JTextArea();
        chatarea = new JTextArea();
        panel1=new JPanel();
        panel2=new JPanel();
        panel3=new JPanel();

		sendbutton.setBounds(20,50,175,75);
		logoutbutton.setBounds(380,50,175,75);
		textarea.setBounds(40,50,500,100);
		sendbutton.addActionListener(this);
		logoutbutton.addActionListener(this);

		mainframe.setLayout(new GridLayout(3,1));
        mainframe.add(panel1);
		mainframe.add(panel2);
		mainframe.add(panel3);

        panel1.setLayout(new GridLayout(1,2));
        My m=new My(chatarea,contactarea,soc);
		Thread t1=new Thread(m);
		t1.start();
        chatscroll= new JScrollPane(chatarea);
		contactscroll=new JScrollPane(contactarea);
		panel1.add(chatscroll);
		
		chatarea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Chat"),BorderFactory.createEmptyBorder(5,5,5,5)),chatarea.getBorder()));
        panel1.add(contactscroll);
        contactarea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Chat Log"),BorderFactory.createEmptyBorder(5,5,5,5)),contactarea.getBorder()));
		contactarea.setLineWrap(true);
		contactarea.setWrapStyleWord(true);
		chatarea.setEditable(false);
		contactarea.setEditable(false);

		

        panel2.setLayout(null);
        textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		panel2.add(textarea);
		textarea.addKeyListener(new KeyListener() {

        
        public void keyTyped(KeyEvent e) {
        }

        
        public void keyPressed(KeyEvent e) {
            if ((e.getKeyCode() == KeyEvent.VK_ENTER) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
               try{
          clientChat();}catch(Exception ex){}
            }
        }

        
        public void keyReleased(KeyEvent e) {
        }
    });
		panel2.setBackground(Color.PINK);

	   panel3.setLayout(null);
	   panel3.setBackground(Color.BLACK);
	   panel3.add(sendbutton);
	   panel3.add(logoutbutton);
	

		mainframe.setSize(600,600);
		mainframe.setVisible(true);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
}
public void actionPerformed(ActionEvent e)
{
if(e.getSource()==sendbutton)
{try{
clientChat();
}catch(Exception ef){}
}
if(e.getSource()==logoutbutton)
{
try{
dout.writeUTF(name+" logged out");
dout.flush();	
	System.exit(0);}catch(Exception el){}
}

}
    
public void clientChat() throws IOException
	{
	
		if(flag==-1)
		{
ss=name+" logged in";
		dout.writeUTF(ss);
        dout.flush();
		flag=0;
	    }
		String s1="";
		
     		s1=name+":"+textarea.getText();
     		
     		dout.writeUTF(s1);
     		textarea.setText(null);
     		dout.flush();
		
	}


}
class My implements Runnable
{
	DataInputStream din;
	JTextArea chatarea,contactarea;
	//Socket soc;
	public My(JTextArea chatarea,JTextArea contactarea,Socket soc)
	{try{
		//soc = new Socket("localhost",1034);
		din= new DataInputStream(soc.getInputStream());}
		catch(Exception ed){}
		this.chatarea=chatarea;
		this.contactarea=contactarea;
	}
	public void run()
	{
		
		String s2="";
		String s1;
	try{
		do
		{
		s1=din.readUTF();
		if(s1.endsWith("logged out"))
		{
			contactarea.append(s1+"\n");}
		if(s1.endsWith("logged in"))
		{
			
			contactarea.append(s1+"\n");
		}
		else
		{
		
		chatarea.append(s1+"\n");
	}			
		}while(true);
	}catch(Exception e){}
	
}
}