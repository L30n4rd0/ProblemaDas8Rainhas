import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.sql.rowset.spi.TransactionalWriter;

public class EA_8_rainhas extends EA<TabuleiroDeRainhas> {
	private Random random = new Random();
	private double PM = 1d/16d;
	
	protected List<TabuleiroDeRainhas> selecionarSobreviventes(List<TabuleiroDeRainhas> pop, TabuleiroDeRainhas[] filhos) {
		
		pop.add(filhos[0]);
		pop.add(filhos[1]);
		
		Collections.sort(pop);
		
		pop.remove(pop.size() - 1);
		pop.remove(pop.size() - 1);
		
		return pop;
	}

	protected TabuleiroDeRainhas[] recombinar(TabuleiroDeRainhas[] pais) throws Exception {
		TabuleiroDeRainhas[] filhos = new TabuleiroDeRainhas[2];
		int pontoDeCrossover = random.nextInt(6) + 1;

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
		for (int i = pontoDeCrossover; i < 8; i++){
			if(!filho1.getRainhas().contains(pai2.getRainha(i))){
				filho1.add(pai2.getRainha(i));
			}

			if(!filho2.getRainhas().contains(pai1.getRainha(i))){
				filho2.add(pai1.getRainha(i));
			}
		}

		/*
		 * Volta para o lado esquerdo do ponto do crossover para preencher as
		 * lacunas deixadas por eventuais rainhas repetidas no loop acima 
		 */
		if(filho1.getRainhas().size() < 8) {
			int paiIndex = 0;
			
			do{
				if(!filho1.getRainhas().contains(pai2.getRainha(paiIndex))){
					filho1.add(pai2.getRainha(paiIndex));
				}
				
				paiIndex++;
			} while(filho1.getRainhas().size() < 8);
		}
		
		if(filho2.getRainhas().size() < 8) {
			int paiIndex = 0;
			
			do{
				if(!filho2.getRainhas().contains(pai1.getRainha(paiIndex))){
					filho2.add(pai1.getRainha(paiIndex));
				}
				
				paiIndex++;
			} while(filho2.getRainhas().size() < 8);
		}

		filhos[0] = filho1;
		filhos[1] = filho2;

		return filhos;
	}

	protected TabuleiroDeRainhas executarMut(TabuleiroDeRainhas filho) throws Exception {
		// realizar a mutação no tabuleiro, ou seja, alterar a posição de algumas rainhas
		TabuleiroDeRainhas filhoComMutacao = filho;
		
		if(Math.random() < PM) {
			int rainhaParaSubstituicao = random.nextInt(8);
			
			while(true) {
				int newX = random.nextInt(8);
				int newY = random.nextInt(8);
				Rainha rainhaMutada = new Rainha(newX, newY);
				
				if(!filho.getRainhas().contains(rainhaMutada)) {
					filho.getRainhas().remove(rainhaParaSubstituicao);
					filho.add(rainhaMutada);
					break;
				}
			}
		}
			
		return filhoComMutacao;
	}

	protected TabuleiroDeRainhas[] selecionarPais(List<TabuleiroDeRainhas> pop) {
		TabuleiroDeRainhas[] paisSelecionados = new TabuleiroDeRainhas[2];		
		List<TabuleiroDeRainhas> possiveisPais = new ArrayList<>();
		
		while (possiveisPais.size() < 5) {
			possiveisPais.add(pop.get(random.nextInt(100)));
		}
		
		Collections.sort(possiveisPais);
		
		paisSelecionados[0] = possiveisPais.get(0);
		paisSelecionados[1] = possiveisPais.get(1);
		
		return paisSelecionados;
	}
	
	protected boolean parar(List<TabuleiroDeRainhas> pop) {
		Collections.sort(pop);
		
		if (pop.get(0).getPontuacao() == 0) {
			System.out.println("Pontuação Ótima Encontrada!");
			return true;
		} else {
			System.out.println("Chegando lá... Pontuação melhor: " + pop.get(0).getPontuacao());
			return false;
		}
	}

	protected void avaliar(TabuleiroDeRainhas tabuleiro) {
		int pontuacao = 0;
		
		for (int i = 0; i < tabuleiro.getRainhas().size(); i++){
			for (int j = i + 1; j < tabuleiro.getRainhas().size(); j++) {
				Rainha rainha1 = tabuleiro.getRainha(i);
				Rainha rainha2 = tabuleiro.getRainha(j);
				
				if (rainha1.x == rainha2.x) pontuacao++;
				
				// verificar ataques de rainhas pela coluna
				if (rainha1.y == rainha2.y) pontuacao++;
				
				// verificar ataques de rainhas pela vertical de 45 graus em relacao ao eixo X
				if (Math.abs(rainha1.x - rainha1.y) == Math.abs(rainha2.x - rainha2.y)) pontuacao++;
				
				// verificar ataques de rainhas pela vertical de 135 graus em relacao ao eixo X
				if (Math.abs(rainha1.x + rainha1.y) == Math.abs(rainha2.x + rainha2.y)) pontuacao++;
			}
		}
		
		tabuleiro.setPontuacao(pontuacao);
	}
	
	protected List<TabuleiroDeRainhas> inicializar() throws Exception {
		List<TabuleiroDeRainhas> tabuleiros = new ArrayList<>();
		
		for (int i = 0; i < 100; i++) {
			TabuleiroDeRainhas tabuleiro = new TabuleiroDeRainhas();
			
			while(tabuleiro.getRainhas().size() < 8) {
				Rainha rainha = new Rainha(random.nextInt(8), random.nextInt(8));
				if(!tabuleiro.getRainhas().contains(rainha)) {
					tabuleiro.add(rainha);
				}
			}
			
			tabuleiros.add(tabuleiro);
		}
		
		return tabuleiros;

	}
	
	public static void main(String[] args) throws Exception {
		EA<TabuleiroDeRainhas> ea = new EA_8_rainhas();
		ea.executor();
	}
}