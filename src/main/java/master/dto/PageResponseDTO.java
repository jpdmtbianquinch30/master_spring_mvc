package master.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Enveloppe de pagination simple et stable a serialiser en JSON.
 * On evite de renvoyer directement l'objet Spring Data Page<T> : sa serialisation JSON
 * par defaut est verbeuse/instable selon les versions de Jackson/spring-data. On expose
 * ici uniquement les informations utiles a un client (contenu de la page + metadonnees).
 */
public class PageResponseDTO<T> {

    private List<T> content;
    private int pageNumber;   // page courante (0-based)
    private int pageSize;     // taille demandee
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;

    public PageResponseDTO() {
    }

    public PageResponseDTO(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.first = page.isFirst();
        this.last = page.isLast();
    }

    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }

    public int getPageNumber() { return pageNumber; }
    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public boolean isFirst() { return first; }
    public void setFirst(boolean first) { this.first = first; }

    public boolean isLast() { return last; }
    public void setLast(boolean last) { this.last = last; }
}
