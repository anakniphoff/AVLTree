package br.com.unisinos.dicionario;

/**
 * 
 * @author Ana Maria Kniphoff e Wesley Silva
 *
 */

public class AvlTree {
	private AvlNode root = null;

	public AvlNode getRoot (){
		return root;
	}
	
	/** Retorna a altura da árvore */
	private static int height(AvlNode t) {
		return t == null ? 0 : t.height;
	}

	/** Retorna o maior valor ente lhs e rhs. */
	private static int max(int lhs, int rhs) {
		return lhs > rhs ? lhs : rhs;
	}

	/** Retorna o fator de balanceamento da árvore com raiz t */
	private int getFactor(AvlNode t) {
		return height( t.left ) - height( t.right);
	}

	/** Rotação simples à direita */
	private static AvlNode doRightRotation(AvlNode k2) {
		AvlNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = max(height(k2.left), height(k2.right)) + 1;
		k1.height = max(height(k1.left), k2.height) + 1;

		return k1;
	}
	
	/** Rotação simples à esquerda */
	private static AvlNode doLeftRotation( AvlNode k1 ) {
		AvlNode k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
		k2.height = max( height( k2.right ), k1.height ) + 1;
		return k2;
		}
	
	/** Rotação dupla à direita */
	private static AvlNode doDoubleRightRotation( AvlNode k3 ) {
		k3.left = doLeftRotation( k3.left );
		return doRightRotation( k3 );
		}
	
	/** Rotação dupla à esquerda */
	private static AvlNode doDoubleLeftRotation (AvlNode k1) {
		k1.right = doRightRotation( k1.right );
		return doLeftRotation( k1 );
		}
	
	/** Busca uma chave na arvore */
	public AvlNode search(String key){
		return search(root, key);
	}
	
	/** Sobrecarga do metodo search */
	private AvlNode search(AvlNode n, String key){
		
		while(n != null){
			//se nao for a chave buscada, retorna n
			if(key.equalsIgnoreCase(n.theElement.palavra))
				return n;
			//se a chave procurada for maior que a chave de n, vai para a o filho da direita de n
			else if(key.compareToIgnoreCase(n.getKey())>0)
				n = n.right;
			//senao para o da esquerda
			else
				n = n.left;
		}
		
		//retorna null se nao encontrou a chave
		return null;
	}

	/** Realiza a rotacao adequada em um nodo */
	private AvlNode rotation (AvlNode nodo) {
		
        //nodo tem fator de balanceamento 2
		if ( getFactor(nodo) == 2 ) {
                if (getFactor (nodo.left)>0)
                	nodo = doRightRotation( nodo ); 
                else
                	nodo = doDoubleRightRotation( nodo );  
        } 
		//se o fator de balanceamento for -2
        else if ( getFactor(nodo) == -2 ) { 
                if ( getFactor(nodo.right)<0 )
                	nodo = doLeftRotation( nodo );
                else
                	nodo = doDoubleLeftRotation( nodo );  
        }
        return nodo;  
    } 
	
	/** Insere um elemento na arvore */
	public boolean insert(AvlNode novo){
		
		//verifica se o elemento ja existe na arvore
		AvlNode temp = this.search(novo.key);
		
		//se existe, retorna false (insercao nao realizada)
		if(temp!=null)
			return false;
		
		//se a raiz estiver vazia, o novo nodo sera a raiz
		else if (root == null){
			root = novo;
			return true;
		}
		else{//senao, insere no local adequado
			novo = insert(novo,root);
			return true;
		}
	}
	
	/** insere um novo elemento */
	private AvlNode insert(AvlNode novo, AvlNode pai){
		//se o novo nodo eh maior que o pai, entao vai para a arvore da direita
		if(novo.compareTo(pai)>0){
			if(pai.right == null){
				
				pai.setRight(novo);
				//atualiza o atributo height
				pai.height = max( height(pai.left), height(pai.right) ) +1;	
			}else{
				insert(novo,pai.right);
				//atualiza o atributo height
				pai.height = max (height(pai.right) , height(pai.left)) + 1;
			}
		//se o novo nodo eh menor que o pai, entao vai para a arvore da esquerda
		}else{
			
			if(pai.left == null){
				pai.setLeft(novo);
				//atualiza o atributo height
				pai.height = max( height(pai.left), height(pai.right)) +1;
			}else{
				insert(novo,pai.left);
				//atualiza o atributo height
				pai.height = max (height(pai.right) , height(pai.left)) + 1;
			}
		}
		return novo;
	}

	/** Faz o balanceamento */
    private void balance(AvlNode nodo){
    	//verifica se eh necessario balancear
    	if (getFactor(nodo) == 2 || getFactor(nodo) == -2)
    		nodo = rotation(nodo);
    }
    
	/**Retorna uma String com todas as chaves (palavras em inglês) da arvore em ordem  */
	public String inorder() {  
        String s = "";
        if(root == null) //se a arvore estiver vazia
        	return "Nenhuma palavra cadastrada.";
		return inorder(root, s);  
    } 
	
	/** ordena */
    protected String inorder(AvlNode p, String s) {  
        if(p!=null){     
    		//segue pela esquerda até onde conseguir, pega o elemento, faz o mesmo percurso, porem pela direita 
        	 s=inorder(p.left,s);  
             s+=p.key + "\n"; 
             s+=inorder(p.right,s);
             return s;
        }
        return "";
    }
    
    /** Retorna uma String com todos os elementos da árvore em ordem, no formato de gravacao definido */
	public String inorderToDAT() {  
        String s = "";
        if(root == null)
        	return "";  
		return inorderToDAT(root, s);  
    } 
	
	/** Sobrecarga */	
    protected String inorderToDAT(AvlNode p, String s) {  
        if(p!=null){     
    		//segue pela esquerda até onde conseguir, pega o elemento, faz o mesmo percurso, porem pela direita 
        	 s=inorderToDAT(p.left,s);  
             s+=p.toDAT(); 
             s+=inorderToDAT(p.right,s);
             return s;
        }
        return "";
    }
    
    /** Encontra o pai de um nodo. Caso o nodo nao seja root, retorna null, pois root nao tem pai. Desconsidera o caso de o nodo nao existir na arvore, pois e apenas
    um metodo auxiliar. A verificacao da existencia do nodo na arvore nao sera encargo desse metodo */
    private AvlNode getPai(AvlNode n){
    	//se n for a raiz, portanto nao tem pai
    	if(n.equals(root))
    		return null;
    	else{ //procura o pai
    		//variavel temporaria que armazena o candidato a pai
    		AvlNode temp = root;
    		//variavel de controle do laço
    		do{
    			//se a esquerda de temp nao estiver vazia e o nodo filho existente ali for o nodo n, entao temp e o pai. encerra a busca
    			if(temp.left != null)
    				if (temp.left.equals(n))
    					return temp;
    			//se a direita de temp nao estiver vazia e o nodo filho existente ali for o nodo n, entao temp e o pai. Encerra a busca
    			if (temp.right != null)
    				if(temp.right.equals(n))
    					return temp;
    			//se n for maior que temp, temp passa a ser o seu proprio filho da direita na proxima iteracao
    			if(n.compareTo(temp)>0)
    				temp = temp.right;
    			//se n for maior que temp, temp passa a ser o seu proprio filho da esquerda na proxima iteracao
    			else
    				temp = temp.left;
    			
    		}while (true);
    	}
    }
    
    /** exclui um nodo que tem um filho somente  */
    private void excluiNodoComUmFilho(AvlNode nodo, AvlNode pai){
    	
    	//se pai e null, significa que se deseja excluir a raiz, possuindo um so filho
    	if(pai == null){
    		if (nodo.left == null)
    			root = root.right;
    		else
    			root = root.left;
    	}
    	//testa se o nodo a ser excluido esta a esquerda de pai, se for o nodo pai "herda" o unico filho do nodo
    	else if (pai.left!=null){
    		if(pai.left.equals(nodo)){
    			//teste para verificar de que lado fica o filho do nodo a ser excluido
    			if(nodo.left != null)
    				pai.setLeft(nodo.left);
    			else
    				pai.setLeft(nodo.right);
    			}
    		}else{
    			//teste para verificar de que lado fica o filho do nodo a ser excluido
    			if(nodo.left != null)
    				pai.setRight(nodo.left);
    			else
    				pai.setRight(nodo.right);
    		}
    }
    
    /** exclui um nodo com dois filhos por meio de copia (wesley) */
    private void exclusaoPorCopia(AvlNode nodo){
    	
    	//encontra o maior nodo da sub-arvore da esquerda para realizar a cópia
    	AvlNode copia = this.encontraMaior(nodo);
    	
    	//salva conteudo do nodo encontrado anteriormente
    	Dicionario temp = copia.theElement;
    	
    	//encontra o pai do nodo usado para cópia
    	AvlNode paiCopia = getPai(copia);
    	
    	//o maior nodo da esquerda sempre sera uma folha ou entao tera um unico filho a esquerda
    	//teste para verificar se o nodo a ser copiado possui um filho, que se existir estará a esquerda
    		
    	//verifica de que lado esta o filho	
    	if(paiCopia.right != null && paiCopia.right.equals(copia))	
    		paiCopia.setRight(copia.left);	
    	else	
    		paiCopia.setLeft(copia.left);
    		
    	//apos excluido o nodo copia, copia-se o conteudo do nodo copia para o nodo que o metodo deseja excluir
    	nodo.setElement(temp);
    }
    
    /** metodo para auxliar na exclusao por copia. Encontra o maior nodo de uma arvore */
    private AvlNode encontraMaior(AvlNode nodo){
    	//pega a raiz da sub-arvore da esquerda
    	AvlNode maior = nodo.left;
    	//logicamente, o nodo da direita sera sempre maior que o nodo pai
    	//percorre-se entao pela direita da subarvore da esquerda do nodo, ate o momento em que se chega a um nodo que nao tem nenhum filho a sua direita, sendo esse o maior nodo
    	while(maior.right != null)
    		maior = maior.right;
    	return maior;
    }
    
    /** wesley */
    public AvlNode remove(String key){
    	//procura se a chave a ser deletada realmente existe
    	AvlNode nodo = this.search(key);
    	//se nao existe, retorna null
    	if (nodo==null)
    		return null;
    	
    	//se encontra, localiza o pai do nodo
    	AvlNode pai = getPai(nodo);
    	
    	//salva o nodo a ser excluido
    	AvlNode nodoExcluido = new AvlNode(nodo.theElement);
    	
    	//exclusao no caso de o nodo ser uma folha
    	if (nodo.left == null && nodo.right == null){
    		//se o nodo estiver a esquerda de pai
    		if(pai.left!=null && pai.left.equals(nodo))
    			pai.setLeft(null);
    		else
    			pai.setRight(null);
    	}
    	//exclusao no caso de o nodo ter apenas um filho
	    else if (nodo.left == null || nodo.right == null)
	    		this.excluiNodoComUmFilho(nodo, pai);
    	//exclusao no caso de o nodo ter dois filhos
	    else{
	    		this.exclusaoPorCopia(nodo);
    	}
    	//retorna o nodo excluido
    	return nodoExcluido;
    }  	 
   
}