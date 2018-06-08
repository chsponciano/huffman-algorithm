package fila;
/**
 *
 * @author Carlos Henrique Ponciano da Silva
 * @param <T>
 */
public class FilaVetor<T extends Comparable<T>> implements Fila<T> {
    private T[] info;
    private int limite;
    private int tamanho;
    private int inicio;

    public FilaVetor(int limite) {
        this.limite = limite;
        this.tamanho = 0;
        this.inicio = 0;
        this.info = (T[]) new Comparable[limite];
    }

    public int getTamanho() {
        return tamanho;
    }    
        
    //////////////Questao 1////////////
    public void encolher(){
        T[] aux = (T[]) new Comparable[this.tamanho];
        int tamanhoInicial = this.tamanho;
        
        for (int i = 0; i < tamanhoInicial; i++) {
            aux[i] = this.retirar();
        }
        this.limite  = tamanhoInicial;
        this.tamanho = tamanhoInicial;
        this.inicio  = 0;
        this.info    = aux;
    }
    /////////////////////////////////////
    
    @Override
    public void inserir(T valor) {
        if (this.tamanho == this.limite) {
            throw new FilaCheiaException();
        }
        int top = (this.inicio + this.tamanho) % this.limite;
        this.info[top] = valor;
        this.tamanho++;
        this.ordenar();
    }
    
    private void ordenar(){
        organizar();
        T aux;
        for (int i = 0; i < this.tamanho; i++) {
            for (int j = 0; j < this.tamanho -1 ; j++) {
                if(this.info[j].compareTo(this.info[j+1]) > 0){
                    aux = this.info[j];
                    this.info[j] = this.info[j+1];
                    this.info[j+1] = aux;
                }
            }
        }
    }
    
    private void organizar(){
        T[] aux =(T[]) new Comparable[this.limite];
        
        for (int i = 0, j = 0; i < aux.length; i++) {
            if(this.info[i] != null){
               aux[j] = this.info[i];
               j++;
            }
        }
        
        this.inicio = 0;
        this.info = aux;
    }
    
    @Override
    public boolean estaVazia() {
        return (this.tamanho == 0);
    }

    @Override
    public T peek() {
        if (this.estaVazia()) {
            throw new FilaVaziaException();
        }

        return this.info[this.inicio];
    }

    @Override
    public T retirar() {
        T valor = this.peek();
        this.info[this.inicio] = null;
        this.inicio = (this.inicio + 1) % this.limite;
        this.tamanho--;

        return valor;
    }

    @Override
    public void liberar() {
        while (!this.estaVazia()) {
            this.retirar();
        }
    }

    @Override
    public String toString() {
        String str = "";
        int indice = this.inicio;
        for (int i = 0; i < this.tamanho; i++) {
            str += this.info[indice];
            str += ((i + 1) != this.tamanho) ? "," : "";
            indice = (indice + 1) % this.limite;
        }
        return str;
    }

    public FilaVetor<T> concatenar(FilaVetor<T> f2) {
        FilaVetor<T> f3 = new FilaVetor<>(this.tamanho + f2.tamanho);

        copiarDadosFila(f3, this);
        copiarDadosFila(f3, f2);

        return f3;
    }

    private void copiarDadosFila(FilaVetor<T> f3, FilaVetor<T> f2) {
        int indiceCopiar;
        indiceCopiar = f2.inicio;
        for (int i = 0; i < f2.tamanho; i++) {
            f3.inserir(f2.info[indiceCopiar]);
            indiceCopiar = (indiceCopiar + 1) % f2.limite;
        }
    }
}
