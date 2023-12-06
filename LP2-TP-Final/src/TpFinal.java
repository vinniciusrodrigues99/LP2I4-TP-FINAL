import com.google.gson.Gson;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        Frame frame = new Frame("Cadastro de Alunos");
        frame.setSize(400, 250);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        Color bgColor = new Color(13, 17, 23);
        Color lbColor = new Color(201, 209, 217);
        Color btColor_1 = new Color(48, 54, 61);
        Color btColor_2 = new Color(56, 139, 253);
        Color btColor_3 = new Color(183, 97, 0);
        Color lbColor_2 = new Color(255, 255, 255);
        Font lbFont = new Font("Arial", Font.BOLD, 12);
        frame.setBackground(bgColor);
        frame.setForeground(lbColor);        

        //LABELS
        Label nomeLabel = new Label("Nome:");
        nomeLabel.setForeground(lbColor);
        Label nameLabel = new Label("Nome:");
        nameLabel.setForeground(lbColor);
        Label idadeLabel = new Label("Idade:");
        idadeLabel.setForeground(lbColor);
        Label pesoLabel = new Label("Peso:");
        pesoLabel.setForeground(lbColor);
        Label alturaLabel = new Label("Altura:");
        alturaLabel.setForeground(lbColor);
        Label objetivoLabel = new Label("Objetivo");
        objetivoLabel.setForeground(lbColor);

        //TEXTFIELDS
        TextField nomeInput = new TextField();
        nomeInput.setColumns(25);
        nomeInput.setBackground(bgColor);
        TextField nameInput = new TextField();
        nameInput.setBackground(bgColor);
        TextField idadeInput = new TextField();
        idadeInput.setBackground(bgColor);
        TextField pesoInput = new TextField();
        pesoInput.setBackground(bgColor);
        TextField alturaInput = new TextField();
        alturaInput.setBackground(bgColor);
        TextField objetivoInput = new TextField();
        objetivoInput.setBackground(bgColor);

        //BUTTONS
        Button pesquisarButton = new Button("Pesquisar");
        pesquisarButton.setBackground(btColor_2);
        pesquisarButton.setForeground(lbColor_2);
        pesquisarButton.setFont(lbFont);
        Button incluirButton = new Button("Incluir");
        incluirButton.setBackground(btColor_1);
        incluirButton.setForeground(lbColor_2);
        incluirButton.setFont(lbFont);
        Button limparButton = new Button("Limpar");
        limparButton.setBackground(btColor_1);
        limparButton.setForeground(lbColor_2);
        limparButton.setFont(lbFont);
        Button apresentaButton = new Button("Apresenta Dados");
        apresentaButton.setFont(lbFont);
        apresentaButton.setBackground(btColor_1);
        apresentaButton.setForeground(lbColor_2);
        Button sairButton = new Button("Sair");
        sairButton.setBackground(btColor_3);
        sairButton.setForeground(lbColor_2);
        sairButton.setFont(lbFont);

        //PANELS
        Panel topRow = new Panel(new FlowLayout(FlowLayout.CENTER));
        Panel centerRow = new Panel(new GridLayout(5, 2, 10, 5));
        Panel bottomRow = new Panel(new GridLayout(2, 2, 10, 10));

        //Organizing the Panels
        topRow.add(nomeLabel);
        topRow.add(nomeInput);
        topRow.add(pesquisarButton);

        centerRow.add(nameLabel);
        centerRow.add(nameInput);
        centerRow.add(idadeLabel);
        centerRow.add(idadeInput);
        centerRow.add(pesoLabel);
        centerRow.add(pesoInput);
        centerRow.add(alturaLabel);
        centerRow.add(alturaInput);
        centerRow.add(objetivoLabel);
        centerRow.add(objetivoInput);
        bottomRow.add(incluirButton);
        bottomRow.add(limparButton);
        bottomRow.add(apresentaButton);
        bottomRow.add(sairButton);

        frame.add(topRow, BorderLayout.NORTH);
        frame.add(centerRow, BorderLayout.CENTER);
        frame.add(bottomRow, BorderLayout.SOUTH);

        frame.setVisible(true);

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
    
