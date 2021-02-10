package com.huajia.daily.thread.storage.synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

public class SynchronizeDemo {
    public static void main(String[] args) {
        Storage storage = new Storage(10);
        for(int i=0; i<11; i++) {
            Producer producer = new Producer(storage);
            producer.start();
        }

        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(100);
                    storage.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class Producer extends Thread {
    Storage storage;

    public Producer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        storage.increase();
    }
}

class Consumer extends Thread {
    Storage storage;

    public Consumer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        storage.take();
    }
}

@Slf4j
class Storage {
    private final List<Object> container = new ArrayList<>();
    private int size;

    public Storage(int size) {
        this.size = size;
    }

    public void increase() {
        synchronized (container) {
            while(container.size() == size) {
                try {
                    System.out.println("仓库已满，等待消费者消费");
                    container.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            container.add(new Object());
            System.out.println(String.format("生产者%s增加成功,当前容量:%s", Thread.currentThread().getName(), container.size()));
            container.notifyAll();
        }
    }

    public Object take() {
        synchronized (container) {
            while (container.size() == 0) {
                System.out.println("仓库已空，等待生产");
                try {
                    container.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Object obj = container.remove(container.size() - 1);
            System.out.println(String.format("仓库有位置，可以减少商品,当前容量: %s", container.size()));

            container.notifyAll();
            return obj;
        }
    }
}