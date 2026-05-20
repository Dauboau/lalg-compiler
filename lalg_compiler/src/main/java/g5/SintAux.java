package g5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe auxiliar contendo os conjuntos FIRST (Primeiro) e FOLLOW (Seguidor)
 */
public class SintAux {

    public static final Map<String, Set<Integer>> FIRST = new HashMap<>();
    public static final Map<String, Set<Integer>> FOLLOW = new HashMap<>();

    /**
     * Função utilitária para unir o conjunto Seguidor da regra atual
     * com o conjunto Seguidor do pai (followPai).
     * @param a O conjunto Seguidor da regra atual (SintAux.FOLLOW.get("regra"))
     * @param b O conjunto contendo os tokens do followPai
     * @return Um conjunto de inteiros contendo a união dos tokens para sincronização
     */
    public static Set<Integer> unir(Set<Integer> a, Set<Integer> b) {
        Set<Integer> set = new HashSet<>();
        if (a != null) set.addAll(a);
        if (b != null) set.addAll(b);
        return set;
    }

    static {
        // Inicialização do conjunto FIRST (Primeiro)
        FIRST.put("programa", Set.of(LALGConstants.SIMB_PROGRAM));
        FIRST.put("corpo", Set.of(LALGConstants.SIMB_CONST, LALGConstants.SIMB_VAR, LALGConstants.SIMB_PROCEDIMENTO, LALGConstants.SIMB_INICIAR));
        FIRST.put("dc", Set.of(LALGConstants.SIMB_CONST, LALGConstants.SIMB_VAR, LALGConstants.SIMB_PROCEDIMENTO));
        FIRST.put("dc_c", Set.of(LALGConstants.SIMB_CONST));
        FIRST.put("dc_v", Set.of(LALGConstants.SIMB_VAR));
        FIRST.put("tipo_var", Set.of(LALGConstants.SIMB_TIPO_REAL, LALGConstants.SIMB_TIPO_INTEIRO));
        FIRST.put("variaveis", Set.of(LALGConstants.ID));
        FIRST.put("mais_var", Set.of(LALGConstants.SIMB_VIRGULA));
        FIRST.put("dc_p", Set.of(LALGConstants.SIMB_PROCEDIMENTO));
        FIRST.put("parametros", Set.of(LALGConstants.SIMB_ABRE_PARENTESES));
        FIRST.put("lista_par", Set.of(LALGConstants.ID));
        FIRST.put("mais_par", Set.of(LALGConstants.SIMB_PONTO_VIRGULA));
        FIRST.put("corpo_p", Set.of(LALGConstants.SIMB_VAR, LALGConstants.SIMB_INICIAR));
        FIRST.put("dc_loc", Set.of(LALGConstants.SIMB_VAR));
        FIRST.put("lista_arg", Set.of(LALGConstants.SIMB_ABRE_PARENTESES));
        FIRST.put("argumentos", Set.of(LALGConstants.ID));
        FIRST.put("mais_ident", Set.of(LALGConstants.SIMB_PONTO_VIRGULA));
        FIRST.put("pfalsa", Set.of(LALGConstants.SIMB_SENAO));
        FIRST.put("comandos", Set.of(LALGConstants.SIMB_LEIA, LALGConstants.SIMB_ESCREVA, LALGConstants.SIMB_ENQUANTO, LALGConstants.SIMB_SE, LALGConstants.ID, LALGConstants.SIMB_INICIAR, LALGConstants.SIMB_PARA));
        FIRST.put("cmd", Set.of(LALGConstants.SIMB_LEIA, LALGConstants.SIMB_ESCREVA, LALGConstants.SIMB_ENQUANTO, LALGConstants.SIMB_SE, LALGConstants.ID, LALGConstants.SIMB_INICIAR, LALGConstants.SIMB_PARA));
        FIRST.put("cmd_aux", Set.of(LALGConstants.SIMB_ATRIBUICAO, LALGConstants.SIMB_ABRE_PARENTESES));
        FIRST.put("condicao", Set.of(LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS, LALGConstants.ID, LALGConstants.NUMERO_INTEIRO, LALGConstants.NUMERO_REAL, LALGConstants.SIMB_ABRE_PARENTESES));
        FIRST.put("relacao", Set.of(LALGConstants.SIMB_IGUAL, LALGConstants.SIMB_DIFERENTE, LALGConstants.SIMB_MAIOR_IGUAL, LALGConstants.SIMB_MENOR_IGUAL, LALGConstants.SIMB_MAIOR, LALGConstants.SIMB_MENOR));
        FIRST.put("expressao", Set.of(LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS, LALGConstants.ID, LALGConstants.NUMERO_INTEIRO, LALGConstants.NUMERO_REAL, LALGConstants.SIMB_ABRE_PARENTESES));
        FIRST.put("op_un", Set.of(LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS));
        FIRST.put("outros_termos", Set.of(LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS));
        FIRST.put("op_ad", Set.of(LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS));
        FIRST.put("termo", Set.of(LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS, LALGConstants.ID, LALGConstants.NUMERO_INTEIRO, LALGConstants.NUMERO_REAL, LALGConstants.SIMB_ABRE_PARENTESES));
        FIRST.put("mais_fatores", Set.of(LALGConstants.SIMB_ASTERISCO, LALGConstants.SIMB_BARRA));
        FIRST.put("op_mul", Set.of(LALGConstants.SIMB_ASTERISCO, LALGConstants.SIMB_BARRA));
        FIRST.put("fator", Set.of(LALGConstants.ID, LALGConstants.NUMERO_INTEIRO, LALGConstants.NUMERO_REAL, LALGConstants.SIMB_ABRE_PARENTESES));
        FIRST.put("numero", Set.of(LALGConstants.NUMERO_INTEIRO, LALGConstants.NUMERO_REAL));

        // Inicialização do conjunto FOLLOW (Seguidor)
        FOLLOW.put("programa", Set.of(LALGConstants.EOF));
        FOLLOW.put("corpo", Set.of(LALGConstants.SIMB_PONTO));
        FOLLOW.put("dc", Set.of(LALGConstants.SIMB_INICIAR));
        FOLLOW.put("dc_c", Set.of(LALGConstants.SIMB_VAR, LALGConstants.SIMB_PROCEDIMENTO, LALGConstants.SIMB_INICIAR));
        FOLLOW.put("dc_v", Set.of(LALGConstants.SIMB_PROCEDIMENTO, LALGConstants.SIMB_INICIAR));
        FOLLOW.put("tipo_var", Set.of(LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_FECHA_PARENTESES));
        FOLLOW.put("variaveis", Set.of(LALGConstants.SIMB_DOIS_PONTOS, LALGConstants.SIMB_FECHA_PARENTESES));
        FOLLOW.put("mais_var", Set.of(LALGConstants.SIMB_DOIS_PONTOS, LALGConstants.SIMB_FECHA_PARENTESES));
        FOLLOW.put("dc_p", Set.of(LALGConstants.SIMB_INICIAR));
        FOLLOW.put("parametros", Set.of(LALGConstants.SIMB_PONTO_VIRGULA));
        FOLLOW.put("lista_par", Set.of(LALGConstants.SIMB_FECHA_PARENTESES));
        FOLLOW.put("mais_par", Set.of(LALGConstants.SIMB_FECHA_PARENTESES));
        FOLLOW.put("corpo_p", Set.of(LALGConstants.SIMB_PROCEDIMENTO, LALGConstants.SIMB_INICIAR));
        FOLLOW.put("dc_loc", Set.of(LALGConstants.SIMB_INICIAR));
        FOLLOW.put("lista_arg", Set.of(LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_SENAO));
        FOLLOW.put("argumentos", Set.of(LALGConstants.SIMB_FECHA_PARENTESES));
        FOLLOW.put("mais_ident", Set.of(LALGConstants.SIMB_FECHA_PARENTESES));
        FOLLOW.put("pfalsa", Set.of(LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_SENAO));
        FOLLOW.put("comandos", Set.of(LALGConstants.SIMB_FIM));
        FOLLOW.put("cmd_aux", Set.of(LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_SENAO));
        FOLLOW.put("cmd", Set.of(LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_SENAO));
        FOLLOW.put("condicao", Set.of(LALGConstants.SIMB_FECHA_PARENTESES, LALGConstants.SIMB_ENTAO));
        FOLLOW.put("relacao", Set.of(LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS, LALGConstants.ID, LALGConstants.NUMERO_INTEIRO, LALGConstants.NUMERO_REAL, LALGConstants.SIMB_ABRE_PARENTESES));
        FOLLOW.put("expressao", Set.of(LALGConstants.SIMB_IGUAL, LALGConstants.SIMB_DIFERENTE, LALGConstants.SIMB_MAIOR_IGUAL, LALGConstants.SIMB_MENOR_IGUAL, LALGConstants.SIMB_MAIOR, LALGConstants.SIMB_MENOR, LALGConstants.SIMB_FECHA_PARENTESES, LALGConstants.SIMB_ENTAO, LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_SENAO, LALGConstants.SIMB_ATE, LALGConstants.SIMB_FACA));
        FOLLOW.put("op_un", Set.of(LALGConstants.ID, LALGConstants.NUMERO_INTEIRO, LALGConstants.NUMERO_REAL, LALGConstants.SIMB_ABRE_PARENTESES));
        FOLLOW.put("outros_termos", Set.of(LALGConstants.SIMB_IGUAL, LALGConstants.SIMB_DIFERENTE, LALGConstants.SIMB_MAIOR_IGUAL, LALGConstants.SIMB_MENOR_IGUAL, LALGConstants.SIMB_MAIOR, LALGConstants.SIMB_MENOR, LALGConstants.SIMB_FECHA_PARENTESES, LALGConstants.SIMB_ENTAO, LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_SENAO, LALGConstants.SIMB_ATE, LALGConstants.SIMB_FACA));
        FOLLOW.put("op_ad", Set.of(LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS, LALGConstants.ID, LALGConstants.NUMERO_INTEIRO, LALGConstants.NUMERO_REAL, LALGConstants.SIMB_ABRE_PARENTESES));
        FOLLOW.put("termo", Set.of(LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS, LALGConstants.SIMB_IGUAL, LALGConstants.SIMB_DIFERENTE, LALGConstants.SIMB_MAIOR_IGUAL, LALGConstants.SIMB_MENOR_IGUAL, LALGConstants.SIMB_MAIOR, LALGConstants.SIMB_MENOR, LALGConstants.SIMB_FECHA_PARENTESES, LALGConstants.SIMB_ENTAO, LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_SENAO, LALGConstants.SIMB_ATE, LALGConstants.SIMB_FACA));
        FOLLOW.put("mais_fatores", Set.of(LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS, LALGConstants.SIMB_IGUAL, LALGConstants.SIMB_DIFERENTE, LALGConstants.SIMB_MAIOR_IGUAL, LALGConstants.SIMB_MENOR_IGUAL, LALGConstants.SIMB_MAIOR, LALGConstants.SIMB_MENOR, LALGConstants.SIMB_FECHA_PARENTESES, LALGConstants.SIMB_ENTAO, LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_SENAO, LALGConstants.SIMB_ATE, LALGConstants.SIMB_FACA));
        FOLLOW.put("op_mul", Set.of(LALGConstants.ID, LALGConstants.NUMERO_INTEIRO, LALGConstants.NUMERO_REAL, LALGConstants.SIMB_ABRE_PARENTESES));
        FOLLOW.put("fator", Set.of(LALGConstants.SIMB_ASTERISCO, LALGConstants.SIMB_BARRA, LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS, LALGConstants.SIMB_IGUAL, LALGConstants.SIMB_DIFERENTE, LALGConstants.SIMB_MAIOR_IGUAL, LALGConstants.SIMB_MENOR_IGUAL, LALGConstants.SIMB_MAIOR, LALGConstants.SIMB_MENOR, LALGConstants.SIMB_FECHA_PARENTESES, LALGConstants.SIMB_ENTAO, LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_SENAO, LALGConstants.SIMB_ATE, LALGConstants.SIMB_FACA));
        FOLLOW.put("numero", Set.of(LALGConstants.SIMB_ASTERISCO, LALGConstants.SIMB_BARRA, LALGConstants.SIMB_MAIS, LALGConstants.SIMB_MENOS, LALGConstants.SIMB_IGUAL, LALGConstants.SIMB_DIFERENTE, LALGConstants.SIMB_MAIOR_IGUAL, LALGConstants.SIMB_MENOR_IGUAL, LALGConstants.SIMB_MAIOR, LALGConstants.SIMB_MENOR, LALGConstants.SIMB_FECHA_PARENTESES, LALGConstants.SIMB_ENTAO, LALGConstants.SIMB_PONTO_VIRGULA, LALGConstants.SIMB_SENAO, LALGConstants.SIMB_ATE, LALGConstants.SIMB_FACA));
    }
}
