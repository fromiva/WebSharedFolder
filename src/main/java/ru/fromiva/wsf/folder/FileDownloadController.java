package ru.fromiva.wsf.folder;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.fromiva.wsf.app.util.ElementNotFoundException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Controller
@RequestMapping("/download")
@AllArgsConstructor
public class FileDownloadController {

    /** File download specific service. */
    private final FileDownloadService fileDownloadService;

    /**
     * Retrieves file, it contents, and properties to handle download requests.
     * @param rootAlias the {@code RootFolder} alias to retrieve relative file
     * @param relative the file path relative to the {@code rootAlias}
     * @return HTTP response entity, with headers and body of the specified file
     */
    @GetMapping("/{rootAlias}/{*relative}")
    @ResponseBody
    public ResponseEntity<Resource> download(@PathVariable final String rootAlias,
                                             @PathVariable final String relative) {
        try {
            FileContentDto dto = fileDownloadService.getContent(rootAlias, relative.substring(1));
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                    .filename(dto.filename(), UTF_8).build().toString());
            headers.add(HttpHeaders.CONTENT_TYPE, dto.mediaType());
            return new ResponseEntity<>(dto.resource(), headers, HttpStatus.OK);
        } catch (ElementNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
