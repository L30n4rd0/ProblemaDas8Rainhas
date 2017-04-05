import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class EA_8_rainhas extends EA<TabuleiroDeRainhas> {
	
	private List<TabuleiroDeRainhas> selecionarSobreviventes(List<TabuleiroDeRainhas> pop, TabuleiroDeRainhas[] filhos) {
		pop.add(filhos[0]);
		pop.add(filhos[1]);
		
		pop.remove(pop.stream().max(Comparator.comparing(TabuleiroDeRainhas::getPontuacao)).get());
		pop.remove(pop.stream().max(Comparator.comparing(TabuleiroDeRainhas::getPontuacao)).get());
		
		return pop;
	}

	private TabuleiroDeRainhas[] recombinar(TabuleiroDeRainhas[] pais) {
		
		// realizar um crossover para gerar 2 filhos a partir de 2 pais recebidos no vetor em parâmetro
		TabuleiroDeRainhas[] filhos = new TabuleiroDeRainhas[2];
		int pontoDeCrossover = new Random().nextInt(8);
		
		TabuleiroDeRainhas pai1 = pais[0];
		TabuleiroDeRainhas pai2 = pais[1];
		TabuleiroDeRainhas filho1 = new TabuleiroDeRainhas();
		TabuleiroDeRainhas filho2 = new TabuleiroDeRainhas();
		
		//Coloca as rainhas do lado esquerdo do ponto de crossover nos filhos 
		for (int i = 0; i < pontoDeCrossover; i++){
			filho1.add(pai1.getRainha(i));
			filho2.add(pai2.getRainha(i));
		}
		
		/*
		 * Coloca as rainhas do lado direito do ponto de crossover nos filhos (trocando os pais).
		 * Como uma rainha do lado esquerdo do pai2 pode ter as mesmas cordenadas de alguma rainha
		 * do pai 1, é necessário fazer o filtro.
		 */
		for (int i = pontoDeCrossover; i < 8 - pontoDeCrossover; i++){
			if(!filho1.getRainhas().contains(pai2.getRainha(i))){
				filho1.add(pai2.getRainha(i));
			}
			
			if(!filho2.getRainhas().contains(pai1.getRainha(i))){
				filho2.add(pai1.getRainha(i));
			}
		}
		
		/*
		 * Volta para o lado direito do ponto do crossover para preencher as
		 * lacunas deixadas por eventuais rainhas repetidas no loop acima 
		 */
		for (int i = 0; i < pontoDeCrossover; i++){
			if(!filho1.getRainhas().contains(pai2.getRainha(i))){
				filho1.add(pai2.getRainha(i));
			}
			
			if(!filho2.getRainhas().contains(pai1.getRainha(i))){
				filho2.add(pai1.getRainha(i));
			}
		}
		
		return filhos;
	}

	private TabuleiroDeRainhas executarMut(TabuleiroDeRainhas tabuleiro) {
		
		// realizar a multação no tabuleiro, ou seja, alterar a posição de algumas rainhas
		
		int i, j, rainhaParaMutacao = new Random().nextInt(8);
		
		while((i = new Random().nextInt(8)) == tabuleiro.getRainhas().get(rainhaParaMutacao).getX()) continue;
		while((j = new Random().nextInt(8)) == tabuleiro.getRainhas().get(rainhaParaMutacao).getX()) continue;
		
		tabuleiro.getRainhas().get(rainhaParaMutacao).setX(i);
		tabuleiro.getRainhas().get(rainhaParaMutacao).setY(j);
		
		return tabuleiro;
	}

	private TabuleiroDeRainhas[] selecionarPais(List<TabuleiroDeRainhas> pop) {
		TabuleiroDeRainhas[] result = new TabuleiroDeRainhas[2];
		
		List<TabuleiroDeRainhas> possiveisPais = new ArrayList<>();
		possiveisPais.add(pop.get(new Random().nextInt(pop.size())));
		
		while(possiveisPais.size() < 5) {
			int randomNum = new Random().nextInt(pop.size());
			
			if (!possiveisPais.contains(pop.get(randomNum))) {
				possiveisPais.add(pop.get(randomNum));
			}
		}
		
		result[0] = possiveisPais.stream().min(Comparator.comparing(TabuleiroDeRainhas::getPontuacao)).get();
		possiveisPais.remove(result[0]);
		result[1] = possiveisPais.stream().min(Comparator.comparing(TabuleiroDeRainhas::getPontuacao)).get();
		
		return result;
	}
	
	private boolean parar(List<TabuleiroDeRainhas> pop) {
		return pop.stream().filter(x -> x.getPontuacao() != 0).count() > 0;
		//fitness 0 ou max
	}

	private void avaliar(TabuleiroDeRainhas tabuleiro) {
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
	}

	private List<TabuleiroDeRainhas> inicializar() {

		List<TabuleiroDeRainhas> tabuleiros = new ArrayList<>();
		
		for (int j = 0; j < 100; j++) {
			TabuleiroDeRainhas tabuleiro = new TabuleiroDeRainhas();
			
			for (int i = 0; i < 8; i++) {
				tabuleiro.add(new Rainha(new Random().nextInt(8), new Random().nextInt(8)));
			}
			tabuleiros.add(tabuleiro);
		}
		return tabuleiros;
		
		
		// codigo anterior
		
//		List<Posicao> result = new ArrayList();
//		for (int i = 0; i < 100; i++) {
//			inicializarRainhas(result);
//			
//			result.add(new Posicao(new Random().nextInt(8), new Random().nextInt(8)));
//		}
		
//		for (int i = 1; i <= 8; i++) {
//			for (int j = 1; j <= 8; j++) {
//				result.add(new Posicao(i, j));
//			}
//		}
//		inicializarRainhas(result);
	}
	
}