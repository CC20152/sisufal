package cc20152.sisufal.models;
public class Servidor {

	private int id;

	private String nome;

	private String siape;

	private String cargo;

	private String CPF;

	private ClasseDocente classe = new ClasseDocente();
        
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getSiape() {
            return siape;
        }

        public void setSiape(String siape) {
            this.siape = siape;
        }

        public String getCargo() {
            return cargo;
        }

        public void setCargo(String cargo) {
            this.cargo = cargo;
        }

        public String getCPF() {
            return CPF;
        }

        public void setCPF(String CPF) {
            this.CPF = CPF;
        }

        public ClasseDocente getClasse() {
            return classe;
        }

        public void setClasse(ClasseDocente classe) {
            this.classe = classe;
        }
        
        @Override
        public String toString() {
                return nome;
        }
        
}
