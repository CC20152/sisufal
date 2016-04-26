package cc20152.sisufal.models;
import java.util.ArrayList;

public class BancaTCC {

	private Integer id;

	private ArrayList<Servidor> listaServidores;

	private ArrayList<Discente> listaDiscentes;

	private ArrayList<Convidado> listaConvidados;

	public void setId(Integer id) {
            this.id = id;
	}

	public Integer getId() {
            return id;
	}

	public void setListaServidores(ArrayList<Servidor> listaServidores) {
            this.listaServidores = listaServidores;
	}

	public ArrayList<Servidor> getListaServidores() {
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

	public ArrayList getListaConvidados() {
            return listaConvidados;
	}

}
