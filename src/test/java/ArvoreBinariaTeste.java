import Arvore.ArvoreBinaria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import Exception.NoInexistenteException;
import Arvore.No;
import Exception.ArvoreVaziaException;

public class ArvoreBinariaTeste {

    private ArvoreBinaria<Integer> arvore;

    @BeforeEach
    public void setUp() {
        arvore = new ArvoreBinaria();
    }

    // verifica se a arvore está vazia antes de inserir os elementos. Após inserir, realiza a busca
    // para verificar se os itens foram inseridos e verifica mais uma vez se a árvore está vazia.
    @Test
    public void testInsercao() throws Exception {

        assertThat(arvore.estaVazia()).isEqualTo(true);

        arvore.inserirNo(10);
        arvore.inserirNo(5);
        arvore.inserirNo(15);
        arvore.inserirNo(3);
        arvore.inserirNo(7);

        assertEquals(arvore.buscar(new No(10)), 10);
        assertEquals( arvore.buscar(new No(5)), 5);
        assertEquals(arvore.buscar(new No(15)), 15);
        assertEquals(arvore.buscar(new No(3)), 3);
        assertEquals(arvore.buscar(new No(7)), 7);

        assertThat(arvore.estaVazia()).isEqualTo(false);
    }

    @Test
    public void testRemocaoNoInexistente() throws Exception {

        arvore.inserirNo(10);
        arvore.inserirNo(5);
        arvore.inserirNo(15);
        arvore.inserirNo(3);
        arvore.inserirNo(7);


        // Verifica se uma exceção NoInexistenteException é lançada ao remover o nó 20 (que não existe)
        NoInexistenteException excecao = assertThrows(NoInexistenteException.class, () -> {
            arvore.remover(new No(20));
        });


        // Verifica a mensagem da exceção lançada
        assertThat(excecao.getMessage()).isEqualTo("Elemento inexistente");

    }

    @Test
    public void testeBuscaApósRemoção() throws Exception {

        arvore.inserirNo(10);
        arvore.inserirNo(5);
        arvore.inserirNo(15);
        arvore.inserirNo(3);
        arvore.inserirNo(7);

        arvore.remover(new No(15));

        //verifica se será lançada uma exceção ao buscar um nó que já foi removido
        NoInexistenteException excecao = assertThrows(NoInexistenteException.class, () -> {
            arvore.buscar(new No(15));
        });
        //verifica a mensagem da exceção
        assertThat(excecao.getMessage()).isEqualTo("Elemento inexistente");

    }

    @Test
    public void testRemocaoPelaRaiz() throws Exception {

        arvore.inserirNo(15);
        arvore.inserirNo(8);
        arvore.inserirNo(9);
        arvore.inserirNo(17);
        arvore.inserirNo(16);

        //remove as raizes atuais e as atualizam,.
        arvore.remover(new No(15));

        assertThat(arvore.getRoot().getValor()).isEqualTo(9);

        arvore.remover(new No(9));

        assertThat(arvore.getRoot().getValor()).isEqualTo(8);

        arvore.remover(new No(8));

        assertThat(arvore.getRoot().getValor()).isEqualTo(17);

        arvore.remover(new No(17));

        assertThat(arvore.getRoot().getValor()).isEqualTo(16);


    }
    //
    @Test
    public void testBusca() throws Exception {

        //insere antes de ralizar as buscas

        arvore.inserirNo(10);
        arvore.inserirNo(5);
        arvore.inserirNo(15);
        arvore.inserirNo(3);
        arvore.inserirNo(7);


        //espera que o resultado seja 10
        assertThat(arvore.buscar(new No(10))).isEqualTo(10);

        //verifica se será lançada uma exceção ao buscar um nó que não existe
        NoInexistenteException excecao = assertThrows(NoInexistenteException.class, () -> {
            arvore.buscar(new No(20));
        });
        //verifica a mensagem da exceção
        assertThat(excecao.getMessage()).isEqualTo("Elemento inexistente");
}


    // Teste da altura da arvore
    // Onde espera que o resultado retorne 2
    // E se a arvore estiver vazia espera um retorno de -1
    @Test
    public void testAltura() throws Exception {
        arvore.inserirNo(10);
        arvore.inserirNo(5);
        arvore.inserirNo(15);
        arvore.inserirNo(3);
        arvore.inserirNo(7);

        assertThat(arvore.altura(arvore.getRoot())).isEqualTo(2);

        arvore.limparArvore();

        assertThat(arvore.altura(arvore.getRoot())).isEqualTo(-1);


    }


    // Se a arvore estiver vazia se espera um true
    // Se a arvore estiver incompleta espera false
    @Test
    public void testEhCompleta() throws Exception {

        assertThat(arvore.ehCompleta()).isEqualTo(true);

        arvore.inserirNo(10);
        arvore.inserirNo(5);
        arvore.inserirNo(15);
        arvore.inserirNo(3);
        arvore.inserirNo(7);

        assertThat(arvore.ehCompleta()).isEqualTo(false);
    }


}
