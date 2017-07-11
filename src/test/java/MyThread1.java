public class MyThread1 implements Runnable{
    public  volatile int ticket=5;
    public void run()
    {
        for(int i=0;i<100;i++)
        {
            if(this.ticket>0)
            {
//                    try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

                System.out.println(Thread.currentThread()+"i="+i+"卖票：ticket "+this.ticket--);


            }
        }
    }
}

 class ThreadTicket {
    public static void main(String[] args) {

        MyThread1 mt=new MyThread1();

            new Thread(mt).start();
        new Thread(mt).start();
        new Thread(mt).start();
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mt.ticket=10;



    }

}