package jogodavelha;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

//MouseListener: É uma interface que possui métodos para monitorar as ações do mouse

public class Painel extends JPanel implements MouseListener {
	private Font minhafonteGrande = new Font("Consolas", Font.BOLD, 40);
	private Font minhaFonteMedia = new Font("Consolas", Font.BOLD, 30);
	private Font minhaFontepequena = new Font("Consolas", Font.BOLD, 15);
	// Tabuleiro do jogo
	private int matriz[][] = new int[3][3];
	private Integer jogador = 1;
	private Integer ganhador = 0;
	private Boolean jogarNov = false;

	private Integer vitorias1 = 0;
	private Integer vitorias2 = 0;
	private Integer empates = 0;

	Color cor1 = new Color(0, 160, 0);

	// Aqui será desenhado o jogo
	@Override
	public void paintComponent(Graphics g2) {

		Graphics2D g = (Graphics2D) g2.create();

		if (jogarNov) {
			Integer jogarNovamente = new JOptionPane().showConfirmDialog(this, "Deseja jogar novamente? ");

			if (jogarNovamente == JOptionPane.OK_OPTION) {
				jogarNov = false;
				reiniciarJogo();
			} else {
				System.exit(1);
			}

		}
		g.setStroke(new BasicStroke(5));// Deixa as linhas mais grossas
		g.setFont(minhafonteGrande);

		g.setColor(Color.white);
		g.fillRect(0, 0, 600, 600);// X, Y, width e height do retângulo

		// Linhas horizontas

		g.setColor(Color.BLUE);
		g.drawLine(0, 200, 600, 200);// x1, y1, x2, y2
		g.drawLine(0, 400, 600, 400);

		// linhas verticais
		g.drawLine(200, 0, 200, 600);
		g.drawLine(400, 0, 400, 600);

		// Lógica:
		// Quando for 1 dentro da matriz vai desenhar um x, quando for 2 é circulo e
		// quando não tiver nada é vazio
		// Percorre a matriz na e desenha, toda vez e desenha um X ou O conforme a
		// condição
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				if (matriz[linha][coluna] == 1) {
					g.setColor(Color.GREEN);
					g.drawString("o", 75 + coluna * 200, 100 + linha * 200);
				} else if (matriz[linha][coluna] == 2) {
					g.setColor(Color.RED);
					g.drawString("x", 75 + coluna * 200, 100 + linha * 200);
				}
			}
		}
		if (ganhador != 0) {
			g.setFont(minhaFonteMedia);
			if (ganhador == 3) {
				g.setColor(Color.BLACK);
				g.drawString("O jogo empatou!", 180, 180);
			} else {

				if (ganhador == 1) {
					g.setColor(cor1);
				} else if (ganhador == 2) {
					g.setColor(Color.RED);
				}

				g.setFont(minhaFonteMedia);
				g.drawString("O jogador " + ganhador + " venceu", 130, 200);
			}
			jogarNov = true;

			repaint();
		}
		g.setFont(minhaFontepequena);
		g.setColor(cor1);
		g.drawString("Vitórias " + vitorias1, 50, 20);

		g.setColor(Color.RED);
		g.drawString("Vitórias " + vitorias2, 450, 20);
		
		g.setColor(Color.BLACK);
		g.drawString("Empates: " + empates, 250, 20);

	}

	// Implementação dos métodos da interface MouseListener
	@Override
	public void mouseClicked(MouseEvent e) {
		// Pegar a linha e a coluna do click
		Integer linha = e.getY() / 200;
		Integer coluna = e.getX() / 200;
		
		//Esses ifs foram criados pois se clicar muito no canto da um erro,
		//pois considera a coluna tres e a mesma não existe.
		if(linha == 3) {
			linha = 2;
		}
		if(coluna == 3) {
			coluna = 2;
		}

		if (jogador == 1 && matriz[linha][coluna] == 0) {
			matriz[linha][coluna] = 1;
			jogador = 2;
		} else if (jogador == 2 && matriz[linha][coluna] == 0) {
			matriz[linha][coluna] = 2;
			jogador = 1;
		}
		verificaGanhador();

		repaint();// Chama o paintCompany e atualiza o desenho

	}

	public void reiniciarJogo() {

		// reinicia a matriz
		for (int linha = 0; linha < 3; linha++) {
			for (int coluna = 0; coluna < 3; coluna++) {
				matriz[linha][coluna] = 0;
				ganhador = 0;
			}
		}
	}

	private void verificaGanhador() {
		for (int linha = 0; linha < 3; linha++) {
			if (matriz[linha][0] == matriz[linha][1] && matriz[linha][0] == matriz[linha][2] && matriz[linha][0] != 0) {
				System.out.println("Houve ganhador");
				ganhador = matriz[linha][0];
				break;
			}
		}

		for (int coluna = 0; coluna < 3; coluna++) {
			if (matriz[0][coluna] == matriz[1][coluna] && matriz[0][coluna] == matriz[2][coluna]
					&& matriz[0][coluna] != 0) {
				System.out.println("Houve ganhador");
				ganhador = matriz[0][coluna];
				break;
			}
		}

		// diagonal principal
		if (matriz[0][0] == matriz[1][1] && matriz[0][0] == matriz[2][2] && matriz[0][0] != 0) {
			System.out.println("Houve ganhador");
			ganhador = matriz[0][0];
		}
		// diagonal da direita
		if (matriz[0][2] == matriz[1][1] && matriz[0][2] == matriz[2][0] && matriz[0][2] != 0) {
			System.out.println("Houve ganhador");
			ganhador = matriz[0][2];
		}

		if (ganhador == 1) {
			vitorias1++;
		} else if (ganhador == 2) {
			vitorias2++;
		} else {
			boolean cheia = true;
			for (int linha = 0; linha < 3; linha++) {
				for (int coluna = 0; coluna < 3; coluna++) {
					if (matriz[linha][coluna] == 0) {
						cheia = false;
					}
				}
			}
			if (cheia) {
				ganhador = 3;// Empate
				empates++;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
