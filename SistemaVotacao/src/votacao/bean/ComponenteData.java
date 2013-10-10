package votacao.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ComponenteData {

	public enum Tipo {
		
		ANO(Calendar.YEAR, 4) {
			//Em casos genéricos seria melhor
			//tratar a montagem do combo de ano 
			//de forma especial, mas como estou com 
			//pres...
			@Override
			public int getMax() {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				return cal.get(idCampoData) + 10;
			}
			
			@Override
			public int getMin() {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				return cal.get(idCampoData);
			}
		},
		MES(Calendar.MONTH, 2) {
			@Override
			public int getValor(Date data) {
				return super.getValor(data) + 1;
			}
			
			@Override
			public int getMax() {
				return super.getMax() + 1;
			}
			
			@Override
			public int getMin() {
				return super.getMin() + 1;
			}
		},
		DIA(Calendar.DAY_OF_MONTH, 2),
		HORA(Calendar.HOUR_OF_DAY, 2),
		MINUTO(Calendar.MINUTE, 2),
		SEGUNDO(Calendar.SECOND, 2);
		
		int idCampoData;
		int numDigitos;
		Tipo(int idCampoData, int numDigitos) {
			this.idCampoData = idCampoData;
			this.numDigitos = numDigitos;
		}
		
		public int getValor(Date data) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			return cal.get(idCampoData);
		}
		
		public void setValor(Date data, int valor) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			cal.set(idCampoData, valor);
		}
		
		public int getMax() {
			Calendar cal = Calendar.getInstance();
			return cal.getMaximum(idCampoData);
		}
		
		public int getMin() {
			Calendar cal = Calendar.getInstance();
			return cal.getMinimum(idCampoData);
		}

		public int getNumDigitos() {
			return numDigitos;
		}
	}
	private Tipo tipo;
	private Date data;

	public ComponenteData(Tipo tipo, Date data) {
		this.tipo = tipo;
		this.data = data;
	}

	public Tipo getTipo() {
		return tipo;
	}
	
	public int getValor() {
		return tipo.getValor(data);
	}
	

	
	public static List<ComponenteData> getComponentes(Date data) {
		List<ComponenteData> componentes = new ArrayList<ComponenteData>();
		for (Tipo t : Tipo.values()) {
			componentes.add(new ComponenteData(t, data));
		}
		return componentes;
	}
	
	
	
	@Override
	public String toString() {
		return "ComponenteData [tipo=" + tipo.name() + 
				", valor=" + getValor() + 
				", max=" + tipo.getMax() + 
				", min=" + tipo.getMin() + "]";
	}

	public static void main(String[] args) {
		List<ComponenteData> componentes = ComponenteData.getComponentes(new Date());
		for(ComponenteData c : componentes) {
			System.out.println(c);
		}
		
		
	}
}
