package g5;

/**
 * Representa um token identificado pelo analisador léxico.
 */
public class Token {

    private final String tipo;
    private final String lexema;
    private final int linha;
    private final int coluna;

    public Token(String tipo, String lexema, int linha, int coluna) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.linha = linha;
        this.coluna = coluna;
    }

    public String getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    @Override
    public String toString() {
        return String.format("Token[tipo=%-20s, lexema='%s', linha=%d, coluna=%d]", tipo, lexema, linha, coluna);
    }
}
