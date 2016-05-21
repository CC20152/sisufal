package cc20152.sisufal.models;
import java.util.ArrayList;

public class Bloco {

    private Integer id;
    private String nome;
    private String codigo;
    private ArrayList<Sala> salas;
    
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

        public ArrayList<Sala> getSalas() {
            return salas;
        }

        public void setSalas(ArrayList<Sala> salas) {
            this.salas = salas;
        }
        
        @Override
        public String toString() {
            return this.nome;
        }

    
}