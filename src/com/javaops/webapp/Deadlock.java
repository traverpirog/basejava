package com.javaops.webapp;

public class Deadlock {
    public static void main(String[] args) {
        Person person = new Person("Jimmy");
        Person person2 = new Person("Tommy");
        new Thread(() -> {
            synchronized (person2) {
                System.out.println(Thread.currentThread().getName() + ": got person2 monitor");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": trying to get person monitor");
                synchronized (person) {
                    System.out.println(Thread.currentThread().getName() + ": got person monitor");
                    person2.greeting(person);
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (person) {
                System.out.println(Thread.currentThread().getName() + ": got person monitor");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": trying to get person2 monitor");
                synchronized (person2) {
                    System.out.println(Thread.currentThread().getName() + ": got person2 monitor");
                    person.greeting(person2);
                }
            }
        }).start();
    }

    record Person(String name) {
        public void greeting(Person person) {
            System.out.println("Hello, " + person.name());
        }
    }
}
