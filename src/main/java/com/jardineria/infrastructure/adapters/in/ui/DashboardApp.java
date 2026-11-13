package com.jardineria.infrastructure.adapters.in.ui;

import com.formdev.flatlaf.FlatLightLaf;
import com.jardineria.application.services.ProductoService;
import com.jardineria.infrastructure.adapters.out.persistence.ProductoRepositoryJdbc;
import com.jardineria.domain.model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DashboardApp extends JFrame {

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private ProductoService productoService;

    public DashboardApp() {
        this.productoService = new ProductoService(new ProductoRepositoryJdbc());
        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión de Jardinería - Dashboard (SQLite)");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        JPanel panelMenu = new JPanel(new GridLayout(6, 1, 10, 10));
        panelMenu.setPreferredSize(new Dimension(200, 0));
        panelMenu.setBackground(new Color(45, 52, 54));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        String[] opcionesMenu = {"Dashboard", "Gestión Comercial", "Catálogo e Inventario", "Estructura Organizacional", "Configuración"};
        for (String opcion : opcionesMenu) {
            JButton btn = new JButton(opcion);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(99, 110, 114));
            btn.setFocusPainted(false);
            panelMenu.add(btn);
        }
        add(panelMenu, BorderLayout.WEST);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createTitledBorder("Inventario de Productos"));
        
        modeloTabla = new DefaultTableModel(new String[]{"Código", "Nombre", "Gama", "Stock", "Precio"}, 0);
        tablaProductos = new JTable(modeloTabla);
        panelCentral.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        for (Producto p : productos) {
            modeloTabla.addRow(new Object[]{
                    p.getCodigoProducto(),
                    p.getNombre(),
                    p.getGama(),
                    p.getCantidadEnStock(),
                    p.getPrecioVenta()
            });
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Error al inicializar FlatLaf");
        }
        SwingUtilities.invokeLater(() -> new DashboardApp().setVisible(true));
    }
}
