package arvore;

/**
 *
 * @author Carlos Henrique Ponciano da Silva && Vinicius Luis da Silva
 */
public class ArvoreBinaria<T> {

    private NoArvoreBinaria<T> raiz;
    private String[] map;
    private int tamanhoMap = 0;

    public ArvoreBinaria() {
        this.raiz = null;
    }

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

    public void setRaiz(NoArvoreBinaria raiz) {
        this.raiz = raiz;
    }

    public NoArvoreBinaria<T> getRaiz() {
        return raiz;
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
