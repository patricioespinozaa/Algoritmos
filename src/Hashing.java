// ======================== ESTRUCTURA DE HASHING ======================== //

package src;

import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;

public class Hashing {
    private ArrayList<ArrayList<Pagina>> tabla ;                        // Tabla de hash con listas de páginas
    private int p;                                                      // Número actual de páginas en la tabla
    private int t;                                                      // Parámetro t para el tamaño de la tabla
    private int totalIOs;                                               // Contador total de accesos I/O
    private int totalInserciones;                                       // Contador total de inserciones 
    private final double maxCostoPromedio;                              // Costo promedio máximo permitido
    private int sumaIos;                                                // suma de todas los Ios de todos los elementos insertados.
    //private Random random;                                              // Generador de números aleatorios

    // Constructor: inicializa la tabla de hash con p páginas y el costo promedio máximo
    public Hashing(double maxCostoPromedio){
        this.p = 1;                                                     // Inicializa con una página
        this.t = 0;                                                     // Inicializa con t = 0
        this.tabla = new ArrayList<>(p);                                // iniciamos la tabla con con p elementos.
        for (int i = 0; i < p; i++) {                                   
            ArrayList<Pagina> paginas = new ArrayList<>();              // Crea una lista de páginas
            paginas.add(new Pagina());                                  // Agrega la página principal
            tabla.add(paginas);                                         // Añade la lista de páginas (inicialmente solo la principal)
        }
        this.totalIOs = 0;                                              // Inicialmente no hay accesos
        this.totalInserciones = 0;                                      // Inicialmente no hay inserciones
        this.maxCostoPromedio = maxCostoPromedio;                       // Costo promedio máximo permitido definido por el usuario
        //this.random = new Random();                                     // Inicializa el generador de números aleatorios
    }

    public void imprimirTabla() {                                       // Imprime la tabla de hash
        for (int i = 0; i < tabla.size(); i++) {                            // Recorre cada página en la tabla
            System.out.println();                                           // Formateo estetico
            System.out.println();
            System.out.print("Página " + i + ": ");                         // Imprime el número de página
            ArrayList<Pagina> listaPaginas = tabla.get(i);                  // Obtiene la lista de páginas    
            for (Pagina pg : listaPaginas) {                                // Recorre cada página en la lista
                long[] datos = pg.getPagina();
                for (int j = 1; j < datos.length; j++) {                    // Mostrar solo los valores insertados (índices desde 1)
                    if (datos[j] != 0) {                                    // * cambiar datos.length por pg.getNumElementos()
                        System.out.print(datos[j] + " ");
                    }
                }
                System.out.print(" | ");                                   // Separar las páginas de rebalse
            }
            System.out.println();
        }
    }

    public int getPromedio(){                                           // Obtiene el promedio de I/Os por inserción (costo promedio)
        return totalInserciones == 0 ? 0 : sumaIos / totalInserciones; 
    }

    public static long hashLong(long input) {                           // Función de hash
        input = (~input) + (input << 21);                                   // Mezcla de bits
        input = input ^ (input >>> 24);
        input = (input + (input << 3)) + (input << 8);                      // Mezcla de bits
        input = input ^ (input >>> 14);
        input = (input + (input << 2)) + (input << 4);                      // Mezcla de bits
        input = input ^ (input >>> 28);
        input = input + (input << 31);
        return input & 0xFFFFFFFFFFFFFFFFL;                                 // -> Asegura que sea positivo (64 bits)
    }

    // Inserta un elemento en la tabla de hash siguiendo las reglas del hashing lineal
    public void insertar(long y) {
        long clave = Math.abs(hashLong(y));                             // Calcula el índice de hash
        long k = clave % (long) Math.pow(2, t + 1);                   // Calcula la posición en la tabla
        //System.out.printf("el hash es %d\n",k);
        if (k < p) {                                                    // Si la posición es menor que p
            insertarEnPagina((int) k, y);                                   // -> Inserta en la página k o en una página de rebalse si está llena
        } else {
            insertarEnPagina((int) (k - (int) Math.pow(2, t)), y);        // Inserta en la página k - 2^t (que aún no ha sido expandida)
        }
    }

    // Inserta un elemento en la página correspondiente, creando páginas de rebalse si es necesario
    // Debe realizar una verificacion de si el elemento existe en la pagina principal y en las de rebalse
    private void insertarEnPagina(int index, long y) {
        totalIOs = 0;                                                       // Inicializa el contador de I/Os para esta inserción
        if (index < 0 || index >= tabla.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites: " + index);
        }
        ArrayList<Pagina> paginas = tabla.get(index);                       // Obtiene la lista de páginas en la posición dada
        totalIOs+=1;                                                        // Registrar acceso I/O al leer la lista de páginas

        // Verificar si el elemento ya existe (lectura) REVISAMOS LA ORIGINAL Y LAS DE REBALSE
        for (Pagina pagina : paginas) {
            totalIOs++;                                                     // Acceso I/O por revisar cada página
            if (pagina.contieneElemento(y)) {
                return;                                                     // -> El elemento ya está en la tabla, no se necesita inserción
            }
        }
        // * Pagina ultimaPagina = paginas.get(paginas.size() - 1);
        Pagina ultimaPagina = paginas.getLast();                            // Obtiene la última página en la lista para insertar el elemento
        if (!ultimaPagina.estaLlena()) {                                    // Si la última página no está llena, inserta el elemento
            ultimaPagina.insertarElemento(y);
            totalIOs++;                                                    // Acceso I/O por escribir en la página
            sumaIos += totalIOs;                                           // Sumamos cuanto nos costo llegar a este punto y se lo añadimos a sumaIos
            totalInserciones++;                                            // Insercion del elemento
            int promedio = getPromedio();                                  // Obtenemos el promedio de I/Os por inserción
            //System.out.printf("Ios necesarios para insertar el elemento: %d%n", promedio);
            // Si el costo promedio definido por el usuario es superado por el promedio actual
            if (maxCostoPromedio<=promedio) {                              
                //System.out.println("se cumple que se supera el costo de insercion");
                expandirPagina();                                              // -> Expandimos la página
            }
        } else {                                                           // Si la última página está llena, crea una nueva página de rebalse
            Pagina nuevaPagina = new Pagina();                                  // Crea una nueva página
            nuevaPagina.insertarElemento(y);                                    // Inserta el elemento en la nueva página   
            paginas.add(nuevaPagina);                                           // Agrega la nueva página a la lista de páginas

            totalIOs += 2;                                                      // Acceso I/O por crear nueva página y escribir en ella
            sumaIos += totalIOs;                                                // Sumamos cuanto nos costo llegar a este punto y se lo añadimos a sumaIos
            totalInserciones+=1;                                                // Insercion del elemento
            int promedio = getPromedio();
            //System.out.printf("Ios necesarios para insertar el elemento luego de qe creamos una pagnia de rebalse: %d%n", promedio);
            // Si el costo promedio definido por el usuario es superado por el promedio actual
            if ( maxCostoPromedio<=promedio) {
                //System.out.println("se cumple que se supera el costo de insercion");
                expandirPagina();                                               // -> Expandimos la página
            }
        }
    }
    
    // Inserta un elemento en la página correspondiente, creando páginas de rebalse si es necesario
    private void insertarRehashing(int index, long y) {         
        if (index < 0 || index >= tabla.size()) {                   // Verifica que el índice esté dentro del rango
            throw new IndexOutOfBoundsException("Índice fuera de los límites: " + index); 
        }

        ArrayList<Pagina> paginas = tabla.get(index);               // Obtiene la lista de páginas en la posición dada
        
        for (Pagina pagina : paginas) {                             // Recorre cada página en la lista
            if (pagina.contieneElemento(y)) {                       // Verifica si el elemento ya está en la página
                return;                                                 // -> El elemento ya está, no se reinserta
            }
        }
    
        Pagina ultimaPagina = paginas.get(paginas.size() - 1);      // Obtiene la última página en la lista
        if (!ultimaPagina.estaLlena()) {                            // Verifica si la página no está llena
            ultimaPagina.insertarElemento(y);                           // -> Inserta el elemento en la página
        } else {                                                    // Si la página está llena
            Pagina nuevaPagina = new Pagina();                          // -> Crea una nueva página
            nuevaPagina.insertarElemento(y);                            // -> Inserta el elemento en la nueva página
            paginas.add(nuevaPagina);                                   // -> Agrega la nueva página a la lista
        }
    }
    
    // Expande la pagina cuando se supera el costo promedio
    private void expandirPagina() {
        //System.out.println("expandiendo tabla");
        int indexExpansion = p - (int) Math.pow(2, t);                          // Obtiene el índice de la página a expandir
        ArrayList<Pagina> paginasAExpandir = tabla.get(indexExpansion);           // Obtiene la lista de páginas a expandir

        // Crea una nueva página en la tabla
        ArrayList<Pagina> nuevaListaPaginas = new ArrayList<>();
        nuevaListaPaginas.add(new Pagina());                                      // Agrega la página principal
        tabla.add(nuevaListaPaginas);                                             // Agrega la lista de páginas a la tabla

        // Copiamos los elementos que hay en la página y en las de rebalse
        ArrayList<Long> elementosAReinsertar = new ArrayList<>();
        for (Pagina pagina : paginasAExpandir) {
            for (int i = 0; i < pagina.getNumElementos(); i++) {
                long elemento = pagina.obtenerElemento(i);
                elementosAReinsertar.add(elemento);
            }
        }

        // Luego podemos borrar lo que hay en la pagina
        paginasAExpandir.clear();
        totalIOs ++;                                                        //  I/O por borrar los elementos de la página (1 o todos los elem)

        paginasAExpandir.add(new Pagina());
        totalIOs++;                                                         //  I/O por agregar nueva página a la lista de páginas
        
        // Reinsertar elementos
        for (long elemento : elementosAReinsertar) {                        
            long clave = Math.abs(hashLong(elemento));                      // Calcula el índice de hash
            long k = clave % (long) Math.pow(2, t + 1);                   // Calcula la posición en la tabla
            insertarRehashing((int)k ,elemento);                            // Hace insercion por rehashing
        }

        // Incrementa p y ajusta t si es necesario
        p++;
        //System.out.println("aumentamos p");
        if (p == Math.pow(2, t + 1)) {
            t++;
        }
    }

    // Obtiene la cantidad total de páginas en la tabla
    public int getCantidadPaginas() {
        int totalPaginas = 0;
        for (ArrayList<Pagina> listaPaginas : tabla) {
            totalPaginas += listaPaginas.size();                            // Contar la página principal y sus páginas de rebalse
        }
        return totalPaginas;
    }

    // Obtiene el porcentaje de llenado promedio en la tabla
    public double porcentajeLlenado() {
        double porcentaje = 0;                                              
        
        // Recorremos todas las listas de páginas en la tabla
        for (ArrayList<Pagina> listaPaginas : tabla) {
            double sumaPorcentajesListaPaginas = 0;                                 // Suma de porcentajes de llenado para las páginas en la lista
            
            // Recorremos cada página en la lista
            for (Pagina p : listaPaginas) {
                int numElementos = p.getNumElementos();                             // Número de elementos en la página
                double porcentajePagina = ((double) numElementos / 128) * 100;      // Convertimos a porcentaje
                sumaPorcentajesListaPaginas += porcentajePagina;                    // Sumamos el porcentaje de llenado de la página
            }

            // Promedio del porcentaje de llenado para las páginas de rebalse (si hay más de una)
            double promedioListaPaginas = sumaPorcentajesListaPaginas / listaPaginas.size();
            porcentaje += promedioListaPaginas;
        }

        // Promedio del porcentaje de llenado en toda la tabla
        return porcentaje / tabla.size();
    }

    // Test pequeño
    /*
    public static void main(String[] args) {
        Hashing tabla1 = new Hashing( 10);                          // Define el costo promedio máximo permitido
        tabla1.imprimirTabla();                                                      // Imprime la tabla inicial
        Random random = new Random();                                                // Inicializa random

        List<Double> porcentajesLlenado = new ArrayList<>();                         // Lista de porcentajes de llenado
        List<Integer> costosPromedio = new ArrayList<>();                            // Lista de costos promedio

        // Rango de elementos a insertar    
        for (int i = 0; i < Math.pow(2,15); i++) {
            long elemento = random.nextLong();
            tabla1.insertar(elemento);                                               // Insertar elemento en la tabla

            // Registrar los valores en intervalos regulares, por ejemplo cada 1000 inserciones
            if (i % 10 == 0) {
                porcentajesLlenado.add(tabla1.porcentajeLlenado());
                costosPromedio.add(tabla1.getPromedio());
            }
        }
        int cantidadPaginas = tabla1.getCantidadPaginas();                           // Obtiene la cantidad de páginas en la tabla
        tabla1.imprimirTabla();                                                      // Imprime la tabla final
        System.out.println("\n Cantidad de paginas :"+ cantidadPaginas+"");          // Imprime la cantidad de páginas
        double porcentaje = tabla1.porcentajeLlenado();
        System.out.println("\n Porcentaje de llenado :"+ porcentaje+"");             // Imprime el porcentaje de llenado
    }
    */
}
