package src.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// ============================================================
// МАКРОКОМАНДА — колекція ConsoleCommand
// Шаблон Command: Menu містить список команд і виконує їх
// ============================================================

/**
 * <h1>Menu</h1>
 *
 * Макрокоманда у шаблоні Command.
 * Містить колекцію об'єктів {@link ConsoleCommand} та
 * делегує виклики відповідним командам.
 *
 * <p>Це і є "макрокоманда" - об'єкт що містить інші команди
 * і керує їх виконанням.</p>
 */
public class Menu implements Command {

    /** Колекція консольних команд - макрокоманда */
    private List<ConsoleCommand> commands = new ArrayList<>();

    /**
     * Додає команду до меню.
     * @param command команда для додавання
     * @return додана команда
     */
    public ConsoleCommand add(ConsoleCommand command) {
        commands.add(command);
        return command;
    }

    /**
     * Виводить рядок меню з усіма доступними командами.
     * @return рядок меню
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n  +-------------------------+\n");
        sb.append("  |   Resistance Calculator  |\n");
        sb.append("  +--------------------------+\n");
        for (ConsoleCommand c : commands) {
            sb.append("  ").append(c).append("\n");
        }
        sb.append("  (q) quit\n");
        sb.append("  >> ");
        return sb.toString();
    }

    /**
     * Запускає головний цикл меню.
     * Читає команди користувача та делегує їх виконання.
     * Це і є виконання макрокоманди.
     */
    @Override
    public void execute() {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        String input;

        loop:
        while (true) {
            System.out.print(this);
            try {
                input = in.readLine();
            } catch (IOException e) {
                System.out.println("  Input error: " + e.getMessage());
                break;
            }

            if (input == null || input.isEmpty()) continue;
            char key = input.charAt(0);

            // Вихід
            if (key == 'q') {
                System.out.println("  >> Bye! ^^");
                break loop;
            }

            // Пошук команди за клавішею
            boolean found = false;
            for (ConsoleCommand c : commands) {
                if (key == c.getKey()) {
                    c.execute();
                    found = true;
                    continue loop;
                }
            }

            if (!found) System.out.println("  >> Unknown command.");
        }
    }

    /**
     * Скасування макрокоманди - скасовує всі команди у зворотному порядку.
     */
    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
}