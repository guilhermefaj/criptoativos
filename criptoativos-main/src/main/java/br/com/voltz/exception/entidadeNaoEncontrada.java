package br.com.voltz.exception;

public class entidadeNaoEncontrada extends Exception{

    public entidadeNaoEncontrada() {
    }

    public entidadeNaoEncontrada(String message) {
        super(message);
    }
}