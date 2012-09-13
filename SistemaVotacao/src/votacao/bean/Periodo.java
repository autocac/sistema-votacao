package votacao.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Periodo {
	private final Date dataInicio;
	private final Date dataFim;

	private final String padraoData;

	public Periodo(Date dataInicio, Date dataFim, String padraoData) {
		verificaNulidade(dataInicio, "Data de início não pode ser null");
		verificaNulidade(dataFim, "Data de fim não pode ser null");
		this.padraoData = padraoData;
		this.dataInicio = getDataNormalizada(dataInicio);
		this.dataFim = getDataNormalizada(dataFim);
		if (this.dataInicio.getTime() > this.dataFim.getTime()) {
			throw new IllegalArgumentException("início posterior ao fim");
		}
	}

	public Periodo(Date dataInicio, Date dataFim) {
		this(dataInicio, dataFim, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	private Date getDataNormalizada(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(this.padraoData);
		try {
			date = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
		}
		return date;
	}

	public Date getDataInicio() {
		return new Date(dataInicio.getTime());
	}

	public Date getDataFim() {
		return new Date(dataFim.getTime());
	}

	private void verificaNulidade(Date data, String mensagem) {
		if (data == null) {
			throw new IllegalArgumentException(mensagem);
		}
	}

	public boolean dentroDoPeriodo(Date data) {

		verificaNulidade(data, "Data verificada não pode ser null");
		data = getDataNormalizada(data);

		return this.dataInicio.getTime() <= data.getTime()
				&& this.dataFim.getTime() >= data.getTime();
	}

	public long getDuracao() {
		long dif = this.dataFim.getTime() - this.dataInicio.getTime();
		return (dif / 1000) / 60;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat(this.padraoData);
		String strDataInicio = sdf.format(dataInicio);
		String strDataFim = sdf.format(dataFim);
		return "Periodo [dataInicio=" + strDataInicio + ", dataFim="
				+ strDataFim + "]";
	}
}