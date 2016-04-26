package cc20152.sisufal.models;
import java.util.ArrayList;

public class BancaConcurso {

	private Integer id;

	private ArrayList<Servidor> listaServidores;

	public void setId(Integer id) {
            this.id = id;
	}

	public Integer getId() {
            return id;
	}

	public ArrayList<Servidor> getListaServidores() {
            return listaServidores;
	}

	public void setListaServidores(ArrayList<Servidor> listaServidores) {
            this.listaServidores = listaServidores;
	}

}
