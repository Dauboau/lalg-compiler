package g5;

import java.io.FileInputStream;

/**
 * Classe SINT, responsável pela Análise Sintática.
 */
public class Sint extends Lex {

    /**
     * Construtor do Sint, 
     * recebe um FileInputStream 
     * para inicializar o analisador sintático 
     * gerado pelo JavaCC (LALG).
     * @param fis O FileInputStream do arquivo fonte a ser analisado.
     */
    public Sint(FileInputStream fis) {
        super(fis);
    }

    /**
     * Inicia o parser a partir do nó raiz da gramática (programa).
     */
    public boolean parse() {
        try {
            analisadorJavaCC.programa();
            if (analisadorJavaCC.syntaxErrors > 0) {
                System.out.println("Análise concluída, porém com " + analisadorJavaCC.syntaxErrors + " erro(s) sintático(s) estrutural(is).");
                return false;
            }
            return true;
        } catch (ParseException e) {
            System.err.println("Erro Crítico Sintático (forado modo pânico): " + e.getMessage());
            return false;
        }
    }
}
