class MyThread extends Thread{
    int id;
}

class MyClass {
    int a = 0;
    int b = 0;
}

class Scheduler {
    boolean[] threadValue = {false, false};
}

public class HardBugTrigger {
    public static void main(String args[]){

        MyClass myObject = new MyClass();
        MyClass object1 = new MyClass();
        MyClass object2 = new MyClass();

        Scheduler myScheduler = new Scheduler();
        myScheduler.threadValue[0] = true;
        myScheduler.threadValue[1] = false;


        MyThread t0 = new MyThread(){
            @Override
            public void run() {
                id = 0;

                while(!myScheduler.threadValue[id]){
                    ;
                }
                myObject.a ++;
                myScheduler.threadValue[id] = false;
                myScheduler.threadValue[1] = true;

                while(!myScheduler.threadValue[id]){
                    ;
                }
                myObject.a += 2;
                myScheduler.threadValue[id] = false;
                myScheduler.threadValue[1] = true;

                while(!myScheduler.threadValue[id]){
                    ;
                }
                while (myObject.b > 1) {
                    myObject.b -= 5;
                    myObject.a ++;
                }
                myScheduler.threadValue[id] = false;
                myScheduler.threadValue[1] = true;

                while(!myScheduler.threadValue[id]){
                    ;
                }
                if (myObject.b == -3) {
                    synchronized (object2) {
                        System.out.println("Thread t0 locks object2");
                        myScheduler.threadValue[id] = false;
                        myScheduler.threadValue[1] = true;

                        while(!myScheduler.threadValue[id]){
                            ;
                        }
                        synchronized (object1) {
                            System.out.println("Thread t0 locks object1");
                        }
                    }
                }

            }
        };

        MyThread t1 = new MyThread(){
            @Override
            public void run() {
                id = 1;

                while(!myScheduler.threadValue[id]){
                    ;
                }
                if (myObject.a == 1) {
                    myObject.a ++;
                } else {
                    myObject.a --;
                }
                myScheduler.threadValue[id] = false;
                myScheduler.threadValue[0] = true;

                while(!myScheduler.threadValue[id]){
                    ;
                }
                myObject.b += 2;
                myScheduler.threadValue[id] = false;
                myScheduler.threadValue[0] = true;

                myScheduler.threadValue[1] = false;
                while(!myScheduler.threadValue[id]){
                    ;
                }
                if (myObject.a == 5) {
                    synchronized (object1){
                        System.out.println("Thread t1 locks object1");
                        myScheduler.threadValue[id] = false;
                        myScheduler.threadValue[0] = true;

                        while(!myScheduler.threadValue[id]){
                            ;
                        }
                        synchronized (object2) {
                            System.out.println("Thread t1 locks object2");
                        }
                    }
                }


            }
        };

        t0.start();
        t1.start();

        // System.out.println("The value of a is " + myObject.a + ", the value of b is " + myObject.b + ".");
    }
}
