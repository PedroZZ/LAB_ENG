package managedBean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Produto;

@ManagedBean
@SessionScoped
public class CarrinhoMB {
	
	private List<Produto> itensCarrinho;
	
	public CarrinhoMB() {
		itensCarrinho = new ArrayList<Produto>();
	}
	
	public List<Produto> getItensCarrinho() {
		return itensCarrinho;
	}

	public void setItensCarrinho(List<Produto> itensCarrinho) {
		this.itensCarrinho = itensCarrinho;
	}
	
	public void adiciona(Produto p) {
		this.itensCarrinho.add(p);
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage("Seu carrinho tem :  " + itensCarrinho.size() + " produtos" ));
	}
	
	public void finalizarCompra() {
		double total;
		total = itensCarrinho.stream().mapToDouble(i -> i.getValor()).sum();
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage("Compra Finalizada. Total : " + total));
		itensCarrinho = new  ArrayList<Produto>();
	}
	
	public void excluir(Produto p) {
		this.itensCarrinho.remove(p);
	}
	
}
