package Interfaces;
import Arvore.No;

public interface IArvoreBinaria extends IArvore {
  void preOrdem(No no);
  void inOrdem(No no);
  void posOrdem(No no);
}