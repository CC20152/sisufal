package cc20152.sisufal.models;
import java.util.Date;

public class TCC {

	private int id;

	private Curso curso;

	private Discente discente;

	private Orientador orientador;

	private String titulo;

	private Date dataInicio;

	private Date dataFim;

	private BancaTCC banca;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Curso getCurso() {
            return curso;
        }

        public void setCurso(Curso curso) {
            this.curso = curso;
        }

        public Discente getDiscente() {
            return discente;
        }

        public void setDiscente(Discente discente) {
            this.discente = discente;
        }

        public Orientador getOrientador() {
            return orientador;
        }

        public void setOrientador(Orientador orientador) {
            this.orientador = orientador;
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

        public BancaTCC getBanca() {
            return banca;
        }

        public void setBanca(BancaTCC banca) {
            this.banca = banca;
        }

}
