package Arvore;

import Interfaces.IArvoreBinaria;
import Exception.NoInexistenteException;
import Exception.ArvoreVaziaException;

import java.util.Queue;

public class ArvoreBinaria<T extends Comparable<T>> implements IArvoreBinaria {

    // Criando um atributo para ser referência da raiz da árvore.
    private No root;

    // É com um construtor seta a raiz como null, já que de início ela não possui valor algum.
    public ArvoreBinaria(){
        root = null;
    }



    // Um metodo para chamar recursividade do No inserir, assim sendo mais facil para o usuario inserir o dado
    // Após o metodo ser chamado atualiza a árvore
    public void inserirNo(int valor) throws Exception {
        root = inserir(root, new No(valor));
    }

    //Inserir recursivamente

    @Override
    public No inserir(No atual, No valor) throws Exception {
        if (atual == null){
            atual = valor;
            return atual;
        }
        if (atual.compareTo(valor) == -1){                   // Para descobrir se o valor é maior ou menor que o valor atual
            if (atual.getFilhoEsq() != null){                // Se o valor for menor, faz um If para verificar se tem espaço disponivel para entrar a esquerda( onde fica o valor menor)
                inserir(atual.getFilhoEsq(), valor);         // Se não tiver espaço chama recursivamente novamente o metodo para pecorre até encontra o espaço que esteja livre
            }else {
                atual.setFilhoEsq(valor);                    // Após encontra o espaço livre, seta a esquerda o novo valor
                atual.getFilhoEsq().setPai(atual);           // E diz quem é seu pai
            }
        } else{
            if (atual.getFilhoDir() != null){                // Se o valor for maior, fazendo o mesmo que no caso do menor
                inserir(atual.getFilhoDir(), valor);
            }else {
                atual.setFilhoDir(valor);
                atual.getFilhoDir().setPai(atual);
            }
        }

        return atual;
    }


    // Temos aqui um remover que ira chamar um remover recursivamente
    // Mas antes faz uma comparação para ver se a árvore está vazia, se estiver lança uma exceção
    // e está salvando em uma aux o valor que vai ser removido, para mostrar para o usuario qual valor foi removido, salvando antes de remover
    // depois chama o remover Recursivo para remover o valor buscado, e colocar no root, para salvar as alterações que até mesmo a propria raiz pode ter sido mudada
    @Override
    public No remover(No no) throws ArvoreVaziaException, NoInexistenteException {
        if (estaVazia()){
            throw new ArvoreVaziaException("A arvore está vazia");
        }

        No aux = no;
        root = removerRecursivo(root, visitar(no, root));
        return aux;
    }

    //Remover recursivamente
    // Conseguindo remover nos 3 casos
    // Caso não tenah filho
    // Caso tenha apenas tenha um filho
    // Caso tenha dois filhos
    // Sendo a recursividade usada para ir remontando a árvore, tendo a sua remoção ocorrida definitivamente com o return,
    // pois nesse ponto que a estrutura da árvore se modifica para conseguir remover o No específico

    private No removerRecursivo(No atual, No dado){

        if (atual == null){                                                        // comparar se o atual está sendo null, pois se for não existe nada ali, então retorna ele mesmo
            return atual;
        }

        if (dado.getValor() < atual.getValor()){                                  // ver se o valor está a direita ou a esquerda do atual, para assim pecorre até achar o pretendido
            atual.setFilhoEsq(removerRecursivo(atual.getFilhoEsq(), dado));
        } else if (dado.getValor() > atual.getValor()) {
            atual.setFilhoDir(removerRecursivo(atual.getFilhoDir(), dado));
        }else {
            if (atual.getFilhoEsq() == null){                                     // Verifica se o No tem filho na esquerda, se não tiver está na direita ou não tem filhos
                return atual.getFilhoDir();                                       // Assim cortando o no atual e ligando o que estava a direita ao pai do atual para remover-lo da arvore
            }else if (atual.getFilhoDir() == null){                               // Assim reconfigura os ponteiros da arvore para pular o no que deseja ser removido e conectado ao filho, podendo ele existir ou não
                return atual.getFilhoEsq();                                       // Se não existir ira apontar para null e se existir , ira apontar para o unico filho
            }
            atual.setValor(maxValue(atual.getFilhoEsq()));// Chamar um metodo para pegar o maior valor da subarvore esquerda/ e substituindo no valor da atual
            atual.setFilhoEsq(removerRecursivo(atual.getFilhoEsq(),atual));
        }

        return atual;

    }

    private int maxValue(No no){
        int maxValue = no.getValor();
        while (no.getFilhoDir() != null){
            maxValue = no.getFilhoDir().getValor();
            no = no.getFilhoDir();
        }
        return maxValue;
    }


    // Visitar consiste em acessar um nó para se usar para alguma operação
    // Para isso foi usado um visitar recursivamente

    @Override
    public No visitar(No dado, No atual) throws NoInexistenteException {
        if (atual == null){
            throw new NoInexistenteException("Elemento inexistente");
        }
        if (dado.getValor() == atual.getValor()){               // Comparando para ver se o dado que se pretende encontra é igual o que existe na arvore
            return atual;                                       // Se for retorna a posição dele na arvore
        }else{
            if (dado.getValor()< atual.getValor()){             // Vendo se o valor está a direita ou esquerda do atual que está sendo comparando determinando para onde vai buscar ele( assim reduzindo o número de busca)
                return visitar(dado, atual.getFilhoEsq());      // chama a recursividade para visistar a esquerda, já que o dado era menor que o atual
            }else {
                return visitar(dado,atual.getFilhoDir());       // mesma coisa que a esquerda só que a direita.
            }
        }
    }

    // Usando o buscar caso queira pegar o valor de tal no
    // começa comparando se a árvore está vazia, se estiver lança uma exceção
    // depois usa um auxiliar, para começar a pecorre desde o início da árvore
    // se atual for null, significa que não conseguiu encontrar o valor na árvore assim gerando uma exceção
    // se o valor do no for igual o do atual significa que achou o valor na arvore
    // se não vai procurar se o valor está a direita ou a esquerda da arvore, fazendo isso até encontra o valor
    @Override
    public int buscar(No no) throws ArvoreVaziaException, NoInexistenteException {
        if (estaVazia()){
            throw new ArvoreVaziaException("A arvore está vazia");
        }

        No atual = root;

        while (true){
            if (atual == null){
                throw new NoInexistenteException("Elemento inexistente");
            }
            if (no.getValor() == atual.getValor()){
                return atual.getValor();
            }else {
                if (no.getValor() < atual.getValor()){
                    atual = atual.getFilhoEsq();
                }else {
                    atual = atual.getFilhoDir();
                }
            }
        }

    }
    @Override
    public boolean estaVazia() {
        if (root == null){
            return true;
        }
        return false;
    }


    //Usando eh completa para chamar um ehcompleta recusivamente
    // alturaTotal recebendo altura
    // e depois chamando a recursividade
    @Override
    public boolean ehCompleta() throws ArvoreVaziaException {
        int alturaTotal = altura(root);
        return ehCompletarecusivo(root,0, alturaTotal);
    }

    // ehcompletarecursivo é uma recursividade do eh completa
    // primeiro compara se o no for null, se for está completo então retorna true
    // Nível maior ou igual à altura indica árvore não completa

    private boolean ehCompletarecusivo(No no, int nivelatual,int alturaTotal){
        if (no == null){
            return true;
        }

        if (nivelatual >= alturaTotal){
            return false;
        }

        return ehCompletarecusivo(no.getFilhoEsq(), nivelatual + 1, alturaTotal) &&
                ehCompletarecusivo(no.getFilhoDir(), nivelatual+ 1, alturaTotal);

    }


    //Para fazer uma altura está se fazendo uma chamada recursiva assim consegue facilmente conseguir a altura
    // Primeiro compara para ver se no, não é igual null significando que a arvore está vazia, então lança um -1, pois o root começa pelo 0;
    // se não tiver então faz uma chamada recursiva onde pecorre a esquerda e a direita guardando a altura para depois fazer o calculo.
    // A altura da árvore é o máximo entre as alturas das subárvores esquerda e direita, mais 1 (para contar o nó atual)
    // para isso se usa o Math.max
    @Override
    public int altura(No no) throws ArvoreVaziaException {
        if (no == null){
           return -1;
        }else {

            int alturaEsquerda = altura(no.getFilhoEsq());
            int alturaDireita = altura(no.getFilhoDir());

            return Math.max(alturaEsquerda,alturaDireita) + 1;
        }

    }

    // imprimir a árvore conforme a escolha do usuario, podendo ser emOrdem/ preOrdem ou posOrdem
    @Override
    public void imprimirArvore(String percurso) throws ArvoreVaziaException {
        if (estaVazia()){
            throw new ArvoreVaziaException("A arvore está vazia");
        }
        System.out.println("-----------------Escolha o tipo de percurso que voce deseja imprimir a arvore-----------------");
        System.out.println("----------------------------------preOrdem----------------------------------");
        System.out.println("----------------------------------inOrdem----------------------------------");
        System.out.println("----------------------------------posOrdem----------------------------------");

        if (percurso.equalsIgnoreCase("preOrdem")){
            preOrdem(root);
        }else if (percurso.equalsIgnoreCase("inOrdem")){
            inOrdem(root);
        } else if (percurso.equalsIgnoreCase("posOrdem")) {
            posOrdem(root);
        }
    }

    // Percursos, para se visitar os nós das árvores, usando da recursividade

    //Implementação do preOrdem
    // Primeiro se visita o valor
    // depois pecorre a esquerda
    // depois pecorre a direita
    @Override
    public void preOrdem(No no) {
        if (no != null){
            System.out.println(no.getValor());
            preOrdem(no.getFilhoEsq());
            preOrdem(no.getFilhoDir());
        }
    }

    //Implementação do inOrdem
    // Começa pecorrendo pela esquerda, depois pega o valor e depois vai a direita
    // Como o nome já diz acaba pegando os dados da arvorem em ordem.
    @Override
    public void inOrdem(No no) {
        if (no != null){
            inOrdem(no.getFilhoEsq());
            System.out.println(no.getValor());
            inOrdem(no.getFilhoDir());
        }
    }

    // Implementação da posOrdem
    // Onde começa pecorrendo primeiro a esquerda, depois pecorre a direita, pega o valor e fica fazendo isso em uma chamada recusiva
    @Override
    public void posOrdem(No no) {
        if (no != null){
            posOrdem(no.getFilhoEsq());
            posOrdem(no.getFilhoDir());
            System.out.println(no.getValor());
        }
    }

    public No getRoot() {
        return root;
    }

    public void setRoot(No root) {
        this.root = root;
    }

    public void limparArvore(){
        this.root = null;
    }
}
