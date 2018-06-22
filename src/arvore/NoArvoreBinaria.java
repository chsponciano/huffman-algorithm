package arvore;
/**
 *
 * @author Carlos Henrique Ponciano da Silva && Vinicius Luis da Silva
 */
public class NoArvoreBinaria<T> implements Comparable<NoArvoreBinaria<T>>{
    private T info;
    private int frequencia;
    private NoArvoreBinaria<T> esquerda;
    private NoArvoreBinaria<T> direita;

    public boolean eFolha(){
        return ((esquerda == null) && (direita == null));
    }
    
    public NoArvoreBinaria(T caracter) {
        this.info = caracter;
        this.esquerda = null;
        this.direita = null;
    }

    public NoArvoreBinaria(T info, int frequencia, NoArvoreBinaria<T> esquerda, NoArvoreBinaria<T> direita) {
        this.info = info;
        this.frequencia = frequencia;
        this.esquerda = esquerda;
        this.direita = direita;
    }   

    public T getInfo() {
        return (T) info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
    
    public NoArvoreBinaria<T> getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(NoArvoreBinaria<T> esquerda) {
        this.esquerda = esquerda;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }

    public NoArvoreBinaria<T> getDireita() {
        return direita;
    }

    public void setDireita(NoArvoreBinaria<T> direita) {
        this.direita = direita;
    }    

    @Override
    public String toString() {
        return "NO: "+String.valueOf(info)+" - FREQUENCIA: "+frequencia;
    }
   
    @Override
    public int compareTo(NoArvoreBinaria<T> outro) {            
        return this.frequencia - outro.frequencia;
    }
}
