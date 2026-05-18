package g5;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;

/**
 * Classe Lex, responsável pela Análise Léxixa.
 */
public class Lex {

    protected LALG analisadorJavaCC;

    protected PrintWriter writer;

    protected int errosLex = 0;

    /**
     * Construtor do Lex, 
     * recebe um FileInputStream 
     * para inicializar o analisador léxico 
     * gerado pelo JavaCC (LALG).
     * @param fis O FileInputStream do arquivo fonte a ser analisado.
     */
    public Lex(FileInputStream fis) {
        analisadorJavaCC = new LALG(fis);
    }

    public Lex(FileInputStream fis, PrintWriter writer) {
        analisadorJavaCC = new LALG(fis);
        this.writer = writer;
    }

    /**
     * Dado um código de token (kind), retorna o nome da constante correspondente
     * definida em LALGConstants. Utiliza reflexão para percorrer os campos da classe
     * e encontrar o nome associado ao valor inteiro do token.
     * @param kind O código inteiro do token a ser identificado.
     * @return O nome do token em minúsculas, ou "desconhecido" se não for encontrado.
     */
    public static String getTokenName(int kind) {
        for (Field f : LALGConstants.class.getDeclaredFields()) {
            try {
                if (f.getType() == int.class && f.getInt(null) == kind) {
                    return f.getName().toLowerCase();
                }
            } catch (Exception e) {
                // Ignore
            }
        }
        return "desconhecido";
    }

    /**
     * Verifica se um código de token (kind) corresponde a um token de erro,
     * @param kind O código inteiro do token a ser verificado.
     * @return true se o token for um token de erro, ou false caso contrário.
     */
    public static boolean isTokenError(int kind) {
        String tokenName = getTokenName(kind);
        if (tokenName.startsWith("erro_")) {
            return true;
        }
        return false;
    }

    /**
     * Obtém o próximo token do analisador léxico.
     * @return O próximo token encontrado no arquivo fonte, ou um token EOF se o final do arquivo for alcançado.
     */
    public Token getNextToken() {
        return this.analisadorJavaCC.getNextToken();
    }

}
