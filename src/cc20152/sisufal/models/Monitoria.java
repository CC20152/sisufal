package cc20152.sisufal.models;
import cc20152.sisufal.models.Disciplina;
import java.util.Date;

public class Monitoria {

	private int id;
	private Disciplina disciplina;
	private Orientador orientador;
	private String sitCertificado;
	private Date dataInicio;
	private Date dataFim;
	private Discente discente;
	private Periodo periodo;

        public Monitoria(){
            this.discente = new Discente();
            this.orientador = new Orientador();
            this.disciplina = new Disciplina();
            this.periodo = new Periodo();
            this.dataInicio = new Date();
            this.dataFim = new Date();
        }
        
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Disciplina getDisciplina() {
            return disciplina;
        }

        public void setDisciplina(Disciplina disciplina) {
            this.disciplina = disciplina;
        }

        public Orientador getOrientador() {
            return orientador;
        }

        public void setOrientador(Orientador orientador) {
            this.orientador = orientador;
        }

        public String getSitCertificado() {
            return sitCertificado;
        }

        public void setSitCertificado(String sitCertificado) {
            this.sitCertificado = sitCertificado;
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

        public Discente getDiscente() {
            return discente;
        }

        public void setDiscente(Discente discente) {
            this.discente = discente;
        }

        public Periodo getPeriodo() {
            return periodo;
        }

        public void setPeriodo(Periodo periodo) {
            this.periodo = periodo;
        }
                
	

}
