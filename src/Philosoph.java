import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosoph extends Thread{
    private String name;
    private int leftFork;
    private int rightFork;
    private int count;
    private Random random;
    private CountDownLatch cdl;
    private Table table;


    public Philosoph(String name, Table table, int left, int right, CountDownLatch cdl) {
        this.table = table;
        this.name = name;
        this.leftFork = left;
        this.rightFork = right;
        this.cdl = cdl;
        count = 0;
        random = new Random();
    }

    @Override
    public void run() {
        while (count < 3) {

            try {
                thinking();
                eating();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(name + " сыт.");
        cdl.countDown();
    }

    private void thinking() throws InterruptedException {
        sleep(random.nextLong(1000, 3000));
    }

    private void eating() throws InterruptedException {
        if (table.getForks(leftFork, rightFork)) {
            System.out.println(name + " взял 2 вилки " + (leftFork + 1) + " & " + (rightFork + 1) + " и ест.");
            sleep(random.nextLong(2000, 5000));
            table.putFork(leftFork, rightFork);
            System.out.println(name + " поел и положил вилки " + (leftFork + 1) + " & " + (rightFork + 1) + ". Размышляет.");
            count++;
        }
    }
}
