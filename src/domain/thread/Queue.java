package src.domain.thread;

// ============================================================

import src.domain.command.Command;

// Інтерфейс черги завдань - шаблон Worker Thread
// ============================================================

/**
 * <h1>Queue</h1>
 *
 * Інтерфейс черги завдань для шаблону Worker Thread.
 * Визначає методи додавання та вилучення команд з черги.
 */
public interface Queue {

    /**
     * Додає нове завдання у чергу.
     * @param cmd завдання типу {@link Command}
     */
    void put(Command cmd);

    /**
     * Вилучає завдання з черги.
     * @return завдання типу {@link Command}
     */
    Command take();
}