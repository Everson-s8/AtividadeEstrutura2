package Arvore;

import Interfaces.INo;
public class No<T extends Comparable<T>> implements INo<T> {
    private T dado;
    private No<T> pai;
    private No<T> esquerda;
    private No<T> direita;

    public No(T elemento){
        dado = elemento;
        pai = esquerda = direita = null;
    }

    @Override
    public void setValor(T valor) {
        this.dado = valor;
    }

    @Override
    public void setPai(No no) {
        this.pai = no;
    }

    @Override
    public void setFilhoEsq(No no) {
        this.esquerda = no;
    }

    @Override
    public void setFilhoDir(No no) {
        this.direita = no;
    }

    @Override
    public T getValor() {
        return dado;
    }

    @Override
    public No getPai() {
        return this.pai;
    }

    @Override
    public No getFilhoEsq() {
        return this.esquerda;
    }

    @Override
    public No getFilhoDir() {
        return this.direita;
    }


    // Está comprando com o valor do no outro( no que está vindo como parametro para comparação)
    // Aplicando a regra padrão do compareTo
    // se os valores forem iguais return 0
    // se os valor do outro No for maior return 1
    // se o valor for menor return -1


    @Override
    public int compareTo(T o) {
        System.out.println("Valor o");
        return dado.compareTo(o);
    }
}




