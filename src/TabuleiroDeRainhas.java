import java.util.ArrayList;
import java.util.List;

public class TabuleiroDeRainhas {
	List<Rainha> rainhas = new ArrayList<Rainha>();
	int pontuacao;
	
	public void add(Rainha p){
		rainhas.add(p);
	}
	
	public int getPontuacao() {
		return pontuacao;
	}
}
