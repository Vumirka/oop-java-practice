package src.domain.thread;

import src.domain.thread.Queue;
import src.domain.command.Command;
import java.util.Vector;

// ============================================================
// Worker Thread - обробник черги завдань у окремому потоці
// ============================================================

/**
 * <h1>CommandQueue</h1>
 *
 * Реалізує шаблон Worker Thread.
 * Створює окремий потік (Worker), який безперервно перевіряє
 * чергу та виконує команди по черзі.
 *
 * <p>Клієнт просто поміщає завдання у чергу методом {@link #put},
 * а Worker сам подбає про їх виконання у фоновому потоці.</p>
 */
public class CommandQueue implements Queue {

    /** Черга завдань - потокобезпечний Vector */
    private Vector<Command> tasks;

    /** Прапор очікування - потік чекає на нові завдання */
    private boolean waiting;

    /** Прапор завершення - зупиняє Worker */
    private boolean shutdown;

    /**
     * Зупиняє Worker після виконання всіх завдань.
     */
    public void shutdown() {
        shutdown = true;
    }

    /**
     * Конструктор - ініціалізує чергу та запускає Worker у новому потоці.
     */
    public CommandQueue() {
        tasks   = new Vector<>();
        waiting  = false;
        shutdown = false;
        new Thread(new Worker()).start();
    }

    /**
     * Додає завдання у чергу.
     * Якщо Worker очікує - будить його.
     * @param cmd команда для виконання
     */
    @Override
    public synchronized void put(Command cmd) {
        tasks.add(cmd);
        if (waiting) {
            notifyAll();
        }
    }

    /**
     * Вилучає завдання з черги.
     * Якщо черга порожня - очікує появи нового завдання.
     * @return наступна команда з черги
     */
    @Override
    public synchronized Command take() {
        while (tasks.isEmpty()) {
            waiting = true;
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            waiting = false;
        }
        return tasks.remove(0);
    }

    // ============================================================
    // Внутрішній клас Worker - серце шаблону Worker Thread
    // ============================================================

    /**
     * <h1>Worker</h1>
     *
     * Обробник потоку — внутрішній клас що реалізує {@link Runnable}.
     * Безперервно вилучає завдання з черги та виконує їх.
     * Це і є суть шаблону Worker Thread.
     */
    private class Worker implements Runnable {

        /**
         * Головний цикл обробника потоку.
         * Вилучає команди з черги і викликає {@link Command#execute()}.
         */
        @Override
        public void run() {
            while (!shutdown) {
                Command cmd = take();
                cmd.execute();
            }
        }
    }
}