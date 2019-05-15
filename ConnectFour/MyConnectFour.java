package fourInARow;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyConnectFour {
	private static final char[] players = new char[] { '1', '2' };

	private final int width, height;
	private final char[][] grid;
	private int lastCol = -1, lastTop = -1;

	public MyConnectFour(int width, int height) {
		this.width = width;
		this.height = height;
		this.grid = new char[height][];
		for (int h = 0; h < height; h++) {
			Arrays.fill(this.grid[h] = new char[width], '.');
		}
	}

	public String toString() {
		return IntStream.range(0, this.width).mapToObj(Integer::toString).collect(Collectors.joining()) + "\n"
				+ Arrays.stream(this.grid).map(String::new).collect(Collectors.joining("\n"));
	}

	public static void main(String[] args) {
		int height = 6;
		int width = 8;
		int moves = height * width;

		System.out.println("Starting the game");

		// create the board
		MyConnectFour game = new MyConnectFour(width, height);
		System.out.println(game);

		// start the game
		for (int currentMove = 0, player = 0; currentMove < moves; currentMove++, player = 1 - player) {
			Scanner input = new Scanner(System.in);

			// choose the column and pin the position print the final grid
			game.chooseTheColumnAndPin(players[player], input, game);
			System.out.println(game);
			if (game.checkWillingPosition(game)) {
				System.out.println("player" + player + " is the winner!!");
				return;
			}

		}
		System.out.println("No winners");

	}

	public Boolean checkWillingPosition(MyConnectFour game) {
		char symbol = game.grid[lastTop][lastCol];
		String win = String.format("%c%c%c%c", symbol, symbol, symbol, symbol);
		return (checkVertical(win) || checkhorizontal(win) || checkFirstDiagonal(win) || checkSecondDiagonal(win));
	}

	public boolean checkhorizontal(String win) {
		if (this.grid[lastTop].toString().contains(win)) {

			return true;
		}
		return false;
	}

	public boolean checkFirstDiagonal(String win) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < height; i++) {
			int w = this.lastCol + this.lastTop - i;
			if (w >= 0 && w < 8) {
				sb.append(this.grid[i][w]);
			}
		}

		if (sb.toString().contains(win))
			return true;

		return false;

	}

	public boolean checkSecondDiagonal(String win) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < height; i++) {
			int w = this.lastCol - this.lastTop + i;
			if (w >= 0 && w < 8) {
				sb.append(this.grid[i][w]);
			}
		}

		if (sb.toString().contains(win))
			return true;

		return false;

	}

	public boolean checkVertical(String win) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < height; i++) {

			sb.append(this.grid[i][lastCol]);
		}

		if (sb.toString().contains(win))
			return true;

		return false;
	}

	public void chooseTheColumnAndPin(char symbol, Scanner input, MyConnectFour game) {

		Boolean columnFull = false;

		do {
			System.out.println("Player " + symbol + "'s turn. Choose the column number from 0-7");

			columnFull = game.filltheSelection(symbol, game, input);
			if (columnFull == null) {
				System.out.println("The column is inavlid please select another column");
				continue;
			} else if (columnFull)
				System.out.println("The column is already full please select another column");

		} while (columnFull == Boolean.TRUE || columnFull == null);

	}

	public Boolean filltheSelection(char symbol, MyConnectFour game, Scanner input) {
		int col = input.nextInt();

		while (col >= 0 && col <= 7) {
			for (int i = 5; i >= 0; i--) {
				if (game.grid[i][col] == '.') {
					game.grid[this.lastTop = i][this.lastCol = col] = symbol;
					return false;
				}
			}
			return true;

		}
		return null;
	}

}