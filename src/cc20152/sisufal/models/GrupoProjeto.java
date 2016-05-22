package cc20152.sisufal.models;
import java.util.ArrayList;

public class GrupoProjeto {

    private Integer id;
    private Projeto projeto;
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
    
    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

}
