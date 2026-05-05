package g5;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe contendo o analisador léxico do compilador!
 */
public class Lex {

    private final String codigoFonte;
    private int posicaoAtual;
    private int linhaAtual;
    private int colunaAtual;

    // Tabela de palavras reservadas 
    // HashMap para acesso rápido
    // chave: palavra reservada 
    // valor: tipo do token correspondente
    private static final Map<String, String> palavrasReservadas = new HashMap<>();

    // Preechimento da tabela de palavras reservadas
    static {
        // Palavras Reservadas
        palavrasReservadas.put("program", "simb_program");
        palavrasReservadas.put("begin", "simb_iniciar");
        palavrasReservadas.put("end", "simb_fim");
        palavrasReservadas.put("const", "simb_const");
        palavrasReservadas.put("var", "simb_var");
        palavrasReservadas.put("real", "simb_tipo_real");
        palavrasReservadas.put("integer", "simb_tipo_inteiro");
        palavrasReservadas.put("procedure", "simb_procedimento");
        palavrasReservadas.put("if", "simb_se");
        palavrasReservadas.put("then", "simb_entao");
        palavrasReservadas.put("else", "simb_senao");
        palavrasReservadas.put("read", "simb_leia");
        palavrasReservadas.put("write", "simb_escreva");
        palavrasReservadas.put("while", "simb_enquanto");
        palavrasReservadas.put("do", "simb_faca");
        
        // Operadores Aritméticos e de Pontuação
        palavrasReservadas.put("+", "simb_mais");
        palavrasReservadas.put("-", "simb_menos");
        palavrasReservadas.put("*", "simb_asterisco");
        palavrasReservadas.put("/", "simb_barra");
        palavrasReservadas.put("=", "simb_igual");
        palavrasReservadas.put(";", "simb_ponto_virgula");
        palavrasReservadas.put(".", "simb_ponto");
        palavrasReservadas.put(",", "simb_virgula");
        palavrasReservadas.put("(", "simb_abre_parenteses");
        palavrasReservadas.put(")", "simb_fecha_parenteses");

        // Operadores Relacionais
        palavrasReservadas.put("<", "simb_menor");
        palavrasReservadas.put("<=", "simb_menor_igual");
        palavrasReservadas.put("<>", "simb_diferente");
        palavrasReservadas.put(">", "simb_maior");
        palavrasReservadas.put(">=", "simb_maior_igual");
        palavrasReservadas.put(":", "simb_dois_pontos");
        palavrasReservadas.put(":=", "simb_atribuicao");
    }

    public Lex(String codigoFonte) {
        this.codigoFonte = codigoFonte;
        this.posicaoAtual = 0;
        this.linhaAtual = 1;
        this.colunaAtual = 1;
    }

    /**
     * Retorna o próximo token encontrado no código fonte.
     * @return O próximo @Token ou um token do tipo simb_eof caso tenha chegado ao fim do arquivo.
     */
    public Token proximoToken() {
        ignorarCaracteresEmBrancoEComentarios();

        if (chegouAoFim()) {
            return criarToken("simb_eof", "EOF");
        }

        char caractereAtual = lerCaractereAtual();

        // 1. Identificadores e Palavras Reservadas
        if (Character.isLetter(caractereAtual)) {
            return processarIdentificadorOuPalavraReservada();
        }

        // 2. Números (Inteiros e Reais)
        if (Character.isDigit(caractereAtual)) {
            return processarNumero();
        }

        // 3. Outros Símbolos (Operadores e Pontuações)
        return processarSimboloOuOperador();
    }

    private Token processarIdentificadorOuPalavraReservada() {
        int colunaInicial = colunaAtual;
        StringBuilder lexema = new StringBuilder();

        while (!chegouAoFim() && (Character.isLetterOrDigit(lerCaractereAtual()) || lerCaractereAtual() == '_')) {
            lexema.append(lerCaractereAtual());
            avancarCaractere();
        }

        String valorLexema = lexema.toString();
        // Verifica se é uma palavra reservada, caso não, é um identificador comum
        String tipoToken = palavrasReservadas.getOrDefault(valorLexema, "simb_identificador");

        return new Token(tipoToken, valorLexema, linhaAtual, colunaInicial);
    }

    private Token processarNumero() {
        int colunaInicial = colunaAtual;
        StringBuilder lexema = new StringBuilder();
        boolean possuiPonto = false;

        while (!chegouAoFim() && (Character.isDigit(lerCaractereAtual()) || lerCaractereAtual() == '.')) {
            char atual = lerCaractereAtual();
            
            if (atual == '.') {
                if (possuiPonto) {
                    break; // Segundo ponto encontrado, sair do loop
                }
                possuiPonto = true;
            }
            
            lexema.append(atual);
            avancarCaractere();
        }

        String tipoToken = possuiPonto ? "simb_numero_real" : "simb_numero_inteiro";
        return new Token(tipoToken, lexema.toString(), linhaAtual, colunaInicial);
    }

    private Token processarSimboloOuOperador() {
        int colunaInicial = colunaAtual;
        char c1 = lerCaractereAtual();
        
        // Verifica primeiro se formam um símbolo composto (2 caracteres), como <=, >=, <>, :=
        if (posicaoAtual + 1 < codigoFonte.length()) {
            char c2 = codigoFonte.charAt(posicaoAtual + 1);
            String simboloDuplo = "" + c1 + c2;
            
            // Busca na tabela de palavras reservadas
            if (palavrasReservadas.containsKey(simboloDuplo)) {
                avancarCaractere(); // consome c1
                avancarCaractere(); // consome c2
                return new Token(palavrasReservadas.get(simboloDuplo), simboloDuplo, linhaAtual, colunaInicial);
            }
        }
        
        // Se não for símbolo duplo, processa como símbolo simples (1 caractere)
        String simboloSimples = String.valueOf(c1);
        avancarCaractere(); // consome c1
        
        String tipoToken = palavrasReservadas.get(simboloSimples);
        if (tipoToken != null) {
            return new Token(tipoToken, simboloSimples, linhaAtual, colunaInicial);
        }
        
        return new Token("simb_desconhecido", simboloSimples, linhaAtual, colunaInicial);
    }

    private void ignorarCaracteresEmBrancoEComentarios() {
        while (!chegouAoFim()) {
            char atual = lerCaractereAtual();

            // Ignorar espaços e quebras de linha
            if (Character.isWhitespace(atual)) {
                if (atual == '\n') {
                    linhaAtual++;
                    colunaAtual = 0; // Vai virar 1 no avancarCaractere()
                }
                avancarCaractere();
                continue;
            }

            // Tratamento de comentários do tipo { ... }
            if (atual == '{') {
                avancarCaractere(); // Pula o '{'
                while (!chegouAoFim() && lerCaractereAtual() != '}') {
                    if (lerCaractereAtual() == '\n') {
                        linhaAtual++;
                        colunaAtual = 0;
                    }
                    avancarCaractere();
                }
                if (!chegouAoFim()) {
                    avancarCaractere(); // Pula o '}'
                }
                continue;
            }

            // Se não é espaço em branco nem comentário, podemos parar
            break;
        }
    }

    private boolean chegouAoFim() {
        return posicaoAtual >= codigoFonte.length();
    }

    private char lerCaractereAtual() {
        return codigoFonte.charAt(posicaoAtual);
    }

    private void avancarCaractere() {
        posicaoAtual++;
        colunaAtual++;
    }

    private Token criarToken(String tipo, String lexema) {
        return new Token(tipo, lexema, linhaAtual, colunaAtual);
    }
}
