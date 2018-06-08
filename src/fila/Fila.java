package fila;
/**
 *
 * @author Carlos Henrique Ponciano da Silva
 */
public interface Fila<T> {
    public void inserir(T valor);
    public boolean estaVazia();
    public T peek();
    public T retirar();
    public void liberar();
}
