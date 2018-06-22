package lista;

import ordenacao.OrdenacaoAbstract;
import ordenacao.OrdenacaoBolha;
import ordenacao.OrdenacaoMergesort;
import ordenacao.OrdenacaoQuicksort;

/**
 *
 * @author Carlos Henrique Ponciano da Silva && Vinicius Luis da Silva
 */
public class ListaOrdenada<T extends Comparable<T>> extends ListaAbstract<T>{

    /*
        Foram desenvolvidos dois algoritmos de ordenação 
        um usando lista e outro usando os sort 
        no final ficou o que apresentou melhores resultados
    */
    @Override
    public void inserir(T valor) {
        long tempoInicio = System.nanoTime();
        if(super.getInfo().length == super.getTamanho()) {
            super.redimensionar();
        }
        Object[] info = super.getInfo();
        int posicao = this.procurarPosicaoDeInsercao(valor);
        
        if(posicao < super.getTamanho()) {
            this.descolarDados(posicao);
        }
        
        info[posicao] = valor;
        super.setTamanho(super.getTamanho() + 1);
        System.out.println(System.nanoTime() - tempoInicio);
    }
/*
    @Override
    public void inserir(T info) {
        long tempoInicio = System.currentTimeMillis();
        
        OrdenacaoAbstract<T> aux;
        super.inserir(info);
        if(super.getInfo().length < 10) {
            aux = new OrdenacaoBolha<>();
        } else if(super.getInfo().length < 20) {
            aux = new OrdenacaoQuicksort<>();
        } else {
            aux = new OrdenacaoMergesort<>();
        }
        aux.setInfo(super.getInfo());
        aux.ordenar();
        super.setInfo(aux.getInfo());
        
        System.out.println(System.currentTimeMillis()- tempoInicio);
    }
*/
    private int procurarPosicaoDeInsercao(T valor) {
        Object[] info = super.getInfo();
        int i;
        for (i = 0; i < super.getTamanho(); i++) {
            if(((Comparable)info[i]).compareTo(valor) > 0) {
                break;
            }
        }
        return i;
    }

    private void descolarDados(int posicao) {
        Object[] info = super.getInfo();
        for (int i = super.getTamanho() - 1; i >= posicao; i--) {
            info[i + 1] = info[i];
        }
    }
 
    
    
}
