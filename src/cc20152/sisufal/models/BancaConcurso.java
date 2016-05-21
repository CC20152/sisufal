package cc20152.sisufal.models;
import java.util.ArrayList;

public class BancaConcurso {

	private Integer id;
        private Concurso concurso;
	private ArrayList<Servidor> listaServidores;

        public BancaConcurso(){
            this.concurso = new Concurso();
            this.listaServidores = new ArrayList<>();
        }
        
        public Concurso getConcurso() {
            return concurso;
        }

        public void setConcurso(Concurso concurso) {
            this.concurso = concurso;
        }
        
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
