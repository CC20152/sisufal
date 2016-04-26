package cc20152.sisufal.models;
public class Servidor {

	private int id;

	private String nome;

	private String siape;

	private Curso cargo;

	private String periodoIngresso;

	private ClasseDocente classe;

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

        public Curso getCargo() {
            return cargo;
        }

        public void setCargo(Curso cargo) {
            this.cargo = cargo;
        }

        public String getPeriodoIngresso() {
            return periodoIngresso;
        }

        public void setPeriodoIngresso(String periodoIngresso) {
            this.periodoIngresso = periodoIngresso;
        }

        public ClasseDocente getClasse() {
            return classe;
        }

        public void setClasse(ClasseDocente classe) {
            this.classe = classe;
        }
        
}
