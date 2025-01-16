package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class formGestCalificaciones {
    private JTextField nMateria1;
    private JTextField nMateria2;
    private JTextField nMateria3;
    private JTextField nMateria4;
    private JPanel ventanaCalificaciones;
    private JButton registrarButton;
    private JTextField nMateria5;
    private JTextField textCedula;
    private JTextField textNombre;
    private JFrame loginFrame;

    public formGestCalificaciones(JFrame loginFrame){
        this.loginFrame = loginFrame;
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cedula = validarCedula(textCedula.getText());

                    String nombre = textNombre.getText().trim();
                    if(nombre.isEmpty()){
                        throw new IllegalArgumentException("El nombre esta vacio");
                    }

                    double materia1 = validarNota(nMateria1.getText(),"Materia 1");
                    double materia2 = validarNota(nMateria2.getText(),"Materia 2");
                    double materia3 = validarNota(nMateria3.getText(),"Materia 3");
                    double materia4 = validarNota(nMateria4.getText(),"Materia 4");
                    double materia5 = validarNota(nMateria5.getText(),"Materia 5");

                    // Si todas las validaciones son exitosas , mostrar mensaje
                    JOptionPane.showMessageDialog(null,"Todas las notas son validas");

                    // Intentar insertar los datos
                    ConexionMySQL conexion = new ConexionMySQL();
                    boolean exito = conexion.insertarEstudiante(cedula,nombre,materia1,materia2,materia3,materia4,materia5);
                    if(exito){
                        JOptionPane.showMessageDialog(null,"Estudiante registrado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null,"Error al registrar el estudiante","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }

    private  double validarNota(String textoNota, String nombreCampo){
        try {
            double nota = Double.parseDouble(textoNota);
            if(nota <0 || nota> 20){
                throw new IllegalArgumentException(nombreCampo + "debe estar entre 0 y 20.");
            }
            return  nota;
        }catch (NumberFormatException e){
            throw  new IllegalArgumentException(nombreCampo + "debe ser un número valido");
        }
    }

    public String validarCedula(String textoCedula){
        if(!textoCedula.matches("\\d{10}")){
            throw new IllegalArgumentException("La cedula debe contener exactamente 10 digitos");
        }
        return textoCedula;
    }

    public void iniciarVentanaCalificacion(){
        JFrame frame = new JFrame("Calificaciones");
        frame.setContentPane(ventanaCalificaciones);
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                loginFrame.setVisible(true);
            }
        });
    }
}
