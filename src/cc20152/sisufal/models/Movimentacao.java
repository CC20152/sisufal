package cc20152.sisufal.models;
import java.util.Date;

public class Movimentacao {

	private Integer id;
	private int nomeSala;
	private int numeroSala;
	private int idSala;
	private int nomeBloco;
	private int numeroBloco;
	private int idBloco;
    private Sala sala;
    private Bloco bloco = sala.getBloco();
	private Date data;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Sala getSala() {
            return sala;
        }

        public void setSala(Sala sala) {
            this.sala = sala;
        }

        public Bloco getBloco() {
            return bloco;
        }

        public void setBloco(Bloco bloco) {
            this.bloco = bloco;
        }

        public int getNomeSala() {
            return nomeSala;
        }

        public void setNomeSala(int nomeSala) {
            this.nomeSala = nomeSala;
        }

        public int getNumeroSala() {
            return numeroSala;
        }

        public void setNumeroSala(int numeroSala) {
            this.numeroSala = numeroSala;
        }

        public int getIdSala() {
            return idSala;
        }

        public void setIdSala(int idSala) {
            this.idSala = idSala;
        }

        public int getNomeBloco() {
            return nomeBloco;
        }

        public void setNomeBloco(int nomeBloco) {
            this.nomeBloco = nomeBloco;
        }

        public int getNumeroBloco() {
            return numeroBloco;
        }

        public void setNumeroBloco(int numeroBloco) {
            this.numeroBloco = numeroBloco;
        }

        public int getIdBloco() {
            return idBloco;
        }

        public void setIdBloco(int idBloco) {
            this.idBloco = idBloco;
        }

        public Date getData() {
            return data;
        }

        public void setData(Date data) {
            this.data = data;
        }

	
}
