
import java.util.*;

public class Main {
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		int firstPlay;
		int hardness;
		Board actual;
		System.out.println("Jogo - Quatro em Linha");
		System.out.print("Insira dificuldade entre 1 e 6: ");
		hardness = sc.nextInt();
		while(hardness < 1 || hardness > 6){
			System.out.print("!!! IMPOSSIVEL !!!\nInsira dificuldade entre 1 e 6: ");
			hardness = sc.nextInt();
		}
		System.out.println("1 - Jogador\n2 - Computador");
		System.out.print("Quem joga primeiro: ");
		firstPlay = sc.nextInt();
		while(firstPlay != 1 && firstPlay != 2){
			System.out.print("!!! IMPOSSIVEL !!!\nQuem joga primeiro: ");
			firstPlay = sc.nextInt();
		}
		int column;
		char[][] start = new char[7][6];
		if(firstPlay == 1){
			actual = new Board (start, null, "Player");
		}else{
			actual = new Board (start, null, "AI");
		}
		
			
		 //who starts first? - "player" or "AI"
		System.out.println(toString(actual.getState()));
		
		while(!actual.alreadyFourCheck(actual.getState())){
			if(actual.getTurn().equals("AI")){
				if(actual.fullBoard(actual.getState())){
					System.out.println("EMPATE");
					break;
				}
				System.out.println("O - Computador - O");
				actual = actual.solve(actual, hardness);
				System.out.println(toString(actual.getState()));
				//System.out.println(actual.getMinMax());
			}
			else{
				// PLAYER TURN
				if(actual.fullBoard(actual.getState()))
					break;
				char[][] temporary = actual.getState();
				System.out.println("X - Jogador - X");
				System.out.print("Inserir na coluna: ");
				column = sc.nextInt() - 1;
				while(column < 0 || column > 6 || temporary[column][5] != '\0'){
					System.out.print("!!!Impossivel!!!\nInserir na coluna: ");
					column = sc.nextInt() - 1;
				}
				for(int i = 0; i < 6; i++){
					if(temporary[column][i] == '\0'){
						temporary[column][i] = 'X';
						break;
					}
				}
				actual = new Board(temporary, null, "AI");
				System.out.println(toString(actual.getState()));
			}
		}
		
		if(actual.fullBoard(actual.getState())){
			System.out.println("EMPATE");
		}else{
			if(actual.getTurn().equals("AI"))
				System.out.println("PARABÉNS, GANHOU");
			else System.out.println("PERDEU");
		}
		
		sc.close();
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
			if(k == 0)
				str += toConcat;
			else str += toConcat + "\n";
		}
		str += "\n 1 2 3 4 5 6 7\n";
		return str;
	}
	
}
