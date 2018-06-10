package arvore;

import java.util.regex.Pattern;

/**
 *
 * @author Carlos Henrique Ponciano da Silva
 */
public class ArvoreBinaria<T> {

    private NoArvoreBinaria<T> raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    //////////////Questao 2////////////
    public int contarFolhas() {
        return contarFolhas(this.raiz);
    }

    private int contarFolhas(NoArvoreBinaria<T> no) {
        if (no == null) {
            return 0;
        }
        if ((no.getDireita() == null) && (no.getEsquerda() == null)) {
            return 1;
        }
        return contarFolhas(no.getDireita()) + contarFolhas(no.getEsquerda());
    }
    /////////////////////////////////////

    public void setRaiz(NoArvoreBinaria raiz) {
        this.raiz = raiz;
    }

    public boolean estaVazia() {
        return (this.raiz == null);
    }

    public boolean pertence(T info) {
        return this.pertence(this.raiz, info);
    }

    private boolean pertence(NoArvoreBinaria no, T info) {
        if (this.raiz == null) {
            return false;
        } else {
            return (no.getInfo().equals(info)
                    || no.getEsquerda().getInfo().equals(info)
                    || no.getDireita().getInfo().equals(info));
        }
    }

    @Override
    public String toString() {
        return arvorePre(raiz);
    }

    public String arvorePre(NoArvoreBinaria<T> no) {
        if (no == null) {
            return "<>";
        } else {
            return "<" + no.getInfo() + " - " + no.getFrequencia()
                    + arvorePre(no.getEsquerda())
                    + arvorePre(no.getDireita())
                    + ">";
        }
    }

    public String[] navegacaoPre() {
        return navegacaoPre(navegacaoPre(raiz));
    }

    private String navegacaoPre(NoArvoreBinaria<T> no) {
        String str;
        if (no == null) {
            return "";
        } else {
            str  = (no.getInfo().equals('\0')) ? "" : " - " + (T) no.getInfo() + ";";
            str += "0" + navegacaoPre(no.getEsquerda());
            str += "1" + navegacaoPre(no.getDireita());
            return str;
        }
    }
    
    private String[] navegacaoPre(String aux){
        String[] auxArr = aux.substring(0, aux.length()-2).split(Pattern.quote(";"));
        String[] digArr = new String[auxArr.length*2];
        
        for (int i = 0, j = 0; i < auxArr.length; i++) {
            String[] sepDig = auxArr[i].split(" - ");
            digArr[j++] = sepDig[1];
            digArr[j++] = sepDig[0];

        }
        
        return digArr;
    }

    public int contarNos() {
        return contarNos(raiz);
    }

    private int contarNos(NoArvoreBinaria<T> no) {
        if (no == null) {
            return 0;
        } else {
            return 1 + contarNos(no.getDireita())
                    + contarNos(no.getEsquerda());
        }
    }
}
