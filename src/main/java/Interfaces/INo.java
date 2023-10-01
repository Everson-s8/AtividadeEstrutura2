package Interfaces;
import Arvore.No;

public interface INo<T> extends Comparable<No<T>> {
  void setValor(int valor);
  void setPai(No no);
  void setFilhoEsq(No no);
  void setFilhoDir(No no);
  int getValor();
  No getPai();
  No getFilhoEsq();
  No getFilhoDir();

  // Está comprando com o valor do no outro( no que está vindo como parametro para comparação)
  // Aplicando a regra padrão do compareTo
  // se os valores forem iguais return 0
  // se os valor do outro No for maior return 1
  // se o valor for menor return -1

}