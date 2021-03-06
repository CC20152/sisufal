package cc20152.sisufal.models;
import java.util.ArrayList;

public class Sala {

	private Integer id;
	private String nome;
	private String codigo;
        private Bloco bloco;
        
        public Sala(){
            bloco = new Bloco();
        }
	
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

        public Bloco getBloco() {
            return bloco;
        }

        public void setBloco(Bloco bloco) {
            this.bloco = bloco;
        }

        @Override
        public String toString() {
            return this.nome;
        }

}
