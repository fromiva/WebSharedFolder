package ru.fromiva.wsf.folder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.fromiva.wsf.util.Encoder;

import static org.assertj.core.api.Assertions.assertThat;

class PathToUrlEncoderTest {

    /** Converter for Unix file systems to test. */
    private final Encoder<String, String> unixEncoder = new PathToUrlEncoder("/", "/");

    /** Converter for Windows file systems to test. */
    private final Encoder<String, String> windowsEncoder = new PathToUrlEncoder("\\", "/");

    @ParameterizedTest(name = "{index} Encode path: {0}")
    @CsvFileSource(resources = "PathToUrlEncoderUnixTest.csv", numLinesToSkip = 1)
    void unixEncode(final String path, final String url) {
        assertThat(unixEncoder.encode(path)).isEqualTo(url);
    }

    @ParameterizedTest(name = "{index} Decode URL: {1}")
    @CsvFileSource(resources = "PathToUrlEncoderUnixTest.csv", numLinesToSkip = 1)
    void unixDecode(final String path, final String url) {
        assertThat(unixEncoder.decode(url)).isEqualTo(path);
    }

    @ParameterizedTest(name = "{index} Check: {0}")
    @CsvFileSource(resources = "PathToUrlEncoderUnixTest.csv", numLinesToSkip = 1)
    void unixCheckContract(final String path) {
        assertThat(unixEncoder.decode(unixEncoder.encode(path)).equals(path)).isTrue();
    }

    @ParameterizedTest(name = "{index} Encode path: {0}")
    @CsvFileSource(resources = "PathToUrlEncoderWindowsTest.csv", numLinesToSkip = 1)
    void windowsEncode(final String path, final String url) {
        assertThat(windowsEncoder.encode(path)).isEqualTo(url);
    }

    @ParameterizedTest(name = "{index} Decode URL: {1}")
    @CsvFileSource(resources = "PathToUrlEncoderWindowsTest.csv", numLinesToSkip = 1)
    void windowsDecode(final String path, final String url) {
        assertThat(windowsEncoder.decode(url)).isEqualTo(path);
    }

    @ParameterizedTest(name = "{index} Check: {0}")
    @CsvFileSource(resources = "PathToUrlEncoderWindowsTest.csv", numLinesToSkip = 1)
    void windowsCheckContract(final String path) {
        assertThat(unixEncoder.decode(unixEncoder.encode(path)).equals(path)).isTrue();
    }
}
