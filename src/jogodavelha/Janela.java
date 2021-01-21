package jogodavelha;

import javax.swing.JFrame;

public class Janela {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Jogo da velha");//Cria uma janela
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);//Fica centralizada
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Na opera��o de fechar, vai terminar o programa
		frame.setVisible(true);//mostrar
		frame.setResizable(false);//N�o maximiza
		
		Painel painel = new Painel();
		painel.setBounds(0, 0, 600, 630);//Define a posi��o e o tamanho do painel
		frame.add(painel);
		
		frame.addMouseListener(painel);
	}

}
