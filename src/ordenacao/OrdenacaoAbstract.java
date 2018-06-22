package ordenacao;

/**
 * @author Vinicius Luis da Silva && Carlos Henrique Ponciano da Silva
 */
public abstract class OrdenacaoAbstract<T extends Comparable<T>> {

    private T[] info;

    public T[] getInfo() {
        return info;
    }

    public void setInfo(T[] info) {
        this.info = info;
    }
    
    public void trimm() {
        int tamanho;
        for (tamanho = 1; tamanho < this.getInfo().length; tamanho++) {
            if(this.getInfo()[tamanho] == null) {
                break;
            }
        }
        T aux[] = this.info.clone();
        this.info = (T[]) new Comparable[tamanho];
        for (int i = 0; i < tamanho; i++) {
            this.info[i] = aux[i];
        }
    }
    
    public void trocar(int a, int b) {
        T aux = this.info[a];
        this.info[a] = this.info[b];
        this.info[b] = aux;
    }
    
    public abstract void ordenar();

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.info.length; i++) {
            str += this.info[i] + " ";
        }
        return str;
    }
    
}
