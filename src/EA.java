import java.util.List;

public abstract class EA<S> {
	
	public void executor() {
		int execucoes = 0;
		
		List<S> pop = inicializar();
		
		for(S s: pop) {
			avaliar(s);
		}
		
		while(!parar() || execucoes == 1000) {
			S[] pais = selecionarPais(pop);
			S[] filhos = recombinar(pais);
			
			filhos[0] = executarMut(filhos[0]);
			filhos[1] = executarMut(filhos[1]);
			
			avaliar(filhos[0]);
			avaliar(filhos[1]);
			
			pop = selecionarSobreviventes(pop, filhos);
			execucoes++;
		}
	}

	private List<S> selecionarSobreviventes(List<S> pop, S[] filhos) {
		// TODO Auto-generated method stub
		return null;
	}

	private S[] recombinar(S[] pais) {
		// TODO Auto-generated method stub
		return null;
	}

	private S executarMut(S s) {
		// TODO Auto-generated method stub
		return null;
	}

	private S[] selecionarPais(List<S> pop) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean parar() {
		// TODO Auto-generated method stub
		return false;
	}

	private void avaliar(S s) {
		// TODO Auto-generated method stub
		
	}

	private List<S> inicializar() {
		// TODO Auto-generated method stub
		return null;
	}
}