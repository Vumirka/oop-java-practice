package src.domain;

/**
 * Text Analyzer
 * Клас для аналізу тексту, переданого через аргументи командного рядка.
 * Підраховує кількість слів, символів та виводить кожне слово з номером.
 */
public class TextAnalyzer {

    /** Роздільник для красивого виводу */
    private static final String LINE = "~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~";

    /** Повідомлення якщо аргументів немає */
    private static final String EMPTY_MESSAGE = "No input provided! Please try again ^^";

    /**
     * Аналізує масив аргументів та будує форматований звіт.
     *
     * @param args масив слів з командного рядка
     * @return готовий рядок з результатами аналізу
     */
    public String analyze(String[] args) {
        StringBuilder result = new StringBuilder();

        result.append("\n").append(LINE).append("\n");
        result.append(" Text Analysis \n");
        result.append(LINE).append("\n\n");

        // Перевірка на порожній ввід
        if (args == null || args.length == 0) {
            result.append("  ").append(EMPTY_MESSAGE).append("\n");
        } else {
            // Підрахунок загальної кількості символів
            int totalChars = 0;
            for (String word : args) {
                totalChars += word.length();
            }

            result.append(" Word count:").append(args.length).append("\n");
            result.append(" Character count:").append(totalChars).append("\n\n");
            result.append(" Words:\n");
            
            // Вивід кожного слова з порядковим номером
            for (int i = 0; i < args.length; i++) {
                result.append("    ").append(i + 1).append(". \"")
                      .append(args[i]).append("\"\n");
            }
        }

        result.append("\n").append(LINE).append("\n");
        return result.toString();
    }
}