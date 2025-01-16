package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class formLogin {
    private JTextField textUsuario;
    private JPasswordField passwordContra;
    private JButton inicioButton;
    private JPanel pantallaLogin;
    private  JFrame frame;


    public formLogin() {
        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textUsuario.getText();
                String contra = new String(passwordContra.getPassword());

                System.out.println(usuario);
                System.out.println(contra);

                // Instanciar la clase de la conexion
                ConexionMySQL conexion = new ConexionMySQL();
                boolean credencialesValidas = conexion.verificarCredenciales(usuario,contra);

                if (credencialesValidas){
                    JOptionPane.showMessageDialog(frame,"Inicio de sesion exitoso");
                    formGestCalificaciones calificaciones = new formGestCalificaciones(frame);
                    calificaciones.iniciarVentanaCalificacion();
                    frame.dispose();
                }else {
                    JOptionPane.showMessageDialog(frame,"Usuario o contraseña incorrectos",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }

    public void ventanaLogin(){
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(pantallaLogin);
        frame.setSize(800,600);
        frame.setVisible(true);
    }



}
