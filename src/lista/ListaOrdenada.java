package lista;

/**
 *
 * @author Carlos Henrique Ponciano da Silva
 */
public class ListaOrdenada<T extends Comparable<T>> extends ListaAbstract<T>{
   
    @Override
    public void inserir(T info) {
        super.inserir(info);
        T aux;
        int tamanho = getTamanho();
        T[] arrDados = (T[])(Object[])getInfo();

        //Ordenação em bolha
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho-1 ; j++) {
                if(arrDados[j].compareTo(arrDados[j+1]) > 0){
                    aux = arrDados[j];
                    arrDados[j] = arrDados[j+1];
                    arrDados[j+1] = aux;
                }
            }
        }
        
        liberar();
        
        for (int i = 0; i < tamanho; i++) {
            super.inserir(arrDados[i]);
        }
    }
}
