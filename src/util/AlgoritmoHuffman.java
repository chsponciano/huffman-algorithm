package util;

import arvore.ArvoreBinaria;
import arvore.NoArvoreBinaria;
import fila.FilaVetor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AlgoritmoHuffman {

    private static final int LIMITE_ASCII = 256;
    private static final char CARACTER_NULO = '\0';
    private static int contador;

    public static void compactar(final String origem, final String destino) {
        String palavra = getText(origem);
        String compactado = getMap(palavra, getTree(getFrequency(palavra)).navegacaoPre());
        
        try {
            if(Files.deleteIfExists(Paths.get(destino))){
                Files.createFile(Paths.get(destino));
            }
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(destino));
            bw.write(compactado.toString());
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(AlgoritmoHuffman.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static String getText(String local) {
        String str = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(local));
            while (br.ready()) {
                str += br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(AlgoritmoHuffman.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return str;
    }

    private static String getMap(String palavra, String[] bytes) {
        final String pula_linha = System.getProperty("line.separator");
        char[] letras = palavra.toCharArray();
        String palavraByte = "", palavraLegenda = "";
        for (int i = 0; i < letras.length; i++) {
            for (int j = 0; j < bytes.length;) {
                char aux = (char) (Integer.parseInt(bytes[j]));
                if (aux == letras[i]) {
                    palavraByte += bytes[j + 1];
                }
                j += 2;
            }
        }

        for (int i = 0; i < bytes.length; i++) {
            palavraLegenda += (char) (Integer.parseInt(bytes[i++])) + " - " + bytes[i] + pula_linha;
        }

        return palavraLegenda + pula_linha + palavraByte;
    }

    private static ArvoreBinaria<Character> getTree(int[] frequencia) {
        FilaVetor<NoArvoreBinaria<Character>> filaNoArvore = new FilaVetor<>(contador);
        for (int i = 0; i < LIMITE_ASCII; i++) {
            if (frequencia[i] != 0) {
                filaNoArvore.inserir(new NoArvoreBinaria(i, frequencia[i], null, null));
            }
        }

        if (filaNoArvore.getTamanho() == 1) {
            filaNoArvore.inserir(new NoArvoreBinaria(CARACTER_NULO, 1, null, null));
        }

        while (filaNoArvore.getTamanho() > 1) {
            final NoArvoreBinaria esquerda = filaNoArvore.retirar();
            final NoArvoreBinaria direita = filaNoArvore.retirar();
            filaNoArvore.inserir(new NoArvoreBinaria(
                    CARACTER_NULO,
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

//    public static boolean desconpactar(String leitura, String escrita){
//        return true;
//    }
}
