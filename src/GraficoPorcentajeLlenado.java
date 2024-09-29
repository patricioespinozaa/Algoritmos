package src;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;

public class GraficoPorcentajeLlenado {

    public void graficarPorcentajeLlenado(double[] porcentajesLlenado, double[] costosReales) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < porcentajesLlenado.length; i++) {
            dataset.addValue(costosReales[i], "Costo Real", Double.toString(porcentajesLlenado[i]));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Porcentaje de Llenado vs Costo Promedio Real",
                "Porcentaje de Llenado",
                "Costo Promedio",
                dataset
        );

        ChartPanel panel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
