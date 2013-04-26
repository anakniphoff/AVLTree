package br.com.unisinos.dicionario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author Ana Maria Kniphoff e Wesley Silva
 *
 */
public class Tradutor {
	
	/** A arvore AVL */
	private AvlTree arvore = new AvlTree();
	
	/**
	 * Carrega o arquivo de dicionário (dicionário.dat) para a árvore AVL.
	 * 
	 * @param arq
	 */
	protected void carregaDicionario(String arq) {
		
		InputStream is = null; 
		BufferedReader br = null;
				
		try {		
			// Le o arquivo dicionario.dat
			is = new FileInputStream(arq);
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;

			while((line = br.readLine()) != null) {
				// Para cada linha que foi lida, quebra a string por #
			    StringTokenizer st = new StringTokenizer(line, "#");

			    // Instancia um dicionario e uma lista de definicoes
			    Dicionario dicionario = new Dicionario();
			    List<String> definicoes = new ArrayList<String>();
			    
			    boolean primeiro = true;
			    while (st.hasMoreTokens()) {
			    	
			    	String token = st.nextToken();
			    	
			    	// Se for o primeiro token, entao e a palavra em ingles
			    	if (primeiro) {
			    		dicionario.setPalavra(token);

			    		primeiro = false;
			    		
			    		//faz retornar ao laço
			    		continue;
			    	}

			    	// Adiciona a definicao na lista
			    	definicoes.add(token);
			    }
			    
			    // Seta as definicoes no dicionario
			    dicionario.setDefinicoes(definicoes);
			    
			    // Inclui o dicionario na arvore
			    arvore.insert(new AvlNode(dicionario));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * Traduz uma única palavra. 
	 * Este método recebe como parâmetro a palavra a ser traduzida 
	 * e retorna a lista das possíveis traduções para esta palavra. 
	 * 
	 * @param palavra
	 * @return
	 */
	public List<String> traduzPalavra(String palavra) {
		List<String> definicoes = null;
		
		AvlNode node = arvore.search(palavra);
		
		if (node != null) {
			Dicionario dicionario = node.getElement();
			definicoes = dicionario.getDefinicoes();
		}
		
		return definicoes;
	}

	/**
	 * Insere uma nova definição no dicionário. 
	 * Recebe como parâmetro a palavra em inglês e lista de possíveis traduções.
	 * 
	 * @param palavra
	 * @param definicoes
	 */
	public void insereTraducao(String palavra, List<String> definicoes) {
		Dicionario dicionario = new Dicionario();
		dicionario.setPalavra(palavra);
		dicionario.setDefinicoes(definicoes);

		// Inclui a palavra e suas definicoes na arvore
		arvore.insert(new AvlNode(dicionario));
	}

	/** 
	 * Salva o arquivo de dicionário (dicionário.dat) 
	 * com as respectivas definições baseado no conteúdo da árvore AVL
	 * 
	 * @param arq
	 */
	public void salvaDicionario(String arq) {
		OutputStream os = null;
		BufferedWriter bw = null;
		
		try {
			// Abre o arquivo dicionario.dat para escrita
			os = new FileOutputStream(arq);
			bw = new BufferedWriter(new OutputStreamWriter(os));
			
			// Percorre a arvore
			String dados = arvore.inorderToDAT();
			bw.write(dados);
//			while(iter.hasNext()) {
//				String key = iter.next();
//				Dicionario dicionario = arvore.get(key);				
				
				// Escreve a palavra
//				bw.write(dicionario.getPalavra());
//				bw.write("#");
//				
//				int size = dicionario.getDefinicoes().size();
//				for (int i = 0; i < size; i++) {
//					// Escreve a definicao
//					bw.write(dicionario.getDefinicoes().get(i));
//
//					// Adiciona o separador, exceto na ultima execucao do laco, onde e adicionada a quebra de linha
//					if (i != (size - 1)) {
//						bw.write("#");
//					} else {
//						bw.write("\r\n");
//					}
//				}
//			}
			//bw.flush();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public AvlTree getArvore() {
		return arvore;
	}
}
