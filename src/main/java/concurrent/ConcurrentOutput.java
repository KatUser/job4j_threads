package concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        another.run(); /* output : main ; Метод run не дает указания выполнить свои операторы в отдельной нити,
        как это делаем метод Thread#start. Метод run напрямую вызывает операторы в той же нити, в которой запущен этот метод.*/
        System.out.println(Thread.currentThread().getName());

        /*
        Thread another = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName());
                    }
                }
        );
         */
    }
}
