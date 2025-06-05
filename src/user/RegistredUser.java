package src;

public class RegistredUser extends User {

        private String password;

        private String email;

        public RegistredUser(String nom, String mdp, String mail) {
                super(nom);
                this.password = mdp;
                this.email = mail;
        }

        public void setPassword(String newMdp) {
                this.password = newMdp;
        }

        public String getPassword() {
                return this.password;
        }

        public String getEmail() {
                return this.email;
        }

}
