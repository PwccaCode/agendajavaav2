package main;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

public class AgendaJanela {

	private JFrame frame;
	private JTextField txtnome;
	private JTextField txttelefone;
	private JTextField txtrua;
	private JTextField txtbairro;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgendaJanela window = new AgendaJanela();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AgendaJanela() {
		initialize();
		Conectar();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField txtbusca;
	
	
	public void Conectar() {
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/59ztuyhdH2","59ztuyhdH2", "5ks3iu9vz2");
		}
		catch (ClassNotFoundException ex)
		{}
		catch (SQLException ex)
		{}
		
	}
	
	
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from agenda");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel (rs));
		}
		
		catch (SQLException e){
			
			e.printStackTrace();
			
		}
		
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 794, 339);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Agenda");
		lblNewLabel.setFont(new Font("Myanmar Text", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(357, 11, 69, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cadastro", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, null));
		panel.setBounds(36, 33, 270, 164);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(10, 30, 43, 21);
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 17));
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Telefone");
		lblNewLabel_1_1.setFont(new Font("Calibri", Font.BOLD, 17));
		lblNewLabel_1_1.setBounds(10, 62, 62, 21);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Rua");
		lblNewLabel_1_2.setFont(new Font("Calibri", Font.BOLD, 17));
		lblNewLabel_1_2.setBounds(10, 94, 43, 21);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Bairro");
		lblNewLabel_1_3.setFont(new Font("Calibri", Font.BOLD, 17));
		lblNewLabel_1_3.setBounds(10, 126, 43, 21);
		panel.add(lblNewLabel_1_3);
		
		txtnome = new JTextField();
		txtnome.setBounds(97, 29, 152, 20);
		panel.add(txtnome);
		txtnome.setColumns(10);
		
		txttelefone = new JTextField();
		txttelefone.setColumns(10);
		txttelefone.setBounds(97, 61, 152, 20);
		panel.add(txttelefone);
		
		txtrua = new JTextField();
		txtrua.setColumns(10);
		txtrua.setBounds(97, 93, 152, 20);
		panel.add(txtrua);
		
		txtbairro = new JTextField();
		txtbairro.setColumns(10);
		txtbairro.setBounds(97, 125, 152, 20);
		panel.add(txtbairro);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nome,telefone,rua,bairro;
				
				nome = txtnome.getText();
				telefone = txttelefone.getText();
				rua = txtrua.getText();
				bairro = txtbairro.getText();
				
				try {
					pst = con.prepareStatement("insert into agenda (nome,telefone,rua,bairro)values(?,?,?,?)");
					pst.setString(1, nome);
					pst.setString(2, telefone);
					pst.setString(3, rua);
					pst.setString(4, bairro);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Adicionado");
					table_load();
					txtnome.setText("");
					txttelefone.setText("");
					txtrua.setText("");
					txtbairro.setText("");
					txtnome.requestFocus();
					
				}
				
				catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 15));
		btnNewButton.setBounds(36, 196, 89, 34);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		btnSair.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSair.setBounds(217, 197, 89, 34);
		frame.getContentPane().add(btnSair);
		
		JButton btnAtualizar = new JButton("Limpar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtnome.setText("");
				txttelefone.setText("");
				txtrua.setText("");
				txtbairro.setText("");
				txtnome.requestFocus();
				
			}
		});
		btnAtualizar.setFont(new Font("Verdana", Font.BOLD, 10));
		btnAtualizar.setBounds(128, 196, 84, 34);
		frame.getContentPane().add(btnAtualizar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(354, 44, 361, 186);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Buscar", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 233, 311, 56);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_4 = new JLabel("Buscar (digite o id)");
		lblNewLabel_1_4.setFont(new Font("Calibri", Font.BOLD, 17));
		lblNewLabel_1_4.setBounds(10, 21, 151, 21);
		panel_1.add(lblNewLabel_1_4);
		
		txtbusca = new JTextField();
		txtbusca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				String id = txtbusca.getText();
				
				try {
					pst = con.prepareStatement("select nome,telefone,rua, bairro from agenda where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
				if(rs.next()==true)
				{
					String nome = rs.getString(1);
					String telefone = rs.getString(2);
					String rua = rs.getString(3);
					String bairro = rs.getString(4);
					
					txtnome.setText(nome);
					txttelefone.setText(telefone);
					txtrua.setText(rua);
					txtbairro.setText(bairro);
					
					
					
				}
				else {
					txtnome.setText("");
					txttelefone.setText("");
					txtrua.setText("");
					txtbairro.setText("");
					
				}
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		txtbusca.setColumns(10);
		txtbusca.setBounds(164, 16, 126, 29);
		panel_1.add(txtbusca);
		
		JButton btnNewButton_1 = new JButton("Atualizar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nome,telefone,rua,bairro,id;
				
				nome = txtnome.getText();
				telefone = txttelefone.getText();
				rua = txtrua.getText();
				bairro = txtbairro.getText();
				id = txtbusca.getText();
				
				try {
					pst = con.prepareStatement("update agenda set nome=?, telefone=?, rua=?, bairro=? where id =?");
					pst.setString(1, nome);
					pst.setString(2, telefone);
					pst.setString(3, rua);
					pst.setString(4, bairro);
					pst.setString(5, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Atualizado");
					table_load();
					txtnome.setText("");
					txttelefone.setText("");
					txtrua.setText("");
					txtbairro.setText("");
					txtnome.requestFocus();
					
				}
				
				catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				
				
				
				
				
				
			}
		});
		btnNewButton_1.setBounds(357, 241, 157, 48);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Deletar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String id;
				

				id = txtbusca.getText();
				
				try {
					pst = con.prepareStatement("delete from agenda where id =?");
					pst.setString(1, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Deletado");
					table_load();
					txtnome.setText("");
					txttelefone.setText("");
					txtrua.setText("");
					txtbairro.setText("");
					txtnome.requestFocus();
					
				}
				
				catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				
				
			}
		});
		btnNewButton_2.setBounds(562, 241, 153, 48);
		frame.getContentPane().add(btnNewButton_2);
	}
}
