
import java.util.*;

public class Board {
	private char[][] state;
	private Board father;
	private int depth;
	private int heuristic;
	private int minmax;
	private String turn;
	
	public Board(char[][] state, Board father, String turn){
		this.state = state;
		this.father = father;
		this.turn = turn;
	}
	
	private Board(char[][] state, Board father, int depth, String turn){
		this.state = state;
		this.father = father;
		this.depth = depth;
		this.turn = turn;
	}
	
	public char[][] getState(){return state;}
	public Board getFather(){return father;}
	public int getDepth(){return depth;}
	public int getHeuristic(){return heuristic;}
	public int getMinMax(){return minmax;}
	public String getTurn(){return turn;}
	
	private char[][] cheat(char[][] voila){
		char[][] result = new char[7][6];
		for(int k = 0; k < 7; k++){
			for(int z = 0; z < 6; z++){
				result[k][z] = voila[k][z];
			}
		}
		return result;
	}
	
	public boolean alreadyFourCheck(char[][] toTest){
		int temp = 0;
		
		//horizontal computador
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 4; j++){
				for(int k = 0; k < 4; k++){
					if(toTest[j+k][i] == 'O'){
						temp++;
					}else{
						temp = 0;
						break;
					}
				}
				if(temp > 3)
					return true;
			}
		}
		
		//horizontal jogador
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 4; j++){
				for(int k = 0; k < 4; k++){
					if(toTest[j+k][i] == 'X'){
						temp++;
					}else{
						temp = 0;
						break;
					}
				}
				if(temp > 3)
					return true;
			}
		}
				
		//vertical computador
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 3; j++){
				for(int k = 0; k < 4; k++){
					if(toTest[i][j+k] == 'O'){
						temp++;
					}else{
						temp = 0;
						break;
					}
				}
				if(temp > 3)
					return true;
			}
		}
		
		//vertical jogador
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 3; j++){
				for(int k = 0; k < 4; k++){
					if(toTest[i][j+k] == 'X'){
						temp++;
					}else{
						temp = 0;
						break;
					}
				}
				if(temp > 3)
					return true;
			}
		}
		
		//diagonal computador
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 7; j++){
				for(int k = 0; k < 4; k++){
					if(j < 3){
						if(toTest[j+k][i+k] == 'O'){
							temp++;
						}else{
							temp = 0;
							break;
						}
					}else if(j == 3){
						if(toTest[j+k][i+k] == 'O'){
							temp++;
						}else{
							temp = 0;
							break;
						}	
					}else{
						if(toTest[j-k][i+k] == 'O'){
							temp++;
						}else{
							temp = 0;
							break;
						}
					}
				}
				if(temp > 3)
					return true;
			}
		}
		
		
		for(int i = 0; i < 3; i++){
			for(int j = 3; j == 3; j++){
				for(int k = 0; k < 4; k++){
					if(toTest[j-k][i+k] == 'O'){
						temp++;
					}else{
						temp = 0;
						break;
					}	
				}
				if(temp > 3)
					return true;
			}
		}
		
		
		
		//diagonal jogador
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 7; j++){
				for(int k = 0; k < 4; k++){
					if(j < 3){
						if(toTest[j+k][i+k] == 'X'){
							temp++;
						}else{
							temp = 0;
							break;
						}
					}else if(j == 3){
						if(toTest[j+k][i+k] == 'X'){
							temp++;
						}else{
							temp = 0;
							break;
						}	
					}else{
						if(toTest[j-k][i+k] == 'X'){
							temp++;
						}else{
							temp = 0;
							break;
						}
					}
				}
				if(temp > 3)
					return true;
			}
		}
		
		
		
		for(int i = 0; i < 3; i++){
			for(int j = 3; j == 3; j++){
				for(int k = 0; k < 4; k++){
					if(toTest[j-k][i+k] == 'X'){
						temp++;
					}else{
						temp = 0;
						break;
					}	
				}
				if(temp > 3)
					return true;
			}
		}
				
		return false;
	}
	
	
	public boolean fullBoard(char[][] toTest){
		for(int i = 0; i < 7; i++){
			if(toTest[i][5] == '\0')
				return false;
		}
		return true;
	}
	
	
	private List<Board> children(Board start){
		List<Board> childs = new ArrayList<Board>();
		Board childBoard;
		char[][] child = new char[7][6];
		
		for(int i = 0; i < 7; i++)
			for(int j = 0; j < 6; j++)
				child[i][j] = start.getState()[i][j];
			
		String turn = new String();
		char symbol = 'O';
		if(start.getTurn().equals("player")){
			turn = "AI";
			symbol = 'X';
		}
		else turn = "player";
		
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 6; j++){
				
				if(child[i][j] == '\0'){
					child[i][j] = symbol;
					childBoard = new Board (cheat(child), start, (start.getDepth())+1, turn);
					childs.add(childBoard);
					for(int k = 0; k < 7; k++)
						for(int z = 0; z < 6; z++)
							child[k][z] = start.getState()[k][z];
					
					break;
				}
	
			}
		}
		
		return childs;
	}
	
	private int heuristic(char[][] board){
		int result;
		int result1 = 0;
		int result2 = 0;
		int temp = 0;
		
		//horizontal para computador
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 4; j++){
				for(int k = 0; k < 4; k++){
					if(board[j+k][i] == 'O'){
						temp++;
					}else if(board[j+k][i] == 'X'){
						temp = 0;
						break;
					}
				}
				result1 += Math.pow(10, temp);
				temp = 0;
			}
		}
		
		//vertical para computador
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 3; j++){
				for(int k = 0; k < 4; k++){
					if(board[i][j+k] == 'O'){
						temp++;
					}else if(board[i][j+k] == 'X'){
						temp = 0;
						break;
					}
				}
				result1 += Math.pow(10, temp);
				temp = 0;
			}
		}
		
		//diagonal para computador
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 7; j++){
				for(int k = 0; k < 4; k++){
					if(j < 3){
						if(board[j+k][i+k] == 'O'){
							temp++;
						}else if(board[j+k][i+k] == 'X'){
							temp = 0;
							break;
						}
					}else if(j == 3){
						if(board[j+k][i+k] == 'O'){
							temp++;
						}else if(board[j+k][i+k] == 'X'){
							temp = 0;
							break;
						}	
					}else{
						if(board[j-k][i+k] == 'O'){
							temp++;
						}else if(board[j-k][i+k] == 'X'){
							temp = 0;
							break;
						}
					}
				}
				result1 += Math.pow(10, temp);
				temp = 0;
			}
		}
		
		
		for(int i = 0; i < 3; i++){
			for(int j = 3; j == 3; j++){
				for(int k = 0; k < 4; k++){
					if(board[j-k][i+k] == 'O'){
						temp++;
					}else if(board[j-k][i+k] == 'X'){
						temp = 0;
						break;
					}	
				}
				result1 += Math.pow(10, temp);
				temp = 0;
			}
		}
		
		
		//horizontal para user
				for(int i = 0; i < 6; i++){
					for(int j = 0; j < 4; j++){
						for(int k = 0; k < 4; k++){
							if(board[j+k][i] == 'X'){
								temp++;
							}else if(board[j+k][i] == 'O'){
								temp = 0;
								break;
							}
						}
						result2 += Math.pow(10, temp);
						temp = 0;
					}
				}
				
				//vertical para user
				for(int i = 0; i < 7; i++){
					for(int j = 0; j < 3; j++){
						for(int k = 0; k < 4; k++){
							if(board[i][j+k] == 'X'){
								temp++;
							}else if(board[i][j+k] == 'O'){
								temp = 0;
								break;
							}
						}
						result2 += Math.pow(10, temp);
						temp = 0;
					}
				}
				
				//diagonal para user
				for(int i = 0; i < 3; i++){
					for(int j = 0; j < 7; j++){
						for(int k = 0; k < 4; k++){
							if(j < 3){
								if(board[j+k][i+k] == 'X'){
									temp++;
								}else if(board[j+k][i+k] == 'O'){
									temp = 0;
									break;
								}
							}else if(j == 3){
								if(board[j+k][i+k] == 'X'){
									temp++;
								}else if(board[j+k][i+k] == 'O'){
									temp = 0;
									break;
								}	
							}else{
								if(board[j-k][i+k] == 'X'){
									temp++;
								}else if(board[j-k][i+k] == 'O'){
									temp = 0;
									break;
								}
							}
						}
						result2 += Math.pow(10, temp);
						temp = 0;
					}
				}
				
				
				for(int i = 0; i < 3; i++){
					for(int j = 3; j == 3; j++){
						for(int k = 0; k < 4; k++){
							if(board[j-k][i+k] == 'X'){
								temp++;
							}else if(board[j-k][i+k] == 'O'){
								temp = 0;
								break;
							}	
						}
						result2 += Math.pow(10, temp);
						temp = 0;
					}
				}
				
		
		result = result1 - result2;
		return result;
	}
	
	private Board minmax(List<Board> list, int hardness){
		int temp = 0;
		for(int i = list.size()-1; list.get(i).depth==hardness; i--){  //USA PROFUNDIDADE 6
			list.get(i).minmax = list.get(i).heuristic;
		}
		
		for(int i = list.size()-1; i > 0; i--){
			for(int j = i; list.get(i).depth==list.get(j).depth; j--){
				temp = j;
				list.get(j).father.minmax = list.get(j).minmax;
			}
			while(i >= temp){
				if(list.get(i).father.turn.equals("player")){
					if(list.get(i).minmax < list.get(i).father.minmax){
						list.get(i).father.minmax = list.get(i).minmax;
					}
				}else{
					if(list.get(i).minmax > list.get(i).father.minmax){
						list.get(i).father.minmax = list.get(i).minmax;
					}
				}
				if(i == temp)
					break;
				i--;
			}
		}
		
		int minmax = list.get(0).minmax;
		int q = 1;
		int p = 0;
		while(list.get(q).depth == 1){
			if(list.get(q).minmax == minmax)
				p = q;
			if(list.get(q).heuristic == minmax){
				return list.get(q);
			}
			q++;
		}

		return list.get(p);
	}
	
	public static String toString(char[][] array){
		char temp;
		String toConcat = new String();
		String str = new String();
		for(int k = 5; k >= 0; k--){
			toConcat = "|";
			for(int z = 0; z < 7; z++){
				if(array[z][k] == '\0')
					temp = '_';
				else
					temp = array[z][k];
				toConcat += temp + "|";
			}
			str += toConcat + "\n";
		}
		return str;
	}
	
	
	public Board solve(Board actual, int hardness){
		List<Board> tree = new ArrayList<Board>();
		actual.depth = 0;
		tree.add(actual);
		
		for(int i = 0 ; i < tree.size(); i++){
			actual = tree.get(i);
			if(actual.depth == hardness){ // USA PROFUNDIDADE DE 6
				//calcula heuristica
				actual.heuristic = heuristic(actual.state);
				//System.out.println(actual.heuristic);
			}
			else{
				if(alreadyFourCheck(actual.state)){
					if(actual.turn.equals("player")){
						actual.heuristic = 99999;
						actual.minmax = 99999;
					}else{
						actual.heuristic = -99999;
						actual.minmax = -99999;
					}
				}else{
					tree.addAll(children(actual));
				}
			}
		}
		
		actual = minmax(tree, hardness);
		
		return actual;
	}	
	
}
