package br.com.financeiro.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.financeiro.dao.ParcelaDAO;
import br.com.financeiro.domain.Parcela;
import br.com.financeiro.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ParcelaBean implements Serializable {

	private Parcela parcela;
	private List<Parcela> parcelas;

	public Parcela getParcela() {
		return parcela;
	}

	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}

	public List<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}

	public void novo() {
		parcela = new Parcela();
	}

	@PostConstruct
	public void listar() {
		try {
			ParcelaDAO parcelaDAO = new ParcelaDAO();
			parcelas = parcelaDAO.listar("id");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar.");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ParcelaDAO parcelaDAO = new ParcelaDAO();
			parcelaDAO.salvar(parcela);
			parcelas = parcelaDAO.listar("id");
			novo();
			Messages.addGlobalInfo("Registro salvo com sucesso.");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorre um erro ao salvar.");
			e.printStackTrace();
		}
	}

	public void excluir(ActionEvent event) {
		parcela = (Parcela) event.getComponent().getAttributes().get("parcelaSelecionada");
		try {
			ParcelaDAO parcelaDAO = new ParcelaDAO();
			parcelaDAO.excluir(parcela);
			parcelas = parcelaDAO.listar("id");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu ao excluir o registro.");
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent event) {
		parcela = (Parcela) event.getComponent().getAttributes().get("parcelaSelecionada");
	}

	public void imprimir() {
		try {
			String caminho = Faces.getRealPath("/reports/parcelas.jasper");
			Map<String, Object> parametros = new HashMap<>();
			Connection conexao = HibernateUtil.getConnection();
			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException e) {
			Messages.addGlobalError("Ocorreu um erro ao gerar o relatório.");
			e.printStackTrace();
		}
	}
}
