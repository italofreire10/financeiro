package br.com.financeiro.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.PagamentoDAO;
import br.com.financeiro.dao.RecebimentoDAO;
import br.com.financeiro.domain.Pagamento;
import br.com.financeiro.domain.Recebimento;

public class BalancoBean {
	private List<Pagamento> pagamentos;
	private List<Recebimento> recebimentos;
	private BigDecimal totalPagamento, totalRecebimento;

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public List<Recebimento> getRecebimentos() {
		return recebimentos;
	}

	public void setRecebimentos(List<Recebimento> recebimentos) {
		this.recebimentos = recebimentos;
	}

	public BigDecimal getTotalPagamento() {
		return totalPagamento;
	}

	public void setTotalPagamento(BigDecimal totalPagamento) {
		this.totalPagamento = totalPagamento;
	}

	public BigDecimal getTotalRecebimento() {
		return totalRecebimento;
	}

	public void setTotalRecebimento(BigDecimal totalRecebimento) {
		this.totalRecebimento = totalRecebimento;
	}

	public void listar(ActionEvent eventA, ActionEvent eventB) {
		Date dataInicio = (Date) eventA.getComponent().getAttributes().get("dataInicio");
		Date dataFim = (Date) eventB.getComponent().getAttributes().get("dataFim");
		try {
			PagamentoDAO pagamentoDAO = new PagamentoDAO();
			pagamentos = pagamentoDAO.listar("data");
			RecebimentoDAO recebimentoDAO = new RecebimentoDAO();
			recebimentos = recebimentoDAO.listar("data");
			for (Pagamento pagamento : pagamentos) {
				if (pagamento.getData().before(dataInicio)) {
					totalPagamento = totalPagamento.add(pagamento.getValor());
				}
			}
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar valores.");
			e.printStackTrace();
		}
	}
}
