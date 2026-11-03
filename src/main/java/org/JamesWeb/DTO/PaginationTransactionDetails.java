package org.JamesWeb.DTO;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PaginationTransactionDetails<T> {

    private List<T> content;
    private Integer number;
    private Integer size;
    private long totalElements;
    private Integer totalPages;
    private boolean first;
    private boolean last;
    private boolean hasNext;
    private boolean hasPrevious;

    public PaginationTransactionDetails() {
    }

    public PaginationTransactionDetails(List<T> content,
                                        Integer number,
                                        Integer size,
                                        long totalElements,
                                        Integer totalPages,
                                        boolean first,
                                        boolean last,
                                        boolean hasNext,
                                        boolean hasPrevious) {
        this.content = content;
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    public static <T> PaginationTransactionDetails<T> from(Page<T> page) {
        return new PaginationTransactionDetails<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
}