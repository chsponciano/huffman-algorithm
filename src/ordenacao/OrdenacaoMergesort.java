package ordenacao;

/**
 * @author Vinicius Luis da Silva && Carlos Henrique Ponciano da Silva
 */
public class OrdenacaoMergesort<T extends Comparable<T>> extends OrdenacaoAbstract<T> {

    @Override
    public void ordenar() {
        super.trimm();
        this.mergeSort(0, super.getInfo().length - 1);
    }
    
    public void mergeSort(int inicio, int fim) {
        T[] arr = super.getInfo();
        
        if(arr != null && inicio < fim && inicio >= 0 && fim < arr.length && arr.length != 0) {
            int meio = (inicio + fim) / 2;
            
            this.mergeSort(inicio, meio);
            this.mergeSort(meio + 1, fim);
            
            this.merge(inicio, fim, meio);
            
        }
        
    }
    
    public void merge(int inicio, int fim, int meio) {
        //Cria um array auxiliar que é uma copia do array auxiliar
        T[] aux = (T[]) new Comparable[super.getInfo().length];
        for (int i = inicio; i <= fim; i++) {
            aux[i] = super.getInfo()[i];
        }
        System.out.println("merge");
        //Criar varivaies auxiliares 
        int i = inicio;
        int j = meio + 1;
        int k = inicio;
        
        //percorre o array auxiliar montado, 
        //apontando para dois pontos distintos dele
        while(i <= meio && j <= fim) {
            //compara os valores que estão na posição que indica os dois ponteiro auxliares
            //de acordo com qual é maior coloca no array principal
            if(aux[i].compareTo(aux[j]) < 0) {
                super.getInfo()[k] = aux[i];
                i++;
            } else {
                super.getInfo()[k] = aux[j];
                j++;
            }
            k++;
        }
        
        //coloca os restantes no array auxiliar no final do array principal
        while(i <= meio) {
            super.getInfo()[k] = aux[i];
            i++;
            k++;
        }
        
        while(j <= fim) {
            super.getInfo()[k] = aux[j];
            j++;
            k++;
        }
        
    }
    
}
