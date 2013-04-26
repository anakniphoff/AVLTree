package br.com.unisinos.dicionario;

import java.util.List;

/**
 * 
 * @author Ana Maria Kniphoff e Wesley Silva
 *
 */
public class Dicionario {
	/** Palavra em ingles */
	protected String palavra;

	/** Lista encadeada que ira armazenar as possiveis traducoes para a palavra */
	protected List<String> definicoes;

	public Dicionario() {
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public List<String> getDefinicoes() {
		return definicoes;
	}

	public void setDefinicoes(List<String> definicoes) {
		this.definicoes = definicoes;
	}
	
	/** compara dois objetos Dicionario */
	public int compareTo(Dicionario p){
		return this.palavra.compareToIgnoreCase(palavra);
	}
	
	/** verifica se os dois dicionarios sao iguais, comparando a palavra */
	 public boolean equals(Dicionario p){
	  return p.palavra.equalsIgnoreCase(this.palavra);
	 }
	
	/**public String toString(){
		String temp = "Palavra: " + palavra +"\nDefinições: ";
		for(int i=0; i<definicoes.size(); i++)
			temp += "* " + definicoes.get(i) + "\n";
		return temp;
	}*/
	
	 /** grava no formato adequado */
	 public String toDAT(){
	  String temp = palavra;
	  for(int i=0; i<definicoes.size(); i++){
	   temp+= "#" + definicoes.get(i);
	  }
	  temp += "\n";
	  return temp;
	 }
}
