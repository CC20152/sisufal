package cc20152.sisufal.models;
import java.util.Date;

public class Concurso {

	private Integer id;
	private String numeroEdital;
	private String areaEstudo;
	private Servidor supervisor;
	private Date dataInicio;
	private Date dataFim;
	private String modalidade;
	private BancaConcurso banca;

        public Concurso(){
            this.banca = new BancaConcurso();
            this.supervisor = new Servidor();
        }
        
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNumeroEdital() {
            return numeroEdital;
        }

        public void setNumeroEdital(String numeroEdital) {
            this.numeroEdital = numeroEdital;
        }

        public String getAreaEstudo() {
            return areaEstudo;
        }

        public void setAreaEstudo(String areaEstudo) {
            this.areaEstudo = areaEstudo;
        }

        public Servidor getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(Servidor supervisor) {
            this.supervisor = supervisor;
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

        public String getModalidade() {
            return modalidade;
        }

        public void setModalidade(String modalidade) {
            this.modalidade = modalidade;
        }

        public BancaConcurso getBanca() {
            return banca;
        }

        public void setBanca(BancaConcurso banca) {
            this.banca = banca;
        }
}
