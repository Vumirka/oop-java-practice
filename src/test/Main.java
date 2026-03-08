package src.test;

import src.domain.TextAnalyzer;

/**
 * Main
 * Головний клас програми. Отримує аргументи командного рядка
 * та передає їх на аналіз до класу TextAnalyzer.
 */
public class Main {
    
    /**
     * Точка входу в програму.
     * @param args слова для аналізу, передані через командний рядок
     */
    public static void main(String[] args) {
        System.out.println("\nStarting Text Analyzer...");

        TextAnalyzer analyzer = new TextAnalyzer();
        System.out.println(analyzer.analyze(args));

        System.out.println("Done! Program finished successfully.\n");
    }
}