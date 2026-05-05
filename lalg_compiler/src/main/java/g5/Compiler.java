package g5;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe principal do Compilador!
 */
public class Compiler {
    
    // Método auxiliar para buscar o nome da constante via reflexão
    public static String getTokenName(int kind) {
        for (java.lang.reflect.Field f : LALGConstants.class.getDeclaredFields()) {
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

    public static void main(String[] args) {

        // Utiliza o programa default (programa_1) ou o caminho do arquivo passado como argumento
        String caminhoArquivo = args.length > 0 ? args[0] : "./programa_1.lalg";
        
        String arquivoSaida = caminhoArquivo.replace(".lalg", "_saida.txt");
        if (!arquivoSaida.endsWith("_saida.txt")) {
            arquivoSaida += "_saida.txt";
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(arquivoSaida));

            // No JavaCC, usamos FileInputStream
            java.io.FileInputStream fis = new java.io.FileInputStream(caminhoArquivo);
            
            // Instancia o analisador léxico gerado pelo JavaCC
            LALG analisadorLexico = new LALG(fis);
            
            String msgInicio = "Início da análise do arquivo: " + caminhoArquivo;
            System.out.println(msgInicio);
            
            Token token = analisadorLexico.getNextToken();
            while (token.kind != LALGConstants.EOF) {
                
                String tipoNome = getTokenName(token.kind);
                String linhaSaida = String.format("%s - %s", token.image, tipoNome);
                
                // Imprime no terminal
                System.out.println(linhaSaida);
                // Imprime no arquivo
                writer.println(linhaSaida);
                
                token = analisadorLexico.getNextToken();
            }

            String msgFim = "\nAnálise concluída com sucesso. Saída salva em: " + arquivoSaida;
            System.out.println(msgFim);

            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao ler ou gravar arquivos: " + e.getMessage());
            System.err.println("Certifique-se de que o arquivo fonte existe e que você tem permissão de escrita na pasta.");
        }

    }
}
