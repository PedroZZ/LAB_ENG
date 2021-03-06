package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Usuario;
import connection.ConnectionImpl;
import connection.GenericConnection;
import exception.ClienteDaoException;

public class ClienteDaoImpl implements ClienteDao {

	private Connection c;

	public ClienteDaoImpl() {
		GenericConnection gc = new ConnectionImpl();
		c = gc.getConnection();
	}

	@Override
	public void incluiCliente(Cliente cli) throws ClienteDaoException, SQLException {

		String query = "INSERT INTO CLIENTE VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = c.prepareStatement(query);
		
		ps.setInt(1, cli.getUsuario().getIdUsuario());
		ps.setString(2, cli.getNome());
		ps.setString(3, cli.getTelefone());
		ps.setString(4, cli.getCep());
		ps.setString(5, cli.getUf());
		ps.setString(6, cli.getCidade());
		ps.setString(7, cli.getBairro());
		ps.setString(8, cli.getRua());
		ps.setInt(9, cli.getNum());
		ps.setString(10, cli.getComplemento());
		
		ps.execute();
		
		
		ps.close();
	}

	@Override
	public List<Cliente> pesquisaCliente(Cliente cli) throws SQLException {
		List<Cliente> lista = new ArrayList<Cliente>();
		String query = "select * from cliente where id = ?";
		try {
			PreparedStatement ps = c.prepareStatement(query);
			ps.setInt(1, cli.getIdCliente());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Cliente c = new Cliente();

				c.setIdCliente(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setTelefone(rs.getString("telefone"));
				c.setCep(rs.getString("cep"));
				c.setUf(rs.getString("uf"));
				c.setCidade(rs.getString("cidade"));
				c.setBairro(rs.getString("bairro"));
				c.setRua(rs.getString("rua"));
				c.setNum(rs.getInt("num"));
				c.setComplemento(rs.getString("complemento"));

				lista.add(c);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public void alteraCliente(Cliente cli) throws SQLException {
		String query = "UPDATE cliente SET nome = ?, telefone=?, cep=?, uf=?, cidade=?,"
				+ " bairro=?, rua=?, num=?, complemento=? WHERE id = ?";
		PreparedStatement ps = c.prepareStatement(query);

		ps.setString(1, cli.getNome());
		ps.setString(2, cli.getTelefone());
		ps.setString(3, cli.getCep());
		ps.setString(4, cli.getUf());
		ps.setString(5, cli.getCidade());
		ps.setString(6, cli.getBairro());
		ps.setString(7, cli.getRua());
		ps.setInt(8, cli.getNum());
		ps.setString(9, cli.getComplemento());
		ps.setInt(10, cli.getIdCliente());

		ps.execute();
		ps.close();

	}

	@Override
	public void excluiCliente(Cliente cli) throws SQLException {
		String query = "DELETE cliente " + " where id = ?";
		PreparedStatement ps = c.prepareStatement(query);

		ps.setInt(1, cli.getIdCliente());

		ps.execute();
		ps.close();
	}

	@Override
	public Cliente getCliente(String email) throws SQLException {
		Cliente cli = new Cliente();
		String query = "select cliente.id, cliente.idUsuario, cliente.nome, cliente.telefone, "
						+ "cliente.cep, cliente.uf, cliente.cidade, cliente.bairro, cliente.rua, "
						+ "cliente.num, cliente.complemento from cliente "
						+ "inner join usuario "
						+ "on cliente.idUsuario = usuario.id "
						+ "where usuario.email = ?";
		
		PreparedStatement ps = c.prepareStatement(query);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		
		if ( rs.next() ) {
			Usuario u = new Usuario();
			cli.setIdCliente(rs.getInt("id"));
			u.setIdUsuario(rs.getInt("idUsuario"));
			cli.setUsuario(u);
			cli.setNome(rs.getString("nome"));
			cli.setTelefone(rs.getString("telefone"));
			cli.setCep(rs.getString("cep"));
			cli.setUf(rs.getString("uf"));
			cli.setCidade(rs.getString("cidade"));
			cli.setBairro(rs.getString("bairro"));
			cli.setRua(rs.getString("rua"));
			cli.setNum(rs.getInt("num"));
			cli.setComplemento(rs.getString("complemento"));
		}
		return cli;
	}

}
