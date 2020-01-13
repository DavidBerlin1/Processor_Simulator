package mypackage;

public class Dispatcher {

  
  
   public static void main(String[] args)
{
       PQueue myQueue=new PQueue(1000);
       //Input Data
       myQueue.makeReady(1);
       myQueue.makeReady(2);
       myQueue.makeReady(5);
       myQueue.makeReady(8);
       myQueue.makeReady(1);
       myQueue.makeBlocked(5);
       myQueue.makeBlocked(6);
       myQueue.makeBlocked(7);
       myQueue.makeBlocked(8);
       myQueue.makeBlocked(9);
       UI ui=new UI(myQueue);
       ui.setVisible(true);
      
}
}

