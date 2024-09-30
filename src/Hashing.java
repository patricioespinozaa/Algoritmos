package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hashing {
    private ArrayList<ArrayList<Pagina>> tabla ;
    private int p; // Número actual de páginas en la tabla
    private int t; // Parámetro t para el tamaño de la tabla
    private int totalIOs; // Contador total de accesos I/O
    private int totalInserciones; // Contador total de inserciones //TODO : Creo que no es necesario este parametro porque la cantidad de insercione la manejamos desde afuera.
    private final double maxCostoPromedio; // Costo promedio máximo permitido
    private int sumaIos; // suma de todas los Ios de todos los elementos insertados.
    private Random random;
    public Hashing(double maxCostoPromedio){
        this.p = 1;
        this.t = 0;
        this.tabla = new ArrayList<>(p); // iniciamos la tabla con con p elementos.
        for (int i = 0; i < p; i++) {
            ArrayList<Pagina> paginas = new ArrayList<>();
            paginas.add(new Pagina());  // Agrega la página principal
            tabla.add(paginas);       // Añade la lista de páginas (inicialmente solo la principal)
        }
        this.totalIOs = 0; // Inicialmente no hay accesos
        this.totalInserciones = 0; // Inicialmente no hay inserciones
        this.maxCostoPromedio = maxCostoPromedio;
        this.random = new Random();
    }
    public void imprimirTabla() {
        for (int i = 0; i < tabla.size(); i++) {
            System.out.print("Página " + i + ": ");
            ArrayList<Pagina> listaPaginas = tabla.get(i);
            for (Pagina pg : listaPaginas) {
                long[] datos = pg.getPagina();
                for (int j = 1; j < datos.length; j++) {  // Mostrar solo los valores insertados (índices desde 1)
                    if (datos[j] != 0) {
                        System.out.print(datos[j] + " ");
                    }
                }
                System.out.print(" | ");  // Separar las páginas de rebalse
            }
            System.out.println();
        }
    }

    public int getPromedio(){
        return sumaIos/totalInserciones;
    }

    public static long hashLong(long input) {
        input = (~input) + (input << 21); // mezcla de bits
        input = input ^ (input >>> 24);
        input = (input + (input << 3)) + (input << 8); // mezcla de bits
        input = input ^ (input >>> 14);
        input = (input + (input << 2)) + (input << 4); // mezcla de bits
        input = input ^ (input >>> 28);
        input = input + (input << 31);
        return input & 0xFFFFFFFFFFFFFFFFL; // asegura que sea positivo (64 bits)
    }
    // Inserta un elemento en la tabla de hash siguiendo las reglas del hashing lineal
    public void insertar(long y) {
        long clave = Math.abs(hashLong(y)); // Calcula el índice de hash
        long k = clave % (long) Math.pow(2, t + 1);
        System.out.printf("el hash es %d\n",k);
        if (k < p) {
            // Inserta en la página k o en una página de rebalse si está llena
            insertarEnPagina((int) k, y);
        } else {
            // Inserta en la página k - 2^t (que aún no ha sido expandida)
            insertarEnPagina((int) (k - (int) Math.pow(2, t)), y);
        }
    }

    // Inserta un elemento en la página correspondiente, creando páginas de rebalse si es necesario
    private void insertarEnPagina(int index, long y) {
        totalIOs = 0;
        if (index < 0 || index >= tabla.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites: " + index);
        }
        ArrayList<Pagina> paginas = tabla.get(index);
        // Registrar acceso I/O al leer la lista de páginas
        totalIOs+=1;
        // Verificar si el elemento ya existe (lectura) REVISAMOS LA ORIGINAL Y LAS DE REBALSE
        for (Pagina pagina : paginas) {
            totalIOs++; // Acceso I/O por revisar cada página //
            if (pagina.contieneElemento(y)) {
                System.out.println("elemento ya esta en la tabla");
                return; // El elemento ya está en la tabla, no se necesita inserción
            }
        }
        Pagina ultimaPagina = paginas.getLast();
        if (!ultimaPagina.estaLlena()) {
            // Si la última página no está llena, inserta el elemento
            ultimaPagina.insertarElemento(y);
            totalIOs+=1; // Acceso I/O por escribir en la página
            System.out.println("elemento insertado");

            sumaIos += totalIOs; // sumamos cuanto nos costo llegar a este punto y se lo añadimos a sumaIos
            System.out.printf("Ios necesarios para insertar el elemento: %d%n", totalIOs);
            totalInserciones+=1;
            if ( maxCostoPromedio<=getPromedio()) {
                System.out.println("se cumple que se supera el costo de insercion");
                expandirPagina();
            }
        } else {
            // Si la última página está llena, crea una nueva página de rebalse
            Pagina nuevaPagina = new Pagina();
            nuevaPagina.insertarElemento(y);
            paginas.add(nuevaPagina);

            totalIOs += 2; // Acceso I/O por crear nueva página y escribir en ella
            sumaIos += totalIOs; // sumamos cuanto nos costo llegar a este punto y se lo añadimos a sumaIos
            System.out.printf("Ios necesarios para insertar el elemento luego de qe creamos una pagnia de rebalse: %d%n", totalIOs);
            totalInserciones+=1;
            if ( maxCostoPromedio<=getPromedio()) {
                System.out.println("se cumple que se supera el costo de insercion");
                expandirPagina();
            }

        }
    }
    private void expandirPagina() {
        System.out.println("expandiendo tabla");
        int indexExpansion = p - (int) Math.pow(2, t);
        ArrayList<Pagina> paginasAExpandir = tabla.get(indexExpansion);

        // Crea una nueva página en la tabla
        ArrayList<Pagina> nuevaListaPaginas = new ArrayList<>();
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
            System.out.println("aumentamos t");
            t++;
        }
    }

    public static void main(String[] args) {
        Hashing tabla1 = new Hashing( 10);
        tabla1.imprimirTabla();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            long elemento = random.nextLong();
            tabla1.insertar(elemento); // Insertar elemento en la tabla
        }
        tabla1.imprimirTabla();

    }
}
