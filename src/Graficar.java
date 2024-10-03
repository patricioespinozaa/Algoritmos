package src;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JFrame;
import java.util.List;

public class Graficar {

    // Graficar (1): Costo promedio real vs cantidad de datos ingresados (todas las curvas de cmáx en un solo gráfico)
    public static void graficarCostoPromedioVsDatosMultipleCmax(List<List<Integer>> listaCantidadDatos, List<List<Integer>> listaCostosReales, double[] costosMaximos) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Crear una serie de datos para cada cmáx
        for (int j = 0; j < costosMaximos.length; j++) {
            XYSeries serieCostoReal = new XYSeries("Costo Promedio Real (cmáx=" + costosMaximos[j] + ")");
            
            List<Integer> cantidadDatos = listaCantidadDatos.get(j);
            List<Integer> costosReales = listaCostosReales.get(j);
            
            for (int i = 0; i < cantidadDatos.size(); i++) {
                serieCostoReal.add(cantidadDatos.get(i), costosReales.get(i));
            }

            dataset.addSeries(serieCostoReal); // Añadir la serie al dataset
        }

        // Crear el gráfico con todas las series
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Costo Promedio Real vs Cantidad de Datos para distintos cmáx",
                "Cantidad de Datos",
                "Costo Promedio Real (I/Os)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        mostrarGrafico(chart);
    }

    // Graficar (2): Porcentaje de llenado vs costos reales
    public static void graficarLlenadoVsCostos(List<Integer> cantidadDatos, List<Double> porcentajesLlenado, List<Integer> costosReales, double cmáx) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Serie para el porcentaje de llenado
        XYSeries seriePorcentajeLlenado = new XYSeries("Porcentaje de Llenado (cmáx=" + cmáx + ")");
        // Serie para el costo promedio real
        XYSeries serieCostoReal = new XYSeries("Costo Promedio Real (cmáx=" + cmáx + ")");

        for (int i = 0; i < cantidadDatos.size(); i++) {
            seriePorcentajeLlenado.add(cantidadDatos.get(i), porcentajesLlenado.get(i));
            serieCostoReal.add(cantidadDatos.get(i), costosReales.get(i));
        }

        dataset.addSeries(seriePorcentajeLlenado);
        dataset.addSeries(serieCostoReal);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Porcentaje de Llenado y Costo Promedio Real (cmáx=" + cmáx + ")",
                "Cantidad de Datos",
                "Valores",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        mostrarGrafico(chart);
    }

    // Mostrar gráfico en JFrame
    private static void mostrarGrafico(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
