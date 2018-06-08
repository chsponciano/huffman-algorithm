package util;

import arvore.ArvoreBinaria;
import arvore.NoArvoreBinaria;
import fila.FilaVetor;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AlgoritmoHuffman {

    private static final int LIMITE_ASCII = 256;
    private static int contador;

    public static void compactar(String palavara) {
        int[] frequencia = getFrequency(palavara);
        ArvoreBinaria<Character> arvore = getTree(frequencia);

        FileWriter fw;
        try {
            fw = new FileWriter("teste");
            fw.write(arvore.toString());
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(AlgoritmoHuffman.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static ArvoreBinaria<Character> getTree(int[] frequencia) {
        FilaVetor<NoArvoreBinaria<Character>> filaNoArvore = new FilaVetor<>(contador);
        for (int i = 0; i < LIMITE_ASCII; i++) {
            if (frequencia[i] != 0) {
                filaNoArvore.inserir(new NoArvoreBinaria(i, frequencia[i], null, null));
            }
        }

        if (filaNoArvore.getTamanho() == 1) {
            filaNoArvore.inserir(new NoArvoreBinaria('\0', 1, null, null));
        }

        while (filaNoArvore.getTamanho() > 1) {
            final NoArvoreBinaria esquerda = filaNoArvore.retirar();
            final NoArvoreBinaria direita = filaNoArvore.retirar();
            filaNoArvore.inserir(new NoArvoreBinaria(
                    '\0',
                    (esquerda.getFrequencia() + direita.getFrequencia()),
                    esquerda,
                    direita));
        }

        ArvoreBinaria<Character> arvore = new ArvoreBinaria<>();
        arvore.setRaiz(filaNoArvore.retirar());

        return arvore;
    }

    private static int[] getFrequency(String palavra) {
        int[] lista = new int[LIMITE_ASCII];

        for (final char caracter : palavra.toCharArray()) {
            contador += (lista[caracter] == 0) ? 1 : 0;
            lista[caracter]++;
        }

        return lista;
    }

//    public static boolean compactar(String leitura, String escrita){       
//        return true;
//    }
//    public static boolean desconpactar(String leitura, String escrita){
//        return true;
//    }
    public static void main(String[] args) {
        AlgoritmoHuffman.compactar("Banana é ruim");
    }

}
