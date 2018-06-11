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
        String palavra = lerConteudo(origem);
        String compactado = getMap(palavra, getTree(getFrequency(palavra)));
        salvarCompactacao(compactado, destino);
    }

    private static void salvarCompactacao(final String compactado, final String destino) {
        try {
            if (Files.deleteIfExists(Paths.get(destino))) {
                Files.createFile(Paths.get(destino));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(destino));
            bw.write(compactado);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(AlgoritmoHuffman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String lerConteudo(final String local) {        
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

    private static String[] map;
    private static int tamanhoMap;
    private static void getConstructMap(NoArvoreBinaria no, final String bytes) {
        if (no.eFolha()) {
            map[tamanhoMap++] = String.valueOf(no.getInfo());
            map[tamanhoMap++] = bytes;
        } else {
            getConstructMap(no.getEsquerda(), bytes + "0");
            getConstructMap(no.getDireita(), bytes + "1");
        }
    }

    private static String getMap(String palavra, ArvoreBinaria arvore) {
        tamanhoMap = 0;
        map = new String[arvore.contarFolhas() * 2];
        getConstructMap(arvore.getRaiz(), "");

        final String PULA_LINHA = System.getProperty("line.separator");

        String palavraByte = "", palavraLegenda = "";
        for (int i = 0; i < palavra.length(); i++) {
            for (int j = 0; j < map.length; j += 2) {
                char aux = (char) Integer.parseInt(map[j]);
                if (aux == palavra.charAt(i)) {
                    palavraByte += map[j + 1];
                }
            }
        }

        for (int i = 0; i < tamanhoMap; i++) {
            palavraLegenda += (char) (Integer.parseInt(map[i++])) + " - " + map[i] + PULA_LINHA;
        }

        return arvore.contarFolhas() + PULA_LINHA + palavraLegenda + palavraByte;
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
            if(lista[caracter] == 0){ contador++; }
            lista[caracter]++;
        }

        return lista;
    }

//    public static boolean desconpactar(String leitura, String escrita){
//        return true;
//    }
}
