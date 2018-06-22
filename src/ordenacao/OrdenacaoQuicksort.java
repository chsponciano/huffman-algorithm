package ordenacao;

/**
 * @author Vinicius Luis da Silva && Carlos Henrique Ponciano da Silva
 */
public class OrdenacaoQuicksort<T extends Comparable<T>> extends OrdenacaoAbstract<T>{

    @Override
    public void ordenar() {
        super.trimm();
        this.quickSort(0, super.getInfo().length - 1);
    }
    
    public void quickSort(int inicio, int fim) {
        int idxPivo;
        if(inicio < fim) {
            idxPivo = particionar(inicio, fim);
            quickSort(inicio, idxPivo - 1);
            quickSort(idxPivo + 1, fim);
        }
    }
    
    public int particionar(int inicio, int fim) {
        int a = inicio;
        int b = fim + 1;
        T pivo = super.getInfo()[inicio];
        
        while(true) {
            do {
                a++;
            } while(a <= fim && (super.getInfo()[a].compareTo(pivo) < 0));
            
            do {
                b--;
            } while(b >= inicio && (super.getInfo()[b].compareTo(pivo) > 0));
            
            if(a >= b) {
                break;
            }
            
            trocar(a, b);
            
        }
        
        trocar(b, inicio);
        return b;
        
    }
    
}
