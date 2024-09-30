package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hashing {
    private ArrayList<ArrayList<Pagina>> tabla ;
    private int p; // Número actual de páginas en la tabla
    private int t; // Parámetro t para el tamaño de la tabla
    private int totalIOs; // Contador total de accesos I/O
    private long totalInserciones; // Contador total de inserciones //TODO : Creo que no es necesario este parametro porque la cantidad de insercione la manejamos desde afuera.
    private double maxCostoPromedio; // Costo promedio máximo permitido
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
    // Función de hash que genera un valor aleatorio entre 0 y 2^64 - 1 y aplica mod 2^t + 1
    private long hash(long y) {
        long h = random.nextLong() & 0xFFFFFFFFFFFFFFFFL;  // Genera hash de 64 bits
        return h % (long) Math.pow(2, t + 1);              // Aplica mod 2^t + 1
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
        System.out.printf("el hash es %d",k);
        if (k < p) {
            // Inserta en la página k o en una página de rebalse si está llena
            insertarEnPagina((int) k, y);
        } else {
            // Inserta en la página k - 2^t (que aún no ha sido expandida)
            insertarEnPagina((int) (k - (int) Math.pow(2, t)), y);
        }

        // Incrementar el número total de inserciones
        totalIOs++; //TODO CREO QUE ACA DEBERIA CONTARSE UNA IO ??

        // Verifica si se debe realizar una expansión basada en el costo promedio maximo
        // verificamos si nos pasamos cada vex que aumenta la cantidad total de inserciones.
        if ( maxCostoPromedio<=totalIOs) {
            expandirPagina();
        }
    }

    // Inserta un elemento en la página correspondiente, creando páginas de rebalse si es necesario
    private void insertarEnPagina(int index, long y) {

        if (index < 0 || index >= tabla.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites: " + index);
        }

        ArrayList<Pagina> paginas = tabla.get(index);
        // Registrar acceso I/O al leer la lista de páginas
        totalIOs++;

        if ( maxCostoPromedio<=totalIOs) {
            expandirPagina();
        }
        // Verificar si el elemento ya existe (lectura) REVISAMOS LA ORIGINAL Y LAS DE REBALSE
        for (Pagina pagina : paginas) {
            totalIOs++; // Acceso I/O por revisar cada página //

            if ( maxCostoPromedio<=totalIOs) {
                expandirPagina();
            }
            if (pagina.contieneElemento(y)) {
                System.out.println("elemento ya esta en la tabla");
                return; // El elemento ya está en la tabla, no se necesita inserción
            }
        }
        Pagina ultimaPagina = paginas.getLast();

        if (!ultimaPagina.estaLlena()) {
            // Si la última página no está llena, inserta el elemento
            ultimaPagina.insertarElemento(y);
            totalIOs++; // Acceso I/O por escribir en la página
            if ( maxCostoPromedio<=totalIOs) {
                expandirPagina();
            }
            //TODO coloque esto aca porque siginifica que insertamos el elemnto y que por lo tanto debemos resetar nuestro contador.
            sumaIos += totalIOs; // sumamos cuanto nos costo llegar a este punto y se lo añadimos a sumaIos
            System.out.printf("sumaios %d%n", sumaIos);
            totalIOs =0; //como ya al paso final lo que hacemos es resetear este parametro

        } else {
            // Si la última página está llena, crea una nueva página de rebalse
            Pagina nuevaPagina = new Pagina();
            nuevaPagina.insertarElemento(y);
            paginas.add(nuevaPagina);
            totalIOs += 2; // Acceso I/O por crear nueva página y escribir en ella
            if ( maxCostoPromedio<=totalIOs) { //TODO : no se si aca deberiamos verificar por cada suma o si esta bien que sumemos los 2 altiro y dps verifiquemos.
                expandirPagina();
            }
            //TODO coloque esto aca porque siginifica que insertamos el elemnto y que por lo tanto debemos resetar nuestro contador.
            sumaIos += totalIOs; // sumamos cuanto nos costo llegar a este punto y se lo añadimos a sumaIos
            System.out.printf("sumaios %d%n", sumaIos);
            totalIOs =0; //como ya al paso final lo que hacemos es resetear este parametro

        }
    }
    private void expandirPagina() {
        int indexExpansion = p - (int) Math.pow(2, t);
        ArrayList<Pagina> paginasAExpandir = tabla.get(indexExpansion);

        // Crea una nueva página en la tabla
        ArrayList<Pagina> nuevaListaPaginas = new ArrayList<>();
        nuevaListaPaginas.add(new Pagina());
        tabla.add(nuevaListaPaginas);

        sumaIos += totalIOs; // sumamos cuanto nos costo llegar a este punto y se lo añadimos a sumaIos
        System.out.printf("sumaios %d%n", sumaIos);
        totalIOs =0; //como ya al paso final lo que hacemos es resetear este parametro

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
    }


    public static void main(String[] args) {
        Hashing tabla1 = new Hashing( 100);

        tabla1.imprimirTabla();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            long elemento = random.nextLong();
            tabla1.insertar(elemento); // Insertar elemento en la tabla
        }
    }
}
/*
    // Metodo para imprimir el estado de la tabla de hash
    public void imprimirTabla() {
        for (int i = 0; i < tabla.size(); i++) {
            System.out.print("Página " + i + ": ");
            ArrayList<page> listaPaginas = tabla.get(i);
            for (page pg : listaPaginas) {
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
    // Método para insertar un elemento en la tabla de hash
    public void insertar(long y) {
        // Calcular k ← h(y) mod 2^t+1
        long mod = (long) Math.pow(2, t + 1);
        long resultado = HashingFunction.hashLong(y);
        long  k = resultado % mod ;

        // Determinar la página de inserción
        if (k < p) {
            // Insertar en la página k
            insertarEnPagina(k, y);
        } else {
            // Insertar en la página k − 2^t
            insertarEnPagina(k - (mod / 2), y);
        }
        //TODO : COSTO PROMEDIO.
        // Verificar si es necesario expandir
        if (contadorOperaciones > costoPromedio) {
            expandir();
        }
    }
    private int calcularAccesosPromedio() {
        // Aquí iría el cálculo de los accesos promedio
        // Ejemplo simple: retornar un valor fijo por ahora
        return costoPromedio;
    }

    // Metodo para expandir la tabla cuando se supera el umbral
    private void expandir() {
        int paginaExpandir = p - (int) Math.pow(2, t);

        // Reubicar los elementos en las páginas correspondientes
        ArrayList<page> paginasExpandir = tabla.get(paginaExpandir); //todo puede contener paginas de rebalse!!

        //todo añadir una nueva pagina
        // Añadir una nueva página al final de la tabla
        ArrayList<page> nuevaPagina = new ArrayList<>();
        nuevaPagina.add(new page());
        tabla.add(nuevaPagina);
        // Recolectar los elementos de la página expandir y sus páginas de desbordamiento
        for (page pg : paginasExpandir) {
            long[] datos = pg.getPagina();
            for (long elemento : datos) {
                long mod = (long) Math.pow(2, t + 1);
                long resultado = HashingFunction.hashLong(elemento);
                long  k = resultado % mod ;
                insertar(elemento);
               //todo llamar a la funcion de hashing modulo 2 t+1


            }
        }
        // Limpiar la página expandida
        paginasExpandir.clear();
        paginasExpandir.add(new page());

        // Actualizar p
        p++;
        // Verificar si es necesario incrementar t
        if (p == Math.pow(2, t + 1)) {
            t++;
        }
    }
    // Inserta un elemento en la página correspondiente
    private void insertarEnPagina(long k, long y) {
        ArrayList<page> paginas = tabla.get((int) k);
        page paginaPrincipal = paginas.getFirst();

        // Intentar insertar en la página principal
        if (!paginaPrincipal.estaLlena()) {
            paginaPrincipal.insertar(y);
        } else {
            // Si la página principal está llena, insertar en una página de desbordamiento
            page ultimaPagina = paginas.get(paginas.size() - 1);
            if (!ultimaPagina.estaLlena()) {
                ultimaPagina.insertar(y);
            } else {
                // Si la última página de desbordamiento también está llena, crear una nueva página de desbordamiento
                page nuevaPagina = new page();
                nuevaPagina.insertar(y);
                paginas.add(nuevaPagina);
                p++; //actualizamos p porque creamos una nueva página
            }
        }
        //TODO : acá hay que calcular nuevamente el costo promedio de busqueda .
    }
 */
