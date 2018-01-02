package br.com.financeiro.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
	private Date dataInicio, dataFim;

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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void novo() {
		pagamento = new Pagamento();
		GrupoPagamentoDAO grupoPagamentoDAO = new GrupoPagamentoDAO();
		grupoPagamentos = grupoPagamentoDAO.listar("descricao");
		ParcelaDAO parcelaDAO = new ParcelaDAO();
		parcelas = parcelaDAO.listar("descricao");
	}

	@PostConstruct
	public void listar() {
		try {
			PagamentoDAO pagamentoDAO = new PagamentoDAO();
			pagamentos = pagamentoDAO.listar("data");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar.");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			PagamentoDAO pagamentoDAO = new PagamentoDAO();
			pagamentoDAO.salvar(pagamento);
			pagamentos = pagamentoDAO.listar("data");
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
			pagamentos = pagamentoDAO.listar("data");
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
			grupoPagamentos = grupoPagamentoDAO.listar("descricao");
			ParcelaDAO parcelaDAO = new ParcelaDAO();
			parcelas = parcelaDAO.listar("descricao");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar informações.");
			e.printStackTrace();
		}
	}

	public void listarFiltro() {		
		listar();
		Iterator<Pagamento> busca = pagamentos.iterator();
		while (busca.hasNext()) {
			Pagamento item = busca.next();
			if (item.getData().before(getDataInicio()) == true || item.getData().after(getDataFim())) {
				busca.remove();
			}
		}
	}
}