package com.javaops.webapp;

public class Deadlock {
    public static void main(String[] args) {
        Person person = new Person("Jimmy");
        Person person2 = new Person("Tommy");
        new Thread(() -> {
            synchronized (person2) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (person) {
                    person2.greeting(person);
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (person) {
                synchronized (person2) {
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
