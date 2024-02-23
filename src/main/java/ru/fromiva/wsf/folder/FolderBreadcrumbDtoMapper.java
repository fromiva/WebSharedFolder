package ru.fromiva.wsf.folder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/** Mapper to support {@code FolderBreadcrumbDto} data transfer object. */
@Component
@AllArgsConstructor
public class FolderBreadcrumbDtoMapper {

    /** Encoder and decoder a path from filename format to URL format and vice versa. */
    private final PathToUrlEncoder encoder;

    /**
     * Produces breadcrumb navigation item.
     * @param title  breadcrumb item name
     * @param url breadcrumb target domain-name relative URL
     * @return breadcrumb item
     */
    public FolderBreadcrumbDto toDto(final String title, final String url) {
        return new FolderBreadcrumbDto(title, url);
    }

    /**
     * Produces a list of related and organized breadcrumb navigation items.
     * @param url relative folder path encoded in URL format to generate a list of items
     * @return a list of items
     */
    public List<FolderBreadcrumbDto> toDtoList(final String url) {
        List<FolderBreadcrumbDto> result = new ArrayList<>();
        StringBuilder cursor = new StringBuilder();
        for (String part : url.split("/")) {
            cursor.append("/").append(part);
            result.add(toDto(encoder.decode(part), encoder.encode(cursor.toString())));
        }
        return result;
    }
}
