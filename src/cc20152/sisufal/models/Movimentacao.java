package cc20152.sisufal.models;
import java.util.Date;

public class Movimentacao {

	private Integer id;
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

        public Date getData() {
            return data;
        }

        public void setData(Date data) {
            this.data = data;
        }

	
}
