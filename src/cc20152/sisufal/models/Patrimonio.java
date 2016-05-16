package cc20152.sisufal.models;
public abstract class Patrimonio {

	private int id;

	private String numero;

	private String nome;

	private Movimentacao UltimaMovimentacao;

	private int idServidorResponsavel;

	private int idDiscenteResponsavel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Movimentacao getUltimaMovimentacao() {
            return UltimaMovimentacao;
        }

        public void setUltimaMovimentacao(Movimentacao UltimaMovimentacao) {
            this.UltimaMovimentacao = UltimaMovimentacao;
        }

        public int getIdServidorResponsavel() {
            return idServidorResponsavel;
        }

        public void setIdServidorResponsavel(int idServidorResponsavel) {
            this.idServidorResponsavel = idServidorResponsavel;
        }

        public int getIdDiscenteResponsavel() {
            return idDiscenteResponsavel;
        }

        public void setIdDiscenteResponsavel(int idDiscenteResponsavel) {
            this.idDiscenteResponsavel = idDiscenteResponsavel;
        }

	

}
