package ca.grm.rot.libs;

public class ThreadTest extends Thread
{
	public void run() {
		while(true)
        System.out.println("Hello from a thread!");
    }
}
