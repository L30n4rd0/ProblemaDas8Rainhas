import java.util.List;

public abstract class EA<S> {
	
	public void executor() throws Exception {
		int execucoes = 0;
		
		List<S> pop = inicializar();
		
		for(S s: pop) {
			avaliar(s);
		}
		
		while(!parar(pop) && execucoes <= 10000) {
			System.out.println("Exec: " + execucoes);
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

	protected List<S> selecionarSobreviventes(List<S> pop, S[] filhos) {
		// TODO Auto-generated method stub
		return null;
	}

	protected S[] recombinar(S[] pais) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected S executarMut(S s) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected S[] selecionarPais(List<S> pop) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean parar(List<S> pop) {
		// TODO Auto-generated method stub
		return false;
	}

	protected void avaliar(S s) {
		// TODO Auto-generated method stub
		
	}

	protected List<S> inicializar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}