package src;


import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;

public class Graficar {
    public static void graficarRelacion(List<Double> porcentajesLlenado, List<Integer> costosPromedio) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < porcentajesLlenado.size(); i++) {
            dataset.addValue(costosPromedio.get(i), "Costo Promedio", porcentajesLlenado.get(i));
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Relación entre porcentaje de llenado y costo promedio",
                "Porcentaje de llenado (%)",
                "Costo Promedio (I/Os)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
