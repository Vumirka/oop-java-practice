package src.test;

import src.domain.command.Application;
import src.domain.command.ConsoleCommand;
import src.domain.command.ExecuteCommand;

/**
 * <h1>Main</h1>
 *
 * Точка входу консольної програми.
 * Отримує єдиний екземпляр {@link Application} через Singleton,
 * додає команду паралельного виконання та запускає програму.
 */
public class Main {

    /**
     * Точка входу.
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        Application app = Application.getInstance();
        app.addCommand(new ExecuteCommand(app.getView()));
        app.run();
    }
}