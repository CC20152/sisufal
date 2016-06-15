package cc20152.sisufal.models;
import java.util.Date;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

public class Usuario {
        
        private int workload = 12;
    
	private int id;
	private String login;
	private String password;
	private boolean isAdmin;
	private Date dataCriacao;
	private Date ultimoLogin;
        private Servidor servidor;
                
        public Usuario(){
            this.servidor = new Servidor();
        }

        public Servidor getServidor() {
            return servidor;
        }

        public void setServidor(Servidor servidor) {
            this.servidor = servidor;
        }
        
	private ArrayList<PermissaoUsuario> listaPermissao;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            String salt = BCrypt.gensalt(this.workload);
            this.password = BCrypt.hashpw(password, salt);
        }

        public boolean isIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        public Date getDataCriacao() {
            return dataCriacao;
        }

        public void setDataCriacao(Date dataCriacao) {
            this.dataCriacao = dataCriacao;
        }

        public Date getUltimoLogin() {
            return ultimoLogin;
        }

        public void setUltimoLogin(Date ultimoLogin) {
            this.ultimoLogin = ultimoLogin;
        }

        public ArrayList<PermissaoUsuario> getListaPermissao() {
            return listaPermissao;
        }

        public void setListaPermissao(ArrayList<PermissaoUsuario> listaPermissao) {
            this.listaPermissao = listaPermissao;
        }

}
