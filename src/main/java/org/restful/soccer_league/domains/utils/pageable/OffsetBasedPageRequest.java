package org.restful.soccer_league.domains.utils.pageable;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Objects;

public class OffsetBasedPageRequest implements Pageable, Serializable {

    private static final long serialVersionUID = -903477129613575L;

    private int limit;
    private int offset;
    private Sort sort;

    public OffsetBasedPageRequest(int limit, int offset, Sort.Direction direction, @Nullable String... paramSortBy) {
        if (limit < 0){
            throw new IllegalArgumentException("Limit must not be less than zero!");
        }

        if (offset < 0){
            throw new IllegalArgumentException("Offset must not be less than zero!");
        }

        this.limit = limit;
        this.offset = offset;

        if(direction.isAscending()){
            this.sort = Objects.isNull(paramSortBy) ? Sort.by("id").ascending() : Sort.by(paramSortBy).ascending();
        }else {
            this.sort = Objects.isNull(paramSortBy) ? Sort.by("id").descending() : Sort.by(paramSortBy).descending();
        }

    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest(getPageSize(), (int) (getOffset() + getPageSize()), Sort.Direction.ASC,"id");
    }

    public Pageable previous() {
        return hasPrevious() ?
                new OffsetBasedPageRequest(getPageSize(), (int) (getOffset() - getPageSize()), Sort.Direction.ASC,"id") : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(getPageSize(), 0, Sort.Direction.ASC,"id");
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

}
