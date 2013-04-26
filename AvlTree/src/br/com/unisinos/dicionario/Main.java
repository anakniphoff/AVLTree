package br.com.unisinos.dicionario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 
 * @author Ana Maria Kniphoff e Wesley Silva
 *
 */
public class Main {
	/**
	 * Mostra na tela a palavra e suas definicoes separadas por virgula
	 *  
	 * @param palavra
	 * @param definicoes
	 */
	public static void showDefinicoes(String palavra, List<String> definicoes) {
    	System.out.print("* Definições para a palavra " + palavra + ": ");
    	
    	int size = definicoes.size();
    	for (int i = 0; i < size; i++) {
    		System.out.print(definicoes.get(i));

    		if (i != (size -1)) {
    			System.out.print(", ");
    		} else {
    			System.out.println(".");
    		}
    	}
	}
	
	/** 
	 * Mostra o menu da aplicacao 
	 */
	public static void showMenu (){
        System.out.println("--- MENU ---");
        System.out.println("1 - traduzir");
        System.out.println("2 - mostrar dicionario");
        System.out.println("0 - sair.");
        System.out.println("");
    }
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Forneca o arquivo de dicionario como parametro do programa.");
			System.exit(-1);
		}
		
		// O argumento que foi fornecido para o programa é o arquivo de dicionario 
		String arquivo = args[0];

		// Instancia um scanner para ler os dados que o usuario digita
		Scanner sc = new Scanner(System.in);
        int op = 0;
        String palavra = null;

        // Instancia um dicionario
        Tradutor tradutor = new Tradutor();
        
        // Carrega as palavras do dicionario que estao no arquivo
        tradutor.carregaDicionario(arquivo);
        
        do {
        	// Mostra o menu
            showMenu();
            
            // Le a opcao que o usuario digitou
            op = sc.nextInt();

            // Se a opcao for 0, entao continua para que saia do laco.
            if (op == 0) {
            	continue;
            } else if (op == 1) { // Opcao para traduzir a palavra 
                System.out.print("Digite o a palavra que deseja traduzir: ");
                
                // Le a palavra digitada pelo usuario
                palavra = sc.next();

                // Busca as definicoes para a palavra digitada
                List<String> definicoes = tradutor.traduzPalavra(palavra);
                
                // Caso nao encontre as definicoes...
                if (definicoes == null) {
                	// Mostra mensagem para o usuario
                	System.out.println("Palavra não encontrada.");
                	
                	// Permite que o usuario digite as definicoes separadas por virgula
                	System.out.println("Digite as definições desta palavra separadas por vírgula: ");
                	
                	// Instancia um dicionario para armazenar a nova palavra e suas definicoes
                	Dicionario novaPalavra = new Dicionario();
                	List<String> definicoesPalavra = new ArrayList<String>();
                	
                	// Seta a nova palavra no dicionario
                	novaPalavra.setPalavra(palavra);
                	
                	// Le as definicoes que o usuario digitou
                	String todasDefinicoes = sc.next();

                	// Quebra a string digitada por virgula (,) 
                	StringTokenizer st = new StringTokenizer(todasDefinicoes, ",");
                	
                	// Para cada token encontrado, adiciona a definicao na lista de definicoes
                	while (st.hasMoreTokens()) {
                		definicoesPalavra.add(st.nextToken());
                	}
                	
                	// Adiciona a lista de definicoes ao dicionario da nova palavra
                	novaPalavra.setDefinicoes(definicoesPalavra);
                	
                	// Adiciona na arvore o novo dicionario
                	tradutor.getArvore().insert(new AvlNode(novaPalavra));
                	
                	//garante que a palavra traduzida seja gravada caso ocorra um erro antes da finalizacao do programa
                    tradutor.salvaDicionario(arquivo);		

                	
                } else {
                	// Caso tenha encontrado a palavra, mostra suas definicoes
                	showDefinicoes(palavra, definicoes);
                }

                System.out.println("");                
            } else if (op == 2) { // Mostra todas as palavras presentes no dicionario
                AvlTree dicionarios = tradutor.getArvore();

                // Caso o dicionario esteja vazio...
                if (dicionarios == null) {
                	System.out.println("Dicionario esta vazio.");
                } else {
                	// Caso contrario, mostra as definicoes de cada palavra
        			// Percorre a arvore
        			String dados = dicionarios.inorderToDAT();
        			System.out.println(dados);
//        			while(iter.hasNext()) {
//        				String key = iter.next();
//        				Dicionario dicionario = tradutor.getArvore().get(key);
//
//        				showDefinicoes(dicionario.getPalavra(), dicionario.getDefinicoes());
//                    }

                	System.out.println("");                    
                }
            } else {
            	// Opcao invalida
                System.out.println("Opcao invalida. Tente novamente");
            }
        } while(op != 0);
        
        // Ao final do programa grava no arquivo a arvore com todos os dicionarios
        tradutor.salvaDicionario(arquivo);		
        sc.close();
	}
}
