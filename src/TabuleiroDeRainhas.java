import java.util.ArrayList;
import java.util.List;

public class TabuleiroDeRainhas implements Comparable<TabuleiroDeRainhas> {
	
	private List<Rainha> rainhas = new ArrayList<Rainha>();
	private int pontuacao;
	
	public void add(Rainha p){
		rainhas.add(p);
	}
	
	public Rainha getRainha(int posicao) {
		return rainhas.get(posicao);
	}
	
	public int getPontuacao() {
		return pontuacao;
	}
	
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public List<Rainha> getRainhas() {
		return rainhas;
	}

	@Override
	public int compareTo(TabuleiroDeRainhas outroTabuleiro) {
		// TODO Auto-generated method stub
	 
        if (this.getPontuacao() < outroTabuleiro.getPontuacao()) {
            return -1;
        }
        if (this.getPontuacao() > outroTabuleiro.getPontuacao()) {
            return 1;
        }
        return 0;
	}
}
