package br.com.unisinos.dicionario;

/**
 * 
 * @author Ana Maria Kniphoff e Wesley Silva
 *
 */

public class AvlNode {
	protected int height;
	protected String key;
	protected AvlNode left, right;
	protected Dicionario theElement;

	public AvlNode (Dicionario theElement) {
		this(theElement, null, null);
	}

	public AvlNode(Dicionario el, AvlNode lt, AvlNode rt) {
		key = el.getPalavra();
		left = lt;
		right = rt;
		height = 0;
		theElement = el;
	}
	
	/** getters */
	public int getHeigh(){
		return height;
	}
	
	public String getKey(){
		return key;
	}
	
	public AvlNode getLeft(){
		return left;
	}
	
	public AvlNode getRight(){
		return right;
	}
	
	public Dicionario getElement(){
		return theElement;
	}
	
	/** setters */
	public void setHeight(int h){
		height = h;
	}
	
	public void setRight(AvlNode r){
		right = r;
	}
	
	public void setLeft(AvlNode l){
		left = l;
	}
	
	public void setElement(Dicionario d){
		key = d.palavra;
		theElement = d;
	}
	
	/** verifica se dois nodos tem a mesma chave */
	public boolean equals(AvlNode n){
		return this.key.equalsIgnoreCase(n.key);
	}
	
	public int compareTo(AvlNode n){
		return this.key.compareToIgnoreCase(n.key);
	}

	public String toString(){
		return theElement.toString();
	}
	
	public String toDAT(){
		return theElement.toDAT();
	}
}