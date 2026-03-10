package src.test;

import src.domain.Application;
import src.domain.ExecuteCommand;

/**
 * <h1>Main</h1>
 *
 * Точка входу програми.
 * Отримує єдиний екземпляр {@link Application} через Singleton,
 * додає команду паралельного виконання та запускає програму.
 */
public class MainDialog {

    /**
     * Точка входу.
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        Application app = Application.getInstance();
        // Додаємо нову команду 'e' до меню перед запуском
        app.addCommand(new ExecuteCommand(app.getView()));
        app.run();
    }
}