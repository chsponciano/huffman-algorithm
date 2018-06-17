package lista;

/**
 *
 * @author Carlos Henrique Ponciano da Silva
 */

public abstract class ListaAbstract<T>{
    private T[] info;
    private int tamanho;
    private final byte TAMANHO_PADRAO = 10;

    public T[] getInfo() {
        return info;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public ListaAbstract() {
        this.info = (T[]) new Comparable[TAMANHO_PADRAO];
        this.tamanho = 0;
    }
    
    public void inserir(T info){
        if(this.info.length == this.tamanho){
            redimensionar();
        }
        this.info[this.tamanho] = info;
        this.tamanho++;
    }    
    
    public void exibir(){
        String str = "";
        for (int i = 0; i < this.tamanho; i++) {
            str += this.info[i];
            str += (i < (this.tamanho - 1)) ? " " : "";    
        }
        System.out.println(str);
    }
    
    public int buscar(T info){
        for (int i = 0; i < this.info.length; i++) {
            if(info.equals(this.info[i])){
                return i;
            }
        }
        return -1;
    }
    
    public void retirar(T info){
        int posicaoInicial = this.buscar(info);
        
        if(posicaoInicial < 0){
            throw new IndexOutOfBoundsException("Posição Inválida!");
        }
        
        for (int i = posicaoInicial; i < this.tamanho-1; i++) {
            this.info[i] = this.info[i+1];         
        }
        this.tamanho--;
        this.info[this.tamanho] = null;
    }
    
    public void liberar(){
        this.info = (T[]) new Comparable[TAMANHO_PADRAO];
        this.tamanho = 0;
    }
    
    public T obterElemento(int posicao){
        if(posicao < 0 || posicao > this.tamanho){
            throw new IndexOutOfBoundsException("Posição Inválida!");
        }
        return this.info[posicao];
    }
    
    public boolean estaVazia(){
        return (this.tamanho == 0);
    }
    
    public int getTamanho(){
        return this.tamanho;
    }
     
    @Override
    public String toString(){
        String str = "";
        for (int i = 0; i < this.tamanho; i++) {
            str += this.info[i];
            str += (i < (this.tamanho - 1)) ? "," : "";            
        }
        return str;
    }
    
    private void redimensionar() {
        T[] redimensionado = (T[]) new Comparable[this.info.length + TAMANHO_PADRAO];
        for (int i = 0; i < this.tamanho; i++) {
            redimensionado[i] = this.info[i];
        }
        this.info = redimensionado;
    }
    
    public void inverter(){
        T aux;
        for (int i = this.tamanho-1, j = 0; i > (this.tamanho/2); i--, j++) {
            aux = this.info[i];
            this.info[i] = this.info[j];
            this.info[j] = aux;
        }
    }   
}
