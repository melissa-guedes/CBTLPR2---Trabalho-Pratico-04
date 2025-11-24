import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TP04 extends JFrame {

    private JTextField txtBusca;
    private JTextField txtNome, txtSalario, txtCargo;
    private Connection conn;
    private ResultSet rs;

    private JButton btnPesquisar, btnAnterior, btnProximo;

    public TP04() {
        super("TRABALHO PRATICO 04");

        setLayout(null);
        setSize(500, 250);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            DB db = new DB();
            db.connect();
            conn = db.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o banco!\n" + e.getMessage());
        }

        JLabel lblBusca = new JLabel("Nome:");
        lblBusca.setBounds(20, 10, 60, 25);
        add(lblBusca);

        txtBusca = new JTextField();
        txtBusca.setBounds(70, 10, 300, 25);
        add(txtBusca);

        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(180, 45, 120, 25);
        btnPesquisar.addActionListener(e -> pesquisar());
        add(btnPesquisar);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 90, 60, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setEditable(false);
        txtNome.setBounds(90, 90, 350, 25);
        add(txtNome);

        JLabel lblSal = new JLabel("Salário:");
        lblSal.setBounds(20, 120, 60, 25);
        add(lblSal);

        txtSalario = new JTextField();
        txtSalario.setEditable(false);
        txtSalario.setBounds(90, 120, 350, 25);
        add(txtSalario);

        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setBounds(20, 150, 60, 25);
        add(lblCargo);

        txtCargo = new JTextField();
        txtCargo.setEditable(false);
        txtCargo.setBounds(90, 150, 350, 25);
        add(txtCargo);

        btnAnterior = new JButton("Anterior");
        btnAnterior.setBounds(100, 185, 120, 25);
        btnAnterior.setEnabled(false);
        add(btnAnterior);

        btnProximo = new JButton("Próximo");
        btnProximo.setBounds(260, 185, 120, 25);
        btnProximo.setEnabled(false);
        add(btnProximo);

        btnProximo.addActionListener(e -> navegarProximo());
        btnAnterior.addActionListener(e -> navegarAnterior());
    }

    private void pesquisar() {
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Sem conexão com o banco!");
            return;
        }

        try {
            String sql = "SELECT f.nome_func, f.sal_func, c.ds_cargo " +
                         "FROM tbfuncs f " +
                         "INNER JOIN tbcargos c ON f.cod_cargo = c.cd_cargo " +
                         "WHERE f.nome_func LIKE ?";

            PreparedStatement pst = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            pst.setString(1, "%" + txtBusca.getText() + "%");
            rs = pst.executeQuery();

            if (rs.next()) {
                mostrarRegistroAtual();
                btnAnterior.setEnabled(false);
                btnProximo.setEnabled(!rs.isLast());
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum registro encontrado!");
                limparCampos();
                btnAnterior.setEnabled(false);
                btnProximo.setEnabled(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro na consulta SQL: " + e.getMessage());
        }
    }

    private void mostrarRegistroAtual() {
        try {
            txtNome.setText(rs.getString("nome_func"));
            txtSalario.setText(rs.getString("sal_func"));
            txtCargo.setText(rs.getString("ds_cargo"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtSalario.setText("");
        txtCargo.setText("");
    }

    private void navegarProximo() {
        try {
            if (rs != null && rs.next()) {
                mostrarRegistroAtual();
                btnAnterior.setEnabled(true);
                btnProximo.setEnabled(!rs.isLast());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navegarAnterior() {
        try {
            if (rs != null && rs.previous()) {
                mostrarRegistroAtual();
                btnProximo.setEnabled(true);
                btnAnterior.setEnabled(!rs.isFirst());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TP04().setVisible(true));
    }
}
