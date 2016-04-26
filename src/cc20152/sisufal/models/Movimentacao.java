package cc20152.sisufal.models;
import java.util.Date;

public class Movimentacao {

	private int id;

	private int nomeSala;

	private int codigoSala;

	private int idSala;

	private int nomeBloco;

	private int codigoBloco;

	private int idBloco;

	private Date data;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNomeSala() {
            return nomeSala;
        }

        public void setNomeSala(int nomeSala) {
            this.nomeSala = nomeSala;
        }

        public int getCodigoSala() {
            return codigoSala;
        }

        public void setCodigoSala(int codigoSala) {
            this.codigoSala = codigoSala;
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

        public int getCodigoBloco() {
            return codigoBloco;
        }

        public void setCodigoBloco(int codigoBloco) {
            this.codigoBloco = codigoBloco;
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
