package cc20152.sisufal.models;
import java.util.Date;

public class Movimentacao {

	private Integer id;
    private Integer idPatrimonio;
    private Integer idSalaDestino;
	private Date data;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSala() {
            return idSalaDestino;
        }

        public void setSala(Integer idSalaDestino) {
            this.idSalaDestino = idSalaDestino;
        }

        public Integer getPatrimonio() {
            return idPatrimonio;
        }

        public void setPatrimonio(Integer idPatrimonio) {
            this.idPatrimonio = idPatrimonio;
        }

        public Date getData() {
            return data;
        }

        public void setData(Date data) {
            this.data = data;
        }

	
}
