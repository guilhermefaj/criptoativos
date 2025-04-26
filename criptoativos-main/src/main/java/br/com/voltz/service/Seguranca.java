package br.com.voltz.service;

public class Seguranca {
    private String idSeguranca;
    private String nivelCriptografia;
    private int tentativasFalhas;
    private  PlataformaWeb plataforma;

    public Seguranca() {
    }

    public Seguranca(String idSeguranca, String nivelCriptografia, int tentativasFalhas, PlataformaWeb plataforma) {
        this.idSeguranca = idSeguranca;
        this.nivelCriptografia = nivelCriptografia;
        this.tentativasFalhas = tentativasFalhas;
        this.plataforma = plataforma;
    }

    public Seguranca(String nivelCriptografia) {
        this.nivelCriptografia = nivelCriptografia;
        this.tentativasFalhas = 0;
    }

    public String getIdSeguranca() {
        return idSeguranca;
    }

    public void setIdSeguranca(String idSeguranca) {
        this.idSeguranca = idSeguranca;
    }

    public String getNivelCriptografia() {
        return nivelCriptografia;
    }

    public void setNivelCriptografia(String nivelCriptografia) {
        this.nivelCriptografia = nivelCriptografia;
    }

    public int getTentativasFalhas() {
        return tentativasFalhas;
    }

    public void setTentativasFalhas(int tentativasFalhas) {
        this.tentativasFalhas = tentativasFalhas;
    }

    public PlataformaWeb getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(PlataformaWeb plataforma) {
        this.plataforma = plataforma;
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
