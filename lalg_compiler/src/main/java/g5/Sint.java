package g5;

import java.io.FileInputStream;

/**
 * Classe SINT, responsável pela Análise Sintática.
 */
public class Sint extends Lex {

    protected int errosSint = 0;

    /**
     * Construtor do Sint, 
     * recebe um FileInputStream 
     * para inicializar o analisador sintático 
     * gerado pelo JavaCC (LALG).
     * @param fis O FileInputStream do arquivo fonte a ser analisado.
     */
    public Sint(FileInputStream fis) {
        super(fis);
        analisadorJavaCC.sint = this;
    }

    /**
     * Implementação do modo pânico para recuperação de erros sintáticos.
     * @param e A exceção ParseException que foi lançada ao detectar um erro sintático.
     * @param syncTokens Um array de códigos de tokens que servem como pontos de sincronização para retomar a análise após um erro.
     */
    public void modoPanico(ParseException e, int[] syncTokens) {
        errosSint++;
        System.err.println("Erro sintático detectado: " + e.getMessage());
        
        Token t = analisadorJavaCC.getToken(1);
        boolean sync = false;
        
        while (t.kind != LALGConstants.EOF) {
            for (int syncToken : syncTokens) {
                if (t.kind == syncToken) {
                    sync = true;
                    break;
                }
            }
            if (sync) break;
            t = analisadorJavaCC.getNextToken();
        }
    }

    /**
     * Inicia o parser a partir do nó raiz da gramática (programa).
     */
    public boolean parse() {
        try {
            analisadorJavaCC.programa();
            if (this.errosSint > 0) {
                System.out.println("Análise concluída, porém com " + this.errosSint + " erro(s) sintático(s) estrutural(is).");
                return false;
            }
            return true;
        } catch (ParseException e) {
            System.err.println("Erro Crítico Sintático (forado modo pânico): " + e.getMessage());
            return false;
        }
    }
}
