package cc20152.sisufal.models;

public class Patrimonio {

	private Integer id;
	private String numero;
	private String nome;
    private Movimentacao ultimaMovimentacao;
    private Sala sala = ultimaMovimentacao.getSala();
    private Bloco bloco = ultimaMovimentacao.getBloco();
	private Integer idServidorResponsavel;
	private Integer idDiscenteResponsavel;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
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

        public Movimentacao getUltimaMovimentacao() {
            return ultimaMovimentacao;
        }

        public void setUltimaMovimentacao(Movimentacao ultimaMovimentacao) {
            this.ultimaMovimentacao = ultimaMovimentacao;
        }

        public Integer getIdServidorResponsavel() {
            return idServidorResponsavel;
        }

        public void setIdServidorResponsavel(Integer idServidorResponsavel) {
            this.idServidorResponsavel = idServidorResponsavel;
        }

        public Integer getIdDiscenteResponsavel() {
            return idDiscenteResponsavel;
        }

        public void setIdDiscenteResponsavel(Integer idDiscenteResponsavel) {
            this.idDiscenteResponsavel = idDiscenteResponsavel;
        }

        @Override
        public String toString() {
            return this.nome;
        }

	

}
