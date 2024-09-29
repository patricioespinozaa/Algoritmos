package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TablaHash {
    private List<List<Pagina>> tabla; // Cada posición en la tabla contiene una lista de páginas
    private int p; // Número actual de páginas en la tabla
    private int t; // Parámetro t para el tamaño de la tabla
    private Random random;
    private long totalIOs; // Contador total de accesos I/O
    private long totalInserciones; // Contador total de inserciones
    private double maxCostoPromedio; // Costo promedio máximo permitido
    private double costoTotal; // Contador de costos totales

    // Constructor: inicializa la tabla con una página y define el costo máximo permitido
    public TablaHash(double maxCostoPromedio) {
        this.p = 1;
        this.t = 0;
        this.tabla = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tabla.add(new ArrayList<Pagina>()); // Inicializa cada lista
        }
        this.random = new Random();
        this.totalIOs = 0; // Inicialmente no hay accesos
        this.totalInserciones = 0; // Inicialmente no hay inserciones
        this.maxCostoPromedio = maxCostoPromedio;
        this.costoTotal = 0;

        // Inicializa la tabla con una página vacía
        List<Pagina> nuevaListaPaginas = new ArrayList<>();
        nuevaListaPaginas.add(new Pagina());
        tabla.add(nuevaListaPaginas);
    }

    // Función de hash que genera un valor aleatorio entre 0 y 2^64 - 1 y aplica mod 2^t + 1
    private long hash(long y) {
        long h = random.nextLong() & 0xFFFFFFFFFFFFFFFFL;  // Genera hash de 64 bits
        return h % (long) Math.pow(2, t + 1);              // Aplica mod 2^t + 1
    }

    // Inserta un elemento en la tabla de hash siguiendo las reglas del hashing lineal
    public void insertar(long y) {
        long k = hash(y); // Calcula el índice de hash
        if (k < p) {
            // Inserta en la página k o en una página de rebalse si está llena
            insertarEnPagina((int) k, y);
        } else {
            // Inserta en la página k - 2^t (que aún no ha sido expandida)
            insertarEnPagina((int) (k - (int) Math.pow(2, t)), y);
        }

        // Incrementar el número total de inserciones
        totalInserciones++;

        // Verifica si se debe realizar una expansión basada en el costo promedio
        if (calcularCostoPromedio() > maxCostoPromedio) {
            expandirPagina();
        }
    }

    // Inserta un elemento en la página correspondiente, creando páginas de rebalse si es necesario
    private void insertarEnPagina(int index, long y) {
        List<Pagina> paginas = tabla.get(index);

        if (index < 0 || index >= tabla.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites: " + index);
        }
        
        // Registrar acceso I/O al leer la lista de páginas
        totalIOs++;
        
        Pagina ultimaPagina = paginas.get(paginas.size() - 1);

        // Verificar si el elemento ya existe (lectura)
        for (Pagina pagina : paginas) {
            totalIOs++; // Acceso I/O por revisar cada página
            if (pagina.contieneElemento(y)) {
                return; // El elemento ya está en la tabla, no se necesita inserción
            }
        }

        if (!ultimaPagina.estaLlena()) {
            // Si la última página no está llena, inserta el elemento
            ultimaPagina.insertarElemento(y);
            totalIOs++; // Acceso I/O por escribir en la página
        } else {
            // Si la última página está llena, crea una nueva página de rebalse
            Pagina nuevaPagina = new Pagina();
            nuevaPagina.insertarElemento(y);
            paginas.add(nuevaPagina);
            totalIOs += 2; // Acceso I/O por crear nueva página y escribir en ella
        }
    }

    // Calcula el costo promedio de accesos I/O por inserción
    public double calcularCostoPromedio() {
        if (totalInserciones == 0) {
            return 0;
        }
        return (double) totalIOs / totalInserciones;
    }

    // Método para obtener el costo promedio real
    public double obtenerCostoPromedioReal() {
        return totalInserciones > 0 ? costoTotal / totalInserciones : 0;
    }

    // Expande la tabla de hash y redistribuye los elementos de la página que se expande
    private void expandirPagina() {
        int indexExpansion = p - (int) Math.pow(2, t);
        List<Pagina> paginasAExpandir = tabla.get(indexExpansion);

        // Crea una nueva página en la tabla
        List<Pagina> nuevaListaPaginas = new ArrayList<>();
        nuevaListaPaginas.add(new Pagina());
        tabla.add(nuevaListaPaginas);
        
        // Redistribuir los elementos de la página que se está expandiendo
        for (Pagina pagina : paginasAExpandir) {
            for (int i = 0; i < pagina.getNumElementos(); i++) {
                long elemento = pagina.obtenerElemento(i);
                insertar(elemento); // Reinsertamos el elemento en la tabla con la nueva función de hash
            }
        }

        // Incrementa p y ajusta t si es necesario
        p++;
        if (p == Math.pow(2, t + 1)) {
            t++;
        }

        totalIOs++; // Expansión implica un acceso I/O
    }

    // Imprime toda la tabla de hash para depuración
    public void imprimirTabla() {
        System.out.println("Tabla de Hash:");
        for (int i = 0; i < tabla.size(); i++) {
            System.out.print("Indice " + i + ": ");
            for (Pagina pagina : tabla.get(i)) {
                pagina.imprimirElementos();
            }
        }
        System.out.println("Total I/Os: " + totalIOs);
        System.out.println("Total inserciones: " + totalInserciones);
        System.out.println("Costo promedio actual: " + calcularCostoPromedio());
    }

    public static void main(String[] args) {
        double maxCostoPromedio = 2.0; // Establecemos un costo promedio máximo permitido
        TablaHash tablaHash = new TablaHash(maxCostoPromedio);

        // Inserción de algunos elementos de ejemplo
        for (int i = 0; i < 10; i++) {
            tablaHash.insertar(i);
        }

        // Imprimir el contenido de la tabla de hash y estadísticas
        tablaHash.imprimirTabla();
    }
}