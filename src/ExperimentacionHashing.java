package src;
import java.util.Random;

public class ExperimentacionHashing {
    
    public static void main(String[] args) {
        // Definir los tamaños de N en potencias de 2 desde 2^10 hasta 2^24
        int[] tamaniosN = {
            (int) Math.pow(2, 10),
            (int) Math.pow(2, 11),
            (int) Math.pow(2, 12),
            (int) Math.pow(2, 13),
            (int) Math.pow(2, 14),
            (int) Math.pow(2, 15),
            (int) Math.pow(2, 16),
            (int) Math.pow(2, 17),
            (int) Math.pow(2, 18),
            (int) Math.pow(2, 19),
            (int) Math.pow(2, 20),
            (int) Math.pow(2, 21),
            (int) Math.pow(2, 22),
            (int) Math.pow(2, 23),
            (int) Math.pow(2, 24)
        };

        // Valores de c_max para ajustar
        double[] costosMaximos = {2.0, 2.5, 3.0, 3.5, 4.0}; // Ejemplos de costos máximos

        // Realizar experimentos para cada tamaño de N
        for (int n : tamaniosN) {
            System.out.println("Experimento para N = " + n);
            // Realizar experimentos para cada costo máximo
            for (double c_max : costosMaximos) {
                System.out.println("  Ajustando c_max = " + c_max);
                
                // Crear una tabla de hash con el costo máximo c_max
                TablaHash tablaHash = new TablaHash(c_max);
                
                // Generar N números aleatorios de 64 bits y contar accesos I/O
                Random random = new Random();
                for (int i = 0; i < n; i++) {
                    long elemento = random.nextLong();
                    tablaHash.insertar(elemento); // Insertar elemento en la tabla
                }
                
                // Obtener y registrar el costo promedio real de inserción
                double costoPromedioReal = tablaHash.calcularCostoPromedio();
                System.out.println("    Costo promedio real para c_max = " + c_max + ": " + costoPromedioReal);
            }
            System.out.println("-----------------------------");
        }
    }

    public void ejecutarExperimento() {
        // Supongamos que tienes estos arrays llenos con los datos de tus experimentos
        double[] costosControlados = {2.1, 2.4, 2.7}; // Datos reales de tu experimento
        double[] costosReales = {2.0, 2.3, 2.6}; // Datos reales de tu experimento
        int[] c_max = {2, 3, 4}; // Valores de c_max usados en los experimentos
        double[] porcentajesLlenado = {80.0, 85.0, 90.0}; // Porcentaje de llenado de las páginas        

        // Crear instancias de los gráficos
        GraficoCostoPromedio graficoCosto = new GraficoCostoPromedio();
        GraficoPorcentajeLlenado graficoPorcentaje = new GraficoPorcentajeLlenado();

        // Graficar los resultados
        graficoCosto.graficarCostoPromedio(costosControlados, costosReales, c_max);
        graficoPorcentaje.graficarPorcentajeLlenado(porcentajesLlenado, costosReales);
    }
}
