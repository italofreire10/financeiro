package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.GrupoRecebimentoDAO;
import br.com.financeiro.domain.GrupoRecebimento;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class GrupoRecebimentoBean implements Serializable {
	private GrupoRecebimento grupoRecebimento;
	private List<GrupoRecebimento> grupoRecebimentos;

	public GrupoRecebimento getGrupoRecebimento() {
		return grupoRecebimento;
	}

	public void setGrupoRecebimento(GrupoRecebimento grupoRecebimento) {
		this.grupoRecebimento = grupoRecebimento;
	}

	public List<GrupoRecebimento> getGrupoRecebimentos() {
		return grupoRecebimentos;
	}

	public void setGrupoRecebimentos(List<GrupoRecebimento> grupoRecebimentos) {
		this.grupoRecebimentos = grupoRecebimentos;
	}

	public void novo() {
		grupoRecebimento = new GrupoRecebimento();
	}

	public void salvar() {
		try {
			GrupoRecebimentoDAO grupoRecebimentoDAO = new GrupoRecebimentoDAO();
			grupoRecebimentoDAO.salvar(grupoRecebimento);
			grupoRecebimentos = grupoRecebimentoDAO.listar();
			Messages.addGlobalInfo("Registro salvo com sucesso.");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar.");
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			GrupoRecebimentoDAO grupoRecebimentoDAO = new GrupoRecebimentoDAO();
			grupoRecebimentos = grupoRecebimentoDAO.listar();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar.");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent event) {
		grupoRecebimento = (GrupoRecebimento) event.getComponent().getAttributes().get("grupoRecebimentoSelecionado");
		try {
			GrupoRecebimentoDAO grupoRecebimentoDAO = new GrupoRecebimentoDAO();
			grupoRecebimentoDAO.excluir(grupoRecebimento);
			grupoRecebimentos = grupoRecebimentoDAO.listar();
			Messages.addGlobalInfo("Registro excluído com sucesso.");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao excluir.");
		}
	}
	
	public void editar(ActionEvent event) {
		grupoRecebimento = (GrupoRecebimento) event.getComponent().getAttributes().get("grupoRecebimentoSelecionado");
	}

}
