package tarea1;

public class page {
    private long[] pagina;
    private int numElementos; // Lleva el conteo de elementos en la página

    public page() {
        long[] pagina = new long[128];
        this.numElementos = 0;
    }
    
    // Metodo para obtener el arreglo
    public long[] getPagina() {
        return pagina;
    }

    public boolean estaLlena() {
        return numElementos >= pagina.length;
    }

    public void insertar(long y) {
        if (!estaLlena()) {
            pagina[numElementos++] = y;
        }
    }
}
