import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EA_8_rainhas extends EA<TabuleiroDeRainhas> {
	
	protected List<TabuleiroDeRainhas> selecionarSobreviventes(List<TabuleiroDeRainhas> pop, TabuleiroDeRainhas[] filhos) {
		
		pop.add(filhos[0]);
		pop.add(filhos[1]);
		
		Collections.sort(pop);
		
		pop.remove(pop.size() - 1);
		pop.remove(pop.size() - 1);
		
//		for (int i = 0; i < pop.size(); i++) {
//			System.out.println(pop.get(i).getPontuacao());
//			
//		}
		
		System.out.println("selecionarSobreviventes");
		
		return pop;
	}

	protected TabuleiroDeRainhas[] recombinar(TabuleiroDeRainhas[] pais) {
		
		// realizar um crossover para gerar 2 filhos a partir de 2 pais recebidos no vetor em parâmetro
		TabuleiroDeRainhas[] filhos = new TabuleiroDeRainhas[2];
		int pontoDeCrossover = new Random().nextInt(6) + 1;
		
		TabuleiroDeRainhas filho1 = pais[0];
		TabuleiroDeRainhas filho2 = pais[1];
		
		//Coloca as rainhas do lado direito do ponto de crossover nos filhos 
		for (int i = pontoDeCrossover; i < 8; i++){
			filho1.getRainhas().set(i, pais[1].getRainha(i));
			filho2.getRainhas().set(i, pais[0].getRainha(i));
		}
		
		filhos[0] = filho1;
		filhos[1] = filho2;
		
		/*
		 * Coloca as rainhas do lado direito do ponto de crossover nos filhos (trocando os pais).
		 * Como uma rainha do lado esquerdo do pai2 pode ter as mesmas cordenadas de alguma rainha
		 * do pai 1, é necessário fazer o filtro.
		 */
//		for (int i = pontoDeCrossover; i < 8 - pontoDeCrossover; i++){
//			if(!filho1.getRainhas().contains(pai2.getRainha(i))){
//				filho1.add(pai2.getRainha(i));
//			}
//			
//			if(!filho2.getRainhas().contains(pai1.getRainha(i))){
//				filho2.add(pai1.getRainha(i));
//			}
//		}
//		
//		/*
//		 * Volta para o lado direito do ponto do crossover para preencher as
//		 * lacunas deixadas por eventuais rainhas repetidas no loop acima 
//		 */
//		for (int i = 0; i < pontoDeCrossover; i++){
//			if(!filho1.getRainhas().contains(pai2.getRainha(i))){
//				filho1.add(pai2.getRainha(i));
//			}
//			
//			if(!filho2.getRainhas().contains(pai1.getRainha(i))){
//				filho2.add(pai1.getRainha(i));
//			}
//		}
		
		//System.out.println("recombinar");
		return filhos;
	}

	protected TabuleiroDeRainhas executarMut(TabuleiroDeRainhas tabuleiro) {
		
		// realizar a multação no tabuleiro, ou seja, alterar a posição de algumas rainhas
		TabuleiroDeRainhas tabuleiroResult = tabuleiro;
		
		int rainhaParaMutacao = new Random().nextInt(8),
		newX = new Random().nextInt(8),
		newY = new Random().nextInt(8);
		
		while(
				(newX = new Random().nextInt(8)) == tabuleiro.getRainhas().get(rainhaParaMutacao).getX() &&
				(newY = new Random().nextInt(8)) == tabuleiro.getRainhas().get(rainhaParaMutacao).getY()
			)
		
		tabuleiro.getRainhas().get(rainhaParaMutacao).setX(newX);
		tabuleiro.getRainhas().get(rainhaParaMutacao).setY(newY);
		
		//System.out.println("executarMut");
		return tabuleiroResult;
	}

	protected TabuleiroDeRainhas[] selecionarPais(List<TabuleiroDeRainhas> pop) {
		Collections.sort(pop);
		
		TabuleiroDeRainhas[] result = new TabuleiroDeRainhas[2];
		
		List<TabuleiroDeRainhas> possiveisPais = new ArrayList<>();
		
		while (possiveisPais.size() < 3) {
			possiveisPais.add(pop.get(new Random().nextInt(10)));
			
		}
		
		while(possiveisPais.size() < 5) {
			int randomNum = new Random().nextInt(pop.size());
			
			possiveisPais.add(pop.get(randomNum));
			
			// *Se colocar o código abaixo,então não funciona. A execução entra em loop infinito depois de umas 200 gerações de indivíduos
			
//			if (!possiveisPais.contains(pop.get(randomNum))) {
//				possiveisPais.add(pop.get(randomNum));
//			}
			
		}
		
		Collections.sort(possiveisPais);
		
		result[0] = possiveisPais.get(0);
		result[1] = possiveisPais.get(1);
		
		//System.out.println("selecionarPais");
		return result;
	}
	
	protected boolean parar(List<TabuleiroDeRainhas> pop) {
		//System.out.println("parar");
		
		Collections.sort(pop);
		
		if (pop.get(0).getPontuacao() == 0)
			return true;
		
		else {
			return false;
		}
	}

	protected void avaliar(TabuleiroDeRainhas tabuleiro) {
		int pontuacao = 0;
		
		for (Rainha rainha1 : tabuleiro.getRainhas()) {
			for (Rainha rainha2 : tabuleiro.getRainhas()) {
				if (!rainha1.equals(rainha2)) {
					// verificar ataques de rainhas pela linha
					if (rainha1.x == rainha2.x) pontuacao++;
					
					// verificar ataques de rainhas pela coluna
					if (rainha1.y == rainha2.y) pontuacao++;
					
					// verificar ataques de rainhas pela vertical de 45 graus em relacao ao eixo X
					if (Math.abs(rainha1.x - rainha1.y) == Math.abs(rainha2.x - rainha2.y)) pontuacao++;
					
					// verificar ataques de rainhas pela vertical de 135 graus em relacao ao eixo X
					if (Math.abs(rainha1.x + rainha1.y) == Math.abs(rainha2.x + rainha2.y)) pontuacao++;
				}
			}
		}
		tabuleiro.setPontuacao(pontuacao);
		//System.out.println("avaliar");
	}
	
	protected List<TabuleiroDeRainhas> inicializar() {

		List<TabuleiroDeRainhas> tabuleiros = new ArrayList<>();
		
		for (int j = 0; j < 100; j++) {
			TabuleiroDeRainhas tabuleiro = new TabuleiroDeRainhas();
			
			for (int i = 0; i < 8; i++) {
				tabuleiro.add(new Rainha(new Random().nextInt(8), new Random().nextInt(8)));
			}
			tabuleiros.add(tabuleiro);
		}
		
		//System.out.println("inicializar");
		return tabuleiros;

	}
	
	public static void main(String[] args) {
		EA<TabuleiroDeRainhas> ea = new EA_8_rainhas();
		ea.executor();
	}
}