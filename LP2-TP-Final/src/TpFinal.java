import com.google.gson.Gson;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TpFinal {
    static Integer currentRecord;

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnectionToMySQL();
            createTables(connection);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        final Connection finalConnection = connection;

        JFrame frame = new JFrame("Cadastro de Alunos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(2, 1));

        JLabel nomeLabel = new JLabel("Nome: ");
        JTextField nomeInput = new JTextField(20);
        JButton pesquisarButton = new JButton("Pesquisar");
        JPanel topRow = new JPanel(new FlowLayout());
        topRow.add(nomeLabel);
        topRow.add(nomeInput);
        topRow.add(pesquisarButton);

        JLabel nameLabel = new JLabel("Nome: ");
        JTextField nameInput = new JTextField(10);
        JLabel idadeLabel = new JLabel("Idade: ");
        JTextField idadeInput = new JTextField(10);
        JLabel pesoLabel = new JLabel("Peso: ");
        JTextField pesoInput = new JTextField(10);
        JLabel alturaLabel = new JLabel("Altura: ");
        JTextField alturaInput = new JTextField(10);
        JLabel objetivoLabel = new JLabel("Objetivo: ");
        JTextField objetivoInput = new JTextField(10);
        JButton incluirButton = new JButton("Incluir");
        JButton limparButton = new JButton("Limpar");
        JButton apresentaButton = new JButton("Apresenta Dados");
        JButton sairButton = new JButton("Sair");
        JPanel bottomRow = new JPanel(new GridLayout(8, 2));

        bottomRow.add(nameLabel);
        bottomRow.add(nameInput);
        bottomRow.add(idadeLabel);
        bottomRow.add(idadeInput);
        bottomRow.add(pesoLabel);
        bottomRow.add(pesoInput);
        bottomRow.add(alturaLabel);
        bottomRow.add(alturaInput);
        bottomRow.add(objetivoLabel);
        bottomRow.add(objetivoInput);
        bottomRow.add(incluirButton);
        bottomRow.add(limparButton);
        bottomRow.add(apresentaButton);
        bottomRow.add(sairButton);

        frame.add(topRow);
        frame.add(bottomRow);

        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeInput.getText().trim(); // Remova espaços em branco extras
                try {
                    // Use um PreparedStatement para evitar SQL Injection
                    PreparedStatement statement = finalConnection.prepareStatement(
                            "SELECT id_aluno, nome_aluno, idade, peso, altura, objetivo FROM alunos_academia WHERE nome_aluno = ?");
                    statement.setString(1, nome);
        
                    ResultSet resultSet = statement.executeQuery();
        
                    if (resultSet.next()) {
                        nameInput.setText(resultSet.getString("nome_aluno"));
                        idadeInput.setText(resultSet.getString("idade"));
                        pesoInput.setText(resultSet.getString("peso"));
                        alturaInput.setText(resultSet.getString("altura"));
                        objetivoInput.setText(resultSet.getString("objetivo"));
                        currentRecord = resultSet.getInt("id_aluno");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Aluno não encontrado");
                        clearInputs(nameInput, idadeInput, pesoInput, alturaInput, objetivoInput);
                    }
        
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        incluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nameInput.getText();
                String idade = idadeInput.getText();
                String peso = pesoInput.getText();
                String altura = alturaInput.getText();
                String objetivo = objetivoInput.getText();

                try {
                    PreparedStatement preparedStatement = finalConnection.prepareStatement(
                            "INSERT INTO alunos_academia (nome_aluno, idade, peso, altura, objetivo) VALUES (?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, nome);
                    preparedStatement.setString(2, idade);
                    preparedStatement.setString(3, peso);
                    preparedStatement.setString(4, altura);
                    preparedStatement.setString(5, objetivo);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Registro incluído com sucesso!");
                        clearInputs(nameInput, idadeInput, pesoInput, alturaInput, objetivoInput);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Falha ao incluir o registro");
                    }

                    // Obtém o ID gerado para o novo registro
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        currentRecord = generatedKeys.getInt(1);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputs(nameInput, idadeInput, pesoInput, alturaInput, objetivoInput);
            }
        });

        apresentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nameInput.getText();
                    String idade = idadeInput.getText();
                    String peso = pesoInput.getText();
                    String altura = alturaInput.getText();
                    String objetivo = objetivoInput.getText();

                    DadosAluno dadosAluno = new DadosAluno(nome, idade, peso, altura, objetivo);
                    Gson gson = new Gson();
                    String jsonData = gson.toJson(dadosAluno);

                    JOptionPane.showMessageDialog(frame, jsonData);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    if (finalConnection != null) {
                        finalConnection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }

    private static void clearInputs(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static void createTables(Connection connection) {
        try {
            Statement statement = connection.createStatement();
    
            // Criação da tabela 'alunos_academia' se ela não existir
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS alunos_academia (" +
                            "ID_ALUNO INT AUTO_INCREMENT PRIMARY KEY," +
                            "NOME_ALUNO VARCHAR(50)," +
                            "IDADE INT," +
                            "PESO DECIMAL(5, 2)," +
                            "ALTURA DECIMAL(5, 2)," +
                            "OBJETIVO VARCHAR(255)" +
                            ")");
    
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
