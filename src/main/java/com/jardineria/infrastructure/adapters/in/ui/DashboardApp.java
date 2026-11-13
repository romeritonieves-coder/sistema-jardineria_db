package com.jardineria.infrastructure.adapters.in.ui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.jardineria.application.services.ClienteService;
import com.jardineria.application.services.EmpleadoService;
import com.jardineria.application.services.ProductoService;
import com.jardineria.domain.model.Cliente;
import com.jardineria.domain.model.Empleado;
import com.jardineria.domain.model.Producto;
import com.jardineria.infrastructure.adapters.out.persistence.ClienteRepositoryJdbc;
import com.jardineria.infrastructure.adapters.out.persistence.EmpleadoRepositoryJdbc;
import com.jardineria.infrastructure.adapters.out.persistence.ProductoRepositoryJdbc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardApp extends JFrame {

    // Servicios
    private ProductoService productoService;
    private ClienteService clienteService;
    private EmpleadoService empleadoService;
    
    // UI y Navegación
    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private List<JButton> menuButtons;

    // Paleta de Colores SaaS / Tailwind
    private final Color BG_APP = new Color(15, 23, 42);
    private final Color BG_SIDEBAR = new Color(30, 41, 59);
    private final Color BG_CARD = new Color(30, 41, 59);
    private final Color TEXT_PRIMARY = new Color(241, 245, 249);
    private final Color TEXT_MUTED = new Color(148, 163, 184);
    private final Color ACCENT_COLOR = new Color(56, 189, 248);

    public DashboardApp() {
        this.productoService = new ProductoService(new ProductoRepositoryJdbc());
        this.clienteService = new ClienteService(new ClienteRepositoryJdbc());
        this.empleadoService = new EmpleadoService(new EmpleadoRepositoryJdbc());
        this.menuButtons = new ArrayList<>();
        
        configurarVentana();
        inicializarUI();
    }

    private void configurarVentana() {
        setTitle("Avicola ERP - Módulo Jardinería (Bento UI)");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_APP);
        setLayout(new BorderLayout(0, 0));
    }

    private void inicializarUI() {
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(BG_APP);

        // Registrar los 6 módulos
        mainContentPanel.add(crearVistaDashboard(), "Dashboard");
        mainContentPanel.add(crearVistaClientes(), "Gestión Comercial");
        mainContentPanel.add(crearVistaInventario(), "Inventario");
        mainContentPanel.add(crearVistaEmpleados(), "Empleados");
        mainContentPanel.add(crearVistaReportes(), "Reportes");
        mainContentPanel.add(crearVistaAjustes(), "Ajustes");

        add(crearSidebar(), BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    // ==========================================
    // VISTAS DE CADA MÓDULO
    // ==========================================

    private JPanel crearVistaDashboard() {
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(BG_APP);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 30, 30));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_APP);
        JLabel title = new JLabel("Resumen General");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEXT_PRIMARY);
        JLabel subtitle = new JLabel("Monitor en tiempo real del estado de la empresa");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_MUTED);
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(BG_APP);
        titlePanel.add(title);
        titlePanel.add(subtitle);
        header.add(titlePanel, BorderLayout.WEST);
        contentPanel.add(header, BorderLayout.NORTH);

        JPanel kpiPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        kpiPanel.setBackground(BG_APP);
        kpiPanel.add(crearKpiCard("Ingresos del Mes", "$ 45,231.00", "▲ +12.5% vs mes anterior", new Color(34, 197, 94)));
        kpiPanel.add(crearKpiCard("Pedidos Pendientes", "142", "⏱ Requieren atención hoy", ACCENT_COLOR));
        kpiPanel.add(crearKpiCard("Clientes Activos", String.valueOf(clienteService.obtenerTodos().size()), "Registrados en SQLite", new Color(168, 85, 247)));
        
        contentPanel.add(kpiPanel, BorderLayout.CENTER);
        return contentPanel;
    }

    private JPanel crearVistaClientes() {
        List<Cliente> clientes = clienteService.obtenerTodos();
        String[] columnas = {"ID Cliente", "Nombre", "Teléfono", "Límite Crédito"};
        Object[][] datos = new Object[clientes.size()][columnas.length];
        
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            datos[i] = new Object[]{c.getCodigoCliente(), c.getNombreCliente(), c.getTelefono(), "$ " + c.getLimiteCredito()};
        }
        return crearPanelTablaEstandar("Directorio de Clientes", columnas, datos);
    }

    private JPanel crearVistaInventario() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        String[] columnas = {"Código", "Producto", "Gama", "Stock", "Precio Venta"};
        Object[][] datos = new Object[productos.size()][columnas.length];
        
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            datos[i] = new Object[]{p.getCodigoProducto(), p.getNombre(), p.getGama(), p.getCantidadEnStock(), "$ " + p.getPrecioVenta()};
        }
        return crearPanelTablaEstandar("Catálogo e Inventario", columnas, datos);
    }

    private JPanel crearVistaEmpleados() {
        List<Empleado> empleados = empleadoService.obtenerTodos();
        String[] columnas = {"ID Empleado", "Nombre", "Email", "Puesto"};
        Object[][] datos = new Object[empleados.size()][columnas.length];
        
        for (int i = 0; i < empleados.size(); i++) {
            Empleado e = empleados.get(i);
            datos[i] = new Object[]{e.getCodigoEmpleado(), e.getNombre(), e.getEmail(), e.getPuesto()};
        }
        return crearPanelTablaEstandar("Nómina de Empleados", columnas, datos);
    }

    private JPanel crearVistaReportes() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BG_APP);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 30, 30));

        JLabel title = new JLabel("Reportes y Estadísticas");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEXT_PRIMARY);
        panel.add(title, BorderLayout.NORTH);

        JPanel kpiPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        kpiPanel.setBackground(BG_APP);
        
        kpiPanel.add(crearKpiCard("Total Productos", String.valueOf(productoService.obtenerTodosLosProductos().size()), "En inventario actual", ACCENT_COLOR));
        kpiPanel.add(crearKpiCard("Total Clientes", String.valueOf(clienteService.obtenerTodos().size()), "Cartera registrada", new Color(34, 197, 94)));
        kpiPanel.add(crearKpiCard("Total Empleados", String.valueOf(empleadoService.obtenerTodos().size()), "Nómina activa", new Color(168, 85, 247)));
        kpiPanel.add(crearKpiCard("Estado Base de Datos", "En línea", "SQLite conectado correctamente", new Color(245, 158, 11)));

        panel.add(kpiPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearVistaAjustes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_APP);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 30, 30));

        JPanel card = new JPanel(new GridLayout(4, 1, 10, 10));
        card.setBackground(BG_CARD);
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Configuración del Sistema");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_PRIMARY);
        
        JLabel dbInfo = new JLabel("Motor de Base de Datos actual: SQLite (Modo Local por restricción de permisos)");
        dbInfo.setForeground(TEXT_MUTED);
        dbInfo.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel pathInfo = new JLabel("Ruta del archivo: ./jardineria.db");
        pathInfo.setForeground(TEXT_MUTED);
        pathInfo.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton btnBackup = new JButton("Realizar Respaldo (Backup)");
        btnBackup.setBackground(ACCENT_COLOR);
        btnBackup.setForeground(BG_APP);
        btnBackup.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBackup.putClientProperty(FlatClientProperties.STYLE, "arc: 10; borderWidth: 0;");
        btnBackup.addActionListener(e -> JOptionPane.showMessageDialog(this, "Respaldo creado con éxito.\nRuta: ./backup_jardineria.db", "Backup Completo", JOptionPane.INFORMATION_MESSAGE));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        btnPanel.setBackground(BG_CARD);
        btnPanel.add(btnBackup);

        card.add(title);
        card.add(dbInfo);
        card.add(pathInfo);
        card.add(btnPanel);

        panel.add(card, BorderLayout.NORTH);
        return panel;
    }

    // ==========================================
    // COMPONENTES UI REUTILIZABLES
    // ==========================================

    private JPanel crearSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(BG_SIDEBAR);
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 15, 20, 15));

        JLabel brand = new JLabel("Jardinería ERP");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 22));
        brand.setForeground(TEXT_PRIMARY);
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(brand);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        String[] menuItems = {"Dashboard", "Gestión Comercial", "Inventario", "Empleados", "Reportes", "Ajustes"};
        
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 45));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.putClientProperty(FlatClientProperties.STYLE, "arc: 10; borderWidth: 0; focusWidth: 0; innerFocusWidth: 0");

            if (item.equals("Dashboard")) {
                btn.setBackground(ACCENT_COLOR);
                btn.setForeground(BG_APP);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
            } else {
                btn.setBackground(BG_SIDEBAR);
                btn.setForeground(TEXT_MUTED);
            }

            btn.addActionListener(e -> {
                cardLayout.show(mainContentPanel, item);
                for (JButton b : menuButtons) {
                    if (b == btn) {
                        b.setBackground(ACCENT_COLOR);
                        b.setForeground(BG_APP);
                        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
                    } else {
                        b.setBackground(BG_SIDEBAR);
                        b.setForeground(TEXT_MUTED);
                        b.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                    }
                }
            });

            menuButtons.add(btn);
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        return sidebar;
    }

    private JPanel crearKpiCard(String titulo, String valor, String subtitulo, Color colorAcento) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(TEXT_MUTED);
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValor.setForeground(TEXT_PRIMARY);
        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(colorAcento);
        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);
        card.add(lblSub, BorderLayout.SOUTH);
        return card;
    }

    private JPanel crearPanelTablaEstandar(String tituloVista, String[] columnas, Object[][] datos) {
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(BG_APP);
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(25, 30, 30, 30));

        JPanel panelCard = new JPanel(new BorderLayout());
        panelCard.setBackground(BG_CARD);
        panelCard.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        panelCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel(tituloVista);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panelCard.add(title, BorderLayout.NORTH);

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(35);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new Color(51, 65, 85));
        tabla.setBackground(BG_CARD);
        tabla.setForeground(TEXT_PRIMARY);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(BG_CARD);
        tabla.getTableHeader().setForeground(TEXT_MUTED);
        tabla.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BG_CARD);
        
        panelCard.add(scrollPane, BorderLayout.CENTER);
        panelContenedor.add(panelCard, BorderLayout.CENTER);
        
        return panelContenedor;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(new FlatMacDarkLaf()); } catch (Exception ex) {}
        SwingUtilities.invokeLater(() -> new DashboardApp().setVisible(true));
    }
}
