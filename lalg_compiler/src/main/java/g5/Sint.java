package g5;

import java.io.FileInputStream;
import java.io.PrintWriter;

import java.util.Set;

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

    /**
     * Construtor do Sint,
     * recebe um FileInputStream
     * para inicializar o analisador sintático
     * gerado pelo JavaCC (LALG) e um PrintWriter para saída.
     * @param fis O FileInputStream do arquivo fonte a ser analisado.
     * @param writer O PrintWriter para escrever a saída.
     */
    public Sint(FileInputStream fis, PrintWriter writer) {
        super(fis, writer);
        analisadorJavaCC.sint = this;
    }

    /**
     * Implementação do modo pânico para recuperação de erros sintáticos.
     * @param e A exceção ParseException que foi lançada ao detectar um erro sintático.
     * @param syncTokensFilho Conjunto de tokens internos à regra que permitem continuar no mesmo procedimento.
     * @param syncTokensPai Conjunto de tokens seguidores do pai que obrigam a sair do procedimento.
     * @return true se deve sair do procedimento (encontrou token do pai ou EOF), false se continua.
     */
    public boolean modoPanico(ParseException e, Set<Integer> syncTokensFilho, Set<Integer> syncTokensPai) {

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
                
        System.out.println("Tokens de sincronização Filhos: " + syncTokensFilho.stream().map(p->getTokenName(p)).collect(java.util.stream.Collectors.toList()));
        System.out.println("Tokens de sincronização Pais: " + syncTokensPai.stream().map(p->getTokenName(p)).collect(java.util.stream.Collectors.toList()));
        while (t.kind != LALGConstants.EOF) {
            t = analisadorJavaCC.getToken(1);
            System.out.println("Próximo token a ser analisado: " + getTokenName(t.kind));
            if (syncTokensFilho != null && syncTokensFilho.contains(t.kind)) {
                System.out.println("Continuando no mesmo procedimento...");
                return false;
            }
            if (syncTokensPai != null && syncTokensPai.contains(t.kind)) {
                System.out.println("Saindo do procedimento...");
                return true;
            }
            analisadorJavaCC.getNextToken();
        }
        System.out.println("Saindo do procedimento por EOF...");
        return true;
    }

    /**
     * Inicia o parser a partir do nó raiz da gramática (programa).
     */
    public boolean parse() {
        try {
            analisadorJavaCC.programa();
            if (this.errosSint > 0 || this.errosLex > 0) {
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
