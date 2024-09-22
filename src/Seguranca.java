public class Seguranca {
    private String nivelCriptografia;
    private int tentativasFalhas;

    public Seguranca(String nivelCriptografia) {
        this.nivelCriptografia = nivelCriptografia;
        this.tentativasFalhas = 0;
    }

    public void criptografarDados() {
        System.out.println("Dados criptografados com nível " + nivelCriptografia);
    }

    public void autenticarAcesso() {
        System.out.println("Acesso autenticado com criptografia.");
    }

    public void monitorarAtividadesSuspeitas() {
        System.out.println("Monitorando atividades suspeitas...");
    }
}
