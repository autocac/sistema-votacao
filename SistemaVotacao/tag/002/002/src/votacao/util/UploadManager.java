package votacao.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import votacao.exception.UploadException;

public class UploadManager {

	private HttpServletRequest request;
	
	private Map<String, Object> parametros = new HashMap<String, Object>();
	
	private boolean carregado = false;

	/**
	 * @param request
	 */
	public UploadManager(HttpServletRequest request) {
		this.request = request;
	}
	
	public Map<String, Object> getParametros() {
		return parametros;
	}

	public String getString(String key) {
		return (String)getList(key).get(0);
	}
	
	public Integer getInt(String key) {
		return Integer.valueOf((String)getList(key).get(0));
	}
	
	public List getList(String key) {
		return (List)getParametros().get(key);
	}
	
	public boolean isCarregado() {
		return carregado;
	}

	public void setCarregado(boolean carregado) {
		this.carregado = carregado;
	}

	public void uploadFormToMemory() throws UploadException {
		try {
			String fileName = ""; 
			
			ServletInputStream in = this.request.getInputStream();
			
			byte[] line = new byte[128];
			int i = in.readLine(line, 0, 128);
			int limTam = i - 2;
			
			if (limTam > 0) { 
				
				String limite = new String(line, 0, limTam);
				
				while (i != -1) {
					String newLine = new String(line, 0, i);
					if (newLine.startsWith("Content-Disposition: form-data; name=\"")) {
						int fieldPosNameIni = newLine.indexOf("name=\"");
						fieldPosNameIni += 6;
						int fieldPosNameFim = newLine.indexOf('"', fieldPosNameIni);
						String fieldName = newLine.substring(fieldPosNameIni, fieldPosNameFim);
						
						String s = new String(line, 0, i - 2);
						int pos = s.indexOf("filename=\"");
						boolean ehArquivo = (pos != -1);
						
						String contentType = "";
						
						if (ehArquivo) {
							String filePath = s.substring(pos + 10, s.length() - 1);
							pos = filePath.lastIndexOf("\\");
							if (pos != -1) {
								fileName = filePath.substring(pos + 1);
							} else {
								fileName = filePath;
							}
							
							//Este eh o conteudo
							i = in.readLine(line, 0, 128);
							
							String contentTypeLine = new String(line, 0, i);
							int posIniContentType = contentTypeLine.indexOf("Content-Type:") + 13;
							contentType = contentTypeLine.substring(posIniContentType).trim();
							
							i = in.readLine(line, 0, 128);
							
						}
						//Linha em branco
						i = in.readLine(line, 0, 128);
						
						ByteArrayOutputStream buffer = new ByteArrayOutputStream();
						newLine = new String(line, 0, i);
						
						while (i != -1 && !newLine.startsWith(limite)) {
							buffer.write(line, 0, i);
							i = in.readLine(line, 0, 128);
							newLine = new String(line, 0, i);
						}
						try {
							byte[] bytes = buffer.toByteArray();
							if (ehArquivo) {
								put(fieldName + "_file", fileName);
								put(fieldName + "_content_type", contentType);
								put(fieldName, bytes);
							} else {
								put(fieldName, new String(bytes).trim());
							}
							carregado = true; 
						} catch (Exception e) {
						}
						
					}
					i = in.readLine(line, 0, 128);
				}
			}
		} catch (IOException e) {
			throw new UploadException(e);
		}
	}

	/**
	 * @param fieldName
	 * @param value
	 */
	private void put(String fieldName, Object value) {
		Object obj = parametros.get(fieldName);
		if (obj != null) {
			ArrayList list = (ArrayList)obj;
			list.add(value);
		} else {
			ArrayList list = new ArrayList();
			list.add(value);
			parametros.put(fieldName, list);
		}
	}
	
	public void uploadFormToFileSystem(String savePath) {
		try {
			String fileName = ""; 
			
			ServletInputStream in = this.request.getInputStream();
			
			byte[] line = new byte[128];
			int i = in.readLine(line, 0, 128);
			int limTam = i - 2;
			String limite = new String(line, 0, limTam);
			
			while (i != -1) {
				String newLine = new String(line, 0, i);
				if (newLine.startsWith("Content-Disposition: form-data; name=\"")) {
					String s = new String(line, 0, i - 2);
					int pos = s.indexOf("filename=\"");
					if (pos != -1) {
						String filePath = s.substring(pos + 10, s.length() - 1);
						pos = filePath.lastIndexOf("\\");
						if (pos != -1) {
							fileName = filePath.substring(pos + 1);
						} else {
							fileName = filePath;
						}
						
						//Este eh o conteudo
						i = in.readLine(line, 0, 128);
						i = in.readLine(line, 0, 128);
						//Linha em branco
						i = in.readLine(line, 0, 128);
						
						ByteArrayOutputStream buffer = new ByteArrayOutputStream();
						newLine = new String(line, 0, i);
						
						while (i != -1 && !newLine.startsWith(limite)) {
							buffer.write(line, 0, i);
							i = in.readLine(line, 0, 128);
							newLine = new String(line, 0, i);
						}
						try {
							RandomAccessFile f = new RandomAccessFile(savePath + fileName, "rw");
							byte[] bytes = buffer.toByteArray();
							f.write(bytes, 0, bytes.length - 2);
							f.close();
						} catch (Exception e) {
						}
					}
				}
				i = in.readLine(line, 0, 128);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
