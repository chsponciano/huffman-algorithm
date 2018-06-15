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
import java.util.regex.Pattern;

public abstract class AlgoritmoHuffman {

    private static final int LIMITE_ASCII = 256;
    private static final char CARACTER_NULO = '\0';
    private static int contador;

    public static void compactar(final String origem, final String destino) {
        String palavra = lerConteudo(origem, false);
        String compactado = getMap(palavra, getTree(getFrequency(palavra)));
        salvarConteudo(compactado, destino);
    }

    public static void descompactar(final String origem, final String destino){
        String conteudo = lerConteudo(origem, true);
        String descompactado = getInterpret(conteudo);
        salvarConteudo(descompactado, destino);
    }
    
    private static void salvarConteudo(final String conteudo, final String destino) {
        try {
            if (Files.deleteIfExists(Paths.get(destino))) {
                Files.createFile(Paths.get(destino));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(destino));
            bw.write(conteudo);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(AlgoritmoHuffman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String lerConteudo(final String local, final boolean pular) {        
        String str = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(local));
            while (br.ready()) {
                str += br.readLine();
                if(pular){
                    str += "\n";
                }
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

    private static String getInterpret(String conteudo) {
        String[] arrStr = conteudo.split("\n");
        String[] mapBytes = new String[Integer.parseInt(arrStr[0])*2];
        String segBytes = arrStr[arrStr.length-1];
        String procuraByte = "";
        String palavra = "";

        for (int i = 1, j = 0; i < arrStr.length-1; i++, j++) {
            String[] str = arrStr[i].split("-");
            mapBytes[j++] = String.valueOf(str[0].charAt(0));
            mapBytes[j] = str[1].substring(1, str[1].length());
        }

        
        for (int i = 0; i < segBytes.length(); i++) {
            procuraByte += String.valueOf(segBytes.charAt(i));
            for (int j = 0; j < mapBytes.length; j += 2) {
                if(mapBytes[j+1].equals(procuraByte)){
                    procuraByte = "";
                    palavra += mapBytes[j];
                    break;
                }
            }
        }
        
        return palavra;
    }
}
