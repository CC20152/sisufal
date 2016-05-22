package cc20152.sisufal.models;
import java.util.ArrayList;

public class BolsaProjeto {

    private Integer id;
    private Projeto projeto;
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

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

}
