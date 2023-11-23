package com.javaops.webapp;

public class Deadlock {
    public static void main(String[] args) {
        Person person = new Person("Jimmy");
        Person person2 = new Person("Tommy");
        createThread(person, person2).start();
        createThread(person2, person).start();
    }

    private static Thread createThread(Person person, Person person2) {
        return new Thread(() -> {
            synchronized (person) {
                System.out.println(Thread.currentThread().getName() + ": got " + person.name() + " monitor");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": trying to get " + person2.name() + " monitor");
                synchronized (person2) {
                    System.out.println(Thread.currentThread().getName() + ": got " + person2.name() + " monitor");
                    person.greeting(person2);
                }
            }
        });
    }

    record Person(String name) {
        public void greeting(Person person) {
            System.out.println("Hello, " + person.name());
        }
    }
}
