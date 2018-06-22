package ordenacao;

/**
 * @author Vinicius Luis da Silva && Carlos Henrique Ponciano da Silva
 */
public class OrdenacaoBolha<T extends Comparable<T>> extends OrdenacaoAbstract<T>{

    @Override
    public void ordenar() {
        super.trimm();
        
        int i, j;
        int n = super.getInfo().length;
        boolean trocou;
        
        for(i = n - 1; i > 0; i--) {
            trocou = false;
            for (j = 0; j < i; j++) {
                if(super.getInfo()[j].compareTo(super.getInfo()[j + 1]) > 0) {
                    super.trocar(j, j + 1);
                    trocou = true;
                }
            }
            if(!trocou) {
                return;
            }
        }
    }
    
}
