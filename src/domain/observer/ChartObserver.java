package src.domain.observer;

import src.domain.observer.ResistanceObserver;
import src.domain.observer.AppAnnotations;
import src.domain.core.ResistanceData;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// ============================================================
// Спостерігач 2 - перемальовує графіки при зміні колекції
// ============================================================

/**
 * <h1>ChartObserver</h1>
 *
 * Другий спостерігач у шаблоні Observer.
 * При отриманні сповіщення перемальовує bar chart і line chart.
 */
@AppAnnotations.Author(name = "Rizhkevych Viktoriia", date = "2026")
public class ChartObserver implements ResistanceObserver {

    /** Панель bar chart */
    private JPanel barPanel;

    /** Панель line chart */
    private JPanel linePanel;

    /** Поточні дані */
    private ArrayList<ResistanceData> currentItems = new ArrayList<>();

    /** Кольори фіолетової палітри */
    private static final Color COL_BG      = new Color(250, 245, 255);
    private static final Color COL_BAR     = new Color(147, 112, 219);
    private static final Color COL_BAR2    = new Color(186, 156, 237);
    private static final Color COL_LINE    = new Color(138, 43, 226);
    private static final Color COL_POINT   = new Color(75, 0, 130);
    private static final Color COL_GRID    = new Color(220, 210, 235);
    private static final Color COL_TEXT    = new Color(80, 60, 100);
    private static final Color COL_AXIS    = new Color(150, 130, 170);

    /**
     * Конструктор - створює дві панелі графіків.
     */
    public ChartObserver() {
        barPanel  = createBarPanel();
        linePanel = createLinePanel();
    }

    /**
     * Повертає панель стовпчастої діаграми.
     * @return JPanel з bar chart
     */
    public JPanel getBarPanel()  { return barPanel; }

    /**
     * Повертає панель лінійного графіка.
     * @return JPanel з line chart
     */
    public JPanel getLinePanel() { return linePanel; }

    /**
     * Оновлює обидва графіки при зміні колекції.
     * @param items оновлена колекція
     */
    @Override
    public void update(ArrayList<ResistanceData> items) {
        currentItems = new ArrayList<>(items);
        barPanel.repaint();
        linePanel.repaint();
    }

    // ----------------------------------------------------------
    // Створення панелі Bar Chart
    // ----------------------------------------------------------
    private JPanel createBarPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBar((Graphics2D) g, getWidth(), getHeight());
            }
        };
    }

    // ----------------------------------------------------------
    // Створення панелі Line Chart
    // ----------------------------------------------------------
    private JPanel createLinePanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLine((Graphics2D) g, getWidth(), getHeight());
            }
        };
    }

    // ----------------------------------------------------------
    // Малювання Bar Chart
    // ----------------------------------------------------------
    private void drawBar(Graphics2D g, int w, int h) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(COL_BG);
        g.fillRect(0, 0, w, h);

        if (currentItems.isEmpty()) {
            drawEmpty(g, w, h, "Натисніть «Генерувати»");
            return;
        }

        int pad   = 50;
        int chartW = w - pad * 2;
        int chartH = h - pad * 2;

        double maxR = currentItems.stream()
            .mapToDouble(ResistanceData::getTotalResistance).max().orElse(1);

        // Сітка
        g.setColor(COL_GRID);
        g.setStroke(new BasicStroke(1f));
        for (int i = 1; i <= 5; i++) {
            int y = pad + chartH - (int)(chartH * i / 5.0);
            g.drawLine(pad, y, w - pad, y);
            g.setColor(COL_TEXT);
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.drawString(String.format("%.1f", maxR * i / 5), 2, y + 4);
            g.setColor(COL_GRID);
        }

        // Осі
        g.setColor(COL_AXIS);
        g.setStroke(new BasicStroke(1.5f));
        g.drawLine(pad, pad, pad, pad + chartH);
        g.drawLine(pad, pad + chartH, w - pad, pad + chartH);

        // Стовпці
        int n    = currentItems.size();
        int barW = Math.max(10, chartW / n - 8);

        for (int i = 0; i < n; i++) {
            double r    = currentItems.get(i).getTotalResistance();
            int barH    = (int)(chartH * r / maxR);
            int x       = pad + i * (chartW / n) + (chartW / n - barW) / 2;
            int y       = pad + chartH - barH;

            // Градієнт стовпця
            GradientPaint gp = new GradientPaint(
                x, y, COL_BAR, x + barW, y + barH, COL_BAR2);
            g.setPaint(gp);
            g.fillRoundRect(x, y, barW, barH, 6, 6);

            g.setColor(COL_LINE);
            g.setStroke(new BasicStroke(1f));
            g.drawRoundRect(x, y, barW, barH, 6, 6);

            // Підпис
            g.setColor(COL_TEXT);
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.drawString(String.format("%.1f", r),
                x + barW / 2 - 12, y - 3);
            g.drawString("#" + (i + 1),
                x + barW / 2 - 7, pad + chartH + 14);
        }

        // Заголовок
        g.setColor(COL_POINT);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString("R_total - стовпчаста діаграма (Ohm)", pad, 20);
    }

    // ----------------------------------------------------------
    // Малювання Line Chart
    // ----------------------------------------------------------
    private void drawLine(Graphics2D g, int w, int h) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(COL_BG);
        g.fillRect(0, 0, w, h);

        if (currentItems.isEmpty()) {
            drawEmpty(g, w, h, "Натисніть «Генерувати»");
            return;
        }

        int pad    = 50;
        int chartW = w - pad * 2;
        int chartH = h - pad * 2;

        double maxR = currentItems.stream()
            .mapToDouble(ResistanceData::getTotalResistance).max().orElse(1);

        // Сітка
        g.setColor(COL_GRID);
        g.setStroke(new BasicStroke(1f));
        for (int i = 1; i <= 5; i++) {
            int y = pad + chartH - (int)(chartH * i / 5.0);
            g.drawLine(pad, y, w - pad, y);
            g.setColor(COL_TEXT);
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.drawString(String.format("%.1f", maxR * i / 5), 2, y + 4);
            g.setColor(COL_GRID);
        }

        // Осі
        g.setColor(COL_AXIS);
        g.setStroke(new BasicStroke(1.5f));
        g.drawLine(pad, pad, pad, pad + chartH);
        g.drawLine(pad, pad + chartH, w - pad, pad + chartH);

        // Лінія
        int n = currentItems.size();
        int[] px = new int[n];
        int[] py = new int[n];

        for (int i = 0; i < n; i++) {
            px[i] = pad + i * chartW / (n - 1 == 0 ? 1 : n - 1);
            py[i] = pad + chartH - (int)(chartH *
                    currentItems.get(i).getTotalResistance() / maxR);
        }

        // Заливка під лінією
        int[] polyX = new int[n + 2];
        int[] polyY = new int[n + 2];
        for (int i = 0; i < n; i++) {
            polyX[i] = px[i];
            polyY[i] = py[i];
        }
        polyX[n]   = px[n - 1];
        polyY[n]   = pad + chartH;
        polyX[n+1] = px[0];
        polyY[n+1] = pad + chartH;

        g.setColor(new Color(186, 156, 237, 60));
        g.fillPolygon(polyX, polyY, n + 2);

        // Лінія графіка
        g.setColor(COL_LINE);
        g.setStroke(new BasicStroke(2.5f,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i < n - 1; i++) {
            g.drawLine(px[i], py[i], px[i+1], py[i+1]);
        }

        // Точки
        for (int i = 0; i < n; i++) {
            g.setColor(Color.WHITE);
            g.fillOval(px[i] - 5, py[i] - 5, 10, 10);
            g.setColor(COL_POINT);
            g.setStroke(new BasicStroke(2f));
            g.drawOval(px[i] - 5, py[i] - 5, 10, 10);

            g.setColor(COL_TEXT);
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.drawString(String.format("%.1f",
                currentItems.get(i).getTotalResistance()),
                px[i] - 12, py[i] - 8);
            g.drawString("#" + (i + 1),
                px[i] - 7, pad + chartH + 14);
        }

        // Заголовок
        g.setColor(COL_POINT);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString("R_total — лінійний графік (Ohm)", pad, 20);
    }

    // ----------------------------------------------------------
    // Порожній стан
    // ----------------------------------------------------------
    private void drawEmpty(Graphics2D g, int w, int h, String msg) {
        g.setColor(COL_AXIS);
        g.setFont(new Font("SansSerif", Font.ITALIC, 14));
        FontMetrics fm = g.getFontMetrics();
        g.drawString(msg,
            (w - fm.stringWidth(msg)) / 2,
            h / 2);
    }
}