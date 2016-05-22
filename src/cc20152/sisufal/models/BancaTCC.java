package cc20152.sisufal.models;
import java.util.ArrayList;

public class BancaTCC {

	private Integer id;

	private ArrayList<Orientador> listaServidores;

	private ArrayList<Discente> listaDiscentes;

	private ArrayList<Convidado> listaConvidados;
        
        
        public BancaTCC(){
            listaServidores = new ArrayList<>();
            listaDiscentes = new ArrayList<>();
            listaConvidados = new ArrayList<>();
        }
	public void setId(Integer id) {
            this.id = id;
	}

	public Integer getId() {
            return id;
	}

	public void setListaServidores(ArrayList<Orientador> listaServidores) {
            this.listaServidores = listaServidores;
	}

	public ArrayList<Orientador> getListaServidores() {
            return listaServidores;
	}

	public void setListaDiscentes(ArrayList<Discente>  listaDiscentes) {
            this.listaDiscentes = listaDiscentes;
	}

	public ArrayList<Discente> getListaDiscentes() {
            return listaDiscentes;
	}

	public void setListaConvidados(ArrayList<Convidado> listaConvidados) {
            this.listaConvidados = listaConvidados;
	}

	public ArrayList<Convidado> getListaConvidados() {
            return listaConvidados;
	}

}
