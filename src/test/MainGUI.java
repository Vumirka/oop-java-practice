package src.test;

import src.domain.observer.AppAnnotations;
import src.domain.observer.ChartObserver;
import src.domain.observer.ReflectionDemo;
import src.domain.core.ResistanceData;
import src.domain.observer.ResistanceObservable;
import src.domain.observer.TableObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// ============================================================
// Головне вікно GUI - Observer + Reflection + Annotations
// ============================================================

/**
 * <h1>MainGUI</h1>
 *
 * Графічний інтерфейс програми.
 * Демонструє шаблон Observer: {@link ResistanceObservable} сповіщає
 * {@link TableObserver} та {@link ChartObserver} про зміни колекції.
 */
@AppAnnotations.Author(name = "Rizhkevych Viktoriia", date = "2026")
public class MainGUI extends JFrame {

    // ----------------------------------------------------------
    // Кольори теми
    // ----------------------------------------------------------
    private static final Color C_BG       = new Color(252, 248, 255);
    private static final Color C_PANEL    = new Color(245, 238, 255);
    private static final Color C_ACCENT   = new Color(138, 43, 226);
    private static final Color C_ACCENT2  = new Color(186, 156, 237);
    private static final Color C_BTN_BG   = new Color(230, 215, 255);
    private static final Color C_BTN_HOV  = new Color(210, 185, 245);
    private static final Color C_TEXT     = new Color(60, 30, 80);
    private static final Color C_HEADER   = new Color(138, 43, 226);

    // ----------------------------------------------------------
    // Компоненти
    // ----------------------------------------------------------
    private ResistanceObservable observable = new ResistanceObservable();
    private ChartObserver        chartObs;
    private TableObserver        tableObs;
    private DefaultTableModel    tableModel;

    private JLabel lblMin, lblMax, lblAvg;
    private JTextArea  reflectionArea;
    private JTabbedPane chartTabs;

    /**
     * Конструктор — будує інтерфейс.
     */
    public MainGUI() {
        super("Resistance Calculator - Ріжкевич Вікторія");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(C_BG);
        setLayout(new BorderLayout(8, 8));

        buildUI();
        registerObservers();
    }

    // ----------------------------------------------------------
    // Побудова інтерфейсу
    // ----------------------------------------------------------
    private void buildUI() {
        // Заголовок
        add(buildHeader(), BorderLayout.NORTH);

        // Центр: таблиця + графіки
        JSplitPane center = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            buildTablePanel(),
            buildChartPanel());
        center.setDividerLocation(340);
        center.setDividerSize(4);
        center.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        add(center, BorderLayout.CENTER);

        // Права панель: кнопки + статистика + рефлексія
        add(buildControlPanel(), BorderLayout.EAST);
    }

    // ----------------------------------------------------------
    // Заголовок
    // ----------------------------------------------------------
    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(C_ACCENT);
        p.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        JLabel title = new JLabel(
            "Resistance Calculator");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Індивідуальне завдання №17");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setForeground(new Color(220, 200, 255));

        p.add(title, BorderLayout.WEST);
        p.add(sub,   BorderLayout.EAST);
        return p;
    }

    // ----------------------------------------------------------
    // Панель таблиці (лівий Observer)
    // ----------------------------------------------------------
    private JPanel buildTablePanel() {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBackground(C_PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_ACCENT2, 1, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JLabel lbl = new JLabel("📋 Колекція даних");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(C_HEADER);
        p.add(lbl, BorderLayout.NORTH);

        String[] cols = {"#", "I (A)", "R_total (Ohm)", "Octal", "Hex"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setRowHeight(26);
        table.setBackground(Color.WHITE);
        table.setForeground(C_TEXT);
        table.setGridColor(C_ACCENT2);
        table.getTableHeader().setBackground(C_BTN_BG);
        table.getTableHeader().setForeground(C_TEXT);
        table.getTableHeader().setFont(
            new Font("SansSerif", Font.BOLD, 12));
        table.setSelectionBackground(C_BTN_HOV);

        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    // ----------------------------------------------------------
    // Панель графіків (правий Observer - вкладки)
    // ----------------------------------------------------------
    private JPanel buildChartPanel() {
        chartObs = new ChartObserver();

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(C_PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_ACCENT2, 1, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JLabel lbl = new JLabel("📊 Графіки R_total");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(C_HEADER);
        p.add(lbl, BorderLayout.NORTH);

        chartTabs = new JTabbedPane();
        chartTabs.setFont(new Font("SansSerif", Font.PLAIN, 12));
        chartTabs.setBackground(C_BG);

        chartObs.getBarPanel().setBackground(new Color(250, 245, 255));
        chartObs.getLinePanel().setBackground(new Color(250, 245, 255));

        chartTabs.addTab("📊 Стовпчаста", chartObs.getBarPanel());
        chartTabs.addTab("📈 Лінійна",    chartObs.getLinePanel());

        p.add(chartTabs, BorderLayout.CENTER);
        return p;
    }

    // ----------------------------------------------------------
    // Права панель: кнопки + статистика + рефлексія
    // ----------------------------------------------------------
    private JPanel buildControlPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(C_PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_ACCENT2, 1, true),
            BorderFactory.createEmptyBorder(12, 10, 12, 10)));
        p.setPreferredSize(new Dimension(200, 0));

        // --- Кнопки керування ---
        p.add(sectionLabel("🎛 Керування"));
        p.add(Box.createVerticalStrut(6));
        p.add(makeBtn("✨ Генерувати",  e -> onGenerate()));
        p.add(Box.createVerticalStrut(4));
        p.add(makeBtn("⚖ Масштабувати", e -> onScale()));
        p.add(Box.createVerticalStrut(4));
        p.add(makeBtn("🔃 Сортувати",   e -> onSort()));
        p.add(Box.createVerticalStrut(14));

        // --- Статистика ---
        p.add(sectionLabel("📈 Статистика"));
        p.add(Box.createVerticalStrut(6));

        lblMin = statLabel("Мін: —");
        lblMax = statLabel("Макс: —");
        lblAvg = statLabel("Сер: —");
        p.add(lblMin);
        p.add(Box.createVerticalStrut(3));
        p.add(lblMax);
        p.add(Box.createVerticalStrut(3));
        p.add(lblAvg);
        p.add(Box.createVerticalStrut(14));

        // --- Рефлексія ---
        p.add(sectionLabel("🔬 Рефлексія"));
        p.add(Box.createVerticalStrut(6));
        p.add(makeBtn("🔍 Inspect",     e -> onReflect()));
        p.add(Box.createVerticalStrut(6));

        reflectionArea = new JTextArea(8, 16);
        reflectionArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
        reflectionArea.setEditable(false);
        reflectionArea.setBackground(new Color(245, 240, 255));
        reflectionArea.setForeground(C_TEXT);
        reflectionArea.setLineWrap(true);
        reflectionArea.setWrapStyleWord(true);
        reflectionArea.setText("Натисніть «Inspect»\nдля демонстрації\nрефлексії...");

        JScrollPane sp = new JScrollPane(reflectionArea);
        sp.setMaximumSize(new Dimension(190, 160));
        sp.setBorder(BorderFactory.createLineBorder(C_ACCENT2));
        p.add(sp);

        return p;
    }

    // ----------------------------------------------------------
    // Реєстрація спостерігачів
    // ----------------------------------------------------------
    private void registerObservers() {
        tableObs = new TableObserver(tableModel);
        observable.addObserver(tableObs);   // Observer 1 - таблиця
        observable.addObserver(chartObs);   // Observer 2 - графіки

        // Observer 3 — статистика (лямбда)
        observable.addObserver(items -> updateStats(items));
    }

    // ----------------------------------------------------------
    // Обробники кнопок
    // ----------------------------------------------------------
    private void onGenerate() {
        observable.generate(8);
    }

    private void onScale() {
        String input = JOptionPane.showInputDialog(this,
            "Коефіцієнт масштабування (0.1 - 3.0):",
            "Масштабування", JOptionPane.PLAIN_MESSAGE);
        if (input == null) return;
        try {
            double f = Double.parseDouble(input.trim());
            if (f > 0) observable.scale(f);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Введіть числове значення!", "Помилка",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSort() {
        observable.sort();
    }

    private void onReflect() {
        StringBuilder sb = new StringBuilder();
        sb.append(ReflectionDemo.inspect(ResistanceObservable.class));
        sb.append("\n");
        sb.append(ReflectionDemo.inspect(TableObserver.class));
        reflectionArea.setText(sb.toString());
    }

    // ----------------------------------------------------------
    // Оновлення статистики (третій Observer - лямбда)
    // ----------------------------------------------------------
    private void updateStats(ArrayList<ResistanceData> items) {
        if (items.isEmpty()) return;
        double min = items.stream()
            .mapToDouble(ResistanceData::getTotalResistance).min().orElse(0);
        double max = items.stream()
            .mapToDouble(ResistanceData::getTotalResistance).max().orElse(0);
        double avg = items.stream()
            .mapToDouble(ResistanceData::getTotalResistance).average().orElse(0);
        lblMin.setText(String.format("Мін: %.2f Ω", min));
        lblMax.setText(String.format("Макс: %.2f Ω", max));
        lblAvg.setText(String.format("Сер: %.2f Ω", avg));
    }

    // ----------------------------------------------------------
    // Допоміжні методи стилізації
    // ----------------------------------------------------------
    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setForeground(C_HEADER);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel statLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 12));
        l.setForeground(C_TEXT);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JButton makeBtn(String text,
            java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btn.setBackground(C_BTN_BG);
        btn.setForeground(C_TEXT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_ACCENT2, 1, true),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(C_BTN_HOV);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(C_BTN_BG);
            }
        });
        btn.addActionListener(listener);
        return btn;
    }

    // ----------------------------------------------------------
    // Точка входу
    // ----------------------------------------------------------
    /**
     * Запускає GUI у потоці EDT.
     * @param args аргументи (не використовуються)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new MainGUI().setVisible(true);
        });
    }
}