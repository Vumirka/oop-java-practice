package src.domain;

// ============================================================
// SINGLETON - єдиний екземпляр Application
// ============================================================

/**
 * <h1>Application</h1>
 *
 * Реалізує шаблон проектування Singleton.
 * Гарантує що існує лише один екземпляр програми.
 *
 * <p>Формує меню з команд та запускає головний цикл.</p>
 */
public class Application {

    /**
     * Єдиний екземпляр класу - шаблон Singleton.
     * Створюється одразу при завантаженні класу.
     */
    private static final Application INSTANCE = new Application();

    /**
     * Закритий конструктор - шаблон Singleton.
     * Забороняє створення екземплярів ззовні.
     */
    private Application() {}

    /**
     * Повертає єдиний екземпляр Application - шаблон Singleton.
     * @return єдиний екземпляр
     */
    public static Application getInstance() {
        return INSTANCE;
    }

    /**
     * Об'єкт View - отримується через Factory Method.
     * Поліморфне поле: тип View, фактичний об'єкт ViewTable.
     */
    private View view = new ViewableTable().getView();

    /**
     * Меню - макрокоманда (шаблон Command).
     */
    private Menu menu = new Menu();

    /**
     * Стек для undo - зберігає виконані команди.
     */
    private java.util.Stack<ConsoleCommand> history
            = new java.util.Stack<>();
    
    /** Повертає об'єкт view для використання ззовні */
    public View getView() {
    return view;
    }

    /** Додає команду до меню ззовні */
    public void addCommand(ConsoleCommand cmd) {
    menu.add(cmd);
    }

    /**
     * Запускає програму - формує меню і запускає цикл.
     */
    public void run() {
        // Додаємо команди до меню (макрокоманда)
        menu.add(new ViewCommand(view));
        menu.add(new GenerateCommand(view, history));
        menu.add(new ScaleCommand(view, history));
        menu.add(new SortCommand(view, history));
        menu.add(new SaveCommand(view));
        menu.add(new RestoreCommand(view));
        menu.add(new UndoCommand(history));

        // Запускаємо меню
        menu.execute();
    }
}