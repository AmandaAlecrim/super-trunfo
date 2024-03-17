package com.supertrunfo;

import com.supertrunfo.model.Carta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class JogoDeCartas extends JFrame {
    private ArrayList<Carta> cartasJogador;
    private JPanel panelAtual;
    private JPanel panelJogo;
    private JPanel panelEscolhaCarta;

    private JButton botaoForca;
    private JButton botaoInteligencia;
    private JButton botaoVelocidade;

    private String atributo;

    public JogoDeCartas() {
        setTitle("Jogo de Cartas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        cartasJogador = new ArrayList<>();
        cartasJogador.add(new Carta("Bulldog", 6, 4, 5));
        cartasJogador.add(new Carta("Australian Shepherd", 5, 3, 7));
        cartasJogador.add(new Carta("Doberman", 7, 3, 5));

        panelJogo = new JPanel(new BorderLayout());
        panelAtual = panelJogo;

        JPanel panelMenu = new JPanel(new BorderLayout());
        JLabel rotuloRodada = new JLabel("Rodada 1");
        rotuloRodada.setHorizontalAlignment(SwingConstants.CENTER);
        panelMenu.add(rotuloRodada, BorderLayout.NORTH);

        JPanel panelCartas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        for (Carta carta : cartasJogador) {
            JPanel panelCarta = new JPanel(new GridBagLayout());
            panelCarta.setPreferredSize(new Dimension(160, 100));
            panelCarta.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Adiciona a borda cinza

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;

            JLabel rotuloCarta = new JLabel(carta.toString(), SwingConstants.CENTER);
            panelCarta.add(rotuloCarta, gbc);

            panelCartas.add(panelCarta);
        }
        panelMenu.add(panelCartas, BorderLayout.CENTER);

        JPanel panelAtributo = new JPanel(new BorderLayout());

        JPanel panelRotuloAtributo = new JPanel(new BorderLayout());
        JLabel rotuloAtributo = new JLabel("Escolha o atributo:");
        rotuloAtributo.setHorizontalAlignment(SwingConstants.CENTER);
        panelRotuloAtributo.add(rotuloAtributo, BorderLayout.NORTH);
        panelAtributo.add(panelRotuloAtributo);

        JPanel panelOpcoesAtributo = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botaoForca = new JButton("Força");
        botaoForca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atributo = "Força";
                botaoForca.setEnabled(false);
                mostrarEscolherCarta();
            }
        });
        panelOpcoesAtributo.add(botaoForca);

        botaoInteligencia = new JButton("Inteligência");
        botaoInteligencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atributo = "Inteligência";
                botaoInteligencia.setEnabled(false);
                mostrarEscolherCarta();
            }
        });
        panelOpcoesAtributo.add(botaoInteligencia);

        botaoVelocidade = new JButton("Velocidade");
        botaoVelocidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atributo = "Velocidade";
                botaoVelocidade.setEnabled(false);
                mostrarEscolherCarta();
            }
        });
        panelOpcoesAtributo.add(botaoVelocidade);
        panelAtributo.add(panelOpcoesAtributo, BorderLayout.SOUTH);

        panelMenu.add(panelAtributo, BorderLayout.SOUTH);

        panelJogo.add(panelMenu, BorderLayout.CENTER);
        adicionarComponenteCentralizado(panelJogo, 0, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        construirPainelEscolhaCarta(atributo);
        pack();
        setLocationRelativeTo(null);
    }

    // Método para adicionar componente centralizado ao JFrame usando GridBagLayout
    private void adicionarComponenteCentralizado(Component componente, int gridx, int gridy, int gridwidth, int gridheight, int fill, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.fill = fill;
        gbc.anchor = anchor;
        gbc.weightx = 1.0; // Estica o componente horizontalmente
        gbc.weighty = 1.0; // Estica o componente verticalmente
        getContentPane().add(componente, gbc);
    }

    private void construirPainelEscolhaCarta(String atributo) {
        panelEscolhaCarta = new JPanel(new BorderLayout());
        List<Carta> nomesCartas = cartasJogador;

        JLabel labelTexto = new JLabel("Escolha sua carta:");
        labelTexto.setHorizontalAlignment(SwingConstants.CENTER);
        panelEscolhaCarta.add(labelTexto, BorderLayout.NORTH);

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Painel para os botões
        for (int i = 0; i < nomesCartas.size(); i++) {
            final int index = i;
            JButton botaoCarta = new JButton(nomesCartas.get(index).escreveCarta(atributo));
            botaoCarta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(JogoDeCartas.this, "Você escolheu a carta:\n" + nomesCartas.get(index).getNome() + "\nFOR: " + nomesCartas.get(index).getForca() + "\nINT: " + nomesCartas.get(index).getInteligencia() + "\nVEL: " + nomesCartas.get(index).getVelocidade(), "Carta Escolhida", JOptionPane.INFORMATION_MESSAGE);
                    voltarAoPainelJogo();
                }
            });
            panelBotoes.add(botaoCarta);
        }

        panelEscolhaCarta.add(panelBotoes, BorderLayout.CENTER);
    }

    private void mostrarEscolherCarta() {
        getContentPane().remove(panelAtual);
        getContentPane().add(panelEscolhaCarta, 0); // Adiciona o panel diretamente ao content pane do JFrame
        validate();
        repaint();
        panelAtual = panelEscolhaCarta;
    }

    private void voltarAoPainelJogo() {
        getContentPane().remove(panelAtual);
        getContentPane().add(panelJogo, 0); // Adiciona o panel diretamente ao content pane do JFrame
        validate();
        repaint();
        panelAtual = panelJogo;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JogoDeCartas().setVisible(true);
            }
        });
    }
}
