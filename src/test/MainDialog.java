package src.test;

import src.domain.Application;

/**
 * <h1>Main</h1>
 *
 * Точка входу програми.
 * Отримує єдиний екземпляр {@link Application} через Singleton
 * та запускає програму.
 */
public class MainDialog {

    /**
     * Точка входу.
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        // Singleton: отримуємо єдиний екземпляр
        Application app = Application.getInstance();
        app.run();
    }
}