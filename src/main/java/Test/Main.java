package Test;


public class Main {

        private static final Object resource1 = new Object();
        private static final Object resource2 = new Object();

        public static void main(String[] args) {
            Thread thread1 = new Thread(() -> {
                synchronized (resource1) {
                    System.out.println("Thread 1: Locked resource 1");

                    // Pour simuler un certain délai dans l'exécution
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (resource2) {
                        System.out.println("Thread 1: Locked resource 2");
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                synchronized (resource2) {
                    System.out.println("Thread 2: Locked resource 2");

                    // Pour simuler un certain délai dans l'exécution
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (resource1) {
                        System.out.println("Thread 2: Locked resource 1");
                    }
                }
            });

            thread1.start();
            thread2.start();
        }
    }


