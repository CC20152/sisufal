package cc20152.sisufal.models;
import java.util.ArrayList;

public class Sala {

	private Integer id;
	private String nome;
	private String codigo;
    private Integer id_bloco;
    private ArrayList<Patrimonio> patrimonios;
       
	
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public Integer getBloco() {
            return id_bloco;
        }

        public void setBloco(Integer id_bloco) {
            this.id_bloco = id_bloco;
        }

        @Override
        public String toString() {
            return this.nome;
        }

}
