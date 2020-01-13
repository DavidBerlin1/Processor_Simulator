package mypackage;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Comparator;




public class PQueue{
	
	private Process myProcess;
	private PriorityQueue<Process> readyQueue;
	private ArrayList<Process> Blocked;
	private ArrayList<Process> ALL;
	private int processID;
	
	private class ProcessComp implements Comparator<Process> {

	       public int compare(Process arg0, Process arg1) {
	           
	           if (arg0.returnPriority() > arg1.returnPriority()) {
	               return 1;
	           }
	           if (arg0.returnPriority() < arg1.returnPriority()) {
	               return -1;
	           }
	           return 0;
	       }
	}
	public PQueue (int Size)
	{
		Comparator<Process> comp = new ProcessComp();
		readyQueue = new PriorityQueue<Process>(Size, comp);
	       Blocked = new ArrayList<Process>(Size);
	       ALL = new ArrayList<Process>(Size);
	       processID = 1;
	}
	
	 public void start() {
	       myProcess = runProcess();
	       myProcess.setEx(true);
	       myProcess.setBlock(false);
	       myProcess.setReady(false);
	   }

	   public void print() {
	       while (readyQueue.size() != 0) {
	           System.out.println(runProcess().returnID());
	       }
	   }

	   public void makeReady(int pr) {
	       Process pro = makeProcess(pr);
	       pro.setReady(true);
	       pro.setBlock(false);
	       pro.setEx(false);
	       readyQueue.add(pro);
	   }

	   public void makeBlocked(int pr) {
	       Process p = makeProcess(pr);
	       p.setReady(false);
	       p.setBlock(true);
	       p.setEx(false);
	       Blocked.add(p);
	   }

	   public Process makeProcess(int p) {
	       String ran = Integer.toString(processID++);
	       Process i = new Process(p, ran);
	       return i;
	   }

	   public Process runProcess() {
	       return readyQueue.remove();
	   }

	   public Process getRunningProcess() {
	       return myProcess;
	   }

	   public ArrayList<Process> getBlockedList() {
	       return Blocked;
	   }

	   public ArrayList<Process> getAllProcesses() {
	       return ALL;
	   }

	   public PriorityQueue<Process> getReadyQueue() {
	       return readyQueue;
	   }

	   public String getStatus() {
	       String init = "Running:\n";
	       if (myProcess != null) {
	           init = init + "\tProcess ID: " + myProcess.returnID() + " "
	                   + "Priority: " + myProcess.returnPriority() + "\n";
	       } else {
	           init = init + "\tNO RUNNING PROCESS" + "\n";
	       }

	       init = init + "Ready Queue\n";

	       if (!readyQueue.isEmpty()) {
	           Object[] tempP = getReadyQueue().toArray();
	           for (int i = 0; i < tempP.length; i++) {
	               Process tempProc = readyQueue.remove();
	               init = init + "\tProcess ID: " + tempProc.returnID() + " "
	                       + "Priority: " + tempProc.returnPriority() + "\n";
	           }
	           for (int i = 0; i < tempP.length; i++) {
	               readyQueue.add((Process) tempP[i]);
	           }
	       } else {
	           init = init + "\tEmpty\n";
	       }
	       init = init + "BlockedList\n";
	       if (!Blocked.isEmpty()) {
	           for (int i = 0; i < Blocked.size(); i++) {
	               init = init + "\tProcess ID: " + Blocked.get(i).returnID()
	                       + " " + "Priority: "
	                       + Blocked.get(i).returnPriority() + "\n";
	           }
	       } else {
	           init = init + "\tEmpty\n";
	       }
	       return init;
	   }

	   public void contextSwitch() {
	       if (!readyQueue.isEmpty()) {
	           myProcess = readyQueue.remove();
	       } else {
	           myProcess = null;
	       }
	   }
	}