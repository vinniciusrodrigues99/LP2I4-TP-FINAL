import java.awt.*;
import java.awt.event.*;

public class newFinal {

    public static void main(String[] args) {
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
    } 
}
