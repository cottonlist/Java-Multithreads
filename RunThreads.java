package Make_a_connection;

public class RunThreads implements Runnable {
    public static void main(String args[]){
        RunThreads runner = new RunThreads();
        Thread alpha = new Thread(runner);
        Thread beta = new Thread(runner);
        Thread gamma = new Thread(runner);
        alpha.setPriority(10);
        beta.setPriority(5);
        gamma.setPriority(1);
        alpha.setName("Alpha thread");
        beta.setName("Beta thread");
        gamma.setName("Gamma thread");
        alpha.start();
        beta.start();
        gamma.start();
    }

    public void run(){
        for (int i=0; i<10; i++){
            String threadName = Thread.currentThread().getName();
            int priority = Thread.currentThread().getPriority();
            System.out.println(threadName + " is of priority " + priority);
        }
    }
}
