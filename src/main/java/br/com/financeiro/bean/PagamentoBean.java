package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.GrupoPagamentoDAO;
import br.com.financeiro.dao.PagamentoDAO;
import br.com.financeiro.dao.ParcelaDAO;
import br.com.financeiro.domain.GrupoPagamento;
import br.com.financeiro.domain.Pagamento;
import br.com.financeiro.domain.Parcela;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PagamentoBean implements Serializable {
	private Pagamento pagamento;
	private List<Pagamento> pagamentos;
	private List<GrupoPagamento> grupoPagamentos;
	private List<Parcela> parcelas;

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public List<GrupoPagamento> getGrupoPagamentos() {
		return grupoPagamentos;
	}

	public void setGrupoPagamentos(List<GrupoPagamento> grupoPagamentos) {
		this.grupoPagamentos = grupoPagamentos;
	}

	public List<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}

	public void novo() {
		pagamento = new Pagamento();
		GrupoPagamentoDAO grupoPagamentoDAO = new GrupoPagamentoDAO();
		grupoPagamentos = grupoPagamentoDAO.listar();
		ParcelaDAO parcelaDAO = new ParcelaDAO();
		parcelas = parcelaDAO.listar();
	}

	@PostConstruct
	public void listar() {
		try {
			PagamentoDAO pagamentoDAO = new PagamentoDAO();
			pagamentos = pagamentoDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar.");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			PagamentoDAO pagamentoDAO = new PagamentoDAO();
			pagamentoDAO.salvar(pagamento);
			pagamentos = pagamentoDAO.listar();
			novo();
			Messages.addGlobalInfo("Registro salvo com sucesso.");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar.");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent event) {
		pagamento = (Pagamento) event.getComponent().getAttributes().get("pagamentoSelecionado");
		try {
			PagamentoDAO pagamentoDAO = new PagamentoDAO();
			pagamentoDAO.excluir(pagamento);
			pagamentos = pagamentoDAO.listar();
			Messages.addGlobalInfo("Registro excluído com sucesso.");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao excluir.");
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent event) {
		pagamento = (Pagamento) event.getComponent().getAttributes().get("pagamentoSelecionado");
		try {
			GrupoPagamentoDAO grupoPagamentoDAO = new GrupoPagamentoDAO();
			grupoPagamentos = grupoPagamentoDAO.listar();
			ParcelaDAO parcelaDAO = new ParcelaDAO();
			parcelas = parcelaDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar informações.");
			e.printStackTrace();
		}
	}
}
