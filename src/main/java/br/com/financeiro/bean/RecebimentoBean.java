package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.GrupoRecebimentoDAO;
import br.com.financeiro.dao.RecebimentoDAO;
import br.com.financeiro.domain.GrupoRecebimento;
import br.com.financeiro.domain.Recebimento;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RecebimentoBean implements Serializable {
	private Recebimento recebimento;
	private List<Recebimento> recebimentos;
	private List<GrupoRecebimento> grupoRecebimentos;

	public Recebimento getRecebimento() {
		return recebimento;
	}

	public void setRecebimento(Recebimento recebimento) {
		this.recebimento = recebimento;
	}

	public List<Recebimento> getRecebimentos() {
		return recebimentos;
	}

	public void setRecebimentos(List<Recebimento> recebimentos) {
		this.recebimentos = recebimentos;
	}

	public List<GrupoRecebimento> getGrupoRecebimentos() {
		return grupoRecebimentos;
	}

	public void setGrupoRecebimentos(List<GrupoRecebimento> grupoRecebimentos) {
		this.grupoRecebimentos = grupoRecebimentos;
	}

	public void novo() {
		recebimento = new Recebimento();
		try {
			GrupoRecebimentoDAO grupoRecebimentoDAO = new GrupoRecebimentoDAO();
			grupoRecebimentos = grupoRecebimentoDAO.listar("descricao");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro inesperado.");
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			RecebimentoDAO recebimentoDAO = new RecebimentoDAO();
			recebimentos = recebimentoDAO.listar("data");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar os registros.");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			RecebimentoDAO recebimentoDAO = new RecebimentoDAO();
			recebimentoDAO.salvar(recebimento);
			recebimentos = recebimentoDAO.listar("data");
			GrupoRecebimentoDAO grupoRecebimentoDAO = new GrupoRecebimentoDAO();
			grupoRecebimentos = grupoRecebimentoDAO.listar("descricao");
			novo();
			Messages.addGlobalInfo("Registro salvo com sucesso.");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar o registro.");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent event) {
		recebimento = (Recebimento) event.getComponent().getAttributes().get("recebimentoSelecionado");
		try {
			RecebimentoDAO recebimentoDAO = new RecebimentoDAO();
			recebimentoDAO.excluir(recebimento);
			recebimentos = recebimentoDAO.listar("data");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao excluir o registro.");
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent event) {
		recebimento = (Recebimento) event.getComponent().getAttributes().get("recebimentoSelecionado");
		try {
			GrupoRecebimentoDAO grupoRecebimentoDAO = new GrupoRecebimentoDAO();
			grupoRecebimentos = grupoRecebimentoDAO.listar("descricao");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar os registros.");
			e.printStackTrace();
		}
	}
}
