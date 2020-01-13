package mypackage;


public class Process{
	
	private int Priority;
	private String ID;
	private boolean execute;
	private boolean block;
	private boolean ready;
	
	
	public Process()
	{
		Priority = 0;
		ID = "";
		execute = false;
		block = false;
		ready = false;
	}
	public Process(int a, String id)
	{
		Priority = a;
		ID = id;
		execute = false;
		block = false;
		ready = false;
	}
	
	
	
	public void setEx(boolean e)
	{
		execute = e;
	}
	public void setReady(boolean r)
	{
		ready = r;
	}
	public void setBlock(boolean b)
	{
		block = b;
	}
	
	
	
	
	
	public int returnPriority()
	{
		return Priority;
	}
	
	public String returnID()
	{
		return ID;
	}
	
	public boolean executeStatus()
	{
		return execute;
	}
	
	public boolean blockStatus()
	{
		return block;
	}
	
	public boolean readyStatus()
	{
		return ready;
	}
	
}
