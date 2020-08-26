package pl.com.ttsw.intership.wholesaleserverapp.rest.pageable;


import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

public class PageHelper {

    private PageHelper() {
    }

    public static Object preparePage(List<?> list, Pageable pageable) {
        final int start = pageable.getPageNumber() * pageable.getPageSize();
        final int end = Math.min(start + pageable.getPageSize(), list.size());
        try {
            return new PageImpl<>(list.subList(start, end), pageable, list.size());
        } catch (IllegalArgumentException err) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
    }
}
