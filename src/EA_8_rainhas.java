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
		
		TabuleiroDeRainhas[] result = new TabuleiroDeRainhas[2];
		int i = new Random().nextInt(8);
		
		TabuleiroDeRainhas t = new TabuleiroDeRainhas();
		
		// continuar
		
		return null;
	}

	private TabuleiroDeRainhas executarMut(TabuleiroDeRainhas tabuleiro) {
		
		// realizar a multação no tabuleiro, ou seja, alterar a posição de algumas rainhas
		
		int i, j, rainhaParaMultacao = new Random().nextInt(8);
		
		while((i = new Random().nextInt(8)) == tabuleiro.rainhas.get(rainhaParaMultacao).getX()) continue;
		while((j = new Random().nextInt(8)) == tabuleiro.rainhas.get(rainhaParaMultacao).getX()) continue;
		
		tabuleiro.rainhas.get(rainhaParaMultacao).setX(i);
		tabuleiro.rainhas.get(rainhaParaMultacao).setY(j);
		
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
		return pop.stream().filter(x -> x.pontuacao != 0).count() > 0;
		//fitness 0 ou max
	}

	private void avaliar(TabuleiroDeRainhas tabuleiro) {
		int pontuacao = 0;
		
		for (Rainha rainha1 : tabuleiro.rainhas) {
			for (Rainha rainha2 : tabuleiro.rainhas) {
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
		tabuleiro.pontuacao = pontuacao;
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