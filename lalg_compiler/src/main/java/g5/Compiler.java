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
    public static void main(String[] args) {

        // Utiliza o programa default (programa_1) ou o caminho do arquivo passado como argumento
        String caminhoArquivo = args.length > 0 ? args[0] : "./programa_1.lalg";
        
        String arquivoSaida = caminhoArquivo.replace(".lalg", "_saida.txt");
        if (!arquivoSaida.endsWith("_saida.txt")) {
            arquivoSaida += "_saida.txt";
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(arquivoSaida));

            String codigoFonte = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
            
            Lex analisadorLexico = new Lex(codigoFonte);
            
            String msgInicio = "Início da análise do arquivo: " + caminhoArquivo;
            System.out.println(msgInicio);
            
            Token token = analisadorLexico.proximoToken();
            while (!token.getTipo().equals("simb_eof")) {
                
                String linhaSaida = String.format("%s - %s", token.getLexema(), token.getTipo());
                
                // Imprime no terminal
                System.out.println(linhaSaida);
                // Imprime no arquivo
                writer.println(linhaSaida);
                
                token = analisadorLexico.proximoToken();
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
