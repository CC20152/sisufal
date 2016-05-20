package cc20152.sisufal.models;
import java.util.Date;

public class Movimentacao {

	private Integer id;
    private Integer id_patrimonio;
    private Integer id_sala_destino;
	private Date data;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getSala() {
            return id_sala_destino;
        }

        public void setSala(Integer id_sala_destino) {
            this.id_sala_destino = id_sala_destino;
        }

        public Integer getPatrimonio() {
            return patrimonio;
        }

        public void setPatrimonio(Integer patrimonio) {
            this.patrimonio = patrimonio;
        }

        public Date getData() {
            return data;
        }

        public void setData(Date data) {
            this.data = data;
        }

	
}
