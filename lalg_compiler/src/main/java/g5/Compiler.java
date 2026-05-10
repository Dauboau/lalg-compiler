package g5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe principal do Compilador.
 */
public class Compiler {

    public static void main(String[] args) {

        File inputDir = new File("input");
        File outputDir = new File("output");

        if (!inputDir.exists()) {
            System.err.println("A pasta 'input' não foi encontrada.");
            return;
        }

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Utiliza o programa default (programa_1) ou o caminho do arquivo passado como argumento
        String nomeArquivo = args.length > 0 ? args[0] : "programa_1.lalg";
        
        nomeArquivo = new File(nomeArquivo).getName();
        if (!nomeArquivo.endsWith(".lalg")) {
            nomeArquivo += ".lalg";
        }

        File arquivoEntrada = new File(inputDir, nomeArquivo);
        String caminhoArquivo = arquivoEntrada.getPath();

        if (!arquivoEntrada.exists()) {
            System.err.println("O arquivo fonte não foi encontrado na pasta 'input': " + caminhoArquivo);
            return;
        }

        String nomeArquivoSaida = nomeArquivo.replace(".lalg", "_saida.txt");
        File arquivoSaidaFile = new File(outputDir, nomeArquivoSaida);
        String arquivoSaida = arquivoSaidaFile.getPath();

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(arquivoSaida));

            java.io.FileInputStream fis = new java.io.FileInputStream(caminhoArquivo);
            
            Lex lex = new Lex(fis);
            
            String msgInicio = "Início da análise do arquivo: " + caminhoArquivo + '\n';
            System.out.println(msgInicio);
            
            Token token = lex.getNextToken();
            while (token.kind != LALGConstants.EOF) {
                
                String tipoNome = Lex.getTokenName(token.kind);
                String linhaSaida = String.format("%s - %s", token.image, tipoNome);
                
                // Imprime no terminal
                System.out.println(linhaSaida);
                // Imprime no arquivo
                writer.println(linhaSaida);
                
                token = lex.getNextToken();
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
