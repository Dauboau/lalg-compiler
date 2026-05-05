package g5;

import java.io.FileInputStream;
import java.lang.reflect.Field;

/**
 * Classe Lex, responsável pela Análise Léxixa.
 */
public class Lex {

    private LALG analisadorLexico;

    /**
     * Construtor do Lex, 
     * recebe um FileInputStream 
     * para inicializar o analisador léxico 
     * gerado pelo JavaCC (LALG).
     * @param fis O FileInputStream do arquivo fonte a ser analisado.
     */
    public Lex(FileInputStream fis) {
        analisadorLexico = new LALG(fis);
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
     * Obtém o próximo token do analisador léxico.
     * @return O próximo token encontrado no arquivo fonte, ou um token EOF se o final do arquivo for alcançado.
     */
    public Token getNextToken() {
        return this.analisadorLexico.getNextToken();
    }

}
