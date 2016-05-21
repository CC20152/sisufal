package cc20152.sisufal.models;
import java.util.Date;

public class Projeto {

    private int id;

    private String titulo;

    private Date dataInicio;

    private Date dataFim;

    private String tipo;
    
    private Servidor servidorCoordenador;

    private InstituicaoFinanciadora financiadora;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Servidor getServidorCoordenador() {
        return servidorCoordenador;
    }

    public void setServidorCoordenador(Servidor servidorCoordenador) {
        this.servidorCoordenador = servidorCoordenador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public InstituicaoFinanciadora getFinanciadora() {
        return financiadora;
    }

    public void setFinanciadora(InstituicaoFinanciadora financiadora) {
        this.financiadora = financiadora;
    }

    public String toString() {
        return this.titulo;
    }
}
