package cc20152.sisufal.models;
import java.util.ArrayList;

public class BolsaProjeto {

	private Integer id;

	private ArrayList<Discente> listaDiscentes;

	public void setId(Integer id) {
            this.id = id;
	}

	public Integer getId() {
		return id;
	}

        public ArrayList<Discente> getListaDiscentes() {
            return listaDiscentes;
        }

        public void setListaDiscentes(ArrayList<Discente> listaDiscentes) {
            this.listaDiscentes = listaDiscentes;
        }
        
        
}
