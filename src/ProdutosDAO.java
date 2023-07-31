/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    conectaDAO controller = new conectaDAO();

    public void cadastrarProduto(ProdutosDTO produto) {
        conn = controller.connectDB();

        try {

            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES(?,?,?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto salvo com sucesso!:");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Não foi possível salvar o produto. ERRO:" + erro.getMessage());
        } finally {
            controller.disconnectDB();
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        conn = controller.connectDB();

        try {

            prep = conn.prepareStatement("SELECT * FROM produtos");
            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO prod = new ProdutosDTO();
                prod.setId(resultset.getInt("id"));
                prod.setNome(resultset.getString("nome"));
                prod.setValor(resultset.getInt("valor"));
                prod.setStatus(resultset.getString("status"));

                listagem.add(prod);
            }
            return listagem;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar os produtos do banco de dados" + erro.getMessage());
        } finally {
            controller.disconnectDB();
        }
        return null;
    }
    public void venderProduto(int id) {
        conn = controller.connectDB();

        try {

            prep = conn.prepareStatement("UPDATE produtos SET status = ? WHERE id = ?");
            prep.setString(1, "Vendido");
            prep.setInt(2, id);

            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!:");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Não foi possível atualizar o status do produto. ERRO:" + erro.getMessage());
        } finally {
            controller.disconnectDB();
        }
    }
}
