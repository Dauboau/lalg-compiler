package g5;

import java.io.FileInputStream;
import java.io.PrintWriter;

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
     * @param writer O PrintWriter para escrever a saída.
     */
    public Sint(FileInputStream fis) {
        super(fis);
        analisadorJavaCC.sint = this;
    }

    public Sint(FileInputStream fis, PrintWriter writer) {
        super(fis, writer);
        analisadorJavaCC.sint = this;
    }

    /**
     * Implementação do modo pânico para recuperação de erros sintáticos.
     * @param e A exceção ParseException que foi lançada ao detectar um erro sintático.
     * @param syncTokens Um array de códigos de tokens que servem como pontos de sincronização para retomar a análise após um erro.
     */
    public void modoPanico(ParseException e, int[] syncTokens) {

        Token t = analisadorJavaCC.getToken(1);
        
        String msgErro;
        if(isTokenError(t.kind)) {
            this.errosLex++;
            msgErro = "Erro léxico detectado: " + e.getMessage();
        }else{
            errosSint++;
            msgErro = "Erro sintático detectado: " + e.getMessage();
        }
        System.err.println(msgErro);
        this.writer.println(msgErro);
        
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
                String msgErro = "Análise concluída, porém com " + this.errosLex + " erro(s) léxico(s) e " + this.errosSint + " erro(s) sintático(s).";
                System.out.println(msgErro);
                this.writer.println(msgErro);
                return false;
            }

            return true;
        } catch (ParseException e) {
            System.err.println("Erro Crítico Sintático (forado modo pânico): " + e.getMessage());
            return false;
        }
    }
}
