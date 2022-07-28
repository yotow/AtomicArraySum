import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        AtomicLong sum = new AtomicLong();

        Function<int[], Runnable> runnable = (int[] shop) -> (Runnable) () -> {
            for (int j : shop) {
                sum.addAndGet(j);
            }
        };

        int[] shop1 = genArray(10, 5);
        int[] shop2 = genArray(10, 5);
        int[] shop3 = genArray(10, 5);

        new Thread(null, runnable.apply(shop1), "shop1").start();
        new Thread(null, runnable.apply(shop2), "shop2").start();
        new Thread(null, runnable.apply(shop3), "shop3").start();

        long s1 = sum.get();
        while (sum.get() != s1 || s1 == 0) {
            s1 = sum.get();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(sum);
    }

    private static int[] genArray(int count, int dig) {
        int[] res = new int[count];
        for (int i = 0; i < count; i++) {
            res[i] = dig;
        }
        return res;
    }
}
