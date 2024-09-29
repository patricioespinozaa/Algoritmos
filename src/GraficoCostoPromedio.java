package src;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class GraficoCostoPromedio {

    public void graficarCostoPromedio(double[] costosControlados, double[] costosReales, int[] c_max) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < costosControlados.length; i++) {
            dataset.addValue(costosControlados[i], "Costo Controlado", Integer.toString(c_max[i]));
            dataset.addValue(costosReales[i], "Costo Real", Integer.toString(c_max[i]));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Costo Promedio Controlado vs Real",
                "c_max",
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
