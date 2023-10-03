package Interfaces;

import Exception.NoInexistenteException;
import Exception.ArvoreVaziaException;
import Arvore.No;

public interface IArvore<T> {

  No inserir(No no, No valor)throws Exception;
  No remover(No no) throws ArvoreVaziaException, NoInexistenteException;
  T buscar(No no) throws ArvoreVaziaException, NoInexistenteException;
  No visitar(No no, No Atual) throws NoInexistenteException;
  
  boolean estaVazia();
  boolean ehCompleta() throws ArvoreVaziaException;
  int altura(No no ) throws ArvoreVaziaException;
  
  void imprimirArvore(String percurso) throws ArvoreVaziaException;
}