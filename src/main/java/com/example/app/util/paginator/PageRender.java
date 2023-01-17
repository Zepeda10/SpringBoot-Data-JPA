package com.example.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T>{
	private String url;
	private Page<T> page;
	private int totalPaginas;
	private int numElementosPorPagina;
	private int paginaActcual;
	private List<PageItem> paginas;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>();
		
		numElementosPorPagina = page.getSize();
		totalPaginas = page.getTotalPages();
		paginaActcual = page.getNumber() + 1;
		
		int desde, hasta;
		
		if(totalPaginas <= numElementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		}else {
			if(paginaActcual <= numElementosPorPagina/2) {
				desde = 1;
				hasta = numElementosPorPagina;
			}else if(paginaActcual >= totalPaginas - numElementosPorPagina/2) {
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			}else {
				desde = paginaActcual - numElementosPorPagina/2;
				hasta = numElementosPorPagina;
			}
		}
		
		for(int i = 0; i < hasta; i++) {
			paginas.add(new PageItem(desde + i, paginaActcual == desde + i));
		}
		
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getPaginaActcual() {
		return paginaActcual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
}
