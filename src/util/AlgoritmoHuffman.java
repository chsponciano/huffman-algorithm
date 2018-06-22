package util;

import arvore.ArvoreBinaria;
import arvore.NoArvoreBinaria;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import lista.ListaOrdenada;

/**
 *
 * @author Carlos Henrique Ponciano da Silva && Vinicius Luis da Silva
 */

public abstract class AlgoritmoHuffman {

    private static final int LIMITE_ASCII = 256;
    private static final char CARACTER_NULO = '\0';
    private static int contador;
    private static String[] map;
    private static int tamanhoMap;

    public static boolean compactar(final String origem, final String destino) {
        String palavra = lerConteudo(origem, false);
        if(palavra.isEmpty()){
            return false;
        }
        
        String compactado = getMap(palavra, getTree(getFrequency(palavra)));
        return salvarConteudo(compactado, destino);
    }

    public static boolean descompactar(final String origem, final String destino){
        String conteudo = lerConteudo(origem, true);
        if(conteudo.isEmpty()){
            return false;
        }
        
        String descompactado = getInterpret(conteudo);
        return salvarConteudo(descompactado, destino);
    }
    
    private static boolean salvarConteudo(final String conteudo, final String destino) {
        try {
            if (Files.deleteIfExists(Paths.get(destino))) {
                Files.createFile(Paths.get(destino));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(destino));
            bw.write(conteudo);
            bw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AlgoritmoHuffman.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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

    private static void constructMap(NoArvoreBinaria no, final String bytes) {
        if (no.eFolha()) {
            map[tamanhoMap++] = String.valueOf(no.getInfo());
            map[tamanhoMap++] = bytes;
        } else {
            constructMap(no.getEsquerda(), bytes + "0");
            constructMap(no.getDireita(), bytes + "1");
        }
    }

    private static String getMap(String palavra, ArvoreBinaria arvore) {
        tamanhoMap = 0;
        map = new String[arvore.contarFolhas() * 2];
        constructMap(arvore.getRaiz(), "");

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
            palavraLegenda += Integer.parseInt(map[i++]) + "=" + map[i] + PULA_LINHA;
        }

        return arvore.contarFolhas() + PULA_LINHA + palavraLegenda + palavraByte;
    }

    private static ArvoreBinaria<Character> getTree(int[] frequencia){
        ListaOrdenada<NoArvoreBinaria<Character>> listaNoArvore = new ListaOrdenada<>();
        
        for(int i = 0; i < LIMITE_ASCII; i++){
            if(frequencia[i] != 0){
                listaNoArvore.inserir(new NoArvoreBinaria(i, frequencia[i], null, null));
            }
        }
        
        if(listaNoArvore.getTamanho() == 1){
            listaNoArvore.inserir(new NoArvoreBinaria(CARACTER_NULO, 1, null, null));
        }
        
        while(listaNoArvore.getTamanho() > 1){
            final NoArvoreBinaria esquerda = listaNoArvore.obterElemento(0);
            final NoArvoreBinaria direita = listaNoArvore.obterElemento(1);
            
            listaNoArvore.retirar(esquerda);
            listaNoArvore.retirar(direita);
            
            listaNoArvore.inserir(new NoArvoreBinaria(CARACTER_NULO, (esquerda.getFrequencia() + direita.getFrequencia()),
                                                      esquerda, direita));
        }
        
        NoArvoreBinaria<Character> raiz = listaNoArvore.obterElemento(0);
        listaNoArvore.retirar(raiz);
        ArvoreBinaria<Character> arvore = new ArvoreBinaria<>();
        arvore.setRaiz(raiz);
        
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
            String[] str = arrStr[i].split("=");
            mapBytes[j++] = String.valueOf((char) Integer.parseInt(str[0]));
            mapBytes[j] = str[1];
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
