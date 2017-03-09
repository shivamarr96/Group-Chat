import java.io.*;
import java.net.*;
import java.util.*;
public class ServerChat
{
	ServerSocket ss;
	Socket s;
	ArrayList al=new ArrayList();
	public ServerChat()
	{
		try
		{
			System.out.println("Server started"); 
			ss = new ServerSocket(1034);
			int count=0;
			while(true)
			{
			s = ss.accept();
			count++;
			System.out.println("CLIENT "+count+"CONNECTED");
			al.add(s);
			MyThread r=new MyThread(s,al);
			Thread t=new Thread(r);
			t.start();
		}

		}catch(Exception e)
		{	System.out.println(e);	}
	}
	 
	public static void main(String[] args) {
		new ServerChat();
	}
}
class MyThread implements Runnable
{
	Socket s;
	ArrayList al;
	public MyThread(Socket s,ArrayList al)
	{
		this.s=s;
		this.al=al;

	}
public void run()
{
	try{
		String s1;
	DataInputStream dis = new DataInputStream(s.getInputStream());
	do
	{
		s1=dis.readUTF();
		System.out.println(s1);
		if(!s1.equals("stop"))
		{
			tellEveryone(s1);
		}
		else
		{
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(s1);
			dos.flush();
			al.remove(s);
		}
	}while(!s1.equals("stop"));	
}catch(Exception e){}	
			
}
public void tellEveryone(String s1)
{
	try
	{
		Iterator i=al.iterator();
	while(i.hasNext())
	{
		Socket sc=(Socket)i.next();
		DataOutputStream dos = new DataOutputStream(sc.getOutputStream());
		dos.writeUTF(s1);
		dos.flush();
		//System.out.println("CLIENT");
}
}catch(Exception e){}
}
}
