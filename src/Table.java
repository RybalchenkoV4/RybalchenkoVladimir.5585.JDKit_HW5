import java.util.concurrent.CountDownLatch;

public class Table extends Thread {

    private final int PHILOSOPH_COUNT = 5;
    private Fork[] forks;
    private Philosoph[] philosophs;
    private CountDownLatch cdl;

    public Table() {
        forks = new Fork[PHILOSOPH_COUNT];
        philosophs = new Philosoph[PHILOSOPH_COUNT];
        cdl = new CountDownLatch(PHILOSOPH_COUNT);
        init();
    }

    @Override
    public void run() {
        System.out.println("Обед философов начинается.");
        try {
            startProcess();
            cdl.await();
        } catch (InterruptedException e) {
            e.fillInStackTrace();
        }
        System.out.println("Все сыты!");
    }

    private void startProcess() {
        for(Philosoph philosoph : philosophs) {
            philosoph.start();
        }
    }

    private void init() {
        for (int i = 0; i < PHILOSOPH_COUNT; i++) {
            forks[i] = new Fork();
        }

        for (int i = 0; i < PHILOSOPH_COUNT; i++) {
            philosophs[i] = new Philosoph("Безмолвный философ №" + i, this, i, (i+1) % PHILOSOPH_COUNT, cdl);
        }
    }

    public synchronized boolean getForks(int leftFork, int rightFork) {
        if (!forks[leftFork].isUsing() && !forks[rightFork].isUsing()) {
            forks[leftFork].Fork(true);
            forks[rightFork].Fork(true);
            return true;
        }
        return false;
    }


    public void putFork(int leftFork, int rightFork) {
        forks[leftFork].Fork(false);
        forks[rightFork].Fork(false);
    }
}
