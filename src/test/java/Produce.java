import java.util.concurrent.locks.Lock;

public class Produce implements Runnable{

    private Lock lock;
    public Produce(Lock lock) {
        this. lock = lock;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        int count = 10;
        while (count > 0) {
            try {
                lock.lock();
                count --;
                System. out.println( "A");
            } finally {
                lock.unlock();
                System.out.println("pro");
                try {
                    Thread. sleep(90L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
}