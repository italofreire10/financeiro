package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.GrupoPagamentoDAO;
import br.com.financeiro.domain.GrupoPagamento;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class GrupoPagamentoBean implements Serializable {

	private GrupoPagamento grupoPagamento;
	private List<GrupoPagamento> grupoPagamentos;

	public GrupoPagamento getGrupoPagamento() {
		return grupoPagamento;
	}

	public void setGrupoPagamento(GrupoPagamento grupoPagamento) {
		this.grupoPagamento = grupoPagamento;
	}

	public List<GrupoPagamento> getGrupoPagamentos() {
		return grupoPagamentos;
	}

	public void setGrupoPagamentos(List<GrupoPagamento> grupoPagamentos) {
		this.grupoPagamentos = grupoPagamentos;
	}

	public void novo() {
		grupoPagamento = new GrupoPagamento();
	}

	public void salvar() {
		try {
			GrupoPagamentoDAO grupoPagamentoDAO = new GrupoPagamentoDAO();
			grupoPagamentoDAO.merge(grupoPagamento);
			grupoPagamentos = grupoPagamentoDAO.listar();
			novo();
			Messages.addGlobalInfo("Registro salvo com sucesso.");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar.");
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			GrupoPagamentoDAO grupoPagamentoDAO = new GrupoPagamentoDAO();
			grupoPagamentos = grupoPagamentoDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar.");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent event) {
		grupoPagamento = (GrupoPagamento) event.getComponent().getAttributes().get("grupoPagamentoSelecionado");
		try {
			GrupoPagamentoDAO grupoPagamentoDAO = new GrupoPagamentoDAO();
			grupoPagamentoDAO.excluir(grupoPagamento);
			grupoPagamentos = grupoPagamentoDAO.listar();
			Messages.addGlobalInfo("Registro excluído com sucesso.");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao excluir.");
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent event) {
		grupoPagamento = (GrupoPagamento) event.getComponent().getAttributes().get("grupoPagamentoSelecionado");
	}
}
