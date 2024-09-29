package tarea1;

import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;


public class HashingLineal {
    private ArrayList<ArrayList<page>> tabla ; 
    private int contadorOperaciones;
    private int costoPromedio; //hay que setearlo uno mismo
    private int p; // Número de páginas 
    private int t; // Contador t


    public HashingLineal(int p){
        this.p = p;
        this.tabla = new ArrayList<>(p);  // Inicializa la tabla con p listas
        // Inicializar cada página (y sus listas de desbordamiento)
        for (int i = 0; i < p; i++) {
            ArrayList<page> paginas = new ArrayList<>();
            paginas.add(new page());  // Agrega la página principal
            tabla.add(paginas);       // Añade la lista de páginas (inicialmente solo la principal)
        }
        this.contadorOperaciones = 0;
        this.costoPromedio = 0;
        this.t = 0;
    }

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


}
