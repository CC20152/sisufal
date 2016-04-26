package cc20152.sisufal.models;
import java.util.ArrayList;

public class GrupoProjeto {

	private int id;

	private ArrayList<Servidor> listaServidores;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ArrayList<Servidor> getListaServidores() {
            return listaServidores;
        }

        public void setListaServidores(ArrayList<Servidor> listaServidores) {
            this.listaServidores = listaServidores;
        }

}
